package synapticloop.h2zero.model;

/*
 * Copyright (c) 2015-2017 synapticloop.
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
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class Question extends BaseQueryObject {

	/**
	 * Create a new question SQL query object from the passed in JSON object
	 * 
	 * @param tableOrView the table to which this question belongs
	 * @param questionObject the jsonObject that contains the question items
	 * 
	 * @throws H2ZeroParseException - if something went wrong with the parsing 
	 */
	public Question(BaseSchemaObject tableOrView, JSONObject questionObject) throws H2ZeroParseException {
		super(tableOrView, questionObject);

		// set up the default allowable keys
		allowableJsonKeys.put(JSONKeyConstants.SELECT_CLAUSE, UsageType.MANDATORY);

		if(null == selectClause) {
			throw new H2ZeroParseException("Questions must always have a '" + JSONKeyConstants.SELECT_CLAUSE + "' and return one and only one boolean object.");
		}

		populateWhereFields(questionObject);
	}

	public String getType() {
		return("Question");
	}
}
