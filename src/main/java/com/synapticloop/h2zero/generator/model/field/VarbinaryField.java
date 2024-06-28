package com.synapticloop.h2zero.generator.model.field;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.generator.exception.H2ZeroParseException;

public class VarbinaryField extends BaseField {

	public VarbinaryField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public VarbinaryField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}

	@Override
	public String getJavaType() {
		return "String";
	}

	@Override
	public String getSqlJavaType() {
		return("String");
	}

	@Override
	public String getSqlNullType() {
		return("VARBINARY");
	}

	@Override
	public boolean getShouldEscape() {
		return true;
	}

	@Override
	public boolean getIsLargeObject() {
		return(true);
	}

	@Override public String getSqlTestValue() { return("new java.sql.Blob()"); }

	@Override public boolean getRequiresLength() { return(true); }

}
