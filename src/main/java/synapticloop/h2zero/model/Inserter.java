package synapticloop.h2zero.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class Inserter extends BaseQueryObject {

	public Inserter(Table table, JSONObject inserterObject) throws H2ZeroParseException {
		super(table, inserterObject);


		if(null != selectClause) {
			populateFields(inserterObject, JSONKeyConstants.SELECT_FIELDS, selectFields, uniqueSelectFields);
		}

		if(null != whereClause) {
			populateFields(inserterObject, JSONKeyConstants.WHERE_FIELDS, whereFields, uniqueWhereFields);
		}

		// TODO - in the base query object class perhaps???
		// now for the value fields
		try {
			JSONArray valueFieldArray = inserterObject.getJSONArray(JSONKeyConstants.VALUE_FIELDS);
			for (int i = 0; i < valueFieldArray.length(); i++) {
				String valueFieldName = valueFieldArray.getString(i);
				BaseField baseField = table.getField(valueFieldName);
				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up value field '" + valueFieldName + "'.");
				}

				BaseField valueBaseField = table.getSetField(valueFieldName);

				if(!uniqueValueFields.containsKey(valueBaseField.getJavaName())) {
					uniqueValueFields.put(valueBaseField.getJavaName(), valueBaseField);
				}

				valueFields.add(valueBaseField);
				allFields.add(valueBaseField);
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}

		if(null == name) {
			throw new H2ZeroParseException("The inserter 'name' attribute cannot be null for table '" + table.getName() + "'.");
		}

		if(null != insertClause) {
			if(null == valuesClause && null == selectClause) {
				throw new H2ZeroParseException("The inserter '" + name + "' for table '" + table.getName() + "'has an insert clause, but neither valuesClause nor selectClause.");
			}
		} else {
			if(null != valuesClause && null != selectClause) {
				throw new H2ZeroParseException("The inserter '" + name + "' for table '" + table.getName() + "'has both a valuesClause and selectClause.");
			}
		}

		if(null != selectClause && valueFields.size() != 0) {
			throw new H2ZeroParseException("The inserter '" + name + "' for table '" + table.getName() + "'has selectClause and value fields.");
		}
	}

	public String getBaseQueryObjectType() {
		return("Inserter");
	}

}
