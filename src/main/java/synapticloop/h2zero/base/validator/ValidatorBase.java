package synapticloop.h2zero.base.validator;

import synapticloop.h2zero.base.validator.bean.ValidationFieldBean;

/*
 * Copyright (c) 2013-2024 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import synapticloop.h2zero.model.field.BaseField;

public abstract class ValidatorBase {
	private boolean isValid = false;

	protected String nmField = null;
	protected String value = null;
	private int minLength = 0;
	private int maxLength = Integer.MAX_VALUE;
	private boolean allowNull = false;

	private boolean hasNullableError = false;
	private boolean hasConfirmError = false;
	private boolean hasLengthError = false;
	private boolean hasGenericError = false;

	private boolean shouldValidateLength = false;

	public ValidatorBase(String nmField, String value, int minLength, int maxLength, boolean allowNull, boolean shouldValidateLength) {
		this.nmField = nmField;

		this.value = value.trim();
		if(this.value.length() == 0) {
			this.value = null;
		}

		this.minLength = minLength;
		this.maxLength = maxLength;
		this.allowNull = allowNull;

		this.shouldValidateLength = shouldValidateLength;
	}

	public ValidationFieldBean validate() {
		ValidationFieldBean validationFieldBean= new ValidationFieldBean(nmField, value);

		// first thing is to determine whether it is null
		if(null == value) {
			if(!allowNull) {
				// we don't allow nulls
				validationFieldBean.setIsIncorrectNullability(true);
				validationFieldBean.setIsUnderLength(true);
			} else {
				// we allow nulls, and it is null - so do nothing
			}

			return(validationFieldBean);
		}

		if(shouldValidateLength) {
			// at this point the value will not be null, time to check the length
			if(value.length() < minLength) {
				validationFieldBean.setIsUnderLength(true);
			}
	
			if(value.length() > maxLength) {
				validationFieldBean.setIsOverLength(true);
			}
		}

		return(validationFieldBean);
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
