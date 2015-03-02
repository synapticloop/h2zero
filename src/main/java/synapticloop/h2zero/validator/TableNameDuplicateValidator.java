package synapticloop.h2zero.validator;

import java.util.ArrayList;
import java.util.HashSet;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class TableNameDuplicateValidator extends Validator {
	private HashSet<String> names = new HashSet<String>();


	@Override
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			names.clear();
			String tableName = table.getName();
				if(names.contains(tableName)) {
					addFatalMessage("Database '" + database.getSchema() + "' has a duplicate table named '" + tableName + "'.");
				} else {
					names.add(tableName);
				}
		}
		return(isValid);
	}

}
