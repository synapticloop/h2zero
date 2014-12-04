package synapticloop.h2zero.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class Constant {
	private ArrayList<Object> values = new ArrayList<Object>();
	private ArrayList<Object> sqlValues = new ArrayList<Object>();
	private String name = null;
	private Object primaryKeyValue = null;

	// this should look like:
	// { "name": "BOOK", "values": [ 1, "book" ] },

	public Constant(JSONObject constantsObject, Table table) throws H2ZeroParseException {

		try {
			this.name = constantsObject.optString("name");
			if(null == name) {
				throw new H2ZeroParseException("name cannot be null for constant object '" + constantsObject + "'.");
			}

			this.name = this.name.toString().toUpperCase().replaceAll("[^A-Z]", "_");

			JSONArray valuesArray = constantsObject.getJSONArray("values");
			for(int i = 0; i < valuesArray.length(); i++) {
				Object object = valuesArray.get(i);


				if(object instanceof String) {
					sqlValues.add("'" + ((String)object).replaceAll("'", "''") + "'");
					String stringified = "\"" + object + "\"";
					values.add(stringified);
					// the first value must be the primary key
					if(i == 0) {
						this.primaryKeyValue = stringified;
					}
				} else {
					sqlValues.add(object);
					String stringified = "new " + table.getFields().get(i).getJavaType() + "(" + object + ")";
					values.add(stringified);
					if(i == 0) {
						this.primaryKeyValue = stringified;
					}
				}


			}

		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not parse constants object '" + constantsObject + "'.");
		}
	}

	public ArrayList<Object> getValues() { return values; }
	public ArrayList<Object> getSqlValues() { return sqlValues; }
	public String getName() { return name; }
	public Object getPrimaryKeyValue() { return primaryKeyValue; }
	public void setPrimaryKeyValue(Object primaryKeyValue) { this.primaryKeyValue = primaryKeyValue; }
}
