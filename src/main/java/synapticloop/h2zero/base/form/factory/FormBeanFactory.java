package synapticloop.h2zero.base.form.factory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import synapticloop.h2zero.base.form.FormBean;
import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.manager.FieldManager;


public class FormBeanFactory {
	public static Map<String, LinkedHashMap<String, BaseFormField>> formFieldMap = new HashMap<String, LinkedHashMap<String,BaseFormField>>();

	/**
	 * Add an entry to the form's field map
	 *
	 * @param formName the form name
	 * @param fieldName the field name
	 * @param baseField the actual field
	 */
	public static void addToFieldMap(String formName, String fieldName, BaseFormField baseField) {
		LinkedHashMap<String, BaseFormField> fieldMap = null;
		//formFieldMap.get(formName);

		if(!formFieldMap.containsKey(formName)) {
			fieldMap = new LinkedHashMap<String, BaseFormField>();
		} else {
			fieldMap = formFieldMap.get(formName);
		}
		fieldMap.put(fieldName, baseField);
		formFieldMap.put(formName, fieldMap);
		FieldManager.addToFieldMap(fieldName, baseField);
	}

	/**
	 * Get the form bean for a particular form name
	 *
	 * @param formName the form name
	 *
	 * @return the newly created formbean
	 */
	public static FormBean getFormBeanInstance(String formName) {
		System.out.println("Getting form for name '" + formName + "'.");
		System.out.println(formFieldMap.get(formName));
		return(new FormBean(new LinkedHashMap<String, BaseFormField>(formFieldMap.get(formName))));
	}
}
