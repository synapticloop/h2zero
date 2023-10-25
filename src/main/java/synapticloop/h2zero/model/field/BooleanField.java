package synapticloop.h2zero.model.field;

/*
 * Copyright (c) 2015-2023 synapticloop.
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


import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class BooleanField extends BaseField {

	public BooleanField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}
	
	public BooleanField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}

	@Override
	public String getJavaType() {
		return("Boolean");
	}

	@Override
	public String getSqlJavaType() {
		return("Boolean");
	}

	@Override
	public String getSqlNullType() {
		return("BOOLEAN");
	}

	@Override
	public boolean getShouldEscape() {
		return false;
	}

}
