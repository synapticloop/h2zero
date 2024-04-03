package com.synapticloop.h2zero.model;

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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.synapticloop.h2zero.model.util.FieldLookupHelper;
import com.synapticloop.h2zero.model.util.JSONKeyConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.synapticloop.h2zero.exception.H2ZeroParseException;
import com.synapticloop.h2zero.model.field.BaseField;
import com.synapticloop.h2zero.util.JsonHelper;
import com.synapticloop.h2zero.util.NamingHelper;

/**
 * The base query object is the helper methods for all of the actions that can
 * occur on the models and schema objects - e.g. finders, updaters, counters,
 * questions, deleters, etc.
 *
 * @author synapticloop
 */
public abstract class BaseQueryObject {
	public enum UsageType {
		OPTIONAL,
		MANDATORY,
		INVALID
	}

	protected Map<String, UsageType> allowableJsonKeys = new HashMap<>();

	protected JSONObject jsonObject = null;
	protected BaseSchemaObject baseSchemaObject = null;

	protected String name; // the name of the query object
	protected String selectClause; // the select clause
	protected List<String> selectClauses = new ArrayList<>();
	protected String whereClause; // the where clause
	protected List<String> whereClauses = new ArrayList<>();
	protected String insertClause; // the insert clause
	protected List<String> insertClauses = new ArrayList<>();
	protected String valuesClause; // the values clause
	protected List<String> valuesClauses = new ArrayList<>();
	protected String orderBy; // the order by clause

	protected Boolean jsonUniqueKey; // whether there is a 'unique' jsonKey for this object

	protected List<BaseField> allFields = new ArrayList<>();
	protected Set<String> allUniqueFieldNames = new HashSet<>();
	protected List<BaseField> allUniqueFields = new ArrayList<>();

	// select fields
	protected List<BaseField> selectFields = new ArrayList<>();
	protected Map<String, BaseField> uniqueSelectFields = new LinkedHashMap<>();


	// where fields and their associated properties
	protected List<BaseField> whereFields = new ArrayList<>();
	protected Map<String, BaseField> uniqueWhereFields = new LinkedHashMap<>();
	protected List<BaseField> inWhereFields = new ArrayList<>();

	protected boolean hasInFields = false;
	private boolean hasWhereFieldAliases = false;


	protected List<BaseField> valueFields = new ArrayList<>();
	protected Map<String, BaseField> uniqueValueFields = new LinkedHashMap<>();

	protected String setClause;
	protected List<String> setClauses = new ArrayList<>();
	protected List<BaseField> setFields = new ArrayList<>();

	protected List<BaseField> updateFields = new ArrayList<>();
	protected Map<String, BaseField> uniqueUpdateFields = new LinkedHashMap<>();

	private final List<String> comments = new ArrayList<>();

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

		// now get the clauses - which can either be an array of strings, or just
		// a simple string
		this.selectClause = getClause(JSONKeyConstants.SELECT_CLAUSE, selectClauses);
		this.whereClause = getClause(JSONKeyConstants.WHERE_CLAUSE, whereClauses);
		this.insertClause = getClause(JSONKeyConstants.INSERT_CLAUSE, insertClauses);
		this.valuesClause = getClause(JSONKeyConstants.VALUES_CLAUSE, valuesClauses);
		this.setClause = getClause(JSONKeyConstants.SET_CLAUSE, setClauses);


		this.orderBy = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.ORDER_BY, null);
		this.jsonUniqueKey = JsonHelper.getBooleanValue(jsonObject, JSONKeyConstants.UNIQUE, null);

		if (null == name) {
			throw new H2ZeroParseException("The '" + getType() + "' '" + JSONKeyConstants.NAME + "' attribute cannot be null.");
		}

		JSONArray optJSONArray = jsonObject.optJSONArray(JSONKeyConstants.COMMENTS);
		if (null != optJSONArray) {
			for (int i = 0; i < optJSONArray.length(); i++) {
				String comment = optJSONArray.optString(i, null);
				if (null != comment) {
					comments.add(comment);
				}
			}
		}

	}

	private String getClause(String jsonKey, List<String> clausesList) {
		JSONArray clauseArray = jsonObject.optJSONArray(jsonKey);
		if (null != clauseArray) {
			StringBuilder stringBuffer = new StringBuilder(" ");
			for (Object object : clauseArray) {
				stringBuffer
						.append(object.toString().trim())
						.append(" ");
				clausesList.add(object.toString().trim());
			}
			return(stringBuffer.toString());
		} else {
			String stringValue = JsonHelper.getStringValue(jsonObject, jsonKey, null);
			clausesList.add(stringValue);
			return stringValue;
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

			if (null == whereClause && whereFieldArray.length() > 0) {
				throw new H2ZeroParseException(
						this.getType()
								+ " '"
								+ this.name
								+ "' cannot have '"
								+ JSONKeyConstants.WHERE_FIELDS
								+ "' when there is no '" + JSONKeyConstants.WHERE_CLAUSE
								+ "'.");
			}

			for (int i = 0; i < whereFieldArray.length(); i++) {
				// at this point we need to check to see whether we are getting an array of objects, or just plain Strings
				String whereFieldName = null;
				String whereFieldAlias = null;
				String whereFieldType = null;

				if (null != whereFieldArray.optJSONObject(i)) {
					JSONObject whereFieldObject = whereFieldArray.getJSONObject(i);
					whereFieldName = whereFieldObject.getString(JSONKeyConstants.NAME);
					whereFieldAlias = whereFieldObject.getString(JSONKeyConstants.ALIAS);
					whereFieldType = whereFieldObject.optString(JSONKeyConstants.TYPE);
					hasWhereFieldAliases = true;
				} else {
					whereFieldName = whereFieldArray.getString(i);
				}

				BaseField baseField = null;
				if (hasWhereFieldAliases) {
					// we need to create a new BaseField identical to the current one - as it is currently cached
					baseField = FieldLookupHelper.getBaseField(baseSchemaObject, whereFieldName).copy();
					baseField.setAlias(whereFieldAlias);
				} else {
					baseField = FieldLookupHelper.getBaseField(baseSchemaObject, whereFieldName);
				}

				if (!this.hasInFields) {
					this.hasInFields = FieldLookupHelper.hasInFieldDesignator(whereFieldName);
				}

				if (hasWhereFieldAliases) {
					baseField.setAlias(whereFieldAlias);
				}

				if (null == baseField) {
					throw new H2ZeroParseException(String.format("Could not look up where field '%s', for %s '%s.%s'.",
							whereFieldName,
							this.getType(),
							baseSchemaObject.getName(),
							name
					));
				}


				whereFields.add(baseField);
				if (baseField.getIsInField()) {
					inWhereFields.add(baseField);
				}

				if (!uniqueWhereFields.containsKey(whereFieldName)) {
					uniqueWhereFields.put(whereFieldName, baseField);
				}
			}
		} catch (JSONException ex) {
			// do nothing
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void populateFields(JSONObject jsonObject, String jsonKey, List<BaseField> fields, Map<String, BaseField> uniqueFields) throws H2ZeroParseException {

		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray(jsonKey);
		} catch (JSONException ex) {
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
			} catch (JSONException ex) {
				StringBuilder stringBuilder = new StringBuilder()
						.append("Could not parse the '")
						.append(jsonKey)
						.append("' array.\n")
						.append("Was expecting the format to be:\n")
						.append("\"")
						.append(jsonKey)
						.append("\": [\n")
						.append("  { \"name\": \"<fieldName1>\", \"type\": \"<type>\" },\n")
						.append("  { \"name\": \"<fieldName2>\", \"type\": \"<type>\" },\n")
						.append("]\n");
				throw new H2ZeroParseException(stringBuilder.toString(), ex);
			}

			if (null != type) {
				String firstUpper = NamingHelper.getFirstUpper(type);
				try {
					Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
					Constructor constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField) constructor.newInstance(fieldObject);

					fields.add(baseField);
					addToAllFields(baseField);

					String baseFieldName = baseField.getName();
					if (!uniqueFields.containsKey(baseFieldName)) {
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

		if (!allUniqueFieldNames.contains(baseField.getName())) {
			allUniqueFields.add(baseField);
		}

		allUniqueFieldNames.add(baseField.getName());
	}

	public String getName() {
		return (name);
	}

	public String getTagName() {
		return (NamingHelper.getFirstUpper(name));
	}

	public String getWhereClause() {
		return (whereClause);
	}

	public String getOrderBy() {
		return (orderBy);
	}

	public List<BaseField> getWhereFields() {
		return (whereFields);
	}

	public List<BaseField> getSelectFields() {
		return (selectFields);
	}

	public List<BaseField> getInWhereFields() {
		return (inWhereFields);
	}

	public boolean getHasInFields() {
		return (hasInFields);
	}

	public String getSelectClause() {
		return selectClause;
	}

	public void setSelectClause(String selectClause) {
		this.selectClause = selectClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getStaticName() {
		return (NamingHelper.getStaticName(name));
	}

	public Collection<BaseField> getUniqueWhereFields() {
		return (this.uniqueWhereFields.values());
	}

	public String getInsertClause() {
		return insertClause;
	}

	public String getValuesClause() {
		return valuesClause;
	}

	public boolean getHasInsertClause() {
		return (null != insertClause);
	}

	public boolean getHasSelectClause() {
		return (null != selectClause);
	}

	public boolean getHasValuesClause() {
		return (null != valuesClause);
	}

	public boolean getHasWhereClause() {
		return (null != whereClause);
	}

	public List<BaseField> getValueFields() {
		return valueFields;
	}

	public Map<String, BaseField> getUniqueValueFields() {
		return uniqueValueFields;
	}

	public Collection<BaseField> getUniqueSelectFields() {
		return (uniqueSelectFields.values());
	}

	public List<BaseField> getAllFields() {
		return allFields;
	}

	public List<BaseField> getAllUniqueFields() {
		return allUniqueFields;
	}

	public String getSetClause() {
		return (setClause);
	}

	public void setSetClause(String setClause) {
		this.setClause = setClause;
	}

	public List<BaseField> getSetFields() {
		return (setFields);
	}

	public List<BaseField> getUpdateFields() {
		return (updateFields);
	}

	public Collection<BaseField> getUniqueUpdateFields() {
		return (uniqueUpdateFields.values());
	}

	public JSONObject getJsonObject() {
		return (jsonObject);
	}

	public boolean getHasJsonUniqueKey() {
		return (null != jsonUniqueKey);
	}

	public Map<String, UsageType> getAllowableJsonKeys() {
		return (allowableJsonKeys);
	}

	public BaseSchemaObject getBaseSchemaObject() {
		return (baseSchemaObject);
	}

	/**
	 * Whether this finder has aliases for the where fields, or it is just straight where field string array
	 *
	 * @return whether this finder has where field aliases
	 */
	public boolean getHasWhereFieldAliases() {
		return hasWhereFieldAliases;
	}

	public List<String> getComments() {
		return comments;
	}

	public abstract boolean getIsAutoGenerated();

	public List<String> getSelectClauses() {
		return selectClauses;
	}

	public List<String> getWhereClauses() {
		return whereClauses;
	}

	public List<String> getInsertClauses() {
		return insertClauses;
	}

	public List<String> getValuesClauses() {
		return valuesClauses;
	}

	public List<String> getSetClauses() {
		return setClauses;
	}
}
