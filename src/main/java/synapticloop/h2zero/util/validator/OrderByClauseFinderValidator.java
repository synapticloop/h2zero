package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class OrderByClauseFinderValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String orderBy = finder.getOrderBy();
				if(null != orderBy) {
					if(orderBy.toLowerCase().contains("order by")) {
						addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' has an orderBy that contains the phrase 'order by'.");
					}
				}
			}
		}

		return(isValid);
	}

}
