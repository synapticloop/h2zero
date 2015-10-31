package synapticloop.h2zero.model.field.validator;

public abstract class ValidatorBase {
	protected boolean requiresConfirm = false;
	protected boolean allowNull = false;
	protected int maxLength = Integer.MAX_VALUE;
	protected int minLength = 0;

	public ValidatorBase(int minLength, int maxLength, boolean allowNull, boolean requiresConfirm) {
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.allowNull = allowNull;
		this.requiresConfirm = requiresConfirm;
	}

	public abstract boolean validate(String field, String fieldConfirm);

	public boolean isValid(String field) {
		if(null == field) {
			return(allowNull);
		} else {
			int length = field.length();
			return(length >= minLength && length <= maxLength);
		}
	}
	/**
	 * Determine whether the field is valid with a confirmation field presented as well
	 * 
	 * @param field the field to validate
	 * @param fieldConfirm the confirm field to validate
	 * 
	 * @return whether the field and the confirm field are the same
	 */
	public boolean isValidConfirm(String field, String fieldConfirm) {
		String tempField = convertToNullIfEmpty(field);
		String tempFieldConfirm = convertToNullIfEmpty(fieldConfirm);

		// check for null and null
		if(null == tempField) {
			if(allowNull) {
				return(null == tempFieldConfirm);
			} else {
				// we don't care what the confirm is at this point
				return(false);
			}
		}

		// at this point tempField is not null
		if(requiresConfirm) { 
			if(field.equals(tempFieldConfirm)) {
				return(isValid(tempField));
			}
		} else {
			return(isValid(tempField));
		}
		return(false);
	}

	protected String convertToNullIfEmpty(String field) {
		if(null == field) {
			return(null);
		}

		if(field.trim().length() == 0) {
			return(null);
		}

		return(field);
	}
	public boolean getAllowNull() { return this.allowNull; }
	public void setAllowNull(boolean allowNull) { this.allowNull = allowNull; }
}
