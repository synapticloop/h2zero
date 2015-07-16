package synapticloop.h2zero.validator.counter;

import java.util.List;

import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

public class CounterJsonUniqueKeyExistsValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Counter> counters = table.getCounters();
			for (Counter counter : counters) {
				if(counter.getHasJsonUniqueKey()) {
					addWarnMessage("Counter '" + table.getName() + "." + counter.getName() + "' has a key of '" + JSONKeyConstants.UNIQUE + "' which is ignored, invalid, and therefore un-neccessary.");
				}
			}
		}
	}

}
