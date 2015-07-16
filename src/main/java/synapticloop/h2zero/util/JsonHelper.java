package synapticloop.h2zero.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
	private JsonHelper() {}

	public static String getStringValue(JSONObject jsonObject, String key, String defaultValue) {
		try {
			return(jsonObject.getString(key));
		} catch (JSONException ojjsonex) {
			// do nothing
		}
		return(defaultValue);
	}

	public static boolean getBooleanValue(JSONObject jsonObject, String key, boolean defaultValue) {
		try {
			return(jsonObject.getBoolean(key));
		} catch (JSONException ojjsonex) {
			// do nothing
		}
		return(defaultValue);
	}

	public static Boolean getBooleanValue(JSONObject jsonObject, String key, Boolean defaultValue) {
		try {
			return(jsonObject.getBoolean(key));
		} catch (JSONException ojjsonex) {
			// do nothing
		}
		return(defaultValue);
	}

	public static int getIntValue(JSONObject jsonObject, String key, int defaultValue) {
		try {
			return(jsonObject.getInt(key));
		} catch (JSONException ojjsonex) {
			// do nothing
		}
		return(defaultValue);
	}
}
