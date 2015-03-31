package synapticloop.h2zero.validator.finder;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class FinderOrderByClauseValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		
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
	}

}
