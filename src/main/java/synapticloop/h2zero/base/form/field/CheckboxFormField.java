package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class CheckboxFormField extends BaseFormField {

	public CheckboxFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public CheckboxFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		if(!passesDefaultChecks()) {
			return(false);
		}

		if(null != value) {
			this.parsedValue = new Boolean(true);
		} else {
			this.parsedValue = new Boolean(false);
		}

		return(true);
	}

}
