package com.synapticloop.h2zero.revenge;

import com.synapticloop.h2zero.revenge.model.Options;
import com.synapticloop.h2zero.revenge.model.Table;

import java.sql.*;
import java.util.*;

public class ModelBuilder {
	private Options options;
	private List<Table> tables = new ArrayList<Table>();

	private final String jdbcString;
	private final String username;
	private final String password;
	private final String schema;

	private String databaseType = "unknown";
	private String databaseName = "unknown";
	private String packageName = "unknown";

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

	private void populateTables() throws SQLException {
		Connection connection = DriverManager.getConnection(jdbcString, username, password);
		DatabaseMetaData metaData = connection.getMetaData();

		String[] types = {"TABLE"};

		try (ResultSet resultSet = metaData.getTables(null, schema, "%", types)) {
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String tableSchema = resultSet.getString("TABLE_SCHEM");
				String tableType = resultSet.getString("TABLE_TYPE");

				try {
					tables.add(new Table(metaData, tableSchema, tableName));
				} catch (SQLException e) {
					System.err.println("[  ERROR ] " + e.getMessage());
				}
			}
		}
	}

	private void orderTables() {
		List<Table> orderedTables = new ArrayList<>();
		Set<String> addedTableNames = new HashSet<>();
		List<Table> remainingTables = new ArrayList<>(tables);

		boolean added;
		do {
			added = false;
			Iterator<Table> iterator = remainingTables.iterator();
			while (iterator.hasNext()) {
				Table table = iterator.next();
				Set<String> referencedTables = table.getReferencedTableNames();
				
				// A table can be added if all its referenced tables have already been added
				boolean canAdd = true;
				for (String referencedTable : referencedTables) {
					// We only care if the referenced table exists in our list of tables to be processed
					boolean existsInOriginalList = false;
					for (Table t : tables) {
						if (t.getName().equalsIgnoreCase(referencedTable)) {
							existsInOriginalList = true;
							break;
						}
					}

					if (existsInOriginalList && !addedTableNames.contains(referencedTable.toLowerCase())) {
						canAdd = false;
						break;
					}
				}

				if (canAdd) {
					orderedTables.add(table);
					addedTableNames.add(table.getName().toLowerCase());
					iterator.remove();
					added = true;
				}
			}
		} while (added && !remainingTables.isEmpty());

		if (!remainingTables.isEmpty()) {
			System.err.println("[   WARN ] Circular dependency or missing tables detected. Adding remaining tables in original order.");
			orderedTables.addAll(remainingTables);
		}

		this.tables = orderedTables;

		System.out.println("[   INFO ] Ordered tables for generation:");
		for (int i = 0; i < tables.size(); i++) {
			System.out.printf("[   INFO ] %4d. %s%n", i + 1, tables.get(i).getName());
		}
	}

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

	public String getDatabaseName() {
		return databaseName;
	}

	public List<Table> getTables() {
		return tables;
	}
}
