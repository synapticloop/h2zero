package synapticloop.h2zero.model;

/*
 * Copyright (c) 2020 synapticloop.
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

public class Upserter extends BaseQueryObject {
	private static final String MODEL_TYPE = "Upserter";

	/**
	 * Create an upserter object
	 * 
	 * @param baseSchemaObject The base schema object to attach to
	 * @param upserterObject The JSON object that encapsulates the upserter
	 * 
	 * @throws H2ZeroParseException If there was an error parsing the JSON upserter 
	 *   object
	 */
	public Upserter(BaseSchemaObject baseSchemaObject, JSONObject upserterObject) throws H2ZeroParseException {
		super(baseSchemaObject, upserterObject);

	}

	@Override
	public String getType() { return MODEL_TYPE; }
}
