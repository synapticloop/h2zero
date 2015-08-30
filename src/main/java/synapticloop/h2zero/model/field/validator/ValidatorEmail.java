package synapticloop.h2zero.model.field.validator;

import org.apache.commons.validator.routines.EmailValidator;


public class ValidatorEmail extends ValidatorBase {

	public ValidatorEmail(int minLength, int maxLength, boolean allowNull, boolean requiresConfirm) {
		super(minLength, maxLength, allowNull, requiresConfirm);
	}

	@Override
	public boolean validate(String field, String fieldConfirm) {
		boolean isValid = isValidConfirm(field, fieldConfirm);

		if(allowNull && isValid && null == convertToNullIfEmpty(field)) {
			return(true);
		}

		if(isValid) {
			// do our validation
			EmailValidator emailValidator = EmailValidator.getInstance(false);
			return(emailValidator.isValid(field));
		}
		return(false);
	}
}
