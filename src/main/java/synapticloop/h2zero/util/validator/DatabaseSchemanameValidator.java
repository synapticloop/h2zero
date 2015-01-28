package synapticloop.h2zero.util.validator;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public class DatabaseSchemanameValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		// return only valid names 
		return(isValid);
	}

}
