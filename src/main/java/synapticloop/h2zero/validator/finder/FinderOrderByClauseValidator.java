package synapticloop.h2zero.validator.finder;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderOrderByClauseValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String orderBy = finder.getOrderBy();
				if(null != orderBy && orderBy.toLowerCase().contains("order by")) {
					addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' has an orderBy that contains the phrase 'order by'.");
				}
			}
		}
	}

}
