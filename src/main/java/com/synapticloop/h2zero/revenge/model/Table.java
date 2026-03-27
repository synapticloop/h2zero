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
import java.util.List;

public class Table {
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

	public Table(DatabaseMetaData metaData, String tableSchema, String tableName) throws SQLException {
		this.name = name;
		try (ResultSet columns = metaData.getColumns(null, tableSchema, tableName, "%")) {

			System.out.printf("%-20s | %-15s | %-10s | %-10s%n", "COLUMN NAME", "DATA TYPE", "SIZE", "NULLABLE");
			System.out.println("-------------------------------------------------------------------------");

			while (columns.next()) {
				String columnName = columns.getString("COLUMN_NAME");
				String typeName = columns.getString("TYPE_NAME");
				int columnSize = columns.getInt("COLUMN_SIZE");
				String isNullable = columns.getString("IS_NULLABLE");

				System.out.printf("%-20s | %-15s | %-10d | %-10s%n",
						columnName, typeName, columnSize, isNullable);
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
