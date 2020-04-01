package synapticloop.h2zero.base.validator;

/*
 * Copyright (c) 2013-2020 synapticloop.
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
		if(null == value && !baseField.getNullable()) {
			isValid = false;
			return;
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
