package synapticloop.h2zero.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class Inserter {
	private ArrayList<BaseField> valueFields = new ArrayList<BaseField>();
	private LinkedHashMap<String, BaseField> uniqueValueFields = new LinkedHashMap<String, BaseField>();
	private ArrayList<BaseField> selectFields = new ArrayList<BaseField>();
	private ArrayList<BaseField> whereFields = new ArrayList<BaseField>();

	private ArrayList<BaseField> allFields = new ArrayList<BaseField>();

	private HashSet<String> allUniqueFieldNames = new HashSet<String>();
	private ArrayList<BaseField> allUniqueFields = new ArrayList<BaseField>();

	private String name;
	private String insertClause;
	private String valuesClause;
	private String selectClause;
	private String whereClause;

	public Inserter(JSONObject jsonObject, Table table) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);

		this.insertClause = JsonHelper.getStringValue(jsonObject, "insertClause", null);
		this.valuesClause = JsonHelper.getStringValue(jsonObject, "valuesClause", null);
		this.selectClause = JsonHelper.getStringValue(jsonObject, "selectClause", null);
		this.whereClause = JsonHelper.getStringValue(jsonObject, "whereClause", null);

		if(null != selectClause) {
			populateFields(jsonObject, "selectFields", selectFields);
		}

		if(null != whereClause) {
			populateFields(jsonObject, "whereFields", whereFields);
		}

		// now for the value fields
		try {
			JSONArray valueFieldArray = jsonObject.getJSONArray("valueFields");
			for (int i = 0; i < valueFieldArray.length(); i++) {
				String valueFieldName = valueFieldArray.getString(i);
				BaseField baseField = table.getField(valueFieldName);
				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up value field '" + valueFieldName + "'.");
				}

				BaseField valueBaseField = table.getSetField(valueFieldName);

				if(!uniqueValueFields.containsKey(valueBaseField.getJavaName())) {
					uniqueValueFields.put(valueBaseField.getJavaName(), valueBaseField);
				}

				valueFields.add(valueBaseField);
				allFields.add(valueBaseField);
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}

		if(null == name) {
			throw new H2ZeroParseException("The inserter 'name' attribute cannot be null for table '" + table.getName() + "'.");
		}

		if(null != insertClause) {
			if(null == valuesClause && null == selectClause) {
				throw new H2ZeroParseException("The inserter '" + name + "' for table '" + table.getName() + "'has an insert clause, but neither valuesClause nor selectClause.");
			}
		} else {
			if(null != valuesClause && null != selectClause) {
				throw new H2ZeroParseException("The inserter '" + name + "' for table '" + table.getName() + "'has both a valuesClause and selectClause.");
			}
		}

		if(null != selectClause && valueFields.size() != 0) {
			throw new H2ZeroParseException("The inserter '" + name + "' for table '" + table.getName() + "'has selectClause and value fields.");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateFields(JSONObject jsonObject, String jsonKey, ArrayList<BaseField> fields) throws H2ZeroParseException {
		JSONArray fieldJson = new JSONArray();
		try {
			fieldJson = jsonObject.getJSONArray(jsonKey);
		} catch (JSONException ojjsonex) {
			// do nothing
			return;
		}

		for (int i = 0; i < fieldJson.length(); i++) {
			String type = null;
			JSONObject fieldObject = null;

			try {
				fieldObject = fieldJson.getJSONObject(i);
				type = fieldObject.getString("type");

				// check to ensure that the field has a name
				fieldObject.getString("name");
			} catch (JSONException ojjsonex) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Could not parse the '");
				stringBuilder.append(jsonKey);
				stringBuilder.append("' array.\n");
				stringBuilder.append("Was expecting the format to be:\n");
				stringBuilder.append("\"");
				stringBuilder.append(jsonKey);
				stringBuilder.append("\": [\n");
				
				stringBuilder.append("  { \"name\": \"<fieldName1>\", \"type\": \"<type>\" },\n");
				stringBuilder.append("  { \"name\": \"<fieldName2>\", \"type\": \"<type>\" },\n");
				stringBuilder.append("]\n");
				throw new H2ZeroParseException(stringBuilder.toString());
			}

			if(null != type) {
				String firstUpper = NamingHelper.getFirstUpper(type);
				try {
					Class forName = Class.forName("synapticloop.h2zero.model.field." + firstUpper + "Field");
					Constructor constructor = forName.getConstructor(JSONObject.class);
					BaseField baseField = (BaseField)constructor.newInstance(fieldObject);

					fields.add(baseField);
					addToAllFields(baseField);

				} catch (Exception ex) {
					throw new H2ZeroParseException(ex.getMessage());
				}
			}
		}
	}

	private void addToAllFields(BaseField baseField) {
		allFields.add(baseField);

		if(!allUniqueFieldNames.contains(baseField.getName())) {
			allUniqueFields.add(baseField);
		}

		allUniqueFieldNames.add(baseField.getName());
	}

	public String getName() { return(name); }

	public String getStaticName() { return(NamingHelper.getStaticName(name)); }

	public String getInsertClause() { return insertClause; }
	public String getValuesClause() { return valuesClause; }
	public String getSelectClause() { return selectClause; }
	public String getWhereClause() { return whereClause; }

	public boolean getHasInsertClause() { return(null != insertClause); }
	public boolean getHasSelectClause() { return(null != selectClause); }
	public boolean getHasValuesClause() { return(null != valuesClause); }
	public boolean getHasWhereClause() { return(null != whereClause); }

	public ArrayList<BaseField> getValueFields() { return valueFields; }
	public LinkedHashMap<String, BaseField> getUniqueValueFields() { return uniqueValueFields; }
	public ArrayList<BaseField> getSelectFields() { return selectFields; }
	public ArrayList<BaseField> getWhereFields() { return whereFields; }
	public ArrayList<BaseField> getAllFields() { return allFields; }
	public ArrayList<BaseField> getAllUniqueFields() { return allUniqueFields; }
}
