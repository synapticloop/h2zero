package synapticloop.h2zero.validator.finder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseNameValidator;

public class FinderNameValidator extends BaseNameValidator {

	public FinderNameValidator() {
		super("Finder");
		allowablePrefixNames.add("find");
		allowablePrefixList = "find";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, "find");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			List<String> finderNames = new ArrayList<String>();

			for (Finder finder : finders) {
				String name = finder.getName();
				finderNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, finderNames);
		}
	}

}
