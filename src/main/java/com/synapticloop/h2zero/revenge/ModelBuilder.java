package com.synapticloop.h2zero.revenge;

/*
 * Copyright (c) 2013-2026 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import com.synapticloop.h2zero.revenge.datastructure.TableGraph;
import com.synapticloop.h2zero.revenge.model.Options;
import com.synapticloop.h2zero.revenge.model.Table;

import java.sql.*;
import java.util.*;

/**
 * <p>The ModelBuilder class is responsible for orchestrating the extraction of
 * database metadata and transforming it into a structured h2zero model.</p>
 *
 * <p>It handles the discovery of tables within a specific schema, orders them
 * based on foreign key dependencies, and generates the final JSON
 * representation.</p>
 */
public class ModelBuilder {
	private static final String SQL_TABLE_NAME = "TABLE_NAME";
	private static final String SQL_TABLE_SCHEMA = "TABLE_SCHEM";
	private static final String SQL_TABLE_CATALOG = "TABLE_CAT";

	private Options options;
	private List<Table> tables = new ArrayList<Table>();
	private boolean hasFoundCircularDependency = false;

	private final String jdbcString;
	private final String username;
	private final String password;
	private final String schema;

	private String databaseType = "unknown";
	private String databaseName = "unknown";
	private String packageName = "unknown";

	/**
	 * <p>Constructs a new ModelBuilder and initializes the reverse-engineering
	 * process.</p>
	 *
	 * @param jdbcString the JDBC connection string
	 * @param databaseType the type of the database (e.g., mysql, postgresql)
	 * @param databaseName the name of the database
	 * @param username the database username
	 * @param password the database password
	 * @param schema the target schema or catalog to reverse-engineer
	 *
	 * @throws ClassNotFoundException if the JDBC driver cannot be loaded
	 * @throws SQLException if a database access error occurs
	 */
	public ModelBuilder(
			String jdbcString,
			String databaseType,
			String databaseName,
			String username,
			String password,
			String schema) throws ClassNotFoundException,	SQLException {

		this.jdbcString = jdbcString;
		this.username = username;
		this.password = password;
		this.databaseType = "sqlite".equalsIgnoreCase(databaseType) ? "sqlite3" : databaseType;
		this.databaseName = databaseName;
		this.schema = schema;

		this.options = new Options(this.databaseType);

		packageName = "change.me.package.name.h2zero." + this.databaseType + "." + databaseName.toLowerCase();

		populateTables();
		orderTables();
	}

	/**
	 * <p>Connects to the database and populates the list of tables found within the target schema.</p>
	 *
	 * @throws SQLException if a database access error occurs
	 */
	private void populateTables() throws SQLException {
		Connection connection = DriverManager.getConnection(jdbcString, username, password);
		DatabaseMetaData metaData = connection.getMetaData();

		String[] types = {"TABLE"};

		// MySQL and MariaDB use Catalogs, others use Schemas.
		String catalogName = null;
		String schemaNamePattern = null;

		String dbProduct = metaData.getDatabaseProductName().toLowerCase();
		if (dbProduct.contains("mysql") || dbProduct.contains("mariadb")) {
			catalogName = schema;
		} else {
			schemaNamePattern = schema;
		}

		try (ResultSet resultSet = metaData.getTables(catalogName, schemaNamePattern, "%", types)) {
			while (resultSet.next()) {
				String tableName = resultSet.getString(SQL_TABLE_NAME);
				String tableSchema = resultSet.getString(SQL_TABLE_SCHEMA);
				String tableCatalog = resultSet.getString(SQL_TABLE_CATALOG);

				// Double check that we only include tables for the selected schema/catalog.
				// Some drivers might be lenient with the getTables filters.
				if (catalogName != null && !catalogName.equalsIgnoreCase(tableCatalog)) {
					continue;
				}
				if (schemaNamePattern != null && tableSchema != null && !schemaNamePattern.equalsIgnoreCase(tableSchema)) {
					continue;
				}

				try {
					tables.add(new Table(metaData, tableSchema != null ? tableSchema : tableCatalog, tableName));
				} catch (SQLException e) {
					System.err.println("[  ERROR ] " + e.getMessage());
				}
			}
		}
	}

	/**
	 * <p>Orders the discovered tables based on their foreign key dependencies using a TableGraph.</p>
	 *
	 * <p>This implementation handles recursive traversal from parent to child.</p>
	 */
	private void orderTables() {
		System.out.println("[   INFO ] Determining table generation order...");
		TableGraph graph = new TableGraph();
		graph.addTables(this.tables);
		this.tables = graph.generateOrder();
		this.hasFoundCircularDependency = graph.hasFoundCircularDependency();

		System.out.println("[   INFO ] Final table generation order:");
		for (int i = 0; i < tables.size(); i++) {
			System.out.printf("[   INFO ]   %d. %s%n", i + 1, tables.get(i).getName());
		}
	}

	/**
	 * <p>Generates the h2zero JSON configuration string for the entire database
	 * model.</p>
	 *
	 * @return a formatted JSON string representing the database schema
	 */
	public String generate() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("{\n")
				.append(options.toJsonString())
				.append("  \"database\": {\n")
				.append("    \"schema\": \"")
				.append(databaseName)
				.append("\",\n")
				.append("    \"package\": \"")
				.append(packageName)
				.append("\",\n");

		stringBuilder.append("    \"tables\": [\n");

		// add in all of the tables
		int i = 0;
		for (Table table : tables) {
			if(i != 0) {
				stringBuilder.append(",\n");
			}
			i++;
			stringBuilder.append(table.toJsonString());
		}
		stringBuilder.append("\n  ]\n")
				.append("  }\n")
				.append("}\n");

		return (stringBuilder.toString());
	}

	/**
	 * <p>Gets the database name.</p>
	 *
	 * @return the database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * <p>Gets the list of discovered tables.</p>
	 *
	 * @return the list of tables
	 */
	public List<Table> getTables() {
		return tables;
	}

	/**
	 * <p>Returns whether any circular dependencies were found during the ordering process.</p>
	 *
	 * @return true if a circular dependency was found, false otherwise
	 */
	public boolean hasFoundCircularDependency() {
		return hasFoundCircularDependency;
	}
}
