package synapticloop.h2zero.base.form;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.field.EmailFormField;


public class FormBean {
	private LinkedHashMap<String, BaseFormField> fieldMap = new LinkedHashMap<String, BaseFormField>();
	private LinkedHashSet<BaseFormField> errorFields = new LinkedHashSet<BaseFormField>();
	private LinkedHashSet<String> errorFieldNames = new LinkedHashSet<String>();

	public FormBean(LinkedHashMap<String, BaseFormField> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public BaseFormField getField(String name) {
		return(fieldMap.get(name));
	}

	public String getFieldValue(String fieldName) {
		BaseFormField baseFormField = getField(fieldName);
		if(null == baseFormField) {
			return("");
		} else {
			return(baseFormField.getValue());
		}

	}

	// TODO HACKY!!!
	public void setFieldInError(String fieldName) {
		errorFields.add(new EmailFormField("", true, false, 0, 0));
	}

	public boolean getIsFieldInError(String fieldName) {
		return(errorFieldNames.contains(fieldName));
	}

	public LinkedHashSet<BaseFormField> getFieldsInError() {
		return(errorFields);
	}

	public void process(HttpServletRequest httpServletRequest) {
		populate(httpServletRequest);
		validate();
	}

	private void populate(HttpServletRequest httpServletRequest) {
		Iterator<String> fieldMapIterator = fieldMap.keySet().iterator();
		while (fieldMapIterator.hasNext()) {
			String key = fieldMapIterator.next();
			BaseFormField baseField = fieldMap.get(key);
			baseField.populate(httpServletRequest);
		}
	}

	private void validate() {
		errorFields.clear();
		Iterator<String> fieldMapIterator = fieldMap.keySet().iterator();
		while (fieldMapIterator.hasNext()) {
			boolean shouldValidate = true;
			String key = fieldMapIterator.next();
			BaseFormField baseField = fieldMap.get(key);

			if(baseField.getOnlyIf()) {
				// need to ensure that we want to validate - this depends on whether
				// 'onlyif' is specified in the configuration

				// this is the field base that holds the value to test
				BaseFormField onlyIfBaseField = fieldMap.get(baseField.getOnlyIfFieldName());
				if(null != onlyIfBaseField) {
					String onlyIfBaseFieldValue = onlyIfBaseField.getValue();

					// we want to test to make sure that onlyIfBaseFieldValue is equal to
					//
					if(null != onlyIfBaseFieldValue) {
						if(!onlyIfBaseFieldValue.equals(baseField.getOnlyIfFieldValue())) {
							shouldValidate = false;
							baseField.setValue(null);
						}
					}
				}

			}

			// now we need to check the exclusive or field
			if(null != baseField.getOrField()) {
				BaseFormField orBaseField = fieldMap.get(baseField.getOrField());
				if(null != orBaseField) {
					if(null == baseField.getValue() && null != orBaseField.getValue()) {
						shouldValidate = false;
					}
				}
			}

			if(shouldValidate) {
				if(!baseField.isValid()) {
					errorFields.add(baseField);
					errorFieldNames.add(key);
				}
			} else {
				// we need to remove it from the errorFields as it probably has already
				// been validated
				errorFields.remove(baseField);
				errorFieldNames.remove(key);
			}
		}
	}

	public boolean isValid() {
		return(errorFields.isEmpty());
	}

	public LinkedHashMap<String, BaseFormField> getValues() {
		return(fieldMap);
	}

	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		Iterator<String> iterator = fieldMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			try {
				jsonObject.put(key, fieldMap.get(key).getValue());
			} catch (JSONException ojjsonex) {
				// we are screwed
			}
		}
		return (jsonObject.toString());
	}
}
