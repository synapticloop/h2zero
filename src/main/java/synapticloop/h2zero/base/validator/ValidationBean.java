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
