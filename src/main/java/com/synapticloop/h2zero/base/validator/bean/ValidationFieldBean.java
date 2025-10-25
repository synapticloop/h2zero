package com.synapticloop.h2zero.base.validator.bean;

/*
 * Copyright (c) 2012-2025 synapticloop.
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

import org.json.JSONObject;

public class ValidationFieldBean {
	private String fieldName = null;
	private String fieldValue = null;

	private boolean isOverLength = false;
	private boolean isUnderLength = false;
	private boolean isIncorrectNullability = false;
	private boolean isIncorrectForeignKey = false;

	private boolean isIncorrectFormat = false;

	private boolean isValid = true;

	public ValidationFieldBean(String fieldName, String fieldValue, boolean isOverLength, boolean isUnderLength, boolean isIncorrectNullability) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.isOverLength = isOverLength;
		this.isUnderLength = isUnderLength;
		this.isIncorrectNullability = isIncorrectNullability;

		if(isOverLength) { this.isValid = false; }
		if(isUnderLength) { this.isValid = false; }
		if(isIncorrectNullability) { this.isValid = false; }
	}

	public ValidationFieldBean(String fieldName, String fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() { return(fieldName); }
	public String getFieldValue() { return(fieldValue); }

	public boolean getIsOverLength() { return(isOverLength); }
	public void setIsOverLength(boolean isOverLength) { 
		this.isOverLength = isOverLength;
		if(isOverLength) {
			this.isValid = false;
		}
	}

	public boolean getIsUnderLength() { return(isUnderLength); }
	public void setIsUnderLength(boolean isUnderLength) {
		this.isUnderLength = isUnderLength; 
		if(isUnderLength) {
			this.isValid = false;
		}
	}

	public boolean getIsIncorrectNullability() { return(isIncorrectNullability); }
	public void setIsIncorrectNullability(boolean isIncorrectNullability) {
		this.isIncorrectNullability = isIncorrectNullability;
		if(isIncorrectNullability) {
			this.isValid = false;
		}
	}

	public boolean getIsIncorrectForeignKey() { return(isIncorrectForeignKey);}
	public void setIsIncorrectForeignKey(boolean isIncorrectForeignKey) { 
		this.isIncorrectForeignKey = isIncorrectForeignKey;
		if(isIncorrectForeignKey) {
			this.isValid = false;
		}
	}
	
	public boolean getIsIncorrectFormat() { return(isIncorrectFormat);}
	public void setIsIncorrectFormat(boolean isIncorrectFormat) { 
		this.isIncorrectFormat = isIncorrectFormat;
		if(isIncorrectFormat) {
			this.isValid = false;
		}
	}


	public boolean getIsValid() { return(this.isValid); }

	public JSONObject toJSON() {
		JSONObject validationObject = new JSONObject();

		validationObject.put("fieldName", fieldName);
		validationObject.put("fieldValue", fieldValue);
		
		validationObject.put("isLengthError", (isUnderLength && isOverLength));

		validationObject.put("isOverLength", isOverLength);
		validationObject.put("isUnderLength", isUnderLength);
		
		validationObject.put("isIncorrectNullability", isIncorrectNullability);
		validationObject.put("isIncorrectForeignKey", isIncorrectForeignKey);
		
		validationObject.put("isValid", !(isOverLength && isUnderLength && isIncorrectNullability && isIncorrectForeignKey));

		return(validationObject);
	}

	public String toJSONString() {
		JSONObject jsonObject= new JSONObject();

		jsonObject.put(fieldName, toJSON());

		return(jsonObject.toString());
	}
}
