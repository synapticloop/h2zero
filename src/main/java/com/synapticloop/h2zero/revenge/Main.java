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

import com.synapticloop.h2zero.revenge.model.Table;
import org.jline.terminal.TerminalBuilder;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static String databaseName;
	public static String databaseType;

	/**
	 * <p>Prompts the user for input via a JLine terminal.</p>
	 *
	 * <p>When isMasked is true, JLine disables character echoing. This method
	 * handles the lifecycle of the terminal resource internally.</p>
	 *
	 * @param prompt the message to display to the user
	 * @param isMasked whether the input should be hidden/masked
	 * @param defaultValue the default value to return if the user enters nothing
	 *
	 * @return the string entered by the user, or the defaultValue if nothing was entered
	 */
	public static String askForInput(String prompt, boolean isMasked, String defaultValue) {
		// Character used for masking. Setting this to null (or '\0')
		// provides "silent" input (like sudo in Linux).
		Character mask = isMasked ? '*' : null;

		try (Terminal terminal = TerminalBuilder.builder()
				.dumb(true) // Allows fallback if a full TTY isn't detected
				.build()) {

			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.build();

			// readLine handles both the prompt display and the masking logic
			String line = reader.readLine(
					prompt  +
							(null != defaultValue ? " (h2zero thinks '" + defaultValue + "' enter to accept)\n" : "") +
							"    >: ",
					mask);
			if ((line == null || line.isEmpty()) && defaultValue != null) {
				return defaultValue;
			}
			return line;

		} catch (IOException e) {
			System.err.println("Error initializing JLine terminal: " + e.getMessage());
			return null;
		}
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
				"JDBC Connection string\n(e.g.: jdbc:<subprotocol>://host:port/?<parameters>)\n",
				false, null);

		parseJdbcString(jdbcString);

		String enteredDatabaseName = askForInput("Database name", false, databaseName);
		String enteredDatabaseType = askForInput("Database type", false, databaseType);

		String username = askForInput("Username", false, null);
		String password = askForInput("Password", true, null);

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
			System.out.println("Available schemas:");
			for (int i = 0; i < schemas.size(); i++) {
				System.out.println(String.format(" [%d] %s", i, schemas.get(i)));
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
		
		String continueChoice = askForInput("Do you want to continue and write the files? (Y/n)", false, null);
		if (!"Y".equalsIgnoreCase(continueChoice)) {
			System.out.println("Operation cancelled.");
			return;
		}

		for (Table table : modelBuilder.getTables()) {
			String fileName = String.format("%s_%s_h2zero.json", enteredDatabaseName, table.getName());
			File file = new File(fileName);
			if (file.exists()) {
				String overwrite = askForInput("File '" + fileName + "' already exists. Overwrite? (y/N)\n  :> ", false, "N");
				if (!"y".equalsIgnoreCase(overwrite)) {
					System.out.println("Skipping " + fileName);
					continue;
				}
			}

			try (FileWriter writer = new FileWriter(file)) {
				writer.write(modelBuilder.generate());
				System.out.println("Wrote file: " + fileName);
			} catch (IOException e) {
				System.err.println("Error writing file " + fileName + ": " + e.getMessage());
			}
		}
	}
}
