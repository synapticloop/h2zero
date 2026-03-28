package com.synapticloop.h2zero.revenge.model;

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

import com.synapticloop.h2zero.generator.model.util.JSONKeyConstants;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Represents a database table and its associated metadata extracted from a
 * database schema.</p>
 *
 * <p>This class handles the discovery of primary keys, indices, columns, and
 * foreign keys for a specific table using JDBC DatabaseMetaData.</p>
 */
public class Table {
	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
	public static final String PKTABLE_NAME = "PKTABLE_NAME";
	public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";

	private String name = null;
	private final List<Column> columns = new ArrayList<>();

	private static final List<String> SQL_INTERACTION_OBJECTS = new ArrayList<>();
	static {
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FIND_ALL_ORDERED);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FIELD_FINDERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FINDERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.UPDATERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FIELD_UPDATERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.INSERTERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.DELETERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FIELD_DELETERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.COUNTERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FIELD_COUNTERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.QUESTIONS);
	}

	/**
	 * <p>Constructs a new Table object by extracting metadata from the database.</p>
	 *
	 * @param metaData the database metadata provider
	 * @param tableSchema the schema containing the table
	 * @param tableName the name of the table to process
	 *
	 * @throws SQLException if a database access error occurs or if no primary key
	 *     is found
	 */
	public Table(DatabaseMetaData metaData, String tableSchema, String tableName) throws SQLException {
		this.name = tableName;

		// First, get primary keys
		Set<String> primaryKeys = new HashSet<>();
		try (ResultSet rs = metaData.getPrimaryKeys(null, tableSchema, tableName)) {
			while (rs.next()) {
				primaryKeys.add(rs.getString(COLUMN_NAME));
			}
		}

		if (primaryKeys.isEmpty()) {
			throw new SQLException("Table '" +
					tableName +
					"' does not have a primary key - This table will __NOT__ be generated.");
		}

		// Second, get indices
		Set<String> indexedColumns = new HashSet<>();
		try (ResultSet rs = metaData.getIndexInfo(null, tableSchema, tableName, false, false)) {
			while (rs.next()) {
				String columnName = rs.getString(COLUMN_NAME);
				if (columnName != null) {
					indexedColumns.add(columnName);
				}
			}
		}

		// Third, get columns
		try (ResultSet rs = metaData.getColumns(null, tableSchema, tableName, "%")) {
			while (rs.next()) {
				Column column = new Column(rs);
				String columnName = column.getName();
				if (primaryKeys.contains(columnName)) {
					column.setIsPrimary(true);
				}
				if (indexedColumns.contains(columnName)) {
					column.setIsIndexed(true);
				}
				columns.add(column);
			}
		}

		// Fourth, get foreign keys
		try (ResultSet rs = metaData.getImportedKeys(null, tableSchema, tableName)) {
			while (rs.next()) {
				String columnName = rs.getString(FKCOLUMN_NAME);
				String pkTableName = rs.getString(PKTABLE_NAME);
				String pkColumnName = rs.getString(PKCOLUMN_NAME);

				for (Column column : columns) {
					if (column.getName().equals(columnName)) {
						column.setForeignKeyTable(pkTableName);
						column.setForeignKeyColumn(pkColumnName);
						break;
					}
				}
			}
		}
	}

	/**
	 * <p>Converts the table and its columns into a JSON formatted string
	 * representation for h2zero.</p>
	 *
	 * @return a JSON string representation of the table
	 */
	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("      {\n")
				.append("        \"name\": \"")
				.append(name)
				.append("\",\n")
				.append("        \"fields\": [");

		int i = 0;
		for (Column column : columns) {
			if(i != 0) {
				stringBuilder.append(",");
			}
			i++;
			stringBuilder
					.append("\n")
					.append(column.toJsonString());
		}
		stringBuilder.append("\n        ]");
		for (String sqlInteractionObject : SQL_INTERACTION_OBJECTS) {
			stringBuilder
					.append(",\n        \"")
					.append(sqlInteractionObject)
					.append("\": [\n        ]");
		}
		stringBuilder
				.append("\n")
				.append("      }");
		return (stringBuilder.toString());
	}

	/**
	 * <p>Gets the name of the table.</p>
	 *
	 * @return the table name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Gets the list of columns for this table.</p>
	 *
	 * @return the list of columns
	 */
	public List<Column> getColumns() {
		return columns;
	}

	/**
	 * <p>Retrieves a set of table names that this table references via foreign
	 * keys.</p>
	 *
	 * <p>Self-references are excluded from this set.</p>
	 *
	 * @return a set of referenced table names
	 */
	public Set<String> getReferencedTableNames() {
		Set<String> referencedTables = new HashSet<>();
		for (Column column : columns) {
			if (column.hasForeignKey()) {
				String fkTable = column.getForeignKeyTable();
				if (!fkTable.equalsIgnoreCase(this.name)) {
					referencedTables.add(fkTable);
				}
			}
		}
		return referencedTables;
	}
}
