package synapticloop.h2zero.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class View {
	private String name;
	private String asClause;
	private boolean cacheable = false;
	private boolean cacheFindAll= false;

	private ArrayList<BaseField> fields = new ArrayList<BaseField>();
	private HashMap<String, BaseField> fieldLookup = new HashMap<String, BaseField>();
//	private HashMap<String, BaseField> setFieldLookup = new HashMap<String, BaseField>();
//	private HashMap<String, BaseField> whereFieldLookup = new HashMap<String, BaseField>();

	private ArrayList<Finder> finders = new ArrayList<Finder>();

	public View(JSONObject jsonObject) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.asClause = JsonHelper.getStringValue(jsonObject, "asClause", null);
		
		if(null == name) {
			throw new H2ZeroParseException("The view 'name' attribute cannot be null.");
		}

		if(null == asClause) {
			throw new H2ZeroParseException("The view 'asClause' attribute cannot be null.");
		}

		this.cacheable = JsonHelper.getBooleanValue(jsonObject, "cacheable", false);
		this.cacheFindAll = JsonHelper.getBooleanValue(jsonObject, "cacheFindAll", false);

		populateFields(jsonObject);
		populateFinders(jsonObject);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateFields(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray("fields");
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("Cannot create a view without 'fields'.");
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
					Constructor constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField)constructor.newInstance(fieldObject);

					fields.add(baseField);
					fieldLookup.put(name, baseField);

//					BaseField setBaseField = (BaseField)constructor.newInstance(fieldObject);
//					setBaseField.suffixJavaName("Set");
//					BaseField whereBaseField = (BaseField)constructor.newInstance(fieldObject);
//					whereBaseField.suffixJavaName("Where");
//
//					setFieldLookup.put(name, setBaseField);
//					whereFieldLookup.put(name, whereBaseField);
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
				getFinders().add(new Finder(finderObject, this));
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse finder.");
			}
		}
	}

	public BaseField getField(String name) { return(fieldLookup.get(name)); }
	public String getName() { return name; }
	public String getAsClause() { return asClause; }
	public String getJavaClassName() { return(NamingHelper.getFirstUpper(name)); }
	public String getJavaFieldName() { return(NamingHelper.getSecondUpper(name)); }
	public ArrayList<BaseField> getFields() { return fields; }
	public boolean getCacheable() { return cacheable; }
	public ArrayList<Finder> getFinders() { return finders; }
	public boolean getCacheFindAll() { return cacheFindAll; }
}