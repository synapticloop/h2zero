package synapticloop.h2zero.model.field;

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class MediumblobField extends BlobField {

	public MediumblobField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public MediumblobField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}
}
