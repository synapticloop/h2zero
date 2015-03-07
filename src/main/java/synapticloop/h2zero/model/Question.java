package synapticloop.h2zero.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class Question {
	private String name;
	private String selectClause;
	private String whereClause;
	private String orderBy;

	private ArrayList<BaseField> whereFields = new ArrayList<BaseField>();
	private LinkedHashMap<String, BaseField> uniqueWhereFields = new LinkedHashMap<String, BaseField>();
	private ArrayList<BaseField> selectFields = new ArrayList<BaseField>();

	private boolean hasInFields = false;
	private ArrayList<BaseField> inWhereFields = new ArrayList<BaseField>();

	public Question(JSONObject counterObject, Table table) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(counterObject, JSONKeyConstants.JSON_KEY_NAME, null);
		this.orderBy = JsonHelper.getStringValue(counterObject, JSONKeyConstants.JSON_KEY_ORDER_BY, null);
		this.selectClause = JsonHelper.getStringValue(counterObject, JSONKeyConstants.JSON_KEY_SELECT_CLAUSE, null);
		// if we have a select clause then we are returning a bean...

		// now for the select fields
		if(null == selectClause) {
			throw new H2ZeroParseException("Questions must always have a '" + JSONKeyConstants.JSON_KEY_SELECT_CLAUSE + "' and return one and only boolean object.");
		}

		// now for the where clauses
		this.whereClause = JsonHelper.getStringValue(counterObject, JSONKeyConstants.JSON_KEY_WHERE_CLAUSE, null);
		// we may not have any whereFields
		try {
			JSONArray whereFieldArray = counterObject.getJSONArray(JSONKeyConstants.JSON_KEY_WHERE_FIELDS);

			if(null == whereClause && whereFieldArray.length() > 0) {
				throw new H2ZeroParseException("Question '" + name + "' cannot have '" + JSONKeyConstants.JSON_KEY_WHERE_FIELDS + "' when there is no '" + JSONKeyConstants.JSON_KEY_WHERE_CLAUSE + "'.");
			}

			for (int i = 0; i < whereFieldArray.length(); i++) {
				String whereFieldName = whereFieldArray.getString(i);

				BaseField baseField = getBaseField(table, whereFieldName);

				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up where field '" + whereFieldName + "', for question '" + name + "'.");
				}

				whereFields.add(baseField);
				if(baseField.getIsInField()) {
					inWhereFields.add(baseField);
				}

				if(!uniqueWhereFields.containsKey(whereFieldName)) {
					uniqueWhereFields.put(whereFieldName, baseField);
				}
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}

		if(null == name) {
			throw new H2ZeroParseException("The question '"  + JSONKeyConstants.JSON_KEY_NAME + "' attribute cannot be null.");
		}
	}


	private BaseField getBaseField(Table table, String fieldName) {
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
			this.hasInFields = true;
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

	public String getName() { return(name); }
	public String getQuestionTagName() { return(NamingHelper.getFirstUpper(name)); }
	public String getWhereClause() { return(whereClause); }
	public String getOrderBy() { return(orderBy); }
	public ArrayList<BaseField> getWhereFields() { return(whereFields); }
	public ArrayList<BaseField> getSelectFields() { return(selectFields); }
	public ArrayList<BaseField> getInWhereFields() { return(inWhereFields); }
	public boolean getHasInFields() { return(hasInFields); }
	public String getSelectClause() { return selectClause; }
	public void setSelectClause(String selectClause) { this.selectClause = selectClause; }

	public String getStaticName() { return(NamingHelper.getStaticName(name)); }
	public Collection<BaseField> getUniqueWhereFields() { return(this.uniqueWhereFields.values()); }
}
