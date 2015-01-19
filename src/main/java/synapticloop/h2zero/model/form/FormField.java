package synapticloop.h2zero.model.form;

/*
 * Copyright (c) 2013-2015 synapticloop.
 * 
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

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class FormField {
	private static final String SYNAPTICLOOP_H2ZERO_BASE_FORM_FIELD = "synapticloop.h2zero.base.form.field.";

	private String name;
	private boolean nullable;
	private boolean confirm;
	private int minLength;
	private int maxLength;
	private String validator;

	public FormField(JSONObject jsonObject) throws H2ZeroParseException {
		this.name = JsonHelper.getStringValue(jsonObject, "name", null);
		this.nullable = JsonHelper.getBooleanValue(jsonObject, "nullable", false);
		this.nullable = JsonHelper.getBooleanValue(jsonObject, "confirm", false);
		this.minLength = JsonHelper.getIntValue(jsonObject, "minLength", 0);
		this.maxLength = JsonHelper.getIntValue(jsonObject, "length", 0);
		this.validator = JsonHelper.getStringValue(jsonObject, "validator", null);
		System.out.println(jsonObject);
		validateFormField();
	}

	public FormField(BaseField baseField) throws H2ZeroParseException {
		this.name = baseField.getName();
		this.nullable = baseField.getNullable();
		this.minLength = baseField.getMinLength();
		this.maxLength = baseField.getMaxLength();
		this.validator = baseField.getValidator();
		validateFormField();
	}

	private void validateFormField() throws H2ZeroParseException {
		if (null == name) {
			throw new H2ZeroParseException("The name for a form field may not be null.");
		}

		if (maxLength == 0) {
			throw new H2ZeroParseException("The maximum length for a form field was not set, or was invalid and is set to 0. Name of '" + name + "'.");
		}

		if (!this.nullable && this.minLength == 0) {
			throw new H2ZeroParseException("The form field is not allowed to be null, but the minLength is 0.");
		}

		if (null == validator) {
			throw new H2ZeroParseException("The validator for a form field may not be null.");
		} else {
			// try and look up the validator
			boolean found = findClass(SYNAPTICLOOP_H2ZERO_BASE_FORM_FIELD + validator);

			// if we haven't found it, maybe it is an alternate external one
			if (!found) {
				found = findClass(validator);
			}

			// if we still haven't found it, then throw an exception
			if (!found) {
				throw new H2ZeroParseException("Could not find a validator of '" + SYNAPTICLOOP_H2ZERO_BASE_FORM_FIELD + validator + "' or '" + validator);
			}
		}
	}

	private boolean findClass(String className) {
		try {
			Class.forName(SYNAPTICLOOP_H2ZERO_BASE_FORM_FIELD + validator);
			return(true);
		} catch (ClassNotFoundException ignore) {
		} catch (SecurityException ignore) {
		} catch (IllegalArgumentException ignore) {
		}
		return(false);
	}

	public String getName() { return(NamingHelper.getSecondUpper(name)); }

	// @TODO - rename these please (see java-create-form-bean.templar
	public String getGetterName() { return(NamingHelper.getFirstUpper(name)); }
	public String getOriginalName() { return(name); }
	public String getUpperName() { return(name.toUpperCase()); }
	// @TODO - end re-naming

	public boolean getNullable() { return (nullable); }
	public boolean getConfirm() { return (confirm); }
	public int getMinLength() { return (minLength); }
	public int getMaxLength() { return (maxLength); }
	public String getValidator() { return (validator); }
}
