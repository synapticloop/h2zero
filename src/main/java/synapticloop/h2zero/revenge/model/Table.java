package synapticloop.h2zero.revenge.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
	private static final String SQL_SELECT_COLUMNS = "select * from COLUMNS where TABLE_SCHEMA = ? and TABLE_NAME = ? order by ORDINAL_POSITION asc";
	private static final String SQL_FIND_FOREIGN_KEYS = "select * from KEY_COLUMN_USAGE where TABLE_SCHEMA = ? and TABLE_NAME = ? and COLUMN_NAME = ? order by ORDINAL_POSITION asc";
	private static final String SQL_FIND_INDEXES = "select * from STATISTICS where TABLE_SCHEMA = ? and TABLE_NAME = ? and COLUMN_NAME = ? and INDEX_NAME != 'PRIMARY'";
	private String name = null;
	private ArrayList<Column> columns = new ArrayList<Column>();

	private static ArrayList<String> SQL_INTERACTION_OBJECTS = new ArrayList<String>();
	static {
		SQL_INTERACTION_OBJECTS.add("fieldFinders");
		SQL_INTERACTION_OBJECTS.add("finders");
		SQL_INTERACTION_OBJECTS.add("updaters");
		SQL_INTERACTION_OBJECTS.add("inserters");
		SQL_INTERACTION_OBJECTS.add("deleters");
		SQL_INTERACTION_OBJECTS.add("counters");
		SQL_INTERACTION_OBJECTS.add("questions");
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

		// now go through and generate all of the keywords that can be done

		stringBuilder.append("    }");
		return (stringBuilder.toString());
	}

}
