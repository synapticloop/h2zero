package synapticloop.h2zero.validator.table;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class TableDeprecatedForeignKeyValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getHasDeprecatedForeignKey()) {
					addWarnMessage("Field '" + table.getName() + "." + baseField.getName() + "' has a deprecated key of 'foreign', this should be replaced with \"foreignKey\": \"" + baseField.getForeignKeyTable() + "." + baseField.getForeignKeyField() + "\".");
				}
			}
		}
	}

}
