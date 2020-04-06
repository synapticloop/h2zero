package synapticloop.h2zero.validator.view;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.validator.BaseValidator;

public class ViewAsClauseValidator extends BaseValidator{

	@Override
	public void validate(Database database, Options options) {
		List<View> views = database.getViews();
		for (View view : views) {
			if(view.getAsClause().endsWith(";")) {
				addWarnMessage("The 'asClause' for " + view.getName() + " has a trailing ';' character, which will cause a warning when generating the SQL statement.");
			}
		}
	}

}
