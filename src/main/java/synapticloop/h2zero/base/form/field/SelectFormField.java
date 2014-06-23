package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class SelectFormField extends BaseFormField {

	public SelectFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public SelectFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		if(!passesDefaultChecks()) {
			return(false);
		}

		this.parsedValue = value;
		return(true);
	}
}
