package synapticloop.h2zero.validator.deleter;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Deleter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.Validator;

public class DeleterWhereClauseValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Deleter> deleters = table.getDeleters();
			for (Deleter deleter : deleters) {
				String whereClause = deleter.getWhereClause();
				if(null != whereClause && !whereClause.toLowerCase().contains("where")) {
					addWarnMessage("Deleter '" + table.getName() + "." + deleter.getName() + "' has a whereClause that does not start with 'where', so I am going to add one.");
					deleter.setWhereClause(" where " + whereClause);
				}
			}
		}
	}
}
