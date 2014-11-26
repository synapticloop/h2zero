package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class DoubleFormField extends BaseFormField {
	public DoubleFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public DoubleFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		boolean passesDefaultChecks = passesDefaultChecks();
		if(passesDefaultChecks) {
			try {
				this.parsedValue = new Double(value);
				return(true);
			} catch(NumberFormatException nfex) {
				return(false);
			}
		}
		return(passesDefaultChecks);
	}
}
