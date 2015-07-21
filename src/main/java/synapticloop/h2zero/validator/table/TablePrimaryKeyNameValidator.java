package synapticloop.h2zero.validator.table;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class TablePrimaryKeyNameValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getPrimary() && baseField.getName().compareToIgnoreCase("id_" + table.getName()) != 0) {
					addWarnMessage("Primary key for table '" + table.getName() + "' __SHOULD__ be named 'id_" + table.getName() + "', currently named '" + baseField.getName() + "'.");
				}
			}
		}
	}
}
