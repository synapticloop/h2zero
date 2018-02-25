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
import synapticloop.h2zero.model.form.Form;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class Database {
	public static final Map<String, Table> tableLookup = new HashMap<String, Table>();
	private String schema = null;
	private String packageName = null;

	private List<Table> tables = new ArrayList<Table>();
	private List<View> views = new ArrayList<View>();
	private List<Form> forms = new ArrayList<Form>();

	private Set<String> tableNames = new HashSet<String>();
	private int defaultStatementCacheSize = 1024;

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
			throw new H2ZeroParseException("The json file must have a key of '" + JSONKeyConstants.DATABASE + "'.", ojjsonex);
		}

		this.schema = JsonHelper.getStringValue(databaseJson, JSONKeyConstants.SCHEMA, null);
		this.packageName = JsonHelper.getStringValue(databaseJson, JSONKeyConstants.PACKAGE, null);

		this.defaultStatementCacheSize = JsonHelper.getIntValue(databaseJson, JSONKeyConstants.DEFAULT_STATEMENT_CACHE_SIZE, 1024);

		if(null == schema) {
			throw new H2ZeroParseException("You must have a key and value of '" + JSONKeyConstants.SCHEMA + "'.");
		}

		if(null == this.packageName) {
			throw new H2ZeroParseException("You must have a key and value of '" + JSONKeyConstants.PACKAGE + "'.");
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

		for (int i = 0; i < tableJson.length(); i++) {
			try {
				JSONObject tableObject = tableJson.getJSONObject(i);
				String tableName = tableObject.getString(JSONKeyConstants.NAME);

				if(tableNames.contains(tableName)) {
					throw new H2ZeroParseException("Duplicate table name of '" + tableName + "' found, exiting...");
				}

				tableNames.add(tableName);

				Table table = tableLookup.get(tableName);
				table.populateActions();
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.TABLES + "' array.", jsonex);
			}
		}

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

		// now that we have the database set up, now it is time for the forms
		JSONArray formJson = new JSONArray();
		try {
			formJson = databaseJson.getJSONArray(JSONKeyConstants.FORMS);
		} catch (JSONException ojjsonex) {
			// no problemo here - no forms
		}

		for (int i = 0; i < formJson.length(); i++) {
			try {
				JSONObject formObject = formJson.getJSONObject(i);
				forms.add(new Form(this, formObject));
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.FORMS + "' array.", jsonex);
			}
		}
	}

	/**
	 * Get the schema name for the database (i.,e. the name of the database)
	 * 
	 * @return the schema name
	 */
	public String getSchema() { return(this.schema); }
	public String getPackage() { return(this.packageName); }
	public List<Table> getTables() { return(tables); }
	public List<View> getViews() { return(views); }
	public List<Form> getForms() { return(forms); }

	public BaseField getField(String fieldName) {
		Iterator<Table> iterator = tables.iterator();
		while (iterator.hasNext()) {
			Table table = iterator.next();
			BaseField baseField = table.getField(fieldName);
			if(null != baseField) {
				return(baseField);
			}
		}
		return(null);
	}

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

	public String getPackagePath() { return(NamingHelper.convertToPath(packageName)); }
	public static Table getTableLookup(String tableName) { return(tableLookup.get(tableName)); }

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
