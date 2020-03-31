package synapticloop.h2zero.model.field;

/*
 * Copyright (c) 2012-2020 synapticloop.
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

public class BlobField extends BaseField {

	public BlobField(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
	}

	public BlobField(JSONObject jsonObject, boolean isInField) throws H2ZeroParseException {
		super(jsonObject, isInField);
	}

	@Override
	public String getJavaType() {
		return "Blob";
	}

	@Override
	public String getSqlJavaType() {
		return("Blob");
	}

	@Override
	public String getSqlNullType() {
		return("BLOB");
	}

	@Override
	public boolean getShouldEscape() {
		return true;
	}

	@Override
	public boolean getIsLargeObject() {
		return(true);
	}

}
