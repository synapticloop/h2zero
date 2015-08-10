package synapticloop.h2zero.validator.updater;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.Updater;
import synapticloop.h2zero.validator.BaseClauseValidator;

public class UpdaterQueryParameterNameValidator extends BaseClauseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Updater> updaters = table.getUpdaters();
			for (Updater updater : updaters) {
				validateClausesAndFields(updater);
			}
		}
	}

}
