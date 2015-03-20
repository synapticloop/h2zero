package synapticloop.h2zero.validator.finder;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

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
						addWarnMessage("Finder '" + table.getName() + "." + finder.getName() + "' has a " + JSONKeyConstants.SELECT_CLAUSE + " that does not start with 'select', so I am going to add one.");
						finder.setSelectClause(" select " + selectClause);
					}
				}
			}
		}

		return(isValid);
	}

}
