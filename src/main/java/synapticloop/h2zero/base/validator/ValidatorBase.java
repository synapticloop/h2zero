package synapticloop.h2zero.base.validator;

import synapticloop.h2zero.model.field.BaseField;

public abstract class ValidatorBase {
	protected BaseField baseField = null;

	public ValidatorBase(BaseField baseField) {
		this.baseField = baseField;
	}

	public void validateDefault() {
		
	}

	public boolean isValid() {
		return(false);
	}
}
