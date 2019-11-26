package synapticloop.h2zero.validator.finder;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderSelectClauseFromValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String selectClause = finder.getSelectClause();
				if(null != selectClause && !selectClause.toLowerCase().contains("from")) {
					addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' has a " + JSONKeyConstants.SELECT_CLAUSE + " that does not contain a 'from' keyword.");
				}
			}
		}
	}

}
