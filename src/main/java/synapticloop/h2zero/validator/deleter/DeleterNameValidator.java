package synapticloop.h2zero.validator.deleter;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Deleter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class DeleterNameValidator extends Validator {

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Deleter> deleters = table.getDeleters();
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
	}
}
