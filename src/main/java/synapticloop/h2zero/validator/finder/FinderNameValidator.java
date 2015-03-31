package synapticloop.h2zero.validator.finder;

import java.util.ArrayList;
import java.util.HashSet;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class FinderNameValidator extends Validator {
	private HashSet<String> finderNames = new HashSet<String>();

	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			finderNames.clear();
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String name = finder.getName();
				if(name.contains(" ")) {
					addFatalMessage("Finder '" + table.getName() + "." + name + "' contains a ' ' (whitespace) character.");
				}

				if(!name.startsWith("find")) {
					addWarnMessage("Finder '" + table.getName() + "." + name + "' should really start with 'find'.");
				}

				if(finderNames.contains(name)) {
					addFatalMessage("Finder '" + table.getName() + "." + name + "' is a duplicate.");
				}

				finderNames.add(name);
			}
		}
	}

}
