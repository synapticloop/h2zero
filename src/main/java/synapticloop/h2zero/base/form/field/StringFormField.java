package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class StringFormField extends BaseFormField {

	public StringFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public StringFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		this.parsedValue = value;
		return(passesDefaultChecks());
	}
}
