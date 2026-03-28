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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Represents a database column and its associated metadata extracted from a
 * database schema.</p>
 */
public class Column {
	private static final Set<String> LENGTH_DATA_TYPES = new HashSet<>();
	static {
		// Length Data Types (Base list)
		LENGTH_DATA_TYPES.add("VARCHAR");
		LENGTH_DATA_TYPES.add("NVARCHAR");
		LENGTH_DATA_TYPES.add("TINYINT"); // Historically requires display width in MySQL
		LENGTH_DATA_TYPES.add("CHAR");
		LENGTH_DATA_TYPES.add("BINARY");
		LENGTH_DATA_TYPES.add("VARBINARY");

		// PostgreSQL & CockroachDB specific types/aliases
		LENGTH_DATA_TYPES.add("CHARACTER VARYING");
		LENGTH_DATA_TYPES.add("CHARACTER");
		LENGTH_DATA_TYPES.add("BIT");
		LENGTH_DATA_TYPES.add("BIT VARYING");
		LENGTH_DATA_TYPES.add("STRING"); // CockroachDB supports STRING(N)

		// SQL Server & SQLite specific types/aliases
		LENGTH_DATA_TYPES.add("NCHAR");
		LENGTH_DATA_TYPES.add("VARYING CHARACTER"); // Common SQLite alias
	}

	private static final Set<String> FLOATING_POINT_DATA_TYPES = new HashSet<>();
	static {
		// Standard SQL Types (Base list)
		FLOATING_POINT_DATA_TYPES.add("DECIMAL");
		FLOATING_POINT_DATA_TYPES.add("NUMERIC");
		FLOATING_POINT_DATA_TYPES.add("FLOAT");
		FLOATING_POINT_DATA_TYPES.add("DOUBLE");
		FLOATING_POINT_DATA_TYPES.add("REAL");

		// PostgreSQL & CockroachDB specific types/aliases
		FLOATING_POINT_DATA_TYPES.add("DOUBLE PRECISION");
		FLOATING_POINT_DATA_TYPES.add("FLOAT4");
		FLOATING_POINT_DATA_TYPES.add("FLOAT8");

		// MySQL & MariaDB specific aliases
		FLOATING_POINT_DATA_TYPES.add("DEC");
		FLOATING_POINT_DATA_TYPES.add("FIXED");

		// SQL Server specific exact numerics
		FLOATING_POINT_DATA_TYPES.add("MONEY");
		FLOATING_POINT_DATA_TYPES.add("SMALLMONEY");

		// Note: SQLite uses REAL, FLOAT, and DOUBLE, which are already covered by the standard list.
	}

	private static final String RS_COLUMN_NAME = "COLUMN_NAME";
	private static final String RS_TYPE_NAME = "TYPE_NAME";
	private static final String RS_COLUMN_SIZE = "COLUMN_SIZE";
	private static final String RS_IS_NULLABLE = "IS_NULLABLE";
	private static final String RS_DECIMAL_DIGITS = "DECIMAL_DIGITS";

	private static final String JSON_NAME = "\"name\": \"";
	private static final String JSON_TYPE = "\", \"type\": \"";
	private static final String JSON_LENGTH = ", \"length\": ";
	private static final String JSON_DECIMAL_LENGTH = ", \"decimalLength\": ";
	private static final String JSON_NULLABLE = ", \"nullable\": ";
	private static final String JSON_PRIMARY = ", \"primary\": ";
	private static final String JSON_INDEX = ", \"index\": ";
	private static final String JSON_UNIQUE = ", \"unique\": ";
	private static final String JSON_FOREIGN_KEY = ", \"foreignKey\": \"";

	private String name = null;
	private String dataType = null;
	private boolean isNullable = false;
	private boolean isPrimary = false;

	private Integer length = null;
	private Integer decimalPlaces = null;

	private boolean hasLength = false;
	private boolean isFloatingPoint = false;

	private String foreignKeyTable = null;
	private String foreignKeyColumn = null;

	private boolean isIndexed = false;
	private boolean isUnique = false;

	/**
	 * <p>Instantiates a new Column object by parsing a row from a standard JDBC
	 * DatabaseMetaData getColumns ResultSet.</p>
	 *
	 * @param resultSet the JDBC result set containing the column metadata
	 *
	 * @throws SQLException if there is an issue extracting values from the ResultSet
	 */
	public Column(ResultSet resultSet) throws SQLException {
		this.name = resultSet.getString(RS_COLUMN_NAME);
		this.dataType = cleanType(resultSet.getString(RS_TYPE_NAME));
		String upperDataType = dataType.toUpperCase();
		this.hasLength = LENGTH_DATA_TYPES.contains(upperDataType);
		this.isFloatingPoint = FLOATING_POINT_DATA_TYPES.contains(upperDataType);

		if(this.hasLength || this.isFloatingPoint) {
			this.length = resultSet.getInt(RS_COLUMN_SIZE);
			if ("NVARCHAR".equalsIgnoreCase(this.dataType) && (this.length == null || this.length == -1)) {
				System.err.println("[WARNING] Found nvarchar column '" + name + "' with length -1, setting to 4000.");
				System.err.println("[WARNING]     You may want to change this to a CLOB datatype");
				this.length = 4000;
			}
		}

		if (this.isFloatingPoint) {
			this.decimalPlaces = resultSet.getInt(RS_DECIMAL_DIGITS);
		}

		this.isNullable = "YES".equals(resultSet.getString(RS_IS_NULLABLE));
	}

	/**
	 * <p>Removes the 'identity' keyword and all non-alphanumeric characters from a field type string.</p>
	 *
	 * <p>For example, an input of "int identity" will return "int", and "varchar(255)"
	 * will return "varchar". This ensures that the resulting string is a
	 * flattened alpha representation.</p>
	 *
	 * @param fieldType the raw string containing the database field type and properties
	 *
	 * @return a sanitised string with 'identity' removed and non-alphanumeric characters stripped
	 */
	private static String cleanType(String fieldType) {
		if (fieldType == null) {
			return null;
		}

		// Step 1: Remove the word 'identity' using a case-insensitive regex
		// The (?i) flag makes the match case-insensitive
		String result = fieldType.replaceAll("(?i)identity", "");

		// Step 2: Remove any character that is not a letter or a digit
		// [^a-zA-Z0-9] matches any character NOT in the alphanumeric range
		result = result.replaceAll("[^a-zA-Z]", "");

		return result;
	}

	/**
	 * <p>Converts the column properties into a JSON formatted string
	 * representation.</p>
	 *
	 * @return a JSON string representation of the column metadata
	 */
	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("        { ")
				.append(JSON_NAME)
				.append(name)
				.append(JSON_TYPE)
				.append(dataType)
				.append("\"");

		if("tinyint".equalsIgnoreCase(this.dataType)) {
			stringBuilder.append(", \"length\": \"1\"");
		} else if(null != length && length.intValue() != 0) {
			if (isFloatingPoint && decimalPlaces != null && decimalPlaces > 0) {
				stringBuilder
						.append(JSON_LENGTH)
						.append(length)
						.append(JSON_DECIMAL_LENGTH)
						.append(decimalPlaces);
			} else {
				stringBuilder
						.append(JSON_LENGTH)
						.append(length);
			}
		}

		stringBuilder
				.append(JSON_NULLABLE)
				.append(isNullable);

		if(isPrimary) {
			stringBuilder
					.append(JSON_PRIMARY)
					.append(isPrimary);
		}

		if(getIsIndexed()) {
			stringBuilder
					.append(JSON_INDEX)
					.append(getIsIndexed());
		}

		if(getIsUnique() && !isPrimary) {
			stringBuilder
					.append(JSON_UNIQUE)
					.append(getIsUnique());
		}

		if(hasForeignKey()) {
			stringBuilder
					.append(JSON_FOREIGN_KEY)
					.append(foreignKeyTable)
					.append(".")
					.append(foreignKeyColumn)
					.append("\"");
		}

		stringBuilder.append(" }");
		return (stringBuilder.toString());
	}

	/**
	 * <p>Retrieves the name of the column.</p>
	 * @return the name of the column
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Retrieves the name of the table referenced by this foreign key column.</p>
	 *
	 * @return the foreign key table name, or null if not a foreign key
	 */
	public String getForeignKeyTable() {
		return this.foreignKeyTable;
	}

	/**
	 * <p>Sets the name of the table referenced by this foreign key column.</p>
	 *
	 * @param foreignKeyTable the name of the referenced table
	 */
	public void setForeignKeyTable(String foreignKeyTable) {
		this.foreignKeyTable = foreignKeyTable;
	}

	/**
	 * <p>Retrieves the name of the specific column referenced by this foreign key.</p>
	 * @return the referenced column name, or null if not a foreign key
	 */
	public String getForeignKeyColumn() {
		return this.foreignKeyColumn;
	}

	/**
	 * <p>Sets the name of the specific column referenced by this foreign key.</p>
	 * @param foreignKeyColumn the name of the referenced column
	 */
	public void setForeignKeyColumn(String foreignKeyColumn) {
		this.foreignKeyColumn = foreignKeyColumn;
	}

	/**
	 * <p>Determines if this column acts as a foreign key based on table and
	 * column populated values.</p>
	 *
	 * @return true if both the foreign key table and column are not null, false
	 *     otherwise
	 */
	public boolean hasForeignKey() {
		return(null != foreignKeyTable && null != foreignKeyColumn);
	}

	/**
	 * <p>Determines if this column is indexed, either explicitly or implicitly
	 * (via primary or foreign key).</p>
	 *
	 * @return true if the column has an index, false otherwise
	 */
	public boolean getIsIndexed() {
		return(isPrimary || hasForeignKey() || isIndexed);
	}

	/**
	 * <p>Sets whether this column has a standard index.</p>
	 *
	 * @param isIndexed true to mark as indexed, false otherwise
	 */
	public void setIsIndexed(boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	/**
	 * <p>Sets whether the values in this column must be unique.</p>
	 *
	 * @param isUnique true to mark as unique, false otherwise
	 */
	public void setIsUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	/**
	 * <p>Determines if the column requires unique values.</p>
	 *
	 * @return true if the column is unique, false otherwise
	 */
	public boolean getIsUnique() {
		return(isUnique);
	}

	/**
	 * <p>Sets whether this column is part of the table's primary key.</p>
	 *
	 * @param isPrimary true to mark as primary key, false otherwise
	 */
	public void setIsPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
}
