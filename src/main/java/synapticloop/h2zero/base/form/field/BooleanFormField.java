package synapticloop.h2zero.base.form.field;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

public class BooleanFormField extends BaseFormField {
	private static final Set<String> TRUE_VALUES = new HashSet<String>();
	static {
		TRUE_VALUES.add("true");
		TRUE_VALUES.add("yes");
		TRUE_VALUES.add("selected");
		TRUE_VALUES.add("checked");
	}

	public BooleanFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public BooleanFormField(String name, boolean allowNull, boolean requiresConfirm, int minLength, int maxLength) {
		super(name, allowNull, requiresConfirm, minLength, maxLength);
	}

	@Override
	public boolean isValid() {
		if(null == value || value.length() == 0) {
			if(allowNull) {
				this.parsedValue = new Boolean(false);
			}
			return(allowNull);
		} else {
			if(TRUE_VALUES.contains(value.toLowerCase())) {
				this.parsedValue = new Boolean(true);
			} else {
				this.parsedValue = new Boolean(false);
			}
		}
		return(true);
	}
}
