package com.synapticloop.h2zero.generator.model;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.generator.exception.H2ZeroParseException;
import com.synapticloop.h2zero.generator.model.field.BaseField;

import java.util.ArrayList;
import java.util.List;

/**
 * Used when the table is a constant - i.e. no need to go to the database to 
 * determine what the values are.
 * 
 * @author synapticloop
 *
 */
public class ConstantCache {
	private final List<String> caches = new ArrayList<>();
	private final String cacheName; // the name of the cache
	private BaseField cacheField; // the cacheField that it is based on

	public ConstantCache(String fieldName, Table table) throws H2ZeroParseException {
		// now we need to go through the list of cache_names and create them
		BaseField field = table.getField(fieldName);
		if(null == field) {
			throw new H2ZeroParseException(String.format("Cannot find the field '%s' on table '%s' for the constant cache", fieldName, table.name));
		}

		if(!field.getUnique()) {
			throw new H2ZeroParseException(String.format("Cannot create a constant cache for the field '%s' on table '%s' as it is not unique.", fieldName, table.name));
		}

		if(table.getConstants().isEmpty()) {
			throw new H2ZeroParseException(String.format("Cannot create a constant cache for field '%s' when there are no defined constants for the table '%s'.", fieldName, table.name));
		}

		// now that we have the field name
		cacheName = "LOOKUP_" + fieldName.toUpperCase();

		// we need toi know what index we are looking for in the field list

		int i = 0;

		for (BaseField baseField : table.getFields()) {
			if(baseField.getName().equals(fieldName)) {
				cacheField = baseField;
				break;
			}
			i++;
		}

		// now we need to go through and create the cacheable values
		for (Constant constant : table.getConstants()) {
			for (Object value : constant.getValues()) {
				
			}

		}

		// now we need to go through the

//		constantsCacheObject.getJSONArray()
//
//		try {
//			this.name = constantsObject.optString(JSONKeyConstants.NAME);
//			if(null == name) {
//				throw new H2ZeroParseException(JSONKeyConstants.NAME + " cannot be null for constant object '" + constantsObject + "'.");
//			}
//
//			this.name = this.name.toUpperCase().replaceAll("[^A-Z]", "_");
//
//			JSONArray valuesArray = constantsObject.getJSONArray(JSONKeyConstants.VALUES);
//			for(int i = 0; i < valuesArray.length(); i++) {
//				Object object = valuesArray.get(i);
//
//
//				if(object instanceof String) {
//					sqlValues.add("'" + ((String)object).replaceAll("'", "''") + "'");
//					String stringified = "\"" + object + "\"";
//					values.add(stringified);
//					// the first value must be the primary key
//					if(i == 0) {
//						this.primaryKeyValue = stringified;
//					}
//				} else {
//					sqlValues.add(object);
//					String stringified = table.getFields().get(i).getJavaType() + ".valueOf(" + object + ")";
//					values.add(stringified);
//					if(i == 0) {
//						this.primaryKeyValue = stringified;
//						this.primaryKeyJavaType = table.getFields().get(i).getJavaType();
//					}
//				}
//
//
//			}
//
//		} catch (JSONException ex) {
//			throw new H2ZeroParseException("Could not parse constants object '" + constantsObject + "'.", ex);
//		}
	}

	public String getCacheName() { return (cacheName); }

	public BaseField getCacheField() { return (cacheField); }
}
