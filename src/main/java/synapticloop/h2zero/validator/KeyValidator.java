package synapticloop.h2zero.validator;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import synapticloop.h2zero.model.BaseQueryObject;
import synapticloop.h2zero.model.BaseQueryObject.UsageType;

public abstract class KeyValidator extends Validator {

	public void validate(BaseQueryObject baseQueryObject) {
		JSONObject jsonObject = baseQueryObject.getJsonObject();
		String baseQueryObjectType = baseQueryObject.getType();
		String tableName = baseQueryObject.getBaseSchemaObject().getName();

		HashMap<String, UsageType> allowableJsonKeys = baseQueryObject.getAllowableJsonKeys();
		Iterator<String> iterator = allowableJsonKeys.keySet().iterator();

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object value = jsonObject.opt(key);
			UsageType usageType = allowableJsonKeys.get(key);

			if(null == value) {
				if(usageType.equals(BaseQueryObject.UsageType.MANDATORY)) {
					addFatalMessage(baseQueryObjectType + " '" + tableName + "." + baseQueryObject.getName() + "' does not have a key of '" + key + "' which is mandatory.");
				}
			} else {
				if(usageType.equals(BaseQueryObject.UsageType.INVALID)) {
					addFatalMessage(baseQueryObjectType + " '"  + tableName + "." + baseQueryObject.getName() + "' has a key of '" + key + "' which is invalid.");
				}
			}
		}

	}
}
