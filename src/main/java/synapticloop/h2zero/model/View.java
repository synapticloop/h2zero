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
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class View extends BaseSchemaObject {
	private String asClause;
	private boolean cacheable = false;
	private boolean cacheFindAll= false;

	private ArrayList<BaseField> fields = new ArrayList<BaseField>();
	private HashMap<String, BaseField> fieldLookup = new HashMap<String, BaseField>();

	public View(JSONObject jsonObject) throws H2ZeroParseException {
		super(jsonObject);
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.asClause = JsonHelper.getStringValue(jsonObject, "asClause", null);
		
		if(null == name) {
			throw new H2ZeroParseException("The view '" + JSONKeyConstants.NAME + "' attribute cannot be null.");
		}

		if(null == asClause) {
			throw new H2ZeroParseException("The view '" + JSONKeyConstants.AS_CLAUSE + "' attribute cannot be null.");
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
			fieldJson = jsonObject.getJSONArray(JSONKeyConstants.FIELDS);
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("Cannot create a view without '" + JSONKeyConstants.FIELDS + "'.");
		}

		for (int i = 0; i < fieldJson.length(); i++) {
			String type = null;
			String name = null;
			JSONObject fieldObject = null;
			try {
				fieldObject = fieldJson.getJSONObject(i);
				type = fieldObject.getString(JSONKeyConstants.TYPE);
				name = fieldObject.getString(JSONKeyConstants.NAME);
			} catch (JSONException ojjsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.FIELDS + "' array.");
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

	public BaseField getField(String name) { return(fieldLookup.get(name)); }
	public String getAsClause() { return asClause; }
	public ArrayList<BaseField> getFields() { return fields; }
	public boolean getCacheable() { return cacheable; }
	public boolean getCacheFindAll() { return cacheFindAll; }
}