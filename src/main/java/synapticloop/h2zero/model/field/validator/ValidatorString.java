package synapticloop.h2zero.model.field.validator;

public class ValidatorString extends ValidatorBase {

	public ValidatorString(int minLength, int maxLength, boolean allowNull, boolean requiresConfirm) {
		super(minLength, maxLength, allowNull, requiresConfirm);
	}

	@Override
	public boolean validate(String field, String confirmField) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
