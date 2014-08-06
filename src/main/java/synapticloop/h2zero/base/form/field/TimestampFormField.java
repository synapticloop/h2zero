package synapticloop.h2zero.base.form.field;

import org.json.JSONObject;

public class TimestampFormField extends BaseFormField {


	public TimestampFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public TimestampFormField(String name, boolean allowNull, boolean requiresConfirm, int minLength, int maxLength) {
		super(name, allowNull, requiresConfirm, minLength, maxLength);
	}

	@Override
	public boolean isValid() {
		return false;
	}

}
