package com.synapticloop.h2zero.revenge;

/*
 * Copyright (c) 2013-2024 synapticloop.
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.synapticloop.h2zero.revenge.model.Options;
import com.synapticloop.h2zero.revenge.model.View;
import com.synapticloop.h2zero.revenge.model.Table;

public class ModelBuilder {
	private static final String SQL_SELECT_TABLES = "select * from TABLES where TABLE_SCHEMA = ?";
	private List<Table> tables = new ArrayList<Table>();
	private List<View> views = new ArrayList<View>();

	private String host;
	private String database;
	private String user;
	private String password;

	private Options options = new Options();

	public ModelBuilder(String host, String database, String user, String password) throws ClassNotFoundException, SQLException {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;


		Class.forName("com.mysql.jdbc.Driver");
		populateTables();
	}

	private void populateTables() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/information_schema", user, password);
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TABLES);
		preparedStatement.setString(1, database);
		ResultSet resultSet = preparedStatement.executeQuery();

		while(resultSet.next()) {
			if("VIEW".equals(resultSet.getString("TABLE_TYPE"))) {
				views.add(new View(connection, database, resultSet.getString("TABLE_NAME")));
			} else {
				tables.add(new Table(connection, database, resultSet.getString("TABLE_NAME")));
			}
		}
	}

	public String generate() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");

		stringBuilder.append("  \"database\": \"" + database + "\",\n");
		stringBuilder.append("  \"package\": \"please.complete.me.h2zero\",\n");

		stringBuilder.append(options.toJsonString());

		stringBuilder.append("  \"tables\": [\n");

		// add in all of the tables
		int i = 0;
		for (Table table : tables) {
			if(i != 0) {
				stringBuilder.append(",\n");
			}
			i++;
			stringBuilder.append(table.toJsonString());
		}
		stringBuilder.append("\n  ],\n");
		stringBuilder.append("  \"views\": [\n");

		// and the views
		i = 0;
		for (View view : views) {
			if(i != 0) {
				stringBuilder.append(",\n");
			}
			i++;
			stringBuilder.append(view.toJsonString());
		}

		stringBuilder.append("\n  ]\n");
		stringBuilder.append("}\n");
		return (stringBuilder.toString());
	}
}
