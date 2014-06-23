package synapticloop.h2zero.base.form.manager;

import java.util.HashMap;

import org.apache.log4j.Logger;

import synapticloop.h2zero.base.form.field.BaseFormField;


public class FieldManager {
	private static final Logger logger = Logger.getLogger(FieldManager.class);
	private static HashMap<String, BaseFormField> baseFieldMap = new HashMap<String, BaseFormField>();

	public static BaseFormField getField(String name) {
		return(baseFieldMap.get(name));
	}

	public static void addToFieldMap(String name, BaseFormField baseField) {
		baseFieldMap.put(name, baseField);
		if(logger.isDebugEnabled()) {
			logger.debug("Adding to static field map, type: '" + baseField.getClass().getSimpleName() + "', with name: '" + name + "'.");
		}
	}
}
