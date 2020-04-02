package synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2020 synapticloop.
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
import java.util.Iterator;
import java.util.List;

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

	public View(JSONObject jsonObject, int defaultStatementCacheSize) throws H2ZeroParseException {
		super(jsonObject, defaultStatementCacheSize);

		this.name = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.NAME, null);
		jsonObject.remove(JSONKeyConstants.NAME);

		this.asClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.AS_CLAUSE, null);
		
		if(null == asClause) {
			// we also allow an array - so we can concatenate it together
			JSONArray asClauseArray = jsonObject.optJSONArray(JSONKeyConstants.AS_CLAUSE);
			if(null != asClauseArray) {
				StringBuilder stringBuilder = new StringBuilder();
				
				Iterator<Object> iterator = asClauseArray.iterator();
				while (iterator.hasNext()) {
					String snippet = (String) iterator.next();
					stringBuilder.append(snippet);
					stringBuilder.append(" ");
				}
				this.asClause = stringBuilder.toString();
			}
		}

		jsonObject.remove(JSONKeyConstants.AS_CLAUSE);

		if(null == name) {
			throw new H2ZeroParseException("The view '" + JSONKeyConstants.NAME + "' attribute cannot be null.");
		}

		if(null == asClause) {
			throw new H2ZeroParseException("The view '" + JSONKeyConstants.AS_CLAUSE + "' attribute cannot be null.");
		}

		this.cacheable = JsonHelper.getBooleanValue(jsonObject, JSONKeyConstants.CACHEABLE, false);
		jsonObject.remove(JSONKeyConstants.CACHEABLE);

		this.cacheFindAll = JsonHelper.getBooleanValue(jsonObject, JSONKeyConstants.CACHE_FIND_ALL, false);
		jsonObject.remove(JSONKeyConstants.CACHE_FIND_ALL);

		populateFields(jsonObject);
		populateFieldFinders(jsonObject);
		populateFinders(jsonObject);
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
					if(baseField.getPopulate()) {
						populateFields.add(baseField);
					} else {
						nonPopulateFields.add(baseField);
					}

				} catch (ClassNotFoundException | SecurityException | NoSuchMethodException | 
						IllegalArgumentException | InstantiationException | IllegalAccessException | 
						InvocationTargetException ex) {

					logFatalFieldParse(ex, ex.getCause().getMessage(), firstUpper);
				}
			}
		}

		jsonObject.remove(JSONKeyConstants.FIELDS);
	}

	@Override public BaseField getField(String name) { return(fieldLookup.get(name)); }

	public String getAsClause() { return asClause; }
	public List<BaseField> getFields() { return fields; }
	public boolean getCacheable() { return cacheable; }
	public boolean getCacheFindAll() { return cacheFindAll; }

	@Override public boolean getIsTable() { return(false); }
	@Override public boolean getIsView() { return(true); }
}
