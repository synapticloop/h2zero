package synapticloop.h2zero.revenge.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Column {
	private static final HashSet<String> LENGTH_DATA_TYPES = new HashSet<String>();
	static {
		LENGTH_DATA_TYPES.add("varchar");
		LENGTH_DATA_TYPES.add("tinyint");
	}
	private static final String SQL_FIND_FOREIGN_KEYS = "select * from KEY_COLUMN_USAGE where REFERENCED_TABLE_SCHEMA = ? and TABLE_NAME = ? and COLUMN_NAME = ?";

	private String name = null;
	private String dataType = null;
	private boolean isNullable = false;
	private boolean isPrimary = false;
	private String defaultValue = null;

	private Long length = null;

	private long numericPrecision = 0;
	private long numericScale = 0;

	private boolean hasDefault = false;
	private boolean hasLength = false;

	private boolean hasForeignKey = false;

	public Column(ResultSet resultSet) throws SQLException {
		this.name = resultSet.getString("COLUMN_NAME");
		this.dataType = resultSet.getString("DATA_TYPE");
		this.hasLength = LENGTH_DATA_TYPES.contains(dataType);

		if(this.hasLength) {
			this.length = resultSet.getLong("CHARACTER_MAXIMUM_LENGTH");
		}


		this.isNullable = resultSet.getString("IS_NULLABLE").equals("YES");
		this.isPrimary = resultSet.getString("COLUMN_KEY").equals("PRI");

		defaultValue = resultSet.getString("COLUMN_DEFAULT");
		if(null != defaultValue && (defaultValue.equals("1") || defaultValue.equals("0"))) {
			hasDefault = true;
		}
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("        { ");
		stringBuilder.append("\"name\": \"" + name + "\", ");
		stringBuilder.append("\"type\": \"" + dataType + "\", ");

		if(this.dataType.equals("tinyint")) {
			stringBuilder.append("length: \"1, ");
		} else if(null != length && length.longValue() != 0) {
			stringBuilder.append("\"length\": " + length + ", ");
		}

		stringBuilder.append("\"nullable\": " + isNullable + ", ");

		if(hasDefault) {
			stringBuilder.append(" \"default\": \"" + defaultValue + "\", ");
		}

		stringBuilder.append("\"primary\": " + isPrimary + " ");

		stringBuilder.append("}");
		return (stringBuilder.toString());
	}
}
