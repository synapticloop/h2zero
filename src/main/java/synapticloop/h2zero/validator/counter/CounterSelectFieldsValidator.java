package synapticloop.h2zero.validator.counter;

import java.util.ArrayList;

import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

public class CounterSelectFieldsValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Counter> counters = table.getCounters();
			for (Counter counter : counters) {
				if(counter.getSelectFields().size() > 0) {
					addWarnMessage("Counter '" + table.getName() + "." + counter.getName() + "' has '" + JSONKeyConstants.SELECT_FIELDS + "' which are ignored, and therefore un-neccessary.");
				}
			}
		}

		return(isValid);
	}

}
