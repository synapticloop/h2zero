package synapticloop.h2zero.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Deleter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;

public class DeleterWhereClauseValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		isValid = true;

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Deleter> deleters = table.getDeleters();
			for (Deleter deleter : deleters) {
				String whereClause = deleter.getWhereClause();
				if(null != whereClause) {
					if(!whereClause.toLowerCase().contains("where")) {
						addWarnMessage("Deleter '" + table.getName() + "." + deleter.getName() + "' has a whereClause that does not start with 'where', so I am going to add one.");
						deleter.setWhereClause(" where " + whereClause);
					}
				}
			}
		}

		return(isValid);

	}

}
