package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Deleter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class DeleterNameValidator extends Validator {

	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Deleter> deleters = table.getDeleters();
			for (Deleter deleter : deleters) {
				String name = deleter.getName();
				if(name.contains(" ")) {
					addFatalMessage("Deleter '" + name + "' for table '" + table.getName() + "' contains a ' ' (whitespace) character.");
				}

				if(!name.startsWith("delete")) {
					addWarnMessage("Deleter '" + name + "' for table '" + table.getName() + "' should really start with 'delete'.");
				}
			}
		}

		return(isValid);
	}
}
