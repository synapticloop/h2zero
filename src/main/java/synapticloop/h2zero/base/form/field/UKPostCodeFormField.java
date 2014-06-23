package synapticloop.h2zero.base.form.field;

import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * This is a postcode field for the UK
 *
 */

public class UKPostCodeFormField extends BaseFormField {
	private static String POSTCODE_REGEX_PATTERN = "(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) ?[0-9][A-Z-[CIKMOV]]{2})";

	public UKPostCodeFormField(JSONObject jsonObject) {
		super(jsonObject);
	}

	public UKPostCodeFormField(String name, boolean allowNull, boolean requiresConfirm, int length, int minLength) {
		super(name, allowNull, requiresConfirm, length, minLength);
	}

	public boolean isValid() {
		if(!passesDefaultChecks()) {
			return(false);
		}

		if(null == value) {
			if(allowNull) {
				return(allowNull);
			}
		} else {
			if(!Pattern.matches(POSTCODE_REGEX_PATTERN, value)) {
				return(false);
			}
			this.parsedValue = value;
		}
		return true;
	}

	public void setValue(String value) {
		super.setValue(value);

		if(null != this.value) {
			// now we need to make sure that it is in <first_digits><space><last_three_digits>
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < value.toUpperCase().toCharArray().length; i++) {
				char character = value.toUpperCase().toCharArray()[i];
				if(character != ' ') {
					stringBuilder.append(character);
				}
			}

			// now that we have all the characters, add a space before the last
			// three digits
			int offset = stringBuilder.length() - 3;
			if(offset > 0) {
				stringBuilder.insert(offset, ' ');
			}
			this.value = stringBuilder.toString();
		}
	}

	public Object getParsedValue() {
		return(parsedValue);
	}

	public void setMinLength(int minLength) {
		// do nothing - this is always going to be 6
	}

	public int getMinLength() {
		return(5);
	}

	public void setMaxLength(int maxLength) {
		// do nothing - this is always going to be 8
	}

	public int getMaxLength() {
		return(8);
	}
}
