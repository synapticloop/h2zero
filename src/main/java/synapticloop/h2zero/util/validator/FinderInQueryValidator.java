package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class FinderInQueryValidator extends Validator {

	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				if(finder.getHasInFields() && !finder.getWhereClause().contains("...")) {
					isValid = false;
					// TODO - refactor this
					addFatalMessage("Finder '" + finder.getName() + "' has in fields, but no '...' in the where clause '" + finder.getWhereClause() + "'.");
				} else if(!finder.getHasInFields() && finder.getWhereClause().contains("...")) {
					isValid = false;
					addFatalMessage("Finder '" + finder.getName() + "' has '...' in the where clause '" + finder.getWhereClause() + "', but no 'in' fields.");
				}
			}
		}

		addValidityMessage();

		return(isValid);
	}

}
