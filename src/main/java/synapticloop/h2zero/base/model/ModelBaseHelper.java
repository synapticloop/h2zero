package synapticloop.h2zero.base.model;

import org.json.JSONObject;

public class ModelBaseHelper {
	public static void addtoJSONObject(JSONObject jsonObject, String key, Object object) {
		jsonObject.put(key, object);
	}
}
