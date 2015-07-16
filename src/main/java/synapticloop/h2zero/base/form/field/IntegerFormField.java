package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class IntegerFormField extends BaseFormField {
	private Integer parsedValue = null;

	public IntegerFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public IntegerFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	@Override
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
