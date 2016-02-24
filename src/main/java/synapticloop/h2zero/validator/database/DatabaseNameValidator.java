package synapticloop.h2zero.validator.database;

import java.util.HashSet;
import java.util.Set;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.validator.BaseValidator;

public class DatabaseNameValidator extends BaseValidator {
	private Set<String> names = new HashSet<String>();


	@Override
	public void validate(Database database, Options options) {
		String schema = database.getSchema();
		// TODO - add in correct naming
	}

}
