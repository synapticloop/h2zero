package synapticloop.h2zero.util.validator;

import java.util.ArrayList;
import java.util.HashSet;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class DuplicateFieldNameValidator extends Validator {
	private HashSet<String> names = new HashSet<String>();

	@Override
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			names.clear();
			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				String fieldName = baseField.getName();
				if(names.contains(fieldName)) {
					addFatalMessage("Table '" + table.getName() + "' has a duplicate field named '" + fieldName + "'.");
				} else {
					names.add(fieldName);
				}
			}
		}
		return(isValid);
	}
}
