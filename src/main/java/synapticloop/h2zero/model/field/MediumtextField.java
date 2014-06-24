package synapticloop.h2zero.model.field;

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;


public class MediumtextField extends BaseField {

	public MediumtextField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public MediumtextField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
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

}
