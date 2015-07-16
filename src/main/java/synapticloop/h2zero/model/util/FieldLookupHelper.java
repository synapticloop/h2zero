package synapticloop.h2zero.model.util;

/*
 * Copyright (c) 2015 synapticloop.
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

import synapticloop.h2zero.model.BaseSchemaObject;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class FieldLookupHelper {
	private static final String IN_DESIGNATOR = "in:";

	private FieldLookupHelper() {}

	/**
	 * Get the base field from the base schema object (table or view) with the specified name
	 * 
	 * @param baseSchemaObject the schema object to look up for the field
	 * @param fieldName the name of the field to lookup
	 * 
	 * @return the base field object that was looked up
	 */
	public static BaseField getBaseField(BaseSchemaObject baseSchemaObject, String fieldName) {
		BaseField baseField = null;
		if(fieldName.startsWith(IN_DESIGNATOR)) {
			fieldName = fieldName.substring(3);

			if(fieldName.contains(".")) {
				// we are doing a table lookup
				String[] splits = fieldName.split("\\.", 2);
				String tableName = splits[0];
				String tableFieldName = splits[1];
				Table tableLookup = Database.getTableLookup(tableName);
				if(null == tableLookup) {
					return(null);
				} else {
					return(tableLookup.getInField(tableFieldName));
				}
			}

			baseField = baseSchemaObject.getInField(fieldName);
		} else {
			if(fieldName.contains(".")) {
				// we are doing a table lookup
				String[] splits = fieldName.split("\\.", 2);
				String tableName = splits[0];
				String tableFieldName = splits[1];

				Table tableLookup = Database.getTableLookup(tableName);
				if(null == tableLookup) {
					return(null);
				} else {
					return(tableLookup.getField(tableFieldName));
				}
			}
			baseField = baseSchemaObject.getField(fieldName);
		}
		return(baseField);
	}

	/**
	 * Return whether a field has an in field designator
	 * 
	 * @param fieldName the field name to test
	 * @return whether the field has an in field designator
	 */
	public static boolean hasInFieldDesignator(String fieldName) {
		return(fieldName.startsWith(IN_DESIGNATOR));
	}
}
