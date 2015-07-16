package synapticloop.h2zero.validator;

import java.util.HashSet;
import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class TableNameDuplicateValidator extends Validator {
	private HashSet<String> names = new HashSet<String>();


	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			names.clear();
			String tableName = table.getName();
				if(names.contains(tableName)) {
					addFatalMessage("Database '" + database.getSchema() + "' has a duplicate table named '" + tableName + "'.");
				} else {
					names.add(tableName);
				}
		}
	}

}
