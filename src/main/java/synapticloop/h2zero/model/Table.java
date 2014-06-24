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
	private String name = null;
	private String engine = "innodb";
	private String charset = "UTF8";
	private boolean cacheable = false;
	private boolean cacheFindAll = false;

	private ArrayList<BaseField> fields = new ArrayList<BaseField>();
	private HashMap<String, BaseField> fieldLookup = new HashMap<String, BaseField>();
	private HashMap<String, BaseField> inFieldLookup = new HashMap<String, BaseField>();
	private HashMap<String, BaseField> setFieldLookup = new HashMap<String, BaseField>();
	private HashMap<String, BaseField> whereFieldLookup = new HashMap<String, BaseField>();

	private ArrayList<BaseField> nonNullFields = new ArrayList<BaseField>();

	private ArrayList<Finder> finders = new ArrayList<Finder>();
	private ArrayList<Updater> updaters = new ArrayList<Updater>();
	private ArrayList<Deleter> deleters = new ArrayList<Deleter>();

	public Table(JSONObject jsonObject) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.engine = JsonHelper.getStringValue(jsonObject, "engine", engine);
		this.charset = JsonHelper.getStringValue(jsonObject, "charset", charset);
		this.cacheable = JsonHelper.getBooleanValue(jsonObject, "cacheable", cacheable);
		this.cacheFindAll = JsonHelper.getBooleanValue(jsonObject, "cacheFindAll", cacheFindAll);

		if(null == name) {
			throw new H2ZeroParseException("The table 'name' attribute cannot be null.");
		}

		// now for the fields
		populateFields(jsonObject);
		populateFinders(jsonObject);
		populateUpdaters(jsonObject);
		populateDeleters(jsonObject);
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

					fields.add(baseField);
					fieldLookup.put(name, baseField);
					inFieldLookup.put(name, inBaseField);

					BaseField setBaseField = (BaseField)constructor.newInstance(fieldObject);
					setBaseField.suffixJavaName("Set");
					BaseField whereBaseField = (BaseField)constructor.newInstance(fieldObject);
					whereBaseField.suffixJavaName("Where");

					setFieldLookup.put(name, setBaseField);
					whereFieldLookup.put(name, whereBaseField);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	public String getName() { return(this.name); }
	public String getUpperName() { return(this.name.toUpperCase()); }
	public String getEngine() { return(this.engine); }
	public String getCharset() { return(this.charset); }
	public ArrayList<BaseField> getFields() { return(fields); }
	public ArrayList<Finder> getFinders() { return(finders); }
	public ArrayList<Updater> getUpdaters() { return(updaters); }
	public ArrayList<Deleter> getDeleters() { return(deleters); }
	public ArrayList<BaseField> getNonNullFields() { return(nonNullFields); }
	public boolean getCacheable() { return(cacheable); }
	public boolean getCacheFindAll() { return(cacheFindAll); }
	public BaseField getField(String name) { return(fieldLookup.get(name)); }
	public BaseField getInField(String name) { return(inFieldLookup.get(name)); }
	public BaseField getSetField(String name) { return(setFieldLookup.get(name)); }
	public BaseField getWhereField(String name) { return(whereFieldLookup.get(name)); }
	public String getJavaClassName() { return(NamingHelper.getFirstUpper(name)); }
	public String getJavaFieldName() { return(NamingHelper.getSecondUpper(name)); }
	public boolean getHasNonNullConstructor() { return(nonNullFields.size() != fields.size()); }

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
