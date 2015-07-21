package synapticloop.h2zero.model;

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

	public Database(JSONObject jsonObject) throws H2ZeroParseException {
		JSONObject databaseJson = null;
		try {
			databaseJson = jsonObject.getJSONObject(JSONKeyConstants.DATABASE);
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("The json file must have a key of '" + JSONKeyConstants.DATABASE + "'.", ojjsonex);
		}

		this.schema = JsonHelper.getStringValue(databaseJson, JSONKeyConstants.SCHEMA, null);
		this.packageName = JsonHelper.getStringValue(databaseJson, JSONKeyConstants.PACKAGE, null);

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
				Table table = new Table(tableObject);
				tables.add(table);
				tableLookup.put(table.getName(), table);
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.TABLES + "' array.");
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
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.TABLES + "' array.");
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
				views.add(new View(viewObject));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.VIEWS + "' array.");
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
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.FORMS + "' array.");
			}
		}
	}

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
