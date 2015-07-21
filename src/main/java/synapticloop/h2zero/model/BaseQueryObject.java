package synapticloop.h2zero.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public static enum UsageType {
		OPTIONAL,
		MANDATORY,
		INVALID
	}

	protected Map<String, UsageType> allowableJsonKeys = new HashMap<String, UsageType>();

	protected JSONObject jsonObject = null;
	protected BaseSchemaObject baseSchemaObject = null;

	protected String name; // the name of the query object
	protected String selectClause; // the select clause
	protected String whereClause; // the where clause
	protected String insertClause; // the insert clause
	protected String valuesClause; // the values clause
	protected String orderBy; // the order by clause

	protected Boolean jsonUniqueKey; // whether there is a 'unique' jsonKey for this object

	protected List<BaseField> allFields = new ArrayList<BaseField>();
	protected Set<String> allUniqueFieldNames = new HashSet<String>();
	protected List<BaseField> allUniqueFields = new ArrayList<BaseField>();

	// select fields
	protected List<BaseField> selectFields = new ArrayList<BaseField>();
	protected Map<String, BaseField> uniqueSelectFields = new LinkedHashMap<String, BaseField>();


	// where fields and their associated properties
	protected List<BaseField> whereFields = new ArrayList<BaseField>();
	protected Map<String, BaseField> uniqueWhereFields = new LinkedHashMap<String, BaseField>();
	protected List<BaseField> inWhereFields = new ArrayList<BaseField>();

	protected boolean hasInFields = false;
	private boolean hasWhereFieldAliases = false;


	protected List<BaseField> valueFields = new ArrayList<BaseField>();
	protected Map<String, BaseField> uniqueValueFields = new LinkedHashMap<String, BaseField>();

	protected String setClause;
	protected List<BaseField> setFields = new ArrayList<BaseField>();

	protected List<BaseField> updateFields = new ArrayList<BaseField>();
	protected Map<String, BaseField> uniqueUpdateFields = new LinkedHashMap<String, BaseField>();

	private List<String> comments = new ArrayList<String>();

	protected BaseQueryObject(BaseSchemaObject baseSchemaObject, JSONObject jsonObject) throws H2ZeroParseException {
		// set up the default allowable keys
		allowableJsonKeys.put(JSONKeyConstants.NAME, UsageType.MANDATORY);
		allowableJsonKeys.put(JSONKeyConstants.UNIQUE, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.SELECT_CLAUSE, UsageType.OPTIONAL);
		allowableJsonKeys.put(JSONKeyConstants.SELECT_FIELDS, UsageType.OPTIONAL);
		allowableJsonKeys.put(JSONKeyConstants.WHERE_CLAUSE, UsageType.OPTIONAL);
		allowableJsonKeys.put(JSONKeyConstants.WHERE_FIELDS, UsageType.OPTIONAL);
		allowableJsonKeys.put(JSONKeyConstants.INSERT_CLAUSE, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.VALUE_FIELDS, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.SET_CLAUSE, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.SET_FIELDS, UsageType.INVALID);
		allowableJsonKeys.put(JSONKeyConstants.ORDER_BY, UsageType.INVALID);

		this.baseSchemaObject = baseSchemaObject;
		this.jsonObject = jsonObject;

		this.name = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.NAME, null);
		this.selectClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.SELECT_CLAUSE, null);

		this.whereClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.WHERE_CLAUSE, null);

		this.insertClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.INSERT_CLAUSE, null);
		this.valuesClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.VALUES_CLAUSE, null);
		this.setClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.SET_CLAUSE, null);

		this.orderBy = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.ORDER_BY, null);
		this.jsonUniqueKey = JsonHelper.getBooleanValue(jsonObject, JSONKeyConstants.UNIQUE, null);

		if(null == name) {
			throw new H2ZeroParseException("The '" + getType() + "' '" + JSONKeyConstants.NAME + "' attribute cannot be null.");
		}

		JSONArray optJSONArray = jsonObject.optJSONArray(JSONKeyConstants.COMMENTS);
		if(null != optJSONArray) {
			for (int i = 0; i < optJSONArray.length(); i++) {
				String comment = optJSONArray.optString(i, null);
				if(null != comment) {
					comments.add(comment);
				}
			}
		}

	}

	public abstract String getType();

	/**
	 * Populate the where fields from the passed in json object
	 * 
	 * @param jsonObject the JSON object which is expected to have an array keyed on JSONKeyConstants.WHERE_FIELDS
	 * @throws H2ZeroParseException if there was an error parsing the where fields
	 */
	protected void populateWhereFields(JSONObject jsonObject) throws H2ZeroParseException {
		// we may not have any whereFields
		try {
			JSONArray whereFieldArray = jsonObject.getJSONArray(JSONKeyConstants.WHERE_FIELDS);

			if(null == whereClause && whereFieldArray.length() > 0) {
				throw new H2ZeroParseException(this.getType() + " '" + this.name + "' cannot have '" + JSONKeyConstants.WHERE_FIELDS + "' when there is no '" + JSONKeyConstants.WHERE_CLAUSE + "'.");
			}

			for (int i = 0; i < whereFieldArray.length(); i++) {
				// at this point we need to check to see whether we are getting an array of objects, or just plain Strings
				String whereFieldName = null;
				String whereFieldAlias = null;

				if(null != whereFieldArray.optJSONObject(i)) {
					JSONObject whereFieldObject = whereFieldArray.getJSONObject(i);
					whereFieldName = whereFieldObject.getString(JSONKeyConstants.NAME);
					whereFieldAlias = whereFieldObject.getString(JSONKeyConstants.ALIAS);
					hasWhereFieldAliases = true;
				} else {
					whereFieldName = whereFieldArray.getString(i);
				}

				BaseField baseField = null;
				if(hasWhereFieldAliases) {
					// we need to create a new BaseField identical to the current one - as it is currently cached
					baseField = FieldLookupHelper.getBaseField(baseSchemaObject, whereFieldName).copy();
				} else {
					baseField = FieldLookupHelper.getBaseField(baseSchemaObject, whereFieldName);
				}

				if(!this.hasInFields) {
					this.hasInFields = FieldLookupHelper.hasInFieldDesignator(whereFieldName);
				}

				if(hasWhereFieldAliases) {
					baseField.setAlias(whereFieldAlias);
				}

				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up where field '" + whereFieldName + "', for " + this.getType() + " '" + name + "'.");
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
	protected void populateFields(JSONObject jsonObject, String jsonKey, List<BaseField> fields, Map<String, BaseField> uniqueFields) throws H2ZeroParseException {

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
				type = fieldObject.getString(JSONKeyConstants.TYPE);

				// check to ensure that the field has a name
				fieldObject.getString(JSONKeyConstants.NAME);
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
					throw new H2ZeroParseException(ex.getMessage(), ex);
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

	public List<BaseField> getWhereFields() { return(whereFields); }
	public List<BaseField> getSelectFields() { return(selectFields); }
	public List<BaseField> getInWhereFields() { return(inWhereFields); }
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

	public List<BaseField> getValueFields() { return valueFields; }
	public Map<String, BaseField> getUniqueValueFields() { return uniqueValueFields; }

	public Collection<BaseField> getUniqueSelectFields() { return(uniqueSelectFields.values()); }

	public List<BaseField> getAllFields() { return allFields; }
	public List<BaseField> getAllUniqueFields() { return allUniqueFields; }

	public String getSetClause() { return(setClause); }
	public void setSetClause(String setClause) { this.setClause = setClause; }
	public List<BaseField> getSetFields() { return(setFields); }
	public List<BaseField> getUpdateFields() { return(updateFields); }
	public Collection<BaseField> getUniqueUpdateFields() { return(uniqueUpdateFields.values()); }

	public JSONObject getJsonObject() { return(jsonObject); }

	public boolean getHasJsonUniqueKey() { return(null != jsonUniqueKey); }
	public Map<String, UsageType> getAllowableJsonKeys() { return(allowableJsonKeys); }
	public BaseSchemaObject getBaseSchemaObject() { return(baseSchemaObject); }

	/**
	 * Whether this finder has aliases for the where fields, or it is just straight where field string array
	 * 
	 * @return whether this finder has where field aliases
	 */
	public boolean getHasWhereFieldAliases() { return hasWhereFieldAliases;}

	public List<String> getComments() { return comments; }

}
