package synapticloop.h2zero.validator;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;

public class BasicValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		// messages
		addInfoMessage("This is an informational message.");
		addWarnMessage("This is a warning message.");
		addFatalMessage("This is a fatal message, which will end the h2zero generation.");

		// get and iterate over the tables
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			// do some validation
			table.getName();
		}

		// get and iterate over the views
		List<View> views = database.getViews();
		for (View view : views) {
			// do some validation
			view.getName();
		}
	}
}
