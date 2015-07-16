package synapticloop.h2zero.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class FieldNameDuplicateValidator extends Validator {
	private Set<String> names = new HashSet<String>();

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			names.clear();
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				String fieldName = baseField.getName();
				if(names.contains(fieldName)) {
					addFatalMessage("Table '" + table.getName() + "' has a duplicate field named '" + fieldName + "'.");
				} else {
					names.add(fieldName);
				}
			}
		}
	}
}
