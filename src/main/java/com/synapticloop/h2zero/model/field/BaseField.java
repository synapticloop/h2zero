package com.synapticloop.h2zero.model.field;

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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.h2zero.model.util.JSONKeyConstants;
import org.json.JSONObject;

import com.synapticloop.h2zero.exception.H2ZeroParseException;
import com.synapticloop.h2zero.util.AssertionHelper;
import com.synapticloop.h2zero.util.JsonHelper;
import com.synapticloop.h2zero.util.NamingHelper;


public abstract class BaseField {
	public static final String PRIMARY_KEY_POSTGRESQL_BIGSERIAL = "bigserial";
	public static final String PRIMARY_KEY_POSTGRESQL_SERIAL = "serial";
	public static final String PRIMARY_KEY_POSTGRESQL_SMALLSERIAL = "smallserial";

	public static final Map<String, String> PRIMARY_KEY_POSTGRESQL_REPLACE = new HashMap<>();
	static {
		PRIMARY_KEY_POSTGRESQL_REPLACE.put("bigint", PRIMARY_KEY_POSTGRESQL_BIGSERIAL);
		PRIMARY_KEY_POSTGRESQL_REPLACE.put("int", PRIMARY_KEY_POSTGRESQL_SERIAL);
		PRIMARY_KEY_POSTGRESQL_REPLACE.put("smallint", PRIMARY_KEY_POSTGRESQL_SMALLSERIAL);
	}

	private static final String COULD_NOT_CREATE_BASE_FIELD_COPY = "Could not create baseField copy";
	// the list of keywords that are allowed for the 'onUpdate' and 'onDelete' JSON keys, this is used for debugging when 
	// a value falls out of the range of allowable update/delete actions 
	private static String ALLOWABLE_UPDATE_DELETE_VALUES = null;
	// a hashset of the allowable values for the 'onUpdate' and the 'onDelete' JSON keys for quick validation and lookup
	private static final Set<String> ALLOWABLE_UPDATE_DELETE_ACTIONS = new HashSet<String>();
	static {
		ALLOWABLE_UPDATE_DELETE_ACTIONS.add("RESTRICT");
		ALLOWABLE_UPDATE_DELETE_ACTIONS.add("CASCADE");
		ALLOWABLE_UPDATE_DELETE_ACTIONS.add("SET NULL");
		ALLOWABLE_UPDATE_DELETE_ACTIONS.add("NO ACTION");

		// build the print out of what the available actions are
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<String> iterator = ALLOWABLE_UPDATE_DELETE_ACTIONS.iterator();
		while (iterator.hasNext()) {
			String value = iterator.next();
			stringBuilder
					.append("'")
					.append(value)
					.append("'");

			if(iterator.hasNext()) {
				stringBuilder.append(", ");
			}
		}

		ALLOWABLE_UPDATE_DELETE_VALUES = stringBuilder.toString();
	}

	// a list of the ignored keys (and therefore soon to be removed)
	private static final List<String> ignoredKeys = new ArrayList<String>();
	static {
		ignoredKeys.add("foreign");
		ignoredKeys.add("finder");
	}

	// the hashmap lookup for the ignored keys and there (possible) replacements
	private static final Map<String, String> replacementKeys = new HashMap<String, String>();
	static {
		replacementKeys.put("foreign", "foreignKey");
		replacementKeys.put("finder", "fieldFinder");
	}

	// the list of ignored keys that were found on this field object
	private final List<String> foundIgnoredKeys = new ArrayList<String>();


	private JSONObject jsonObjectConstructor = null; // the json object that was used to construct this object
	protected String name = null; // the name of the database field
	private String alias = null; // the alias of the field to use
	protected String type = null; // the type of the field
	protected int length = 0; // the length of the field
	protected boolean nullable = true; // whether the field is nullable
	protected int decimalLength = 0; // the decimal length - if required for the fields
	protected String defaultValue = null; // the default value
	protected boolean primary = false; // whether this field is primary
	protected boolean index = false; // whether to index this field
	protected boolean unique = false; // whether this field is unique
	protected boolean populate = true; // whether to populate this field
	protected boolean isSecure = false; // whether this field is secure and therefore do not output it to the console
	protected boolean isInField = false; // whether this field is an in field (i.e. where <field_name> in)
	protected boolean isLargeObject = false; // whether this field is a BLOB/CLOB or equivalent
	protected String onUpdate = null; // the onUpdate action
	protected String onDelete = null; // the onDelete action
	public int fieldIndex = 0; // the index of the field on the table - 0 being the first 

	protected boolean requiresConfirm = false; // whether this fields requires a confirmation field for entry

	protected String foreignKeyTable = null;  // the foreign key table for lookups
	protected String foreignKeyField = null; // the foreign key field for lookups

	// generated for the updaters
	protected String javaName = null;
	private String secondaryJavaName = null;

	// these are for the forms
	protected String validator = null;
	protected String formField = null;
	protected int minLength;

	private boolean isAutoGeneratedFinder = false;
	private final String foreignKey;

	private String comment = null;

	/**
	 * Create a new basefield object from the passed in JSON object
	 * 
	 * @param jsonObject the json object to create the basefield
	 * 
	 * @throws H2ZeroParseException if there was a problem with getting the values out of the json object
	 */
	public BaseField(JSONObject jsonObject) throws H2ZeroParseException {
		this.jsonObjectConstructor = jsonObject;

		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.javaName = NamingHelper.getSecondUpper(name);
		this.secondaryJavaName = NamingHelper.getSecondPartUpper(name);
		this.type = JsonHelper.getStringValue(jsonObject, "type", null);
		this.length = JsonHelper.getIntValue(jsonObject, "length", length);
		this.decimalLength = JsonHelper.getIntValue(jsonObject, "decimalLength", decimalLength);
		this.nullable = JsonHelper.getBooleanValue(jsonObject, "nullable", nullable);
		this.defaultValue = JsonHelper.getStringValue(jsonObject, "default", defaultValue);
		this.primary = JsonHelper.getBooleanValue(jsonObject, "primary", primary);
		this.index = JsonHelper.getBooleanValue(jsonObject, "index", index);
		this.unique = JsonHelper.getBooleanValue(jsonObject, "unique", unique);

		this.validator = JsonHelper.getStringValue(jsonObject, "validator", null);
		this.minLength = JsonHelper.getIntValue(jsonObject, "minLength", 0);
		this.populate = jsonObject.optBoolean("populate", true);
		this.isSecure = jsonObject.optBoolean("secure", false);

		this.foreignKey = jsonObject.optString(JSONKeyConstants.FOREIGN_KEY, null);

		this.requiresConfirm = jsonObject.optBoolean(JSONKeyConstants.CONFIRM, false);
		this.comment = jsonObject.optString(JSONKeyConstants.COMMENT, null);

		if(null != comment) {
			if(comment.length() > 255) {
				comment = comment.substring(0, 255);
			}
			comment = comment.replaceAll("'", "''");
		}

		if(null != foreignKey) {
			// split into table and field
			String[] split = foreignKey.split("\\.");
			if(split.length != 2) {
				throw new H2ZeroParseException("Field '" + name + "' has a '" + JSONKeyConstants.FOREIGN_KEY + " key which must be in the format of <foreign_table_name>.<foreign_field_name>");
			} else {
				foreignKeyTable = split[0];
				foreignKeyField = split[1];
			}
		}

		AssertionHelper.assertNotNull("name", name);
		AssertionHelper.assertNotNull("type", name);

		// now we need the on cascades - but only if there are foreign keys

		onDelete = jsonObject.optString(JSONKeyConstants.ON_DELETE, null);
		if(null != onDelete) {
			if(null == foreignKeyTable || null == foreignKeyField) {
				throw new H2ZeroParseException("Field '" + name + "' cannot have a '" + JSONKeyConstants.ON_DELETE + "' unless there is a '" + JSONKeyConstants.FOREIGN + "' on the same field.");
			}

			onDelete = onDelete.toUpperCase();
			// make sure it is a valid value
			if(!ALLOWABLE_UPDATE_DELETE_ACTIONS.contains(onDelete)) {
				throw new H2ZeroParseException("Field '" + name + "' cannot have a '" + JSONKeyConstants.ON_DELETE + "' has an invalid value of '" + onDelete + "', allowable values are: " + ALLOWABLE_UPDATE_DELETE_VALUES);
			}
		}


		onUpdate = jsonObject.optString(JSONKeyConstants.ON_UPDATE, null);
		if(null != onUpdate) {
			if(null == foreignKeyTable || null == foreignKeyField) {
				throw new H2ZeroParseException("Field '" + name + "' cannot have a '" + JSONKeyConstants.ON_UPDATE + "' unless there is a '" + JSONKeyConstants.FOREIGN + "' on the same field.");
			}

			onUpdate = onUpdate.toUpperCase();
			// make sure it is a valid value
			if(!ALLOWABLE_UPDATE_DELETE_ACTIONS.contains(onUpdate)) {
				throw new H2ZeroParseException("Field '" + name + "' cannot have a '" + JSONKeyConstants.ON_UPDATE + "' has an invalid value of '" + onUpdate + "', allowable values are: " + ALLOWABLE_UPDATE_DELETE_VALUES);
			}
		}

		for (String key : ignoredKeys) {
			if(jsonObject.opt(key) != null) {
				foundIgnoredKeys.add(key);
			}
		}
	}

	public BaseField(JSONObject jsonObject, boolean isInfield) throws H2ZeroParseException {
		this(jsonObject);
		this.isInField = isInfield;
	}

	/**
	 * Get the field name
	 * 
	 * @return the field name
	 */
	public String getName() { return(name); }
	public String getUpperName() { return(name.toUpperCase()); }
	public String getType() { return(type); }
	public boolean setPostgreSQLType(String originalType) {
		if(PRIMARY_KEY_POSTGRESQL_REPLACE.containsKey(originalType)) {
			this.type = PRIMARY_KEY_POSTGRESQL_REPLACE.get(originalType);
			return(true);
		} else {
			return(false);
		}
	}
	public String getUpperType() { return(NamingHelper.getFirstUpper(type)); }
	public boolean getNullable() { return(nullable); }
	public String getDefault() { return(defaultValue); }
	public boolean getPrimary() { return(primary); }
	public boolean getIndex() { return(index); }
	public boolean getUnique() {return(unique); }
	public boolean getPopulate() { return populate; }
	public void setPopulate(boolean populate) { this.populate = populate; }
	public boolean getIsSecure() { return isSecure; }
	public boolean getIsInField() { return isInField; }

	public String getJavaName() {
		if(null != alias) {
			return(alias);
		} else {
			return(javaName);
		}
	}

	public String getSecondaryJavaName() { return secondaryJavaName; }
	public void suffixJavaName(String suffix) { this.javaName = javaName + suffix; }

	public String getJavaAccessorName() {
		if(null != alias) {
			return(NamingHelper.getFirstUpper(alias));
		} else {
			return(NamingHelper.getFirstUpper(name));
		}
	}

	public String getForeignKeyTable() { return(foreignKeyTable); }
	public Table getForeignKeyTableLookup() { return(Database.getTableLookup(foreignKeyTable)); }
	public String getForeignKeyField() { return(foreignKeyField); }
	public BaseField getForeignKeyFieldLookup() { return(Database.getTableLookup(foreignKeyTable).getField(foreignKeyField)); }

	public abstract String getJavaType();
	public abstract String getSqlNullType();
	public abstract String getSqlJavaType();
	public abstract boolean getShouldEscape();

	public String getFormField() {
		if(null == formField) {
			return(getSqlJavaType() + "FormField");
		} else {
			return(formField);
		}
	}

	public String getValidator() {
		if(null == validator) {
			return(getSqlJavaType() + "Validator");
		} else {
			return(validator);
		}
	}

	public int getMinLength() { return(minLength); }
	public int getMaxLength() { return(length); }

	public String getLengthFormat() {
		StringBuilder stringBuilder = new StringBuilder();
		if(length != 0) {
			stringBuilder.append("(");
			stringBuilder.append(length);
			if(decimalLength != 0) {
				stringBuilder.append(",");
				stringBuilder.append(decimalLength);
			}
			stringBuilder.append(")");
		}
		return(stringBuilder.toString());
	}

	public boolean getIsLargeObject() { return isLargeObject; }
	public boolean getIsAutoGeneratedFinder() { return isAutoGeneratedFinder; }
	public void setIsAutoGeneratedFinder(boolean isAutoGeneratedFinder) { this.isAutoGeneratedFinder = isAutoGeneratedFinder; }
	public String getAlias() { return alias; }
	public void setAlias(String alias) { this.alias = alias; }
	public boolean getHasAlias() { return(null != this.alias); }
	public String getOnUpdate() { return(onUpdate); }
	public String getOnDelete() { return(onDelete); }
	public boolean getRequiresConfirm() { return(requiresConfirm); }
	public int getFieldIndex() { return(this.fieldIndex); }
	public void setFieldIndex(int fieldIndex) { this.fieldIndex = fieldIndex; }

	public boolean getIsForeignKey() {return(getForeignKeyField() != null); }

	public List<String> getFoundIgnoredKeys() { return foundIgnoredKeys; }
	public String getReplacementForKey(String key) { return(replacementKeys.get(key)); }

	public abstract String getSqlTestValue();

	/**
	 * Return a copy of this BaseField with the same details as the original.  In effect this creates
	 * a new BaseField object from the original json object that was passed in to create this field.
	 * 
	 * @return A copy of this BaseField object
	 * 
	 * @throws H2ZeroParseException If the BaseField could not be created.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseField copy() throws H2ZeroParseException {
		String firstUpper = NamingHelper.getFirstUpper(this.type);

		Class forName = null;
		Constructor constructor = null;
		BaseField baseField = null;
		try {
			forName = Class.forName(this.getClass().getPackage().getName() + "." + firstUpper + "Field");
			constructor = forName.getConstructor(JSONObject.class);
			baseField = (BaseField)constructor.newInstance(jsonObjectConstructor);
		} catch (NoSuchMethodException | 
				SecurityException | 
				ClassNotFoundException | 
				InstantiationException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException ex) {

			throw new H2ZeroParseException(COULD_NOT_CREATE_BASE_FIELD_COPY, ex);
		}

		return(baseField);
	}

	public String getComment() { return comment; }
	public void setComment(String comment) { this.comment = comment; }
}
