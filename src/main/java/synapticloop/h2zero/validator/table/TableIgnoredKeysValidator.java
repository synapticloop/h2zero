package synapticloop.h2zero.validator.table;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseValidator;

public class TableIgnoredKeysValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<String> foundIgnoredKeys = table.getFoundIgnoredKeys();
			String replacementKey = "There is no replacement for this key.";
			for (String key : foundIgnoredKeys) {
				if(table.getReplacementForKey(key) != null) {
					replacementKey = "This should be replaced by key '" + table.getReplacementForKey(key) + "'.";
				}
				addWarnMessage("Table '" + table.getName() + "' has a json key of '" + key + "' which is no longer valid and consequently ignored.  " + replacementKey);
			}
		}

	}

}
