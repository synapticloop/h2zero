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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class Updater {
	private String name;
	private String setClause;
	private String whereClause;
	private ArrayList<BaseField> setFields = new ArrayList<BaseField>();
	private ArrayList<BaseField> whereFields = new ArrayList<BaseField>();
	private LinkedHashMap<String, BaseField> uniqueUpdateFields = new LinkedHashMap<String, BaseField>();
	private ArrayList<BaseField> updateFields = new ArrayList<BaseField>();

	public Updater(JSONObject jsonObject, Table table) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.setClause = JsonHelper.getStringValue(jsonObject, "setClause", null);
		this.whereClause = JsonHelper.getStringValue(jsonObject, "whereClause", null);

		// now for the set fields
		try {
			JSONArray setFieldArray = jsonObject.getJSONArray("setFields");
			for (int i = 0; i < setFieldArray.length(); i++) {
				String setFieldName = setFieldArray.getString(i);
				BaseField baseField = table.getField(setFieldName);
				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up set field '" + setFieldName + "'.");
				}
				setFields.add(baseField);

				BaseField updateBaseField = table.getSetField(setFieldName);

				if(!uniqueUpdateFields.containsKey(updateBaseField.getJavaName())) {
					uniqueUpdateFields.put(updateBaseField.getJavaName(), updateBaseField);
				}

				updateFields.add(updateBaseField);
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}

		// and the where fields
		try {
			JSONArray whereFieldArray = jsonObject.getJSONArray("whereFields");
			for (int i = 0; i < whereFieldArray.length(); i++) {
				String whereFieldName = whereFieldArray.getString(i);
				BaseField baseField = table.getField(whereFieldName);
				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up where field '" + whereFieldName + "', for updater '" + name + "'.");
				}

				BaseField updateBaseField = table.getWhereField(whereFieldName);
				updateFields.add(updateBaseField);

				if(!uniqueUpdateFields.containsKey(updateBaseField.getJavaName())) {
					uniqueUpdateFields.put(updateBaseField.getJavaName(), updateBaseField);
				}
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}

		if(null == name) {
			throw new H2ZeroParseException("The updater 'name' attribute cannot be null.");
		}
	}

	public String getName() { return(name); }
	public String getSetClause() { return(setClause); }
	public void setSetClause(String setClause) { this.setClause = setClause; }
	public String getWhereClause() { return(whereClause); }
	public void setWhereClause(String whereClause) { this.whereClause = whereClause; }
	public ArrayList<BaseField> getWhereFields() { return(whereFields); }
	public ArrayList<BaseField> getSetFields() { return(setFields); }
	public ArrayList<BaseField> getUpdateFields() { return(updateFields); }
	public Collection<BaseField> getUniqueUpdateFields() { return(uniqueUpdateFields.values()); }

	public String getStaticName() { return(NamingHelper.getStaticName(name)); }
}
