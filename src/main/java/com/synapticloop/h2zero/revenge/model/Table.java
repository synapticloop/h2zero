package com.synapticloop.h2zero.revenge.model;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.synapticloop.h2zero.generator.model.util.JSONKeyConstants;

public class Table {
	private static final String SQL_SELECT_COLUMNS = "select * from COLUMNS where TABLE_SCHEMA = ? and TABLE_NAME = ? order by ORDINAL_POSITION asc";
	private static final String SQL_FIND_FOREIGN_KEYS = "select * from KEY_COLUMN_USAGE where TABLE_SCHEMA = ? and TABLE_NAME = ? and COLUMN_NAME = ? order by ORDINAL_POSITION asc";
	private static final String SQL_FIND_INDEXES = "select * from STATISTICS where TABLE_SCHEMA = ? and TABLE_NAME = ? and COLUMN_NAME = ? and INDEX_NAME != 'PRIMARY'";
	private String name = null;
	private List<Column> columns = new ArrayList<Column>();

	private static List<String> SQL_INTERACTION_OBJECTS = new ArrayList<String>();
	static {
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FIELD_FINDERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.FINDERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.UPDATERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.INSERTERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.DELETERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.COUNTERS);
		SQL_INTERACTION_OBJECTS.add(JSONKeyConstants.QUESTIONS);
	}

	public Table(Connection connection, String database, String name) throws SQLException {
		this.name = name;

		PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COLUMNS);
		preparedStatement.setString(1, database);
		preparedStatement.setString(2, name);
		ResultSet resultSet = preparedStatement.executeQuery();

		while(resultSet.next()) {
			columns.add(new Column(resultSet));
		}

		resultSet.close();
		preparedStatement.close();

		for (Column column : columns) {
			preparedStatement = connection.prepareStatement(SQL_FIND_FOREIGN_KEYS);
			preparedStatement.setString(1, database);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, column.getName());

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				String referencedTableName = resultSet.getString("REFERENCED_TABLE_NAME");
				String referenceColumnName = resultSet.getString("REFERENCED_COLUMN_NAME");

				if(null != referencedTableName && null != referenceColumnName) {
					column.setForeignKeyColumn(referenceColumnName);
					column.setForeignKeyTable(referencedTableName);
				}
			}

			resultSet.close();
			preparedStatement.close();
			
			preparedStatement = connection.prepareStatement(SQL_FIND_INDEXES);
			preparedStatement.setString(1, database);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, column.getName());

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				boolean nonUnique = resultSet.getBoolean("NON_UNIQUE");
				if(!nonUnique) {
					column.setIsUnique(true);
				} else {
					column.setIsIndexed(false);
				}
			}
		}
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("    {\n");
		stringBuilder.append("      \"name\": \"" + name + "\",\n");
		stringBuilder.append("      \"fields\": [");

		int i = 0;
		for (Column column : columns) {
			if(i != 0) {
				stringBuilder.append(",");
			}
			i++;
			stringBuilder.append("\n");
			stringBuilder.append(column.toJsonString());
		}
		stringBuilder.append("\n      ]");
		for (String sqlInteractionObject : SQL_INTERACTION_OBJECTS) {
			stringBuilder.append(",\n      \"");
			stringBuilder.append(sqlInteractionObject);
			stringBuilder.append("\": [\n      ]");
		}
		stringBuilder.append("\n");

		stringBuilder.append("    }");
		return (stringBuilder.toString());
	}

}
