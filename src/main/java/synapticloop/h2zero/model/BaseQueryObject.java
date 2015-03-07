package synapticloop.h2zero.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.FieldLookupHelper;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public abstract class BaseQueryObject {
	protected JSONObject jsonObject = null;
	protected Table table = null;

	protected String name;
	protected String selectClause;
	protected String whereClause;
	protected String orderBy;

	protected ArrayList<BaseField> whereFields = new ArrayList<BaseField>();
	protected LinkedHashMap<String, BaseField> uniqueWhereFields = new LinkedHashMap<String, BaseField>();
	protected ArrayList<BaseField> selectFields = new ArrayList<BaseField>();

	protected boolean hasInFields = false;
	protected ArrayList<BaseField> inWhereFields = new ArrayList<BaseField>();

	protected BaseQueryObject(Table table, JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		this.table = table;
		this.name = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.NAME, null);
		this.whereClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.WHERE_CLAUSE, null);
		this.orderBy = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.ORDER_BY, null);
		this.selectClause = JsonHelper.getStringValue(jsonObject, JSONKeyConstants.SELECT_CLAUSE, null);
	}

	protected void populateWhereFields(JSONObject jsonObject) throws H2ZeroParseException {
		// we may not have any whereFields
		try {
			JSONArray whereFieldArray = jsonObject.getJSONArray(JSONKeyConstants.WHERE_FIELDS);

			if(null == whereClause && whereFieldArray.length() > 0) {
				throw new H2ZeroParseException(this.getClass().getSimpleName() + " '" + name + "' cannot have '" + JSONKeyConstants.WHERE_FIELDS + "' when there is no '" + JSONKeyConstants.WHERE_CLAUSE + "'.");
			}

			for (int i = 0; i < whereFieldArray.length(); i++) {
				String whereFieldName = whereFieldArray.getString(i);

				BaseField baseField = FieldLookupHelper.getBaseField(table, whereFieldName);

				if(null == baseField) {
					throw new H2ZeroParseException("Could not look up where field '" + whereFieldName + "', for " + this.getClass().getSimpleName() + "'" + name + "'.");
				}

				whereFields.add(baseField);
				if(baseField.getIsInField()) {
					inWhereFields.add(baseField);
				}

				if(!uniqueWhereFields.containsKey(whereFieldName)) {
					uniqueWhereFields.put(whereFieldName, baseField);
				}
			}
		} catch (JSONException ojjsonex) {
			// do nothing
		}
	}


	public String getName() { return(name); }
	public String getTagName() { return(NamingHelper.getFirstUpper(name)); }
	public String getWhereClause() { return(whereClause); }
	public String getOrderBy() { return(orderBy); }
	public ArrayList<BaseField> getWhereFields() { return(whereFields); }
	public ArrayList<BaseField> getSelectFields() { return(selectFields); }
	public ArrayList<BaseField> getInWhereFields() { return(inWhereFields); }
	public boolean getHasInFields() { return(hasInFields); }
	public String getSelectClause() { return selectClause; }
	public void setSelectClause(String selectClause) { this.selectClause = selectClause; }
	public void setWhereClause(String whereClause) { this.whereClause = whereClause; }

	public String getStaticName() { return(NamingHelper.getStaticName(name)); }
	public Collection<BaseField> getUniqueWhereFields() { return(this.uniqueWhereFields.values()); }
}
