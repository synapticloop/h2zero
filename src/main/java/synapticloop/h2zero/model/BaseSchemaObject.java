package synapticloop.h2zero.model;

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
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;
import synapticloop.h2zero.util.SimpleLogger;

/**
 * The base schema object is either a table or view and contains common 
 * functionality between the two.
 * 
 * @author synapticloop
 */
public abstract class BaseSchemaObject {
	protected JSONObject jsonObject;
	protected String name = null;
	private int findAllStatementCacheSize = 1024; // 1024 is the default size

	private final List<Finder> finders = new ArrayList<>(); // a list of all of the finders
	private final List<Counter> counters = new ArrayList<>(); // a list of all of the counters
	private final List<Question> questions = new ArrayList<>(); // a list of all of the questions

	protected final List<String> autoGeneratedUniqueFinders = new ArrayList<>(); // a list of all of the automatically generated 'unique' result finders
	protected final List<String> autoGeneratedMultipleFinders = new ArrayList<>(); // a list of all of the automatically generated 'multiple' result finders

	protected final List<String> autoGeneratedUniqueNullFinders = new ArrayList<>(); // a list of all of the automatically generated 'unique' result finders
	protected final List<String> autoGeneratedMultipleNullFinders = new ArrayList<>(); // a list of all of the automatically generated 'multiple' result finders

	protected final List<String> autoGeneratedUpdaters = new ArrayList<>(); // a list of all of the automatically generated updaters from the 'fieldUpdaters' JSON key
	protected final List<String> autoGeneratedCounters = new ArrayList<>(); // a list of all of the automatically generated counters from the 'fieldCounters' JSON key

	protected Map<String, BaseField> fieldLookup = new HashMap<>(); // a quick lookup map of all of the fields for this table
	protected Map<String, BaseField> inFieldLookup = new HashMap<>(); // a quick lookup for all of the 'in' fields

	protected List<BaseField> fields = new ArrayList<>();  // a list of all of the fields on this table
	protected final List<BaseField> uniqueFields = new ArrayList<>();

	protected List<BaseField> populateFields = new ArrayList<>();  // a list of all of the fields on this table
	protected List<BaseField> nonPopulateFields = new ArrayList<>();  // a list of all of the fields on this table

	protected BaseField primaryKeyField = null; // the field that is the primary key

	protected Set<String> referencedFieldTypes = new HashSet<>(); // this is a set of all of the referenced field types
	private final int defaultStatementCacheSize;  // the default statement cache size

	/**
	 * Instantiate a base schema object (either a table or a view)
	 *  
	 * @param jsonObject The JSON object from which the details will be extracted
	 * @param defaultStatementCacheSize The default size of the cache statements
	 */
	public BaseSchemaObject(JSONObject jsonObject, int defaultStatementCacheSize) {
		this.jsonObject = jsonObject;
		this.findAllStatementCacheSize = JsonHelper.getIntValue(jsonObject, JSONKeyConstants.FINDALL_STATEMENT_CACHE_SIZE, defaultStatementCacheSize);
		this.defaultStatementCacheSize = defaultStatementCacheSize;
	}

	/**
	 * Return whether this is a table base schema object
	 *
	 * @return whether this base schema object is a table
	 */
	public abstract boolean getIsTable();

	/**
	 * Return whether this is a view base schema object
	 *
	 * @return whether this is a view (if false, it is a table)
	 */
	public abstract boolean getIsView();

	/**
	 * Populate finders from the short-hand "fieldFinders" JSON key
	 * 
	 * @param jsonObject The json object on which the field finders resides
	 * @throws H2ZeroParseException If there was an error parsing the JSON
	 */
	@SuppressWarnings("rawtypes")
	protected void populateFieldFinders(JSONObject jsonObject) throws H2ZeroParseException {
		// now for the auto-generated finders
		JSONArray finderJson = new JSONArray();
		try {
			finderJson = jsonObject.getJSONArray(JSONKeyConstants.FIELD_FINDERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no field finders is ok
			return;
		}

		for(int i = 0; i < finderJson.length(); i++) {
			JSONObject fieldFinderObject = finderJson.optJSONObject(i);
			if(null == fieldFinderObject) {
				throw new H2ZeroParseException("Found a '" + JSONKeyConstants.FIELD_FINDERS +  "' json array on table/view '" + this.name + "', however the value at index '" + i + "' is not a json object.");
			}

			Iterator keys = fieldFinderObject.keys();
			// should only be one key
			String autoFinderName = (String)keys.next();
			String autoFinder = fieldFinderObject.optString(autoFinderName);

			if(null != autoFinder) {
				if(autoFinder.compareToIgnoreCase(JSONKeyConstants.UNIQUE) == 0 || autoFinder.compareToIgnoreCase(JSONKeyConstants.SINGLE) == 0) {
					autoGeneratedUniqueFinders.add(autoFinderName);
				} else if(autoFinder.compareToIgnoreCase(JSONKeyConstants.MULTIPLE) == 0) {
					autoGeneratedMultipleFinders.add(autoFinderName);
				} else {
					throw new H2ZeroParseException("Found an auto generate finder on '" + this.name + "." + autoFinderName + "' with a value of '" + autoFinder + "'.  The allowable values are '" + JSONKeyConstants.UNIQUE + "', '" + JSONKeyConstants.SINGLE + "' or '" + JSONKeyConstants.MULTIPLE + "'.");
				}
			}
		}

		jsonObject.remove(JSONKeyConstants.FIELD_FINDERS);
	}

	@SuppressWarnings("rawtypes")
	protected void populateFieldNullFinders(JSONObject jsonObject) throws H2ZeroParseException {
		// now for the auto-generated finders
		JSONArray finderJson = new JSONArray();
		try {
			finderJson = jsonObject.getJSONArray(JSONKeyConstants.FIELD_NULL_FINDERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
			return;
		}

		for(int i = 0; i < finderJson.length(); i++) {
			JSONObject fieldFinderObject = finderJson.optJSONObject(i);
			if(null == fieldFinderObject) {
				throw new H2ZeroParseException("Found a '" + JSONKeyConstants.FIELD_NULL_FINDERS +  "' json array on table/view '" + this.name + "', however the value at index '" + i + "' is not a json object.");
			}

			Iterator keys = fieldFinderObject.keys();
			// should only be one key
			String autoFinderName = (String)keys.next();
			String autoFinder = fieldFinderObject.optString(autoFinderName);

			if(null != autoFinder) {
				if(autoFinder.compareToIgnoreCase(JSONKeyConstants.UNIQUE) == 0 || autoFinder.compareToIgnoreCase(JSONKeyConstants.SINGLE) == 0) {
					autoGeneratedUniqueNullFinders.add(autoFinderName);
				} else if(autoFinder.compareToIgnoreCase(JSONKeyConstants.MULTIPLE) == 0) {
					autoGeneratedMultipleNullFinders.add(autoFinderName);
				} else {
					throw new H2ZeroParseException("Found an auto generate finder on '" + this.name + "." + autoFinderName + "' with a value of '" + autoFinder + "'.  The allowable values are '" + JSONKeyConstants.UNIQUE + "', '" + JSONKeyConstants.SINGLE + "' or '" + JSONKeyConstants.MULTIPLE + "'.");
				}
			}
		}

		jsonObject.remove(JSONKeyConstants.FIELD_NULL_FINDERS);
	}

	protected void populateFinders(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray finderJson = new JSONArray();
		try {
			finderJson = jsonObject.getJSONArray(JSONKeyConstants.FINDERS);
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		// now go through and addFinders based on the multiple and unique finders
		for (String multipleFinder : autoGeneratedMultipleFinders) {
			generateAutomaticFinder(multipleFinder, false);
		}

		for (String uniqueFinder : autoGeneratedUniqueFinders) {
			generateAutomaticFinder(uniqueFinder, true);
		}

		// now go through and addNullFinders based on the multiple and unique finders
		for (String multipleFinder : autoGeneratedMultipleNullFinders) {
			generateAutomaticNullFinder(multipleFinder, false);
		}

		for (String uniqueFinder : autoGeneratedUniqueNullFinders) {
			generateAutomaticNullFinder(uniqueFinder, true);
		}

		for (int i = 0; i < finderJson.length(); i++) {
			try {
				JSONObject finderObject = finderJson.getJSONObject(i);
				finders.add(new Finder(this, finderObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse finder.", jsonex);
			}
		}

		jsonObject.remove(JSONKeyConstants.FINDERS);
	}

	private void generateAutomaticFinder(String uniqueFinder, boolean unique) throws H2ZeroParseException {
		JSONObject autoFinder = new JSONObject();
		String[] fields = uniqueFinder.split(",");
		JSONArray whereFieldsArray = new JSONArray();

		try {
			StringBuilder fieldNameBuilder = new StringBuilder();
			fieldNameBuilder.append("findBy");

			StringBuilder whereClauseBuilder = new StringBuilder();
			whereClauseBuilder.append("where ");

			for (int i = 0; i < fields.length; i++) {
				String field = fields[i].trim();
				fieldNameBuilder.append(NamingHelper.getFirstUpper(field));

				if(i != 0) {
					whereClauseBuilder.append(" and ");
				}

				whereClauseBuilder.append(field);
				whereClauseBuilder.append(" = ?");

				whereFieldsArray.put(field);
			}

			autoFinder.put(JSONKeyConstants.NAME, fieldNameBuilder.toString());

			autoFinder.put(JSONKeyConstants.WHERE_CLAUSE, whereClauseBuilder.toString());
			autoFinder.put(JSONKeyConstants.UNIQUE, unique);
			autoFinder.put(JSONKeyConstants.WHERE_FIELDS, whereFieldsArray);

			Finder finder = new Finder(this, autoFinder);
			finder.setIsAutoFinder(true);
			finders.add(finder);
		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not generate the field finder for '" + uniqueFinder + "'.", jsonex);
		}
	}

	
	/**
	 * Generate the automatic finder (i.e. the shortcut finder) for finding results
	 * where the field is null
	 * 
	 * @param uniqueFinder The finder
	 * @param unique whether this returns a unique result
	 * 
	 * @throws H2ZeroParseException If something went horribly wrong
	 */
	private void generateAutomaticNullFinder(String uniqueFinder, boolean unique) throws H2ZeroParseException {
		JSONObject autoFinder = new JSONObject();
		String[] fields = uniqueFinder.split(",");
		JSONArray whereFieldsArray = new JSONArray();

		try {
			StringBuilder fieldNameBuilder = new StringBuilder();
			fieldNameBuilder.append(JSONKeyConstants.FIND_BY_NULL);
			StringBuilder whereClauseBuilder = new StringBuilder();
			whereClauseBuilder.append("where ");

			for (int i = 0; i < fields.length; i++) {
				String field = fields[i].trim();
				fieldNameBuilder.append(NamingHelper.getFirstUpper(field));

				if(i != 0) {
					whereClauseBuilder.append(" and ");
				}

				whereClauseBuilder.append(field);
				whereClauseBuilder.append(" is null");

				whereFieldsArray.put(field);
			}

			autoFinder.put(JSONKeyConstants.NAME, fieldNameBuilder.toString());

			autoFinder.put(JSONKeyConstants.WHERE_CLAUSE, whereClauseBuilder.toString());
			autoFinder.put(JSONKeyConstants.UNIQUE, unique);

			// we do not put in where fields as we are searching on null, not on a particular value
			// autoFinder.put(JSONKeyConstants.WHERE_FIELDS, whereFieldsArray);

			Finder finder = new Finder(this, autoFinder);
			finder.setIsAutoFinder(true);
			finders.add(finder);
		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not generate the field null finder for '" + uniqueFinder + "'.", jsonex);
		}
	}


	/**
	 * Populate all of the questions that are being generated for a table.  A question is a simple query that returns one
	 * and only one boolean true/false value
	 * 
	 * @param jsonObject The jsonObject to parse for questions
	 * @throws H2ZeroParseException if something went wrong with the parsing
	 */
	protected void populateQuestions(JSONObject jsonObject) throws H2ZeroParseException {
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

		jsonObject.remove(JSONKeyConstants.QUESTIONS);
	}

	public boolean getHasQuestionInfields() {
		for (Question question : questions) {
			if(question.getHasInFields()) {
				return(true);
			}
		}
		return(false);
	}

	/**
	 * Generate a counter from the 'fieldCounters' JSON Array key.  In essence,
	 * this creates a generated JSON Object from the fieldCounters name and adds
	 * it to the counter array - which is then generated through the process.
	 *
	 * @param counterFieldName The name of the counter field (or fields, or infields)
	 * @throws H2ZeroParseException If there was an error parsing the fieldCounter
	 */
	private void generateAutomaticCounter(String counterFieldName) throws H2ZeroParseException {
		JSONObject autoCounter = new JSONObject();
		JSONArray whereFieldsArray = new JSONArray();

		String[] fields = counterFieldName.split(",");

		StringBuilder whereClauseBuilder = new StringBuilder();
		whereClauseBuilder.append("where ");

		try {
			StringBuilder updaterNameBuilder = new StringBuilder();
			updaterNameBuilder.append("countAllBy");

			for (int i = 0; i < fields.length; i++) {
				String field = fields[i].trim();
				updaterNameBuilder.append(NamingHelper.getFirstUpper(field));

				if (i != 0) {
					whereClauseBuilder.append(" and ");
				}

				whereClauseBuilder.append(field);
				whereClauseBuilder.append(" = ?");

				whereFieldsArray.put(field);
			}

			autoCounter.put(JSONKeyConstants.NAME, updaterNameBuilder.toString());
			autoCounter.put(JSONKeyConstants.WHERE_CLAUSE, whereClauseBuilder.toString());
			autoCounter.put(JSONKeyConstants.WHERE_FIELDS, whereFieldsArray);

			Counter counter = new Counter(this, autoCounter);
			counter.setIsAutoCounter(true);
			counters.add(counter);
		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not generate the field counter for '" + counterFieldName + "'.", jsonex);
		}
	}

	/**
	 * Populate all of the counters that are being generated for a table.  A counter is a simple query that returns one
	 * and only one integer value for the query which is assumed to be a count
	 *
	 * @param jsonObject The jsonObject to parse for questions
	 * @throws H2ZeroParseException if something went wrong with the parsing
	 */
	protected void populateCounters(JSONObject jsonObject) throws H2ZeroParseException {
		for (String counter : autoGeneratedCounters) {
			generateAutomaticCounter(counter);
		}

		JSONArray counterJson = jsonObject.optJSONArray(JSONKeyConstants.COUNTERS);
		if(null == counterJson) {
			// no counters is OK
			return;
		}

		for (int i = 0; i < counterJson.length(); i++) {
			try {
				JSONObject counterObject = counterJson.getJSONObject(i);
				counters.add(new Counter(this, counterObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse counter JSON Array.", jsonex);
			}
		}

		jsonObject.remove(JSONKeyConstants.COUNTERS);
	}

	protected void populateFieldCounters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray fieldCounterJson = jsonObject.optJSONArray(JSONKeyConstants.FIELD_COUNTERS);

		if(null == fieldCounterJson) {
			// no field counters array is OK
			return;
		}

		for (int i = 0; i < fieldCounterJson.length(); i++) {
			// these should all be strings - not objects as with other field(updaters/deleters/counters)
			// not arrays of JSON Objects as the fieldFinders are
			String fieldCounterName = null;
			try {
				fieldCounterName = fieldCounterJson.getString(i);
				autoGeneratedCounters.add(fieldCounterName);
			} catch (JSONException ex) {
				throw new H2ZeroParseException("Found a '" + JSONKeyConstants.FIELD_COUNTERS + "' element on the '" + this.name + "' table, however the value at index '" + i + "' is not a String.");
			}
		}

		jsonObject.remove(JSONKeyConstants.FIELD_COUNTERS);
	}


	protected void logFatalFieldParse(Exception exception, String message, String firstUpper) throws H2ZeroParseException {
		SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, exception.getClass().getSimpleName() + ": on table or view '" + this.name + "', throwing upwards..., for field synapticloop.h2zero.model.field." + firstUpper + "Field");
		throw new H2ZeroParseException(message, exception);
	}

	/**
	 * Go through all of the fields and populate the referenced field types
	 */
	protected void populateReferencedFieldTypes() {
		for (BaseField baseField : fields) {
			referencedFieldTypes.add(baseField.getSqlJavaType());
		}
	}

	/**
	 * Whether this table requires the import for this specific field type
	 * 
	 * @param fieldType the type of the field
	 * 
	 * @return whether an import statement is required for this table
	 */
	public boolean requiresImport(String fieldType) {
		return(referencedFieldTypes.contains(fieldType));
	}


	public String getName() { return(this.name); }
	public String getUpperName() { return(this.name.toUpperCase()); }
	public String getJavaClassName() { return(NamingHelper.getFirstUpper(name)); }
	public String getJavaFieldName() { return(NamingHelper.getSecondUpper(name)); }

	public BaseField getField(String name) { return(fieldLookup.get(name)); }
	public BaseField getInField(String name) { return(inFieldLookup.get(name)); }

	public List<Finder> getFinders() { return(finders); }
	public List<Counter> getCounters() { return(counters); }
	public List<Question> getQuestions() { return(questions); }

	public int getFindAllStatementCacheSize() { return findAllStatementCacheSize; }
	public int getDefaultStatementCacheSize() { return defaultStatementCacheSize; }

	public List<BaseField> getPopulateFields() { return(populateFields); }
	public List<BaseField> getNonPopulateFields() { return(nonPopulateFields); }

	public boolean getShouldHydrate() { return(nonPopulateFields.size() != 0); }
	/**
	 * Return whether this schema object has any questions on it
	 * 
	 * @return whether this schema object has any questions on it
	 */
	public boolean getHasQuestions() { return(!questions.isEmpty()); }

	public List<BaseField> getUniqueFields() { return(uniqueFields); }
}
