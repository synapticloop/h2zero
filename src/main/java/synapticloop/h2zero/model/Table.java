package synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2018 synapticloop.
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.DatabaseFieldTypeConfirm;
import synapticloop.h2zero.model.util.FieldLookupHelper;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.KeyHelper;
import synapticloop.h2zero.util.NamingHelper;

/**
 * The table encapsulates everything that is required for a single database table
 *
 */
public class Table extends BaseSchemaObject {

	private static List<String> ignoredKeys = new ArrayList<String>();
	static {
		ignoredKeys.add("comment");
		ignoredKeys.add("cacheable");
		ignoredKeys.add("cacheFindAll");
	}

	public static Set<String> ALLOWABLE_KEYS = new HashSet<String>();
	static {
		ALLOWABLE_KEYS.add("name");
		ALLOWABLE_KEYS.add("comments");
		ALLOWABLE_KEYS.add("fields");
		ALLOWABLE_KEYS.add("constants");
		ALLOWABLE_KEYS.add("fieldFinders");
		ALLOWABLE_KEYS.add("finders");
		ALLOWABLE_KEYS.add("questions");
		ALLOWABLE_KEYS.add("updaters");
		ALLOWABLE_KEYS.add("counters");
		ALLOWABLE_KEYS.add("deleters");
		ALLOWABLE_KEYS.add("inserters");
	}

	private static Map<String, String> replacementKeys = new HashMap<String, String>();
	static {
		replacementKeys.put("comment", "comments");
	}

	private List<String> foundIgnoredKeys = new ArrayList<String>();

	private String engine = "innodb";
	private String charset = "UTF8";
	private List<String> comments = new ArrayList<String>();

	private boolean hasLargeObject = false;
	private boolean hasForeignKey = false;
	private boolean hasNullableFields = false;

	// all fields that are not marked as secure
	private List<BaseField> nonSecureFields = new ArrayList<BaseField>();
	// all fields that are marked as secure
	private List<BaseField> secureFields = new ArrayList<BaseField>();

	private Map<String, BaseField> setFieldLookup = new HashMap<String, BaseField>();
	private Map<String, BaseField> whereFieldLookup = new HashMap<String, BaseField>();

	private List<BaseField> nonNullFields = new ArrayList<BaseField>();
	private List<BaseField> nonPrimaryFields = new ArrayList<BaseField>();

	private List<Updater> updaters = new ArrayList<Updater>(); // a list of all of the updaters
	private List<Inserter> inserters = new ArrayList<Inserter>(); // a list of all of the inserters
	private List<Deleter> deleters = new ArrayList<Deleter>(); // a list of all of the deleters
	private List<Constant> constants = new ArrayList<Constant>(); // a list of all of the constants
	private List<Counter> counters = new ArrayList<Counter>(); // a list of all of the counters
	private List<Question> questions = new ArrayList<Question>(); // a list of all of the questions

	private final Options options;


	/**
	 * Create a new Table object from the passed in jsonObject.
	 * 
	 * @param options The options for the h2zero generation
	 * @param jsonObject the json object to create the table from.
	 * @param defaultStatementCacheSize the default statement cache size
	 *  
	 * @throws H2ZeroParseException if there was an error parsing the jsonObject
	 */
	public Table(Options options, JSONObject jsonObject, int defaultStatementCacheSize) throws H2ZeroParseException {
		super(jsonObject, defaultStatementCacheSize);

		this.options = options;

		this.name = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.NAME, null);
		this.engine = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.ENGINE, engine);
		this.charset = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.CHARSET, charset);

		// maybe it is a comment array
		JSONArray optJSONArray = jsonObject.optJSONArray(JSONKeyConstants.COMMENTS);
		if(null != optJSONArray) {
			for (int i = 0; i < optJSONArray.length(); i++) {
				String comment = optJSONArray.optString(i, null);
				if(null != comment) {
					comments.add(comment);
				}
			}
		}

		if(null == name) {
			throw new H2ZeroParseException("The table 'name' attribute cannot be null.");
		}

		for (String key : ignoredKeys) {
			if(jsonObject.opt(key) != null) {
				foundIgnoredKeys.add(key);
			}
		}

		// now we are going to go through and determine all of the 
		KeyHelper.findMissingKeys(this, jsonObject, ALLOWABLE_KEYS);
		// now for the fields
		populateFields(jsonObject);

	}

	/**
	 * Populate all of the actions that can be performed on this table, including 
	 *  <ul>
	 *    <li>field finders</li>
	 *    <li>finders</li>
	 *    <li>updaters</li>
	 *    <li>deleters</li>
	 *    <li>inserters</li>
	 *    <li>constants</li>
	 *    <li>counters</li>
	 *    <li>questsions</li>
	 *  </ul>
	 * 
	 * @throws H2ZeroParseException if there was an error parsing the jsonObject
	 */
	public void populateActions() throws H2ZeroParseException {
		populateFieldFinders(jsonObject);
		populateFinders(jsonObject);
		populateFieldUpdaters(jsonObject);
		populateUpdaters(jsonObject);
		populateDeleters(jsonObject);
		populateInserters(jsonObject);
		populateConstants(jsonObject);
		populateCounters(jsonObject);
		populateQuestions(jsonObject);

		populateReferencedFieldTypes();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateFields(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray(JSONKeyConstants.FIELDS);
		} catch (JSONException jsonex) {
			throw new H2ZeroParseException(String.format("Cannot create a table without '%s'.",JSONKeyConstants.FIELDS), jsonex);
		}

		for (int i = 0; i < fieldJson.length(); i++) {
			String type = null;
			String fieldName = null;

			JSONObject fieldObject = null;
			try {
				fieldObject = fieldJson.getJSONObject(i);
				fieldName = fieldObject.getString(JSONKeyConstants.NAME);
				if(null == fieldName) {
					throw new H2ZeroParseException("Cannot have a field with a null name.");
				}

				type = fieldObject.optString("type", null);
				if(null == type || type.trim().length() == 0) {
					throw new H2ZeroParseException(String.format("No 'type' value found for field '%s'.", fieldName));
				}

				// now check to ensure that you can use this field type for the database
				String database = options.getDatabase();

				if(!DatabaseFieldTypeConfirm.getIsValidFieldTypeForDatabase(database, type)) {
					throw new H2ZeroParseException(String.format("Type %s'' value found for field '%s' is not valid for database '%s'", type, fieldName, database));
				}

			} catch (JSONException jsonex) {
				throw new H2ZeroParseException(String.format("Could not parse the '%s' array.", JSONKeyConstants.FIELDS), jsonex);
			}

			if(null != type) {
				String firstUpper = NamingHelper.getFirstUpper(type);
				try {
					Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
					Constructor constructor = forName.getConstructor(JSONObject.class, boolean.class);

					BaseField inBaseField = (BaseField)constructor.newInstance(fieldObject, true);

					constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField)constructor.newInstance(fieldObject);
					baseField.setFieldIndex(i);

					if(!baseField.getNullable()) {
						nonNullFields.add(baseField);
					}

					// test to see whether any of the fields can be nullable (for the inserters)
					if(baseField.getNullable()) {
						hasNullableFields = true;
					}

					if(!baseField.getPrimary()) {
						nonPrimaryFields.add(baseField);
					} else {
						this.primaryKeyField = baseField;
					}

					if(baseField.getIsLargeObject()) {
						hasLargeObject = true;
					}

					fields.add(baseField);
					if(baseField.getIsSecure()) {
						secureFields.add(baseField);
					} else {
						nonSecureFields.add(baseField);
					}

					fieldLookup.put(fieldName, baseField);
					inFieldLookup.put(fieldName, inBaseField);

					BaseField setBaseField = (BaseField)constructor.newInstance(fieldObject);
					setBaseField.suffixJavaName("Set");
					BaseField whereBaseField = (BaseField)constructor.newInstance(fieldObject);
					whereBaseField.suffixJavaName("Where");

					setFieldLookup.put(fieldName, setBaseField);
					whereFieldLookup.put(fieldName, whereBaseField);

					// add it to the cache - for later lookups
					FieldLookupHelper.addToTableFieldCache(this.name, fieldName);

				} catch (ClassNotFoundException | 
						InstantiationException | 
						IllegalAccessException | 
						IllegalArgumentException | 
						InvocationTargetException | 
						NoSuchMethodException | SecurityException ex) {
					logFatalFieldParse(ex, ex.getMessage(), firstUpper);
				}
			}
		}

		// now figure out if there is a foreign key relationship
		for (BaseField baseField : fields) {
			String foreignKeyTable = baseField.getForeignKeyTable();
			String foreignKeyField = baseField.getForeignKeyField();
			if(null != foreignKeyField && null != foreignKeyTable) {
				hasForeignKey = true;
				// at this point - see whether the foreign key table and field actually exists
				if(!FieldLookupHelper.hasTableField(foreignKeyTable, foreignKeyField)) {
					throw new H2ZeroParseException(String.format("Field '%s' on table '%s' is trying to reference a foreign key of '%s.%s' which has not been defined yet.", baseField.getName(), name, foreignKeyTable, foreignKeyField));
				}
			}
		}
	}

	private void populateUpdaters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray updaterJson = new JSONArray();
		try {
			updaterJson = jsonObject.getJSONArray(JSONKeyConstants.UPDATERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no updaters are ok
		}

		// now go through and addUpdaters based on the multiple and unique updaters
		for (String multipleUpdater : autoGeneratedMultipleUpdaters) {
			generateAutomaticUpdater(multipleUpdater, false);
		}

		for (String uniqueUpdater : autoGeneratedUniqueUpdaters) {
			generateAutomaticUpdater(uniqueUpdater, true);
		}

		for (int i = 0; i < updaterJson.length(); i++) {
			try {
				JSONObject updaterObject = updaterJson.getJSONObject(i);
				updaters.add(new Updater(this, updaterObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse updaters.", jsonex);
			}
		}
	}

	private void generateAutomaticUpdater(String updaterFieldName, boolean unique) throws H2ZeroParseException {
		JSONObject autoUpdater = new JSONObject();
		JSONArray whereFieldsArray = new JSONArray();
		JSONArray setFieldsArray = new JSONArray();

		try {
			StringBuilder updaterNameBuilder = new StringBuilder();
			updaterNameBuilder.append("update");
			updaterNameBuilder.append(NamingHelper.getFirstUpper(updaterFieldName));

			StringBuilder setClauseBuilder = new StringBuilder();
			setClauseBuilder.append("set ");
			setClauseBuilder.append(updaterFieldName);
			setClauseBuilder.append(" = ? ");

			setFieldsArray.put(updaterFieldName);

			StringBuilder whereClauseBuilder = new StringBuilder();

			String primaryKeyFieldName = primaryKeyField.getName();

			if(unique) {
				updaterNameBuilder.append("By");
				updaterNameBuilder.append(NamingHelper.getFirstUpper(primaryKeyFieldName));
				whereClauseBuilder.append("where ");
				whereClauseBuilder.append(primaryKeyFieldName);
				whereClauseBuilder.append(" = ?");

				whereFieldsArray.put(primaryKeyFieldName);
			}


			autoUpdater.put(JSONKeyConstants.NAME, updaterNameBuilder.toString());

			autoUpdater.put(JSONKeyConstants.SET_CLAUSE, setClauseBuilder.toString());
			autoUpdater.put(JSONKeyConstants.SET_FIELDS, setFieldsArray);

			if(unique) {
				autoUpdater.put(JSONKeyConstants.WHERE_CLAUSE, whereClauseBuilder.toString());
				autoUpdater.put(JSONKeyConstants.WHERE_FIELDS, whereFieldsArray);
			}

			Updater updater = new Updater(this, autoUpdater);

			updater.setIsAutoUpdater(true);
			updaters.add(updater);
		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not generate the field updater for '" + updaterFieldName + "'.", jsonex);
		}
	}

	private void populateFieldUpdaters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray updaterJson = new JSONArray();
		try {
			updaterJson = jsonObject.getJSONArray(JSONKeyConstants.FIELD_UPDATERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no field updaters are ok
		}

		for (int i = 0; i < updaterJson.length(); i++) {

			JSONObject fieldUpdaterObject = updaterJson.optJSONObject(i);
			if(null == fieldUpdaterObject) {
				throw new H2ZeroParseException("Found a '" + JSONKeyConstants.FIELD_UPDATERS +  "' json array on table '" + this.name + "', however the value at index '" + i + "' is not a json object.");
			}

			Iterator<String> keys = fieldUpdaterObject.keys();
			// should only be one key
			String autoUpdaterName = (String)keys.next();
			String autoUpdater = fieldUpdaterObject.optString(autoUpdaterName);

			if(null != autoUpdater) {
				if(autoUpdater.compareToIgnoreCase(JSONKeyConstants.UNIQUE) == 0 || autoUpdater.compareToIgnoreCase(JSONKeyConstants.SINGLE) == 0) {
					autoGeneratedUniqueUpdaters.add(autoUpdaterName);
				} else if(autoUpdater.compareToIgnoreCase(JSONKeyConstants.MULTIPLE) == 0) {
					autoGeneratedMultipleUpdaters.add(autoUpdaterName);
				} else {
					throw new H2ZeroParseException("Found an auto generate updater on '" + this.name + "." + autoUpdaterName + "' with a value of '" + autoUpdater + "'.  The allowable values are '" + JSONKeyConstants.UNIQUE + "', '" + JSONKeyConstants.SINGLE + "' or '" + JSONKeyConstants.MULTIPLE + "'.");
				}
			}
		}
	}

	private void populateDeleters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray deleterJson = new JSONArray();
		try {
			deleterJson = jsonObject.getJSONArray(JSONKeyConstants.DELETERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < deleterJson.length(); i++) {
			try {
				JSONObject deleterObject = deleterJson.getJSONObject(i);
				deleters.add(new Deleter(this, deleterObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse deleters.", jsonex);
			}
		}
	}

	private void populateInserters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray inserterJson = new JSONArray();
		try {
			inserterJson = jsonObject.getJSONArray(JSONKeyConstants.INSERTERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < inserterJson.length(); i++) {
			try {
				JSONObject inserterObject = inserterJson.getJSONObject(i);
				inserters.add(new Inserter(this, inserterObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse inserters.", jsonex);
			}
		}
	}

	private void populateConstants(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray constantJson = new JSONArray();
		try {
			constantJson = jsonObject.getJSONArray(JSONKeyConstants.CONSTANTS);
		} catch (JSONException ojjsonex) {
			// do nothing - no constants is ok
		}

		for (int i = 0; i < constantJson.length(); i++) {
			try {
				JSONObject constantsObject = constantJson.getJSONObject(i);
				constants.add(new Constant(constantsObject, this));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse constants.", jsonex);
			}
		}
	}

	/**
	 * Populate all of the counters that are being generated for a table.  A counter is a simple query that returns one
	 * and only one integer value for the query which is assumed to be a count
	 * 
	 * @param jsonObject The jsonObject to parse for questions
	 * @throws H2ZeroParseException if something went wrong with the parsing
	 */
	private void populateCounters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray counterJson = new JSONArray();
		try {
			counterJson = jsonObject.getJSONArray(JSONKeyConstants.COUNTERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no counters is ok
		}

		for (int i = 0; i < counterJson.length(); i++) {
			try {
				JSONObject counterObject = counterJson.getJSONObject(i);
				counters.add(new Counter(this, counterObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse counter JSON Array.", jsonex);
			}
		}
	}

	/**
	 * Populate all of the questions that are being generated for a table.  A question is a simple query that returns one
	 * and only one boolean true/false value
	 * 
	 * @param jsonObject The jsonObject to parse for questions
	 * @throws H2ZeroParseException if something went wrong with the parsing
	 */
	private void populateQuestions(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray questionJson = new JSONArray();
		try {
			questionJson = jsonObject.getJSONArray(JSONKeyConstants.QUESTIONS);
		} catch (JSONException ojjsonex) {
			// do nothing - no questions is ok
		}

		for (int i = 0; i < questionJson.length(); i++) {
			try {
				JSONObject questionObject = questionJson.getJSONObject(i);
				questions.add(new Question(this, questionObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse questions JSON Array.", jsonex);
			}
		}
	}

	public boolean getHasQuestionInfields() {
		for (Question question : questions) {
			if(question.getHasInFields()) {
				return(true);
			}
		}
		return(false);
	}



	// boring old getters and setters
	public String getEngine() { return(this.engine); }
	public String getCharset() { return(this.charset); }
	public List<String> getComments() { return comments; }

	public List<BaseField> getFields() { return(fields); }

	public List<Updater> getUpdaters() { return(updaters); }
	public List<Inserter> getInserters() { return(inserters); }
	public List<Deleter> getDeleters() { return(deleters); }
	public List<Constant> getConstants() { return(constants); }
	public List<Counter> getCounters() { return(counters); }
	public List<Question> getQuestions() { return(questions); }

	public List<BaseField> getNonNullFields() { return(nonNullFields); }
	public List<BaseField> getNonPrimaryFields() { return(nonPrimaryFields); }
	public BaseField getSetField(String name) { return(setFieldLookup.get(name)); }
	public BaseField getWhereField(String name) { return(whereFieldLookup.get(name)); }
	public String getJavaClassName() { return(NamingHelper.getFirstUpper(name)); }
	public String getJavaFieldName() { return(NamingHelper.getSecondUpper(name)); }
	public boolean getHasNonNullConstructor() { return(nonNullFields.size() != fields.size()); }
	public boolean getHasLargeObject() { return hasLargeObject; }
	public boolean getHasQuestions() { return(!questions.isEmpty()); }

	public boolean getHasNullableFields() { return(hasNullableFields); }

	public boolean getIsConstant() { return(!constants.isEmpty()); }

	public boolean getIsTable() { return(true); }
	public boolean getIsView() { return(false); }

	public List<String> getFoundIgnoredKeys() { return foundIgnoredKeys; }
	public String getReplacementForKey(String key) { return(replacementKeys.get(key)); }

	public boolean getHasForeignKey() { return this.hasForeignKey; }
	public void setHasForeignKey(boolean hasForeignKey) { this.hasForeignKey = hasForeignKey; }
}
