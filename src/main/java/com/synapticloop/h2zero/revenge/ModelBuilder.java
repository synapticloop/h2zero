package com.synapticloop.h2zero.revenge;

import com.synapticloop.h2zero.revenge.model.Options;
import com.synapticloop.h2zero.revenge.model.Table;
import com.synapticloop.h2zero.revenge.model.View;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelBuilder {
	private Options options;
	private List<Table> tables = new ArrayList<Table>();
	private List<View> views = new ArrayList<View>();

	private final String jdbcString;
	private final String username;
	private final String password;
	private final String schema;

	private String databaseType = "unknown";
	private String databaseName = "unknown";

	private JSONObject databaseObject = new JSONObject();

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
		this.databaseType = databaseType;
		this.databaseName = databaseName;
		this.schema = schema;

		this.options = new Options(databaseType);

		databaseObject = new JSONObject();
		databaseObject.put("schema", databaseName);
		databaseObject.put("package", "change.me.package.name.h2zero." + databaseType + "." + databaseName.toLowerCase());

		populateTables();
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

	public String generate() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("{\n")
				.append(options.toJsonString())
				.append("  \"database\": {\n")
				.append("    \"schema\": \"")
				.append(databaseObject.getString("schema"))
				.append("\",\n")
				.append("    \"package\": \"")
				.append(databaseObject.getString("package"))
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
		stringBuilder.append("  }\n")
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
