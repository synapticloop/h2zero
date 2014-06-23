package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class RadioFormField extends BaseFormField {

	public RadioFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public RadioFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		return(passesDefaultChecks());
	}

}
