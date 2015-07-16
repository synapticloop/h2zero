package synapticloop.h2zero.validator.table;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class TablePrimaryKeyTypeValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getPrimary() && baseField.getType().compareToIgnoreCase("bigint") != 0) {
					addFatalMessage("Primary key '" + table.getName() + "." + baseField.getName() + "' __MUST__ be of SQL type 'bigint'.");
				}
			}
		}
	}
}
