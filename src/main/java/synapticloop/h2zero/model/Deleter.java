package synapticloop.h2zero.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.FieldLookupHelper;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

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

public class Deleter {
	private String name;
	private String whereClause;
	private ArrayList<BaseField> whereFields = new ArrayList<BaseField>();

	public Deleter(JSONObject jsonObject, Table table) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.whereClause = JsonHelper.getStringValue(jsonObject, "whereClause", null);
		// now for the where fields
		try {
			JSONArray whereFieldArray = jsonObject.getJSONArray("whereFields");
			for (int i = 0; i < whereFieldArray.length(); i++) {
				String whereFieldName = whereFieldArray.getString(i);
				BaseField baseField = FieldLookupHelper.getBaseField(table, whereFieldName);
//				this.hasInFields = FieldLookupHelper.hasInFields(whereFieldName);

				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up where field '" + whereFieldName + "', for deleter '" + name + "'.");
				}
				whereFields.add(baseField);
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}

		if(null == name) {
			throw new H2ZeroParseException("The deleter 'name' attribute cannot be null.");
		}
	}

	public String getName() { return(name); }
	public String getWhereClause() { return(whereClause); }
	public void setWhereClause(String whereClause) { this.whereClause = whereClause; }
	public ArrayList<BaseField> getWhereFields() { return(whereFields); }

	public String getStaticName() { return(NamingHelper.getStaticName(name)); }

}
