package synapticloop.h2zero.model.field;

/*
 * Copyright (c) 2013-2024 synapticloop.
 * 
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

import java.math.BigDecimal;

public class NumericField extends BaseField {

	public NumericField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public NumericField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}

	public String getJavaType() {
		return("BigDecimal");
	}

	public String getSqlJavaType() {
		return("BigDecimal");
	}

	public String getSqlNullType() {
		return("NUMERIC");
	}

	public String getLengthFormat() {
		StringBuilder stringBuilder = new StringBuilder();
		if(length != 0) {
			stringBuilder.append("(");
			stringBuilder.append(length);
			stringBuilder.append(",");
			stringBuilder.append(decimalLength);
			stringBuilder.append(")");
		}
		return(stringBuilder.toString());
	}

	public boolean getShouldEscape() {
		return false;
	}

	@Override public String getSqlTestValue() { return("new java.math.BigDecimal(\"0.0\")"); }

}
