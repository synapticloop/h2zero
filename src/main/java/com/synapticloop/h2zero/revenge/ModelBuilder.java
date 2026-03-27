package com.synapticloop.h2zero.revenge;

import com.synapticloop.h2zero.revenge.model.Table;
import com.synapticloop.h2zero.revenge.model.View;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelBuilder {
	private static final String SQL_SELECT_TABLES = "select * from TABLES where TABLE_SCHEMA = ?";

	private List<Table> tables = new ArrayList<Table>();
	private List<View> views = new ArrayList<View>();

	private final String jdbcString;
	private final String username;
	private final String password;

	private String databaseType = "unknown";
	private String databaseName = "unknown";

	private JSONObject optionsObject = new JSONObject();
	private JSONObject databaseObject = new JSONObject();

	public ModelBuilder(
			String jdbcString,
			String databaseType,
			String databaseName,
			String username,
			String password) throws ClassNotFoundException,	SQLException {

		this.jdbcString = jdbcString;
		this.username = username;
		this.password = password;
		this.databaseType = databaseType;
		this.databaseName = databaseName;

		optionsObject.put("database", databaseType);
		JSONArray generatorsArray = new JSONArray();
		generatorsArray.put("java");
		generatorsArray.put("sql");
		optionsObject.put("generators", generatorsArray);

		databaseObject = new JSONObject();
		databaseObject.put("schema", databaseType);
		databaseObject.put("package", "example.package.name.h2zero." + databaseType + "." + databaseName.toLowerCase());

		populateTables();
	}

	private void populateTables() throws SQLException {

		Connection connection = DriverManager.getConnection(jdbcString, username, password);
		DatabaseMetaData metaData = connection.getMetaData();

		String[] types = {"TABLE"};

		try (ResultSet resultSet = metaData.getTables(null, null, "%", types)) {
			System.out.println("List of Tables:");
			while (resultSet.next()) {
				// Common metadata columns:
				// 1. TABLE_CAT (String) => table catalog
				// 2. TABLE_SCHEM (String) => table schema
				// 3. TABLE_NAME (String) => table name
				// 4. TABLE_TYPE (String) => table type

				String tableName = resultSet.getString("TABLE_NAME");
				String tableSchema = resultSet.getString("TABLE_SCHEM");
				String tableType = resultSet.getString("TABLE_TYPE");

				System.out.println(String.format("Schema: %s | Name: %s | Type: %s",
						tableSchema, tableName, tableType));

				tables.add(new Table(metaData, tableSchema, tableName));

			}
		}
	}

	public String generate() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");

		stringBuilder.append(String.format("  \"database\": \"%s\",\n", databaseType));
		stringBuilder.append("  \"package\": \"please.complete.me.h2zero\",\n");

//		stringBuilder.append(options.toJsonString());

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
