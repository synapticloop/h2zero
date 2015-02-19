package synapticloop.h2zero.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;


public class Table {
	private JSONObject jsonObject = null;

	private String name = null;
	private String engine = "innodb";
	private String charset = "UTF8";
	private ArrayList<String> comments = new ArrayList<String>();

	private boolean cacheable = false;
	private boolean cacheFindAll = false;
	private boolean hasLargeObject = false;

	// a list of all of the fields that this table has
	private ArrayList<BaseField> fields = new ArrayList<BaseField>();
	// all fields that are not marked as secure
	private ArrayList<BaseField> nonSecureFields = new ArrayList<BaseField>();
	// all fields that are marked as secure
	private ArrayList<BaseField> secureFields = new ArrayList<BaseField>();

	private HashMap<String, BaseField> fieldLookup = new HashMap<String, BaseField>();
	private HashMap<String, BaseField> inFieldLookup = new HashMap<String, BaseField>();
	private HashMap<String, BaseField> setFieldLookup = new HashMap<String, BaseField>();
	private HashMap<String, BaseField> whereFieldLookup = new HashMap<String, BaseField>();

	private ArrayList<BaseField> nonNullFields = new ArrayList<BaseField>();
	private ArrayList<BaseField> nonPrimaryFields = new ArrayList<BaseField>();

	private ArrayList<Finder> finders = new ArrayList<Finder>();
	private ArrayList<Updater> updaters = new ArrayList<Updater>();
	private ArrayList<Inserter> inserters = new ArrayList<Inserter>();
	private ArrayList<Deleter> deleters = new ArrayList<Deleter>();
	private ArrayList<Constant> constants = new ArrayList<Constant>();
	private ArrayList<Counter> counters = new ArrayList<Counter>();

	public Table(JSONObject jsonObject) throws H2ZeroParseException {
		this.jsonObject = jsonObject;

		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.engine = JsonHelper.getStringValue(jsonObject, "engine", engine);
		this.charset = JsonHelper.getStringValue(jsonObject, "charset", charset);
		this.cacheable = JsonHelper.getBooleanValue(jsonObject, "cacheable", cacheable);
		this.cacheFindAll = JsonHelper.getBooleanValue(jsonObject, "cacheFindAll", cacheFindAll);
		String tempComments = JsonHelper.getStringValue(jsonObject, "comment", null);
		if(null != tempComments) {
			String[] split = tempComments.split("\\n");
			for (int i = 0; i < split.length; i++) {
				comments.add(split[i]);

			}
		}

		if(null == name) {
			throw new H2ZeroParseException("The table 'name' attribute cannot be null.");
		}

		// now for the fields
		populateFields(jsonObject);
	}

	public void populateActions() throws H2ZeroParseException{
		populateFinders(jsonObject);
		populateUpdaters(jsonObject);
		populateDeleters(jsonObject);
		populateInserters(jsonObject);
		populateConstants(jsonObject);
		populateCounters(jsonObject);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateFields(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray("fields");
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("Cannot create a table without 'fields'.");
		}

		for (int i = 0; i < fieldJson.length(); i++) {
			String type = null;
			String name = null;
			JSONObject fieldObject = null;
			try {
				fieldObject = fieldJson.getJSONObject(i);
				type = fieldObject.getString("type");
				name = fieldObject.getString("name");
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the 'fields' array.");
			}

			if(null != type) {
				String firstUpper = NamingHelper.getFirstUpper(type);
				try {
					Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
					Constructor constructor = forName.getConstructor(JSONObject.class, boolean.class);

					BaseField inBaseField = (BaseField)constructor.newInstance(fieldObject, true);

					constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField)constructor.newInstance(fieldObject);

					if(!baseField.getNullable()) {
						nonNullFields.add(baseField);
					}

					if(!baseField.getPrimary()) {
						nonPrimaryFields.add(baseField);
					}

					if(baseField.getIsLargeObject()) {
						hasLargeObject = true;
					}

					fields.add(baseField);
					fieldLookup.put(name, baseField);
					inFieldLookup.put(name, inBaseField);

					BaseField setBaseField = (BaseField)constructor.newInstance(fieldObject);
					setBaseField.suffixJavaName("Set");
					BaseField whereBaseField = (BaseField)constructor.newInstance(fieldObject);
					whereBaseField.suffixJavaName("Where");

					setFieldLookup.put(name, setBaseField);
					whereFieldLookup.put(name, whereBaseField);
				} catch (ClassNotFoundException cnfex) {
					throw new H2ZeroParseException(cnfex.getMessage());
				} catch (SecurityException sex) {
					throw new H2ZeroParseException(sex.getMessage());
				} catch (NoSuchMethodException nsmex) {
					throw new H2ZeroParseException(nsmex.getMessage());
				} catch (IllegalArgumentException iaex) {
					throw new H2ZeroParseException(iaex.getMessage());
				} catch (InstantiationException iex) {
					throw new H2ZeroParseException(iex.getMessage());
				} catch (IllegalAccessException iaex) {
					throw new H2ZeroParseException(iaex.getMessage());
				} catch (InvocationTargetException itex) {
					throw new H2ZeroParseException(itex.getMessage());
				}
			}
		}
	}

	private void populateFinders(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray finderJson = new JSONArray();
		try {
			finderJson = jsonObject.getJSONArray("finders");
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < finderJson.length(); i++) {
			try {
				JSONObject finderObject = finderJson.getJSONObject(i);
				finders.add(new Finder(finderObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse finder.");
			}
		}
	}

	private void populateUpdaters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray updaterJson = new JSONArray();
		try {
			updaterJson = jsonObject.getJSONArray("updaters");
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < updaterJson.length(); i++) {
			try {
				JSONObject updaterObject = updaterJson.getJSONObject(i);
				updaters.add(new Updater(updaterObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse updaters.");
			}
		}
	}

	private void populateDeleters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray deleterJson = new JSONArray();
		try {
			deleterJson = jsonObject.getJSONArray("deleters");
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < deleterJson.length(); i++) {
			try {
				JSONObject deleterObject = deleterJson.getJSONObject(i);
				deleters.add(new Deleter(deleterObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse deleters.");
			}
		}
	}

	private void populateInserters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray inserterJson = new JSONArray();
		try {
			inserterJson = jsonObject.getJSONArray("inserters");
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < inserterJson.length(); i++) {
			try {
				JSONObject inserterObject = inserterJson.getJSONObject(i);
				inserters.add(new Inserter(inserterObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse inserters.");
			}
		}
	}

	private void populateConstants(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray constantJson = new JSONArray();
		try {
			constantJson = jsonObject.getJSONArray("constants");
		} catch (JSONException ojjsonex) {
			// do nothing - no constants is ok
		}

		for (int i = 0; i < constantJson.length(); i++) {
			try {
				JSONObject constantsObject = constantJson.getJSONObject(i);
				constants.add(new Constant(constantsObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse constants.");
			}
		}
	}

	private void populateCounters(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray counterJson = new JSONArray();
		try {
			counterJson = jsonObject.getJSONArray("counters");
		} catch (JSONException ojjsonex) {
			// do nothing - no finders is ok
		}

		for (int i = 0; i < counterJson.length(); i++) {
			try {
				JSONObject counterObject = counterJson.getJSONObject(i);
				counters.add(new Counter(counterObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse counter.");
			}
		}
	}

	public String getName() { return(this.name); }
	public String getUpperName() { return(this.name.toUpperCase()); }
	public String getEngine() { return(this.engine); }
	public String getCharset() { return(this.charset); }
	public ArrayList<String> getComments() { return comments; }

	public ArrayList<BaseField> getFields() { return(fields); }

	public ArrayList<Finder> getFinders() { return(finders); }
	public ArrayList<Updater> getUpdaters() { return(updaters); }
	public ArrayList<Inserter> getInserters() { return(inserters); }
	public ArrayList<Deleter> getDeleters() { return(deleters); }
	public ArrayList<Constant> getConstants() { return(constants); }
	public ArrayList<Counter> getCounters() { return(counters); }

	public ArrayList<BaseField> getNonNullFields() { return(nonNullFields); }
	public ArrayList<BaseField> getNonPrimaryFields() { return(nonPrimaryFields); }
	public boolean getCacheable() { return(cacheable); }
	public boolean getCacheFindAll() { return(cacheFindAll); }
	public BaseField getField(String name) { return(fieldLookup.get(name)); }
	public BaseField getInField(String name) { return(inFieldLookup.get(name)); }
	public BaseField getSetField(String name) { return(setFieldLookup.get(name)); }
	public BaseField getWhereField(String name) { return(whereFieldLookup.get(name)); }
	public String getJavaClassName() { return(NamingHelper.getFirstUpper(name)); }
	public String getJavaFieldName() { return(NamingHelper.getSecondUpper(name)); }
	public boolean getHasNonNullConstructor() { return(nonNullFields.size() != fields.size()); }
	public boolean getHasLargeObject() { return hasLargeObject; }

	public boolean getIsConstant() { return(constants.size() > 0); }

	public String getDropTableDefinition() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("drop table if exists " + name + ";\n");
		return (stringBuilder.toString());
	}

	public String getCreateTableDefinition() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("create table if not exists " + name + " (\n");
		// now for the fields
		Iterator<BaseField> iterator = fields.iterator();

		while (iterator.hasNext()) {
			BaseField baseField = iterator.next();
			stringBuilder.append(baseField.getCreateField());
			if(iterator.hasNext()) {
				stringBuilder.append(",");
			}
		}

		stringBuilder.append(") engine=" + engine + " default charset=" + charset + ";\n");
		return (stringBuilder.toString());
	}
}
