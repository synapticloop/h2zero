package synapticloop.h2zero.model.field.validator;

/*
 * Copyright (c) 2012-2019 synapticloop.
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
