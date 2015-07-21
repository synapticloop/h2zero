package synapticloop.h2zero.validator.counter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseNameValidator;

public class CounterNameValidator extends BaseNameValidator {

	public CounterNameValidator() {
		super("Counter");
		allowablePrefixNames.add("count");
		allowablePrefixList = "count";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, "count");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Counter> counters = table.getCounters();
			List<String> counterNames = new ArrayList<String>();
			for (Counter counter : counters) {
				String name = counter.getName();
				counterNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, counterNames);
		}
	}

}
