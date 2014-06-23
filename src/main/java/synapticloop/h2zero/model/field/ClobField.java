package synapticloop.h2zero.model.field;

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;


public class ClobField extends BaseField {

	public ClobField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public String getJavaType() {
		return "Clob";
	}

	public String getSqlJavaType() {
		return("Clob");
	}

	public String getSqlNullType() {
		return("CLOB");
	}

}
