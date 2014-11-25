package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public abstract class Validator {
	protected ArrayList<String> messages = new ArrayList<String>();

	public abstract boolean isValid(Database database, Options options);
	
	public ArrayList<String> getMessages() {
		return(messages);
	}
}
