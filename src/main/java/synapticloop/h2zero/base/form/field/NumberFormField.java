package synapticloop.h2zero.base.form.field;

import java.util.HashSet;

import org.json.JSONObject;

public class NumberFormField extends BaseFormField {
	private static HashSet<Character> VALID_NUMBERS = new HashSet<Character>();
	static {
		VALID_NUMBERS.add('0');
		VALID_NUMBERS.add('1');
		VALID_NUMBERS.add('2');
		VALID_NUMBERS.add('3');
		VALID_NUMBERS.add('4');
		VALID_NUMBERS.add('5');
		VALID_NUMBERS.add('6');
		VALID_NUMBERS.add('7');
		VALID_NUMBERS.add('8');
		VALID_NUMBERS.add('9');
	}

	public NumberFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public NumberFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	@Override
	public boolean isValid() {
		if(!passesDefaultChecks()) {
			return(false);
		}

		// we need to make sure that there are only numbers in the field
		if(null != value) {
			char[] charArray = value.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if(!VALID_NUMBERS.contains(c)) {
					return(false);
				}

			}
		}
		return(true);
	}

}
