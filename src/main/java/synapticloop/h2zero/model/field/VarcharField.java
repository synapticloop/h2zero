package synapticloop.h2zero.model.field;

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;


public class VarcharField extends BaseField {

	public VarcharField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
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

}
