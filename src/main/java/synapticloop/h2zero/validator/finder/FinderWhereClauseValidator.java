package synapticloop.h2zero.validator.finder;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class FinderWhereClauseValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		isValid = true;

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String whereClause = finder.getWhereClause();
				if(null != whereClause) {
					if(!whereClause.toLowerCase().contains("where")) {
						addWarnMessage("Finder '" + table.getName() + "." + finder.getName() + "' has a whereClause that does not start with 'where', so I am going to add one.");
						finder.setWhereClause(" where " + whereClause);
					}
				}
			}
		}

		return(isValid);

	}

}
