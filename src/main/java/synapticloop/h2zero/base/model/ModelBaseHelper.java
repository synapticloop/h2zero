package synapticloop.h2zero.base.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ModelBaseHelper {
	/**
	 * Add an object to the JSON object
	 * 
	 * @param jsonObject The JSON object to add the object to
	 * @param key the key to add to the JSON object
	 * @param object the object to add
	 */
	public static void addtoJSONObject(JSONObject jsonObject, String key, Object object) {
		jsonObject.put(key, object);
	}
	
	public static JSONArray getJSONArrayResponse(List<?> list) {
		JSONArray jsonArray = new JSONArray();
		for (Object object : list) {
			jsonArray.put(((ModelBase)object).toJSON());
		}
		return(jsonArray);
	}

	public static JSONObject getJSONResponse(Object object) {
		if(object instanceof List<?>) {
			getJSONArrayResponse((List<?>)object);
		}
		return(((ModelBase)object).getToJSON());
	}

}
