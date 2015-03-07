package synapticloop.h2zero.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.FieldLookupHelper;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public abstract class BaseQueryObject {
	protected JSONObject jsonObject = null;
	protected Table table = null;

	protected String name;
	protected String selectClause;
	protected String whereClause;
	protected String insertClause;
	protected String valuesClause;
	protected String orderBy;

	// where fields and their associated properties
	protected ArrayList<BaseField> whereFields = new ArrayList<BaseField>();
	protected LinkedHashMap<String, BaseField> uniqueWhereFields = new LinkedHashMap<String, BaseField>();
	protected ArrayList<BaseField> inWhereFields = new ArrayList<BaseField>();
	protected boolean hasInFields = false;

	protected ArrayList<BaseField> selectFields = new ArrayList<BaseField>();
	protected LinkedHashMap<String, BaseField> uniqueSelectFields = new LinkedHashMap<String, BaseField>();

	protected ArrayList<BaseField> valueFields = new ArrayList<BaseField>();
	protected LinkedHashMap<String, BaseField> uniqueValueFields = new LinkedHashMap<String, BaseField>();

	protected ArrayList<BaseField> allFields = new ArrayList<BaseField>();

	protected HashSet<String> allUniqueFieldNames = new HashSet<String>();

	protected ArrayList<BaseField> allUniqueFields = new ArrayList<BaseField>();

	protected String setClause;
	protected ArrayList<BaseField> setFields = new ArrayList<BaseField>();
	protected LinkedHashMap<String, BaseField> uniqueUpdateFields = new LinkedHashMap<String, BaseField>();
	protected ArrayList<BaseField> updateFields = new ArrayList<BaseField>();

	protected BaseQueryObject(Table table, JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		this.table = table;
		this.name = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.NAME, null);
		this.whereClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.WHERE_CLAUSE, null);
		this.orderBy = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.ORDER_BY, null);
		this.selectClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.SELECT_CLAUSE, null);

		this.insertClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.INSERT_CLAUSE, null);
		this.valuesClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.VALUES_CLAUSE, null);
		this.setClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.SET_CLAUSE, null);
	}

	protected void populateWhereFields(JSONObject jsonObject) throws H2ZeroParseException {
		// we may not have any whereFields
		try {
			JSONArray whereFieldArray = jsonObject.getJSONArray(JSONKeyConstants.WHERE_FIELDS);

			if(null == whereClause && whereFieldArray.length() > 0) {
				throw new H2ZeroParseException(this.getClass().getSimpleName() + " '" + name + "' cannot have '" + JSONKeyConstants.WHERE_FIELDS + "' when there is no '" + JSONKeyConstants.WHERE_CLAUSE + "'.");
			}

			for (int i = 0; i < whereFieldArray.length(); i++) {
				String whereFieldName = whereFieldArray.getString(i);

				BaseField baseField = FieldLookupHelper.getBaseField(table, whereFieldName);

				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up where field '" + whereFieldName + "', for " + this.getClass().getSimpleName() + "'" + name + "'.");
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
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void populateFields(JSONObject jsonObject, String jsonKey, ArrayList<BaseField> fields, LinkedHashMap<String, BaseField> uniqueFields) throws H2ZeroParseException {

		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray(jsonKey);
		} catch (JSONException ojjsonex) {
			// do nothing
			return;
		}

		for (int i = 0; i < fieldJson.length(); i++) {
			String type = null;
			JSONObject fieldObject = null;

			try {
				fieldObject = fieldJson.getJSONObject(i);
				type = fieldObject.getString("type");

				// check to ensure that the field has a name
				fieldObject.getString("name");
			} catch (JSONException ojjsonex) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Could not parse the '");
				stringBuilder.append(jsonKey);
				stringBuilder.append("' array.\n");
				stringBuilder.append("Was expecting the format to be:\n");
				stringBuilder.append("\"");
				stringBuilder.append(jsonKey);
				stringBuilder.append("\": [\n");

				stringBuilder.append("  { \"name\": \"<fieldName1>\", \"type\": \"<type>\" },\n");
				stringBuilder.append("  { \"name\": \"<fieldName2>\", \"type\": \"<type>\" },\n");
				stringBuilder.append("]\n");
				throw new H2ZeroParseException(stringBuilder.toString());
			}

			if(null != type) {
				String firstUpper = NamingHelper.getFirstUpper(type);
				try {
					Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
					Constructor constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField)constructor.newInstance(fieldObject);

					fields.add(baseField);
					addToAllFields(baseField);

					String baseFieldName = baseField.getName();
					if(!uniqueFields.containsKey(baseFieldName)) {
						uniqueFields.put(baseFieldName, baseField);
					}

				} catch (Exception ex) {
					throw new H2ZeroParseException(ex.getMessage());
				}
			}
		}
	}

	private void addToAllFields(BaseField baseField) {
		allFields.add(baseField);

		if(!allUniqueFieldNames.contains(baseField.getName())) {
			allUniqueFields.add(baseField);
		}

		allUniqueFieldNames.add(baseField.getName());
	}

	public String getName() { return(name); }
	public String getTagName() { return(NamingHelper.getFirstUpper(name)); }
	public String getWhereClause() { return(whereClause); }
	public String getOrderBy() { return(orderBy); }
	public ArrayList<BaseField> getWhereFields() { return(whereFields); }
	public ArrayList<BaseField> getSelectFields() { return(selectFields); }
	public ArrayList<BaseField> getInWhereFields() { return(inWhereFields); }
	public boolean getHasInFields() { return(hasInFields); }
	public String getSelectClause() { return selectClause; }
	public void setSelectClause(String selectClause) { this.selectClause = selectClause; }
	public void setWhereClause(String whereClause) { this.whereClause = whereClause; }

	public String getStaticName() { return(NamingHelper.getStaticName(name)); }
	public Collection<BaseField> getUniqueWhereFields() { return(this.uniqueWhereFields.values()); }

	public String getInsertClause() { return insertClause; }
	public String getValuesClause() { return valuesClause; }

	public boolean getHasInsertClause() { return(null != insertClause); }
	public boolean getHasSelectClause() { return(null != selectClause); }
	public boolean getHasValuesClause() { return(null != valuesClause); }
	public boolean getHasWhereClause() { return(null != whereClause); }

	public ArrayList<BaseField> getValueFields() { return valueFields; }
	public LinkedHashMap<String, BaseField> getUniqueValueFields() { return uniqueValueFields; }

	public Collection<BaseField> getUniqueSelectFields() { return(uniqueSelectFields.values()); }

	public ArrayList<BaseField> getAllFields() { return allFields; }
	public ArrayList<BaseField> getAllUniqueFields() { return allUniqueFields; }

	public String getSetClause() { return(setClause); }
	public void setSetClause(String setClause) { this.setClause = setClause; }
	public ArrayList<BaseField> getSetFields() { return(setFields); }
	public ArrayList<BaseField> getUpdateFields() { return(updateFields); }
	public Collection<BaseField> getUniqueUpdateFields() { return(uniqueUpdateFields.values()); }
}
