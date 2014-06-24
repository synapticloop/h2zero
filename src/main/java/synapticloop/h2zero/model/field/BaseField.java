package synapticloop.h2zero.model.field;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.util.AssertionHelper;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;


public abstract class BaseField {
	protected String name;
	protected String type;
	protected int length = 0;
	protected boolean nullable = true;
	protected int decimalLength = 0;
	protected String defaultValue = null;
	protected boolean primary = false;
	protected boolean index = false;
	protected boolean unique = false;
	protected boolean populate = true;
	protected boolean secure = false;
	protected boolean isInField = false;

	protected String foreignKeyTable = null;
	protected String foreignKeyField = null;

	// generated for the updaters
	protected String javaName = null;
	private String secondaryJavaName = null;
	private String secondaryJavaFieldName = null;

	// these are for the forms
	protected String validator;
	protected int minLength;

	public BaseField(JSONObject jsonObject) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.javaName = NamingHelper.getSecondUpper(name);
		this.secondaryJavaName = NamingHelper.getSecondPartUpper(name);
		this.secondaryJavaFieldName = NamingHelper.getFirstPartUpper(name);
		this.type = JsonHelper.getStringValue(jsonObject, "type", null);
		this.length = JsonHelper.getIntValue(jsonObject, "length", length);
		this.decimalLength = JsonHelper.getIntValue(jsonObject, "decimalLength", decimalLength);
		this.nullable = JsonHelper.getBooleanValue(jsonObject, "nullable", nullable);
		this.defaultValue = JsonHelper.getStringValue(jsonObject, "default", defaultValue);
		this.primary = JsonHelper.getBooleanValue(jsonObject, "primary", primary);
		this.index = JsonHelper.getBooleanValue(jsonObject, "index", index);
		this.unique = JsonHelper.getBooleanValue(jsonObject, "unique", unique);

		this.validator = JsonHelper.getStringValue(jsonObject, "validator", null);
		this.minLength = JsonHelper.getIntValue(jsonObject, "minLength", 0);
		this.populate = jsonObject.optBoolean("populate", true);
		this.populate = jsonObject.optBoolean("secure", false);

		try {
			JSONObject foreignKey = jsonObject.getJSONObject("foreign");
			foreignKeyTable = foreignKey.getString("table");
			foreignKeyField = foreignKey.getString("field");
		} catch (JSONException ojjsonex) {
			// nothing to do
		}
		AssertionHelper.assertNotNull("name", name);
		AssertionHelper.assertNotNull("type", name);
	}

	public BaseField(JSONObject jsonObject, boolean isInfield) throws H2ZeroParseException {
		this(jsonObject);
		this.isInField = isInfield;
	}

	public String getName() { return(name); }
	public String getUpperName() { return(name.toUpperCase()); }
	public String getType() { return(type); }
	public String getUpperType() { return(NamingHelper.getFirstUpper(type)); }
	public boolean getNullable() { return(nullable); }
	public String getDefault() { return(defaultValue); }
	public boolean getPrimary() { return(primary); }
	public boolean getIndex() { return(index); }
	public boolean getUnique() {return(unique); }
	public boolean getPopulate() { return populate; }
	public void setPopulate(boolean populate) { this.populate = populate; }
	public boolean getSecure() { return secure; }
	public boolean getIsInField() { return isInField; }

	public String getJavaName() { return(javaName); }
	public String getSecondaryJavaName() { return secondaryJavaName; }
	public String getSecondaryJavaFieldName() { return secondaryJavaFieldName; }
	public void suffixJavaName(String suffix) { this.javaName = javaName + suffix; }

	public String getJavaAccessorName() { return(NamingHelper.getFirstUpper(name)); }

	public String getForeignKeyTable() { return(foreignKeyTable); }
	public Table getForeignKeyTableLookup() { return(Database.getTableLookup(foreignKeyTable)); }
	public String getForeignKeyField() { return(foreignKeyField); }
	public BaseField getForeignKeyFieldLookup() { return(Database.getTableLookup(foreignKeyTable).getField(foreignKeyField)); }

	public abstract String getJavaType();
	public abstract String getSqlNullType();
	public abstract String getSqlJavaType();

	public String getValidator() { return(validator); }
	public int getMinLength() { return(minLength); }
	public int getMaxLength() { return(length); }

	public String getLengthFormat() {
		StringBuilder stringBuilder = new StringBuilder();
		if(length != 0) {
			stringBuilder.append("(");
			stringBuilder.append(length);
			if(decimalLength != 0) {
				stringBuilder.append(",");
				stringBuilder.append(decimalLength);
			}
			stringBuilder.append(")");
		}
		return(stringBuilder.toString());
	}

	public String getCreateField() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\t");

		// name
		stringBuilder.append(name);
		stringBuilder.append(" ");

		// type and length
		stringBuilder.append(type);
		stringBuilder.append(getLengthFormat());

		if(!nullable) {
			stringBuilder.append(" not");
		}

		stringBuilder.append(" null");

		if(primary) {
			stringBuilder.append(" auto_increment");
		}

		if(null != defaultValue) {
			stringBuilder.append(" default " + defaultValue);
		}

		if(primary) {
			stringBuilder.append(",\n\tprimary key(" + name + ")");
		}

		if(unique) {
			stringBuilder.append(",\n\tunique key(" + name + ")");
		}

		if(index) {
			stringBuilder.append(",\n\tindex (" + name + ")");
		}

		stringBuilder.append("\n");
		return (stringBuilder.toString());
	}

}
