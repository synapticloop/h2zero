package com.synapticloop.h2zero.util;

/*
 * Copyright (c) 2012-2024 synapticloop.
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
