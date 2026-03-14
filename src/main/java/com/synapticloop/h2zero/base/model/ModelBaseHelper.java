package com.synapticloop.h2zero.base.model;

/*
 * Copyright (c) 2012-2026 synapticloop.
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ModelBaseHelper {
	/**
	 * Add an object to the JSON object
	 * 
	 * @param jsonObject The JSON object to add the object to
	 * @param key the key to add to the JSON object
	 * @param object the object to add
	 */
	public static void addToJSONObject(JSONObject jsonObject, String key, Object object) {
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
