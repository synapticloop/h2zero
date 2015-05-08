package synapticloop.h2zero.validator.table;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class TableIgnoredKeysValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<String> foundIgnoredKeys = table.getFoundIgnoredKeys();
			for (String key : foundIgnoredKeys) {
				addWarnMessage("Table '" + table.getName() + "' has a json key of '" + key + "' which is no longer valid and consequently ignored.");
			}

			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				foundIgnoredKeys = baseField.getFoundIgnoredKeys();
				for (String key : foundIgnoredKeys) {
					addWarnMessage("Field '" + table.getName() + "." + baseField.getName() +  "' has a json key of '" + key + "' which is no longer valid and consequently ignored.");
				}
			}
		}

	}

}
