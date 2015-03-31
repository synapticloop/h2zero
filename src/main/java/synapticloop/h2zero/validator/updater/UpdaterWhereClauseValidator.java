package synapticloop.h2zero.validator.updater;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.Updater;
import synapticloop.h2zero.validator.Validator;

public class UpdaterWhereClauseValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Updater> updaters = table.getUpdaters();
			for (Updater updater : updaters) {
				String whereClause = updater.getWhereClause();
				if(null != whereClause) {
					if(!whereClause.toLowerCase().contains("where")) {
						addWarnMessage("Updater '" + table.getName() + "." + updater.getName() + "' has a whereClause that does not start with 'where', so I am going to add one.");
						updater.setWhereClause(" where " + whereClause);
					}
				}
			}
		}
	}

}
