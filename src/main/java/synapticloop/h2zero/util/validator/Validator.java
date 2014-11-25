package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public abstract class Validator {
	private ArrayList<String> messages = new ArrayList<String>();

	public abstract boolean isValid(Database database, Options options);
	
	protected void addInfoMessage(String message) {
		messages.add("[INFO]: " + message);
	}

	protected void addFatalMessage(String message) {
		messages.add("[FATAL]: " + message);
	}

	public ArrayList<String> getMessages() {
		return(messages);
	}
}
