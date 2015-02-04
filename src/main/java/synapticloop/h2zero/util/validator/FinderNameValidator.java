package synapticloop.h2zero.util.validator;

import java.util.ArrayList;
import java.util.HashSet;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class FinderNameValidator extends Validator {
	private HashSet<String> finderNames = new HashSet<String>();

	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			finderNames.clear();
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String name = finder.getName();
				if(name.contains(" ")) {
					addFatalMessage("Finder '" + name + "' for table '" + table.getName() + "' contains a ' ' (whitespace) character.");
				}

				if(!name.startsWith("find")) {
					addWarnMessage("Finder '" + name + "' for table '" + table.getName() + "' should really start with 'find'.");
				}

				if(finderNames.contains(name)) {
					addFatalMessage("Finder '" + name + "' for table '" + table.getName() + "' is a duplicate.");
				}

				finderNames.add(name);
			}
		}
		return(isValid);
	}

}
