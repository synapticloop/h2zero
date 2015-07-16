package synapticloop.h2zero.validator.inserter;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Inserter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class InserterNameValidator extends Validator {

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Inserter> inserters = table.getInserters();
			for (Inserter inserter : inserters) {
				String name = inserter.getName();
				if(name.contains(" ")) {
					addFatalMessage("Inserter '" + name + "' for table '" + table.getName() + "' contains a ' ' (whitespace) character.");
				}

				if(!name.startsWith("insert")) {
					addWarnMessage("Inserter '" + name + "' for table '" + table.getName() + "' should really start with 'insert'.");
				}
			}
		}
	}
}
