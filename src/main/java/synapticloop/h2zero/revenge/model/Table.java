package synapticloop.h2zero.revenge.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
	private static final String SQL_SELECT_COLUMNS = "select * from COLUMNS where TABLE_SCHEMA = ? and TABLE_NAME = ? order by ORDINAL_POSITION asc";
	private String name = null;
	private ArrayList<Column> columns = new ArrayList<Column>();

	public Table(Connection connection, String database, String name) throws SQLException {
		this.name = name;
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COLUMNS);
		preparedStatement.setString(1, database);
		preparedStatement.setString(2, name);
		ResultSet resultSet = preparedStatement.executeQuery();

		while(resultSet.next()) {
			columns.add(new Column(resultSet));
		}
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("    {\n");
		stringBuilder.append("      \"name\": \"" + name + "\",\n");
		stringBuilder.append("      \"cacheable\": false,\n");
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

		stringBuilder.append("\n      ]\n");
		stringBuilder.append("    }");
		return (stringBuilder.toString());
	}

}
