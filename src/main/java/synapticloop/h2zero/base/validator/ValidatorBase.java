package synapticloop.h2zero.base.validator;

import synapticloop.h2zero.model.field.BaseField;

public abstract class ValidatorBase {
	protected BaseField baseField = null;
	private boolean isValid = false;

	private boolean hasNullableError = false;
	private boolean hasConfirmError = false;
	private boolean hasLengthError = false;
	private boolean hasGenericError = false;

	public ValidatorBase(BaseField baseField) {
		this.baseField = baseField;
	}

	public void validateDefault(String value, String valueConfirm) {
		if(null == value) {
			if(!baseField.getNullable()) {
				isValid = false;
				return;
			}
		}

		if(baseField.getRequiresConfirm()) {
			hasConfirmError = value.equals(valueConfirm);
		}

	}

	public boolean getIsValid() { return(isValid); }
	public void setIsValid(boolean isValid) { this.isValid = isValid; }
	public boolean getHasNullableError() { return hasNullableError; }
	public void setHasNullableError(boolean hasNullableError) { this.hasNullableError = hasNullableError; }
	public boolean getHasConfirmError() { return hasConfirmError; }
	public void setHasConfirmError(boolean hasConfirmError) { this.hasConfirmError = hasConfirmError; }
	public boolean getHasLengthError() { return hasLengthError; }
	public void setHasLengthError(boolean hasLengthError) { this.hasLengthError = hasLengthError; }
	public boolean getHasGenericError() { return hasGenericError; }
	public void setHasGenericError(boolean hasGenericError) { this.hasGenericError = hasGenericError; }
}
