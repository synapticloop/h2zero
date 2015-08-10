package synapticloop.h2zero.validator.deleter;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Deleter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseClauseValidator;

public class DeleterQueryParameterNameValidator extends BaseClauseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Deleter> deleters = table.getDeleters();
			for (Deleter deleter : deleters) {
				validateClausesAndFields(deleter);
			}
		}
	}
}
