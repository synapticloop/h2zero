package synapticloop.h2zero.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;

public class UniqueTableViewNameValidator extends BaseValidator {

	public void validate(Database database, Options options) {
		Set<String> tableViewNames = new HashSet<String>();

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			String name = table.getName();
			if(tableViewNames.contains(name)) {
				addFatalMessage("Table with name '" + name + "' already exists.");
			}
			tableViewNames.add(name);
		}

		List<View> views = database.getViews();
		for (View view : views) {
			String name = view.getName();
			if(tableViewNames.contains(name)) {
				addFatalMessage("View with name '" + name + "' already exists as a table or view.");
			}
			tableViewNames.add(name);
		}

	}

}
