package synapticloop.h2zero.validator;

/*
 * Copyright (c) 2013-2024 synapticloop.
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

import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import synapticloop.h2zero.model.BaseQueryObject;
import synapticloop.h2zero.model.BaseQueryObject.UsageType;

public abstract class BaseKeyValidator extends BaseValidator {

	public void validate(BaseQueryObject baseQueryObject) {
		JSONObject jsonObject = baseQueryObject.getJsonObject();
		String baseQueryObjectType = baseQueryObject.getType();
		String tableName = baseQueryObject.getBaseSchemaObject().getName();

		Map<String, UsageType> allowableJsonKeys = baseQueryObject.getAllowableJsonKeys();
		Iterator<String> iterator = allowableJsonKeys.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
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
