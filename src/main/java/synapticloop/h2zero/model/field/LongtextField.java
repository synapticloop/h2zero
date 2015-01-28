package synapticloop.h2zero.model.field;

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class LongtextField extends BaseField {

	public LongtextField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public LongtextField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}

	public String getJavaType() {
		return "String";
	}

	public String getSqlJavaType() {
		return("String");
	}

	public String getSqlNullType() {
		return("VARCHAR");
	}

	public boolean getShouldEscape() {
		return true;
	}

}
