package synapticloop.h2zero.validator.inserter;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Inserter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseKeyValidator;

public class InserterKeyValidator extends BaseKeyValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Inserter> inserters = table.getInserters();
			for (Inserter inserter : inserters) {
				validate(inserter);
			}
		}
	}
}
