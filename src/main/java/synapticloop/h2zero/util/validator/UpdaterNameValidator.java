package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.Updater;

public class UpdaterNameValidator extends Validator {

	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Updater> updaters = table.getUpdaters();
			for (Updater updater : updaters) {
				String name = updater.getName();
				if(name.contains(" ")) {
					addFatalMessage("Updater '" + name + "' for table '" + table.getName() + "' contains a ' ' (whitespace) character.");
				}

				if(!name.startsWith("update")) {
					addWarnMessage("Updater '" + name + "' for table '" + table.getName() + "' should really start with 'update'.");
				}
			}
		}

		return(isValid);
	}
}
