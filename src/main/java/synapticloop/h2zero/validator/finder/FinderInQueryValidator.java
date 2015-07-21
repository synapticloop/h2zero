package synapticloop.h2zero.validator.finder;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderInQueryValidator extends BaseValidator {

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				// in some instances - there is no where clause - especially if it is all in the selectClause
				if(null == finder.getWhereClause()) {
					continue;
				}

				if(finder.getHasInFields() && !finder.getWhereClause().contains("...")) {
					isValid = false;
					addFatalMessage("Finder '" + finder.getName() + "' has in fields, but no '...' in the where clause '" + finder.getWhereClause() + "'.");
				} else if(!finder.getHasInFields() && finder.getWhereClause().contains("...")) {
					isValid = false;
					addFatalMessage("Finder '" + finder.getName() + "' has '...' in the where clause '" + finder.getWhereClause() + "', but no 'in' fields.");
				}
			}
		}
	}

}
