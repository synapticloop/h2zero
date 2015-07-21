package synapticloop.h2zero.validator.finder;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderWhereClauseValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String whereClause = finder.getWhereClause();
				if(null != whereClause && !whereClause.toLowerCase().contains("where")) {
					addWarnMessage("Finder '" + table.getName() + "." + finder.getName() + "' has a whereClause that does not start with 'where', so I am going to add one.");
					finder.setWhereClause(" where " + whereClause);
				}
			}
		}
	}

}
