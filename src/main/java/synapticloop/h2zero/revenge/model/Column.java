package synapticloop.h2zero.revenge.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Column {
	private static final Set<String> LENGTH_DATA_TYPES = new HashSet<String>();
	static {
		LENGTH_DATA_TYPES.add("varchar");
		LENGTH_DATA_TYPES.add("tinyint");
	}

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

	private String foreignKeyTable = null;
	private String foreignKeyColumn = null;
	
	private boolean isIndexed = false;
	private boolean isUnique = false;

	public Column(ResultSet resultSet) throws SQLException {
		this.name = resultSet.getString("COLUMN_NAME");
		this.dataType = resultSet.getString("DATA_TYPE");
		this.hasLength = LENGTH_DATA_TYPES.contains(dataType);

		if(this.hasLength) {
			this.length = resultSet.getLong("CHARACTER_MAXIMUM_LENGTH");
		}


		this.isNullable = "YES".equals(resultSet.getString("IS_NULLABLE"));
		this.isPrimary = "PRI".equals(resultSet.getString("COLUMN_KEY"));

		defaultValue = resultSet.getString("COLUMN_DEFAULT");
		if(null != defaultValue && (defaultValue.equals("1") || defaultValue.equals("0"))) {
			hasDefault = true;
		}
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("        { ");
		stringBuilder.append("\"name\": \"" + name + "\"");
		stringBuilder.append(", \"type\": \"" + dataType + "\"");

		if(this.dataType.equals("tinyint")) {
			stringBuilder.append("length: \"1, ");
		} else if(null != length && length.longValue() != 0) {
			stringBuilder.append(", \"length\": " + length);
		}

		stringBuilder.append(", \"nullable\": " + isNullable);

		if(hasDefault) {
			stringBuilder.append(", \"default\": \"" + defaultValue + "\"");
		}

		if(isPrimary) {
			stringBuilder.append(", \"primary\": " + isPrimary);
		}

		if(getIsIndexed()) {
			stringBuilder.append(", \"index\": " + getIsIndexed());
		}

		if(getIsUnique() && !isPrimary) {
			stringBuilder.append(", \"unique\": " + getIsUnique());
		}

		if(hasForeignKey()) {
			stringBuilder.append(", \"foreignKey\": \"" + foreignKeyTable + "." + foreignKeyColumn + "\"");
		}

		stringBuilder.append(" }");
		return (stringBuilder.toString());
	}

	public String getName() { return name; }

	public String getForeignKeyTable() { return this.foreignKeyTable; }
	public void setForeignKeyTable(String foreignKeyTable) { this.foreignKeyTable = foreignKeyTable; }
	public String getForeignKeyColumn() { return this.foreignKeyColumn; }
	public void setForeignKeyColumn(String foreignKeyColumn) { this.foreignKeyColumn = foreignKeyColumn; }
	public boolean hasForeignKey() { return(null != foreignKeyTable && null != foreignKeyColumn); }
	public boolean getIsIndexed() { return(isPrimary || hasForeignKey() || isIndexed); }
	public void setIsIndexed(boolean isIndexed) { this.isIndexed = isIndexed; }
	public void setIsUnique(boolean isUnique) { this.isUnique = isUnique; }
	public boolean getIsUnique() { return(isUnique); }
}
