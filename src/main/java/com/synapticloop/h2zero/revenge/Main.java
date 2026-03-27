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

import com.synapticloop.h2zero.generator.model.Options;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>The main entry point for the h2zero revenge tool, which reverse-engineers
 * a database schema into an h2zero JSON configuration file.</p>
 *
 * <p>This class handles user interaction, JDBC connection string parsing,
 * schema discovery, and file generation.</p>
 *
 * @author synapticloop
 */
public class Main {
	private static final Pattern PATTERN_JDBC_CONNECTION = Pattern.compile("jdbc:(postgresql|mysql|mariadb|sqlite|sqlserver):(?:(?://[^/;/]+(?:[:/])?)|(?:))(.*)");
	private static final Pattern PATTERN_DB_NAME = Pattern.compile("databaseName=([^;]+)");
	private static final String DB_SQLITE = "sqlite";
	private static final String DB_SQLITE_3 = "sqlite3";
	private static final String DB_SQLSERVER = "sqlserver";
	private static final String DB_MYSQL = "mysql";
	private static final String DB_MARIADB = "mariadb";
	private static final String DB_POSTGRESQL = "postgresql";
	private static final String DB_COCKROACH = "cockroach";
	private static final String DB_MICROSOFT = "microsoft";

	private static final String RS_TABLE_CATALOG = "TABLE_CAT";
	private static final String RS_TABLE_SCHEMA = "TABLE_SCHEM";

	private static final String LOG_INFO = "[   INFO ] ";
	private static final String LOG_ERROR = "[  ERROR ] ";
	private static final String LOG_SELECT = "[ SELECT ] ";
	private static final String LOG_PROMPT = "[ PROMPT ] ";
	private static final String LOG_REPLY = "[  REPLY ] ";
	private static final String LOG_PARSE = "[  PARSE ] ";
	private static final String LOG_OUTPUT = "[ OUTPUT ] ";

	public static String databaseName;
	public static String databaseType;

	/**
	 * <p>Asks the user for input via the standard console using a Scanner.</p>
	 *
	 * <p>Note: Masking is only supported if System.console() is available;
	 * otherwise, it falls back to plain text input via Scanner.</p>
	 *
	 * @param prompt the prompt message to display to the user
	 * @param isMasked whether the input should be hidden (password style)
	 * @param defaultValue the value to return if the user provides empty input
	 *
	 * @return the string entered by the user, or the defaultValue if empty
	 */
	public static String askForInput(String prompt, boolean isMasked, String defaultValue) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		if(null != defaultValue) {
			prompt = prompt.trim();
		}

		String displayPrompt = LOG_PROMPT + prompt +
				(null != defaultValue ? "\n           (h2zero thinks '" + defaultValue + "' enter to accept)\n" : "") +
				LOG_REPLY;

		System.out.print(displayPrompt);

		String line = null;

		// Attempt to use System.console() for masking if requested
		if (isMasked && System.console() != null) {
			char[] passwordChars = System.console().readPassword();
			if (passwordChars != null) {
				line = new String(passwordChars);
			}
		} else {
			// Fallback to standard Scanner for non-masked or when Console is unavailable
			if (scanner.hasNextLine()) {
				line = scanner.nextLine();
			}
		}

		if ((line == null || line.trim().isEmpty()) && defaultValue != null) {
			return defaultValue;
		}

		return line;
	}

	/**
	 * <p>Parses the provided JDBC connection string to extract the database type
	 * and name.</p>
	 *
	 * <p>This method also provides a visual representation of the parsing results
	 * in the console.</p>
	 *
	 * @param jdbcString the JDBC connection string to parse
	 *
	 * @return true if the string was successfully parsed, false otherwise
	 */
	private static boolean parseJdbcString(String jdbcString) {
		if (jdbcString == null) return false;

		// PostgreSQL: jdbc:postgresql://host:port/database
		// MySQL: jdbc:mysql://host:port/database
		// MariaDB: jdbc:mariadb://host:port/database
		// SQLite: jdbc:sqlite:path/to/database.db
		// MSSQL: jdbc:sqlserver://[host[\instanceName][:port]][;property=value[;property=value]]

		Matcher matcher = PATTERN_JDBC_CONNECTION.matcher(jdbcString);

		if (matcher.find()) {
			databaseType = matcher.group(1);
			int typeStart = matcher.start(1);
			int typeEnd = matcher.end(1);

			String remainder = matcher.group(2);
			int dbStart = -1;
			int dbEnd = -1;

			if (DB_SQLITE.equals(databaseType)) {
				databaseType = DB_SQLITE_3;
				databaseName = remainder;
				dbStart = matcher.start(2);
				dbEnd = matcher.end(2);
			} else if (DB_SQLSERVER.equals(databaseType)) {
				// For SQL Server, database name is usually in the properties: ;databaseName=dbName
				Matcher dbNameMatcher = PATTERN_DB_NAME.matcher(jdbcString);
				if (dbNameMatcher.find()) {
					databaseName = dbNameMatcher.group(1);
					dbStart = dbNameMatcher.start(1);
					dbEnd = dbNameMatcher.end(1);
				}
			} else {
				// For the others, the database name is everything after the last slash (excluding params)
				int paramIdx = remainder.indexOf('?');
				if (paramIdx != -1) {
					databaseName = remainder.substring(0, paramIdx);
					dbStart = matcher.start(2);
					dbEnd = matcher.start(2) + paramIdx;
				} else {
					databaseName = remainder;
					dbStart = matcher.start(2);
					dbEnd = matcher.end(2);
				}
			}

			System.out.println(LOG_PARSE + jdbcString);
			char[] dashArr = new char[jdbcString.length()];
			char[] labelArr = new char[jdbcString.length()];
			Arrays.fill(dashArr, ' ');
			Arrays.fill(labelArr, ' ');

			for (int i = typeStart; i < typeEnd; i++) dashArr[i] = '-';
			System.arraycopy("Type".toCharArray(), 0, labelArr, typeStart, Math.min(4, typeEnd - typeStart));

			if (dbStart != -1) {
				for (int i = dbStart; i < dbEnd; i++) dashArr[i] = '-';
				System.arraycopy("DB".toCharArray(), 0, labelArr, dbStart, Math.min(2, dbEnd - dbStart));
			}

			System.out.println(LOG_PARSE + new String(dashArr));
			System.out.println(LOG_PARSE + new String(labelArr));

			return true;
		}
		return false;
	}

	/**
	 * <p>Retrieves a list of all available schemas or catalogs based on the
	 * database type.</p>
	 *
	 * @param connection The active JDBC connection to the target database.
	 *
	 * @return A list of strings containing the names of the schemas or databases.
	 *
	 * @throws SQLException If a database access error occurs.
	 */
	public static List<String> getAvailableSchemas(Connection connection) throws SQLException {
		List<String> results = new ArrayList<>();
		DatabaseMetaData metaData = connection.getMetaData();
		String databaseProductName = metaData.getDatabaseProductName().toLowerCase();

		// MySQL and MariaDB use Catalogs for their primary "database" containers.
		if (databaseProductName.contains(DB_MYSQL) || databaseProductName.contains(DB_MARIADB)) {
			try (ResultSet rs = metaData.getCatalogs()) {
				while (rs.next()) {
					results.add(rs.getString(RS_TABLE_CATALOG));
				}
			}
		} else {
			// PostgreSQL, SQL Server, and CockroachDB use Schemas.
			// SQLite technically has one 'main' schema, but getSchemas() handles it gracefully.
			try (ResultSet rs = metaData.getSchemas()) {
				while (rs.next()) {
					results.add(rs.getString(RS_TABLE_SCHEMA));
				}
			}
		}

		return results;
	}

	/**
	 * <p>Determines if a schema is a restricted system-level schema based on the
	 * DB type.</p>
	 *
	 * @param dbName The lower-case product name of the database.
	 * @param schema The name of the schema to check.
	 *
	 * @return true if it is a system schema, false otherwise.
	 */
	private static boolean isSystemSchema(String dbName, String schema) {
		if (dbName.contains(DB_POSTGRESQL) ||
				dbName.contains(DB_COCKROACH)) {
			return schema.startsWith("pg_") || schema.equalsIgnoreCase("information_schema");
		}
		if (dbName.contains(DB_MYSQL) ||
				dbName.contains(DB_MARIADB)) {
			return schema.equalsIgnoreCase("information_schema") ||
					schema.equalsIgnoreCase(DB_MYSQL) ||
					schema.equalsIgnoreCase("performance_schema") ||
					schema.equalsIgnoreCase("sys");
		}
		if (dbName.contains(DB_MICROSOFT)) {
			return schema.equals("sys") ||
					schema.equalsIgnoreCase("information_schema") ||
					schema.toLowerCase().startsWith("db_");
		}
		return false;
	}


	/**
	 * <p>The main method that executes the reverse-engineering process.</p>
	 *
	 * @param args command-line arguments (not used)
	 *
	 * @throws SQLException if a database access error occurs
	 * @throws ClassNotFoundException if the JDBC driver class cannot be found
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String jdbcString = null;
		boolean isValidJdbc = false;
		while (!isValidJdbc) {
			jdbcString = askForInput(
					"JDBC Connection string\n           (e.g.: jdbc:<subprotocol>://host:port/?<parameters>)\n",
					false, null);

			isValidJdbc = parseJdbcString(jdbcString);
			if (!isValidJdbc) {
				System.err.println(LOG_ERROR + "The JDBC connection string does not look like the correct format.");
				String continueAnyway = askForInput("Do you want to continue anyway? (y/N)", false, "N");
				if ("y".equalsIgnoreCase(continueAnyway)) {
					isValidJdbc = true;
				} else {
					System.err.println(LOG_ERROR + "Cannot continue... Exiting...");
					return;
				}
			}
		}

		String enteredDatabaseName = askForInput("Database name", false, databaseName);

		// Now for the database type selection
		List<String> allowableDatabases = new ArrayList<>();
		allowableDatabases.addAll(Options.ALLOWABLE_DATABASES);
		Collections.sort(allowableDatabases);

		String enteredDatabaseType = null;
		if (!allowableDatabases.isEmpty()) {
			while (enteredDatabaseType == null) {
				System.out.println(LOG_SELECT + "Select database type:");
				int defaultIndex = -1;
				for (int i = 0; i < allowableDatabases.size(); i++) {
					String dbType = allowableDatabases.get(i);
					String prefix = " ";
					String suffix = "";
					if (dbType.equalsIgnoreCase(databaseType)) {
						prefix = "*";
						suffix = " (default)";
						defaultIndex = i;
					}
					System.out.println(String.format("    [%s%2d ] %s%s", prefix, i, dbType, suffix));
				}

				String choiceStr = askForInput("Select database type index", false, defaultIndex != -1 ? String.valueOf(defaultIndex) : null);
				try {
					int index = Integer.parseInt(choiceStr);
					if (index >= 0 && index < allowableDatabases.size()) {
						enteredDatabaseType = allowableDatabases.get(index);
					} else {
						System.err.println(LOG_ERROR + "Invalid index. Please select a number from the list.");
					}
				} catch (NumberFormatException e) {
					System.err.println(LOG_ERROR + "Invalid input. Please enter a valid index number.");
				}
			}
		}

		if (enteredDatabaseType == null) {
			enteredDatabaseType = askForInput("Database type", false, databaseType);
		}

		String username = askForInput("Username\n", false, null);
		String password = askForInput("Password\n", true, null);

		List<String> schemas = new ArrayList<>();
		String databaseProductName = "unknown";
		try (Connection connection = DriverManager.getConnection(jdbcString, username, password)) {
			DatabaseMetaData metaData = connection.getMetaData();
			databaseProductName = metaData.getDatabaseProductName().toLowerCase();
			schemas = getAvailableSchemas(connection);
		} catch (SQLException e) {
			System.err.println(LOG_ERROR + "Error fetching schemas: " + e.getMessage());
			System.err.println(LOG_ERROR + "Cannot continue... Exiting...");
			return;
		}

		String schemaChoice = null;
		if (!schemas.isEmpty()) {
			int defaultSchemaIndex = -1;
			for (int i = 0; i < schemas.size(); i++) {
				if (schemas.get(i).equalsIgnoreCase(enteredDatabaseName)) {
					defaultSchemaIndex = i;
					break;
				}
			}

			while (schemaChoice == null) {
				System.out.println(LOG_SELECT + "Available schemas:");
				for (int i = 0; i < schemas.size(); i++) {
					String schemaName = schemas.get(i);
					String prefix = (i == defaultSchemaIndex) ? "*" : " ";
					StringBuilder suffix = new StringBuilder();
					if (i == defaultSchemaIndex) {
						suffix.append(" (default)");
					}
					if (isSystemSchema(databaseProductName, schemaName)) {
						suffix.append(" (system ??)");
					}
					System.out.println(String.format("    [%s%2d ] %s%s", prefix, i, schemaName, suffix.toString()));
				}
				String choiceStr = askForInput("Select schema index\n", false, defaultSchemaIndex != -1 ?
								String.valueOf(defaultSchemaIndex) : null);
				try {
					int index = Integer.parseInt(choiceStr);
					if (index >= 0 && index < schemas.size()) {
						schemaChoice = schemas.get(index);
					} else {
						System.err.println(LOG_ERROR + "Invalid index. Please select a number from the list.");
					}
				} catch (NumberFormatException e) {
					System.err.println(LOG_ERROR + "Invalid input. Please enter a valid index number.");
				}
			}
		}

		System.out.println(LOG_INFO + "Summary of inputs:");
		System.out.println(LOG_INFO + "  JDBC String:   " + jdbcString);
		System.out.println(LOG_INFO + "  Database Name: " + enteredDatabaseName);
		System.out.println(LOG_INFO + "  Database Type: " + enteredDatabaseType);
		System.out.println(LOG_INFO + "  Username:      " + username);
		System.out.println(LOG_INFO + "  Schema:        " + (schemaChoice != null ? schemaChoice : "<default>"));

		String continueChoice = askForInput("Do you want to continue and write the file? (Y/n)", false, "Y");
		if (!"Y".equalsIgnoreCase(continueChoice)) {
			System.out.println("Operation cancelled.");
			return;
		}

		ModelBuilder modelBuilder = new ModelBuilder(jdbcString, enteredDatabaseType, enteredDatabaseName, username, password, schemaChoice);

		String fileName = String.format("%s_%s_h2zero.json", enteredDatabaseType, enteredDatabaseName);
		File file = new File(fileName);
		if (file.exists()) {
			String overwrite = askForInput("File '" + fileName + "' already exists. Overwrite? (y/N)", false, "N");
			if (!"y".equalsIgnoreCase(overwrite)) {
				System.out.println(LOG_OUTPUT + "Skipping " + fileName);
				return;
			}
		}

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(modelBuilder.generate());
			System.out.println(LOG_OUTPUT + "Wrote file: " + fileName);
		} catch (IOException e) {
			System.err.println(LOG_ERROR + "Error writing file " + fileName + ": " + e.getMessage());
		}
	}
}
