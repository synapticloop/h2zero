package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class FloatFormField extends BaseFormField {
	public FloatFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public FloatFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		boolean passesDefaultChecks = passesDefaultChecks();
		if(passesDefaultChecks) {
			try {
				this.parsedValue = new Long(value);
				return(true);
			} catch(NumberFormatException nfex) {
				return(false);
			}
		}
		return(passesDefaultChecks);
	}
}
