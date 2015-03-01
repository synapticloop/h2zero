package synapticloop.h2zero.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class FinderSelectClauseValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String selectClause = finder.getSelectClause();
				if(null != selectClause) {
					if(!selectClause.toLowerCase().contains("select")) {
						addWarnMessage("Finder '" + table.getName() + "." + finder.getName() + "' has a selectClause that does not start with 'select', so I am going to add one.");
						finder.setSelectClause(" select " + selectClause);
					}
				}
			}
		}

		return(isValid);
	}

}
