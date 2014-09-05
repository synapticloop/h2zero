package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class DateFormField extends BaseFormField {


	public DateFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public DateFormField(String name, boolean allowNull, boolean requiresConfirm, int minLength, int maxLength) {
		super(name, allowNull, requiresConfirm, minLength, maxLength);
	}

	@Override
	public boolean isValid() {
		return false;
	}

}
