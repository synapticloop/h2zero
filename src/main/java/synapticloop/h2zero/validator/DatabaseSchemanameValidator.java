package synapticloop.h2zero.validator;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public class DatabaseSchemaNameValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		// return only valid names 
		return(isValid);
	}

}
