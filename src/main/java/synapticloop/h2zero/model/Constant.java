package synapticloop.h2zero.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class Constant {
	private ArrayList<Object> values = new ArrayList<Object>();
	private String name = null;
	private String value = null;

	public Constant(JSONArray constantsArray, Table table) throws H2ZeroParseException {
		int length = constantsArray.length();
		if(length != 2) {
			throw new H2ZeroParseException("Constants are a lookup pair, and may only have 2 entries - constants array is '" + constantsArray + "'.");
		}


		try {
			Object valueObject = constantsArray.get(0);
			Object nameObject = constantsArray.get(1);

			this.value = valueObject.toString();
			this.name = nameObject.toString().toUpperCase().replaceAll("[^A-Z]", "_");

			if(nameObject instanceof String) {
				nameObject = "'" + ((String)nameObject).replaceAll("'", "''") + "'";
			}

			values.add(valueObject);
			values.add(nameObject);


		} catch (JSONException jsonex) {
			throw new H2ZeroParseException("Could not parse constants array '" + constantsArray + "'.");
		}
	}

	public ArrayList<Object> getValues() { return values; }
	public String getName() { return name; }
	public String getValue() { return value; }
}
