package synapticloop.h2zero.model.field;

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class BlobField extends BaseField {

	public BlobField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public BlobField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}

	public String getJavaType() {
		return "Blob";
	}

	public String getSqlJavaType() {
		return("Blob");
	}

	public String getSqlNullType() {
		return("BLOB");
	}

	public boolean getShouldEscape() {
		return true;
	}

	public boolean getIsLargeObject() {
		return(true);
	}

}
