package synapticloop.h2zero.base.validator.bean;

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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class ValidationBean {
	private static final String JSON_KEY_IS_VALID = "isValid";
	private static final String JSON_KEY_NUM_VALID = "numValid";
	private static final String JSON_KEY_NUM_INVALID = "numInvalid";
	private static final String JSON_KEY_FIELDS = "fields";

	private List<ValidationFieldBean> validationFieldBeans = new ArrayList<>();

	private boolean isValid = true;

	public ValidationBean() {}

	public void addValidationFieldBean(ValidationFieldBean validationFieldBean) {
		validationFieldBeans.add(validationFieldBean);
		if(!validationFieldBean.getIsValid()) {
			this.isValid = false;
		}
	}

	public boolean getIsValid() { return(isValid); }

	public String toJsonString() {
		int numValid = 0;
		int numInvalid = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JSON_KEY_IS_VALID, isValid);

		JSONObject fieldObject = new JSONObject();

		for (ValidationFieldBean validationFieldBean : validationFieldBeans) {
			fieldObject.put(validationFieldBean.getFieldName(), validationFieldBean.toJSON());
			if(validationFieldBean.getIsValid()) {
				numValid++;
			} else {
				numInvalid++;
			}
		}

		jsonObject.put(JSON_KEY_NUM_VALID, numValid);
		jsonObject.put(JSON_KEY_NUM_INVALID, numInvalid);

		jsonObject.put(JSON_KEY_FIELDS, fieldObject);

		return(jsonObject.toString());
	}
}
