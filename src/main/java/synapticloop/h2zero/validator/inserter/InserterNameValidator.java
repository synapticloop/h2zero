package synapticloop.h2zero.validator.inserter;

import java.util.ArrayList;
import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Inserter;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseNameValidator;

public class InserterNameValidator extends BaseNameValidator {
	public InserterNameValidator() {
		super("Inserter");
		allowablePrefixNames.add("insert");
		allowablePrefixList = "insert";
	}

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Inserter> inserters = table.getInserters();
			List<String> inserterNames = new ArrayList<String>();
			for (Inserter inserter : inserters) {
				String name = inserter.getName();
				inserterNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, inserterNames);
		}
	}
}
