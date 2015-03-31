package synapticloop.h2zero.validator.table;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class TablePrimaryKeyNameValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getPrimary()) {
					if(baseField.getName().compareToIgnoreCase("id_" + table.getName()) != 0) {
						addWarnMessage("Primary key for table '" + table.getName() + "' __SHOULD__ be named 'id_" + table.getName() + "', currently named '" + baseField.getName() + "'.");
					}
				}
			}
		}
	}
}
