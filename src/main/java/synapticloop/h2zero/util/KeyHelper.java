package synapticloop.h2zero.util;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;

import synapticloop.h2zero.model.BaseSchemaObject;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

public class KeyHelper {

	public static void findMissingKeys(BaseSchemaObject baseSchemaObject, JSONObject jsonObject, Set<String> allowableKeys) {
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if(!allowableKeys.contains(key)) {
				SimpleLogger.logWarn(LoggerType.PARSE, baseSchemaObject.getClass(), "Found a key with name '" + key + "', which is not utilised.");
			}
		}
	}

}
