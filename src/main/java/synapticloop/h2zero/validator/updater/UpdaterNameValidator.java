package synapticloop.h2zero.validator.updater;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.Updater;
import synapticloop.h2zero.validator.Validator;

public class UpdaterNameValidator extends Validator {

	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Updater> updaters = table.getUpdaters();
			for (Updater updater : updaters) {
				String name = updater.getName();
				if(name.contains(" ")) {
					addFatalMessage("Updater '" + table.getName() + "." + name + "' contains a ' ' (whitespace) character.");
				}

				if(!name.startsWith("update")) {
					addWarnMessage("Updater '" + table.getName() + "." + name + "' should really start with 'update'.");
				}
			}
		}
	}
}
