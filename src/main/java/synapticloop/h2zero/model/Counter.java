package synapticloop.h2zero.model;

/*
 * Copyright (c) 2013-2015 synapticloop.
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

public class Counter extends BaseQueryObject {

	/**
	 * Create a new counter SQL query object from the passed in JSON object
	 * 
	 * @param table the table to which this counter belongs
	 * @param counterObject the jsonObject that contains the counter items
	 * 
	 * @throws H2ZeroParseException - if something went wrong with the parsing 
	 */

	public Counter(Table table, JSONObject counterObject) throws H2ZeroParseException {
		super(table, counterObject);

		// set up the default allowable keys
		allowableJsonKeys.put(JSONKeyConstants.UNIQUE, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.SELECT_FIELDS, UsageType.INVALID);

		if(null == selectClause) {
			// automatically add one 
			this.selectClause = "select count(*) from " + table.getName() + " ";
		}

		populateWhereFields(counterObject);

		if(null == name) {
			throw new H2ZeroParseException("The counter '" + JSONKeyConstants.NAME + "' attribute cannot be null.");
		}
	}

	public String getBaseQueryObjectType() {
		return("Counter");
	}
}
