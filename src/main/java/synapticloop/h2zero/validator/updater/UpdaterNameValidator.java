package synapticloop.h2zero.validator.updater;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.Updater;
import synapticloop.h2zero.validator.BaseNameValidator;

public class UpdaterNameValidator extends BaseNameValidator {

	public UpdaterNameValidator() {
		super("Updater");
		allowablePrefixNames.add("update");
		allowablePrefixList = "update";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, "update");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Updater> updaters = table.getUpdaters();
			List<String> updaterNames = new ArrayList<String>();
			for (Updater updater : updaters) {
				String name = updater.getName();
				updaterNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, updaterNames);
		}
	}
}
