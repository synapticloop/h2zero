package synapticloop.h2zero.util;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;

import synapticloop.h2zero.model.BaseSchemaObject;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

/*
 * Copyright (c) 2012-2019 synapticloop.
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
