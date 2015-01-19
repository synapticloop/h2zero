package synapticloop.h2zero.model.form;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.util.JsonHelper;

public class Form {
	// a reference to the database, which contains all of the tables and fields
	private Database database = null;

	private String name = null;
	private String respondTo = null;

	private ArrayList<FormField> formFields = new ArrayList<FormField>();

	public Form(Database database, JSONObject jsonObject) throws H2ZeroParseException {
		this.database = database;
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.respondTo = JsonHelper.getStringValue(jsonObject, "respondTo", null);

		if(null == name) {
			throw new H2ZeroParseException("Cannot create a form with 'name' value of null");
		}

		if(null == respondTo) {
			throw new H2ZeroParseException("Cannot create a form with 'respondTo' value of null");
		}

		populateFields(jsonObject);
	}

	private void populateFields(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray("fields");
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("Cannot create a form bean without 'fields'.");
		}

		// good to go
		for (int i = 0; i < fieldJson.length(); i++) {
			String ref = null;
			String name = null;
			JSONObject fieldObject = null;
			BaseField baseField = null;
			try {
				fieldObject = fieldJson.getJSONObject(i);
				// we need either a ref or a name
				ref = JsonHelper.getStringValue(fieldObject, "ref", null);
				System.out.println("Form field ref of " + ref);
				if(ref != null) {
					baseField = database.getField(ref);
					if(null == baseField) {
						throw new H2ZeroParseException("A ref of '" + ref + "' was used, but not field could be found with this name.");
					} else {
						// we have the field and are good to go
						formFields.add(new FormField(baseField));
					}
				} else {
					//maybe we have a name???
					name = JsonHelper.getStringValue(fieldObject, "name", null);
					System.out.println("Form field name of " + name);
					if(null == name) {
						throw new H2ZeroParseException("A form field must have a name.");
					} else {
						formFields.add(new FormField(fieldObject));
					}
				}
				
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the 'fields' array.");
			}
		}
	}

	public String getName() { return(name); }
	public ArrayList<FormField> getFields() { return(formFields); }

	public void setRespondTo(String respondTo) {
		this.respondTo = respondTo;
	}

	public String getRespondTo() {
		return respondTo;
	}
}