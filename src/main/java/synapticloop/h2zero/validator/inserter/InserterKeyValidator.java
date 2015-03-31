package synapticloop.h2zero.validator.inserter;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Inserter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.KeyValidator;

public class InserterKeyValidator extends KeyValidator {

	@Override
	public void validate(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Inserter> inserters = table.getInserters();
			for (Inserter inserter : inserters) {
				validate(inserter);
			}
		}
	}
}
