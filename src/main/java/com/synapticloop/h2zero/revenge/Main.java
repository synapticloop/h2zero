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
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
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
		String displayPrompt = "[ PROMPT ] " + prompt +
				(null != defaultValue ? "\n           (h2zero thinks '" + defaultValue + "' enter to accept)\n" : "") +
				"[  REPLY ] ";

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

	private static void parseJdbcString(String jdbcString) {
		if (jdbcString == null) return;

		// PostgreSQL: jdbc:postgresql://host:port/database
		// MySQL: jdbc:mysql://host:port/database
		// MariaDB: jdbc:mariadb://host:port/database
		// SQLite: jdbc:sqlite:path/to/database.db
		// MSSQL: jdbc:sqlserver://[host[\instanceName][:port]][;property=value[;property=value]]

		Pattern pattern = Pattern.compile("jdbc:(postgresql|mysql|mariadb|sqlite|sqlserver):(?:(?://[^/;/]+(?:[:/])?)|(?:))(.*)");
		Matcher matcher = pattern.matcher(jdbcString);

		if (matcher.find()) {
			databaseType = matcher.group(1);
			String remainder = matcher.group(2);

			if ("sqlite".equals(databaseType)) {
				databaseType = "sqlite3";
				databaseName = remainder;
			} else if ("sqlserver".equals(databaseType)) {
				// For SQL Server, database name is usually in the properties: ;databaseName=dbName
				Pattern dbNamePattern = Pattern.compile("databaseName=([^;]+)");
				Matcher dbNameMatcher = dbNamePattern.matcher(jdbcString);
				if (dbNameMatcher.find()) {
					databaseName = dbNameMatcher.group(1);
				}
			} else {
				// For the others, the database name is everything after the last slash (excluding params)
				int paramIdx = remainder.indexOf('?');
				if (paramIdx != -1) {
					databaseName = remainder.substring(0, paramIdx);
				} else {
					databaseName = remainder;
				}
			}
		}
	}


	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String jdbcString = askForInput(
				"JDBC Connection string\n           (e.g.: jdbc:<subprotocol>://host:port/?<parameters>)\n",
				false, null);

		parseJdbcString(jdbcString);

		String enteredDatabaseName = askForInput("Database name", false, databaseName);

		// Now for the database type selection
		List<String> allowableDatabases = new ArrayList<>();
		try {
			Field field = Options.class.getDeclaredField("ALLOWABLE_DATABASES");
			field.setAccessible(true);
			Set<String> dbSet = (Set<String>) field.get(null);
			allowableDatabases.addAll(dbSet);
			Collections.sort(allowableDatabases);
		} catch (Exception e) {
			System.err.println("Could not retrieve allowable databases: " + e.getMessage());
		}

		String enteredDatabaseType = null;
		if (!allowableDatabases.isEmpty()) {
			System.out.println("[ SELECT ] Select database type:");
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
				}
			} catch (NumberFormatException e) {
				if (defaultIndex != -1) {
					enteredDatabaseType = allowableDatabases.get(defaultIndex);
				}
			}
		}

		if (enteredDatabaseType == null) {
			enteredDatabaseType = askForInput("Database type", false, databaseType);
		}

		String username = askForInput("Username\n", false, null);
		String password = askForInput("Password\n", true, null);

		List<String> schemas = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(jdbcString, username, password)) {
			DatabaseMetaData metaData = connection.getMetaData();
			try (ResultSet rs = metaData.getSchemas()) {
				while (rs.next()) {
					schemas.add(rs.getString("TABLE_SCHEM"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching schemas: " + e.getMessage());
		}

		String schemaChoice = null;
		if (!schemas.isEmpty()) {
			System.out.println("[ SELECT ] Available schemas:");
			for (int i = 0; i < schemas.size(); i++) {
				System.out.println(String.format("    [ %2d ] %s", i, schemas.get(i)));
			}
			String choiceStr = askForInput("Select schema index:\n", false, null);
			try {
				int index = Integer.parseInt(choiceStr);
				if (index >= 0 && index < schemas.size()) {
					schemaChoice = schemas.get(index);
				}
			} catch (NumberFormatException e) {
				schemaChoice = schemas.get(0);
			}
		}

		ModelBuilder modelBuilder = new ModelBuilder(jdbcString, enteredDatabaseType, enteredDatabaseName, username, password, schemaChoice);
		
		String continueChoice = askForInput("Do you want to continue and write the file? (Y/n)", false, "Y");
		if (!"Y".equalsIgnoreCase(continueChoice)) {
			System.out.println("Operation cancelled.");
			return;
		}

		String fileName = String.format("%s_%s_h2zero.json", enteredDatabaseType, enteredDatabaseName);
		File file = new File(fileName);
		if (file.exists()) {
			String overwrite = askForInput("File '" + fileName + "' already exists. Overwrite? (y/N)", false, "N");
			if (!"y".equalsIgnoreCase(overwrite)) {
				System.out.println("[ OUTPUT ] Skipping " + fileName);
				return;
			}
		}

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(modelBuilder.generate());
			System.out.println("[ OUTPUT ] Wrote file: " + fileName);
		} catch (IOException e) {
			System.err.println("[  ERROR ] Error writing file " + fileName + ": " + e.getMessage());
		}
	}
}
