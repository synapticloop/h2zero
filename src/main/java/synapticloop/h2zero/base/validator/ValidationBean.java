package synapticloop.h2zero.base.validator;

import synapticloop.h2zero.model.field.BaseField;

public class ValidationBean {
	private BaseField[] baseFields;
	private boolean isValid = true;

	public ValidationBean(BaseField ... baseFields) {
		this.baseFields = baseFields;
	}

	public void validate() {
		for (int i = 0; i < baseFields.length; i++) {
			BaseField baseField = baseFields[i];
			
		}
	}

	public boolean getIsValid() {
		return(isValid);
	}
}
