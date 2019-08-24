package synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2019 synapticloop.
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
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

/**
 * This is the database model which contains all of the tables, and views.  
 * It also holds any additional JSONObjects that other extensions may wish 
 * to use for their configuration.
 *
 */
public class Database {
	public static final Map<String, Table> tableLookup = new HashMap<String, Table>();
	private String schema = null;
	private String packageName = null;

	private List<Table> tables = new ArrayList<Table>();
	private List<View> views = new ArrayList<View>();

	private Set<String> tableNames = new HashSet<String>();
	private int defaultStatementCacheSize = 1024;

	private Map<String, Object> additionalKeys = new HashMap<String, Object>();

	/**
	 * Parse and create a new database object from the passed in JSON object
	 * 
	 * @param options The options for the h2zero generation
	 * @param jsonObject the database jsonObject
	 * 
	 * @throws H2ZeroParseException if there was an error parsing the json object
	 */
	public Database(Options options, JSONObject jsonObject) throws H2ZeroParseException {
		JSONObject databaseJson = null;
		try {
			databaseJson = jsonObject.getJSONObject(JSONKeyConstants.DATABASE);
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException(String.format("The json file must have a key of '%s'.", JSONKeyConstants.DATABASE), ojjsonex);
		}

		// get the generic information from the json object
		this.schema = JsonHelper.getStringValue(databaseJson, JSONKeyConstants.SCHEMA, null);
		databaseJson.remove(JSONKeyConstants.SCHEMA);

		this.packageName = JsonHelper.getStringValue(databaseJson, JSONKeyConstants.PACKAGE, null);
		databaseJson.remove(JSONKeyConstants.PACKAGE);

		this.defaultStatementCacheSize = JsonHelper.getIntValue(databaseJson, JSONKeyConstants.DEFAULT_STATEMENT_CACHE_SIZE, 1024);
		databaseJson.remove(JSONKeyConstants.DEFAULT_STATEMENT_CACHE_SIZE);

		// do some checking on the values, fail if not available
		if(null == schema) {
			throw new H2ZeroParseException(String.format("You must have a key and value of '%s'.", JSONKeyConstants.SCHEMA));
		}

		if(null == this.packageName) {
			throw new H2ZeroParseException(String.format("You must have a key and value of '%s'.", JSONKeyConstants.PACKAGE));
		}

		// now that we have the database set up, now it is time for the tables
		JSONArray tableJson = new JSONArray();
		try {
			tableJson = databaseJson.getJSONArray(JSONKeyConstants.TABLES);
		} catch (JSONException ojjsonex) {
			// whilst it is possible to create a database without any tables, is it
			// advisable?
		}

		for (int i = 0; i < tableJson.length(); i++) {
			try {
				JSONObject tableObject = tableJson.getJSONObject(i);
				Table table = new Table(options, tableObject, defaultStatementCacheSize);
				tables.add(table);
				tableLookup.put(table.getName(), table);
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.TABLES + "' array.", jsonex);
			}
		}

		// we need to now go through and populate the finders/updaters/deleters just
		// in case we have a forward lookup
		for (Table table : tables) {
			String tableName = table.getName();

			table.populateActions();

			if(tableNames.contains(tableName)) {
				throw new H2ZeroParseException("Duplicate table name of '" + tableName + "' found, exiting...");
			}

			tableNames.add(tableName);
		}

		// remove the key from the object, so we can determine what is left over
		databaseJson.remove(JSONKeyConstants.TABLES);

		// now that we have the database set up, now it is time for the views
		JSONArray viewJson = new JSONArray();
		try {
			viewJson = databaseJson.getJSONArray(JSONKeyConstants.VIEWS);
		} catch (JSONException ojjsonex) {
			// no views is OK
		}

		for (int i = 0; i < viewJson.length(); i++) {
			try {
				JSONObject viewObject = viewJson.getJSONObject(i);
				views.add(new View(viewObject, defaultStatementCacheSize));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.VIEWS + "' array.", jsonex);
			}
		}
		databaseJson.remove(JSONKeyConstants.VIEWS);

		// go through and add all remaining keys to the additional keys object
		Iterator<String> keys = databaseJson.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();

			Object keyObject = databaseJson.get(key);
			additionalKeys.put(key, keyObject);
			SimpleLogger.logInfo(LoggerType.PARSE_ADDITIONAL, this.getClass(), String.format("Found an additional JSONObject keyed on '%s', with value: %s", key, keyObject.toString()));
		}

		jsonObject.remove(JSONKeyConstants.DATABASE);
	}

	/**
	 * Get the schema name for the database (i.,e. the name of the database)
	 * 
	 * @return the schema name
	 */
	public String getSchema() { return(this.schema); }

	/**
	 * Get the base package that the code will be generated under
	 * 
	 * @return ths jave package that the code will be generated under
	 */
	public String getPackage() { return(this.packageName); }

	/**
	 * Get the tables that are going to be generated
	 * 
	 * @return the tables that are going to be generated
	 */
	public List<Table> getTables() { return(tables); }

	/**
	 * Get the views that are going to be generated
	 * 
	 * @return the views that are going to be generated
	 */
	public List<View> getViews() { return(views); }

	/**
	 * Get a field from a specific table
	 * 
	 * @param tableName the name of the table
	 * @param fieldName the name of the field
	 * 
	 * @return the basefield for the table, or null if it doesn't exist
	 */
	public BaseField getTableField(String tableName, String fieldName) {
		Table table = tableLookup.get(tableName);
		if(null == table) {
			return(null);
		} else {
			return(table.getField(fieldName));
		}
	}

	/**
	 * Get the package path (i.e. synapticloop.h2zero.model will return synapticloop/h2zero/model) - this is 
	 * the java output path for the package
	 * 
	 * @return The path for the java package
	 */
	public String getPackagePath() { return(NamingHelper.convertToPath(packageName)); }

	/**
	 * Get the table keyed on the name, or null if it doesn't exist
	 * 
	 * @param tableName the name of the table to return
	 * 
	 * @return The table keyed on name
	 */
	public static Table getTableLookup(String tableName) { return(tableLookup.get(tableName)); }

	/**
	 * Retrieve an additional key that is not mapped by the default h2zero 
	 * processor, this will be one of the JSON types, e.g. JSONArray, JSONObject, 
	 * String, etc.  The calling code will need to confirm the type.
	 * 
	 * @param key The key to look for
	 * 
	 * @return the object if it exists on the h2zero map
	 */
	public Object getJSONObjectForKey(String key) {
		if(additionalKeys.containsKey(key)) {
			return(additionalKeys.get(key));
		} else {
			return(null);
		}
	}

	/**
	 * Get the maximum number of fields for all of the defined tables
	 * 
	 * @return the maximum number of fields that a table has
	 */
	public int getMaxNumFields() {
		int maxNumField = 0;
		for (Table table : tables) {
			int size = table.getFields().size();
			if(size > maxNumField) {
				maxNumField = size;
			}
		}
		return(maxNumField);
	}
}
