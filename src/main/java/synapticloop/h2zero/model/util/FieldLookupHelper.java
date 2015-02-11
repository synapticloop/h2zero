package synapticloop.h2zero.model.util;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class FieldLookupHelper {
	public static boolean hasInFields(String fieldName) {
		return(fieldName.startsWith("in:"));
	}

	public static BaseField getBaseField(Table table, String fieldName) {
		BaseField baseField = null;
		if(fieldName.startsWith("in:")) {
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

			baseField = table.getInField(fieldName);
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
			baseField = table.getField(fieldName);
		}
		return(baseField);
	}

}
