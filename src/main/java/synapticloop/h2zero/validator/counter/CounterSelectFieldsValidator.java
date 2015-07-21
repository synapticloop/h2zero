package synapticloop.h2zero.validator.counter;

import java.util.List;

import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.BaseValidator;

public class CounterSelectFieldsValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Counter> counters = table.getCounters();
			for (Counter counter : counters) {
				if(!counter.getSelectFields().isEmpty()) {
					addWarnMessage("Counter '" + table.getName() + "." + counter.getName() + "' has '" + JSONKeyConstants.SELECT_FIELDS + "' which are ignored, and therefore un-neccessary.");
				}
			}
		}
	}

}
