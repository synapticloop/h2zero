package synapticloop.h2zero.model;

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
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class Counter extends BaseQueryObject {
	private boolean isAutoCounter = false; // whether this is an automatically generated counter (i.e. from the fieldCounters JSON key)

	/**
	 * Create a new counter SQL query object from the passed in JSON object
	 * 
	 * @param tableOrView the table or view to which this counter belongs
	 * @param counterObject the jsonObject that contains the counter items
	 * 
	 * @throws H2ZeroParseException - if something went wrong with the parsing 
	 */
	public Counter(BaseSchemaObject tableOrView, JSONObject counterObject) throws H2ZeroParseException {
		super(tableOrView, counterObject);

		// set up the default allowable keys
		allowableJsonKeys.put(JSONKeyConstants.UNIQUE, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.SELECT_FIELDS, UsageType.INVALID);

		if(null == selectClause) {
			// automatically add one 
			this.selectClause = "select count(*) from " + tableOrView.getName() + " ";
		}

		populateWhereFields(counterObject);
	}

	@Override public String getType() { return("Counter"); }

	public boolean getIsAutoCounter() { return(isAutoCounter); }

	public void setIsAutoCounter(boolean isAutoCounter) {this.isAutoCounter = isAutoCounter; }

	@Override public boolean getIsAutoGenerated() {
		return(isAutoCounter);
	}

}
