package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class IntFormField extends BaseFormField {

	public IntFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public IntFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	private Integer parsedValue = null;

	public Object getParsedValue() {
		return(parsedValue);
	}

	public boolean isValid() {
		if(!passesDefaultChecks()) {
			return(false);
		}

		this.parsedValue = null;
		// now for the actual validation
		if(null != value) {
			try {
				this.parsedValue = Integer.parseInt(value);
			} catch(NumberFormatException jlnfex) {
				return(false);
			}
		}

		return(true);
	}

}
