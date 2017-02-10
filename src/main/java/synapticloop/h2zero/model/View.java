package synapticloop.h2zero.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Map<String, BaseField> fieldLookup = new HashMap<String, BaseField>();

	public View(JSONObject jsonObject, int defaultStatementCacheSize) throws H2ZeroParseException {
		super(jsonObject, defaultStatementCacheSize);
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
		populateFieldFinders(jsonObject);
		populateQuestions(jsonObject);
		populateCounters(jsonObject);

		populateReferencedFieldTypes();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateFields(JSONObject jsonObject) throws H2ZeroParseException {
		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray(JSONKeyConstants.FIELDS);
		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Cannot create a view without '" + JSONKeyConstants.FIELDS + "'.", jsonex);
		}

		for (int i = 0; i < fieldJson.length(); i++) {
			String type = null;
			String name = null;
			JSONObject fieldObject = null;
			try {
				fieldObject = fieldJson.getJSONObject(i);
				type = fieldObject.getString(JSONKeyConstants.TYPE);
				name = fieldObject.getString(JSONKeyConstants.NAME);
			} catch (JSONException jsonex) {
				throw new H2ZeroParseException("Could not parse the '" + JSONKeyConstants.FIELDS + "' array.", jsonex);
			}

			if(null != type) {
				String firstUpper = NamingHelper.getFirstUpper(type);
				try {
					Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
					Constructor constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField)constructor.newInstance(fieldObject);

					fields.add(baseField);
					fieldLookup.put(name, baseField);

				} catch (ClassNotFoundException cnfex) {
					logFatalFieldParse(cnfex, cnfex.getCause().getMessage(), firstUpper);
				} catch (SecurityException e) {
					logFatalFieldParse(e, e.getCause().getMessage(), firstUpper);
				} catch (NoSuchMethodException e) {
					logFatalFieldParse(e, e.getCause().getMessage(), firstUpper);
				} catch (IllegalArgumentException e) {
					logFatalFieldParse(e, e.getCause().getMessage(), firstUpper);
				} catch (InstantiationException e) {
					logFatalFieldParse(e, e.getCause().getMessage(), firstUpper);
				} catch (IllegalAccessException e) {
					logFatalFieldParse(e, e.getCause().getMessage(), firstUpper);
				} catch (InvocationTargetException e) {
					logFatalFieldParse(e, e.getCause().getMessage(), firstUpper);
				}
			}
		}
	}

	public BaseField getField(String name) { return(fieldLookup.get(name)); }
	public String getAsClause() { return asClause; }
	public List<BaseField> getFields() { return fields; }
	public boolean getCacheable() { return cacheable; }
	public boolean getCacheFindAll() { return cacheFindAll; }

	public boolean getIsTable() { return(false); }
	public boolean getIsView() { return(true); }
}