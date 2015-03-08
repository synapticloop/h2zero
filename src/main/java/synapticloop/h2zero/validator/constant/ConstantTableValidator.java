package synapticloop.h2zero.validator.constant;

import java.util.ArrayList;
import java.util.HashSet;

import synapticloop.h2zero.model.Constant;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class ConstantTableValidator extends Validator {
	HashSet<String> primaryKeyValues = new HashSet<String>();
	HashSet<String> names = new HashSet<String>();

	@Override
	public boolean isValid(Database database, Options options) {

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			primaryKeyValues.clear();
			names.clear();
			if(table.getIsConstant()) {
				// go through and validate the values
				ArrayList<Constant> constants = table.getConstants();
				for (Constant constant : constants) {
					String primaryKeyValue = constant.getPrimaryKeyValue().toString();
					String name = constant.getName();
					if(primaryKeyValues.contains(primaryKeyValue)) {
						addFatalMessage("Constant model '" + table.getName() + "' has a duplicate primary key value of '" + primaryKeyValue + "'.");
					} else {
						primaryKeyValues.add(primaryKeyValue);
					}

					if(names.contains(name)) {
						addFatalMessage("Constant model '" + table.getName() + "' has a duplicate name of '" + name + "'.");
					} else {
						names.add(name);
					}
				}
			}
		}
		return(isValid);
	}

}
