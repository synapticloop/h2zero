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
