package synapticloop.h2zero.base.form.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.h2zero.base.form.field.BaseFormField;


public class FieldManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(FieldManager.class);

	private static Map<String, BaseFormField> baseFieldMap = new HashMap<String, BaseFormField>();

	private FieldManager() {}

	public static BaseFormField getField(String name) {
		return(baseFieldMap.get(name));
	}

	public static void addToFieldMap(String name, BaseFormField baseField) {
		baseFieldMap.put(name, baseField);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Adding to static field map, type: '" + baseField.getClass().getSimpleName() + "', with name: '" + name + "'.");
		}
	}
}
