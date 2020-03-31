package synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2020 synapticloop.
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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class Constant {
	private List<Object> values = new ArrayList<Object>();
	private List<Object> sqlValues = new ArrayList<Object>();
	private String name = null;
	private Object primaryKeyValue = null;

	// this should look like:
	// { "name": "BOOK", "values": [ 1, "book" ] },

	public Constant(JSONObject constantsObject, Table table) throws H2ZeroParseException {

		try {
			this.name = constantsObject.optString(JSONKeyConstants.NAME);
			if(null == name) {
				throw new H2ZeroParseException(JSONKeyConstants.NAME + " cannot be null for constant object '" + constantsObject + "'.");
			}

			this.name = this.name.toString().toUpperCase().replaceAll("[^A-Z]", "_");

			JSONArray valuesArray = constantsObject.getJSONArray(JSONKeyConstants.VALUES);
			for(int i = 0; i < valuesArray.length(); i++) {
				Object object = valuesArray.get(i);


				if(object instanceof String) {
					sqlValues.add("'" + ((String)object).replaceAll("'", "''") + "'");
					String stringified = "\"" + object + "\"";
					values.add(stringified);
					// the first value must be the primary key
					if(i == 0) {
						this.primaryKeyValue = stringified;
					}
				} else {
					sqlValues.add(object);
					String stringified = "new " + table.getFields().get(i).getJavaType() + "(" + object + ")";
					values.add(stringified);
					if(i == 0) {
						this.primaryKeyValue = stringified;
					}
				}


			}

		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not parse constants object '" + constantsObject + "'.", jsonex);
		}
	}

	public List<Object> getValues() { return values; }
	public List<Object> getSqlValues() { return sqlValues; }
	public String getName() { return name; }
	public Object getPrimaryKeyValue() { return primaryKeyValue; }
	public void setPrimaryKeyValue(Object primaryKeyValue) { this.primaryKeyValue = primaryKeyValue; }
}
