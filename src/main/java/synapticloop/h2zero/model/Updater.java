package synapticloop.h2zero.model;

/*
 * Copyright (c) 2013-2018 synapticloop.
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class Updater extends BaseQueryObject {
	private boolean isAutoUpdater = false;

	public Updater(Table table, JSONObject jsonObject) throws H2ZeroParseException {
		super(table, jsonObject);

		allowableJsonKeys.put(JSONKeyConstants.SET_CLAUSE, UsageType.MANDATORY);
		allowableJsonKeys.put(JSONKeyConstants.SET_FIELDS, UsageType.OPTIONAL);

		// now for the set fields
		try {
			JSONArray setFieldArray = jsonObject.getJSONArray(JSONKeyConstants.SET_FIELDS);
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

		// TODO - this needs both a set and where field...
		// this is probably going to end up with the wrong generation as the same value is used for the set and where fields
		// which the other base sql objects are not aware of...  This skips all of the aliases and in fields...
		try {
			JSONArray whereFieldArray = jsonObject.getJSONArray(JSONKeyConstants.WHERE_FIELDS);
			for (int i = 0; i < whereFieldArray.length(); i++) {
				BaseField updateBaseField = null;

				String whereFieldName = whereFieldArray.getString(i);
				if(whereFieldName.contains(".")) {
					// we are doing a table lookup
					String[] splits = whereFieldName.split("\\.", 2);
					String tableName = splits[0];
					String tableFieldName = splits[1];
					Table tableLookup = Database.getTableLookup(tableName);
					if(null != tableLookup) {
						updateBaseField = tableLookup.getField(tableFieldName);
					}
				} else {
					updateBaseField = table.getField(whereFieldName);
				}

				if(null == updateBaseField) {
					throw new H2ZeroParseException(String.format("Could not look up where field '%s', for %s '%s.%s'.", 
							whereFieldName,
							this.getType(),
							baseSchemaObject.getName(),
							name
							));
				}

//				BaseField updateBaseField = table.getWhereField(whereFieldName);
				updateFields.add(updateBaseField);

				if(!uniqueUpdateFields.containsKey(updateBaseField.getJavaName())) {
					uniqueUpdateFields.put(updateBaseField.getJavaName(), updateBaseField);
				}
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}
	}

	@Override
	public String getType() {
		return("Updater");
	}

	public boolean getIsAutoUpdater() { return isAutoUpdater; }

	public void setIsAutoUpdater(boolean isAutoUpdater) { this.isAutoUpdater = isAutoUpdater; }
}
