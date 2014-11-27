package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public abstract class Validator {
	protected boolean isValid = true;
	private ArrayList<String> messages = new ArrayList<String>();

	public abstract boolean isValid(Database database, Options options);
	
	protected void addInfoMessage(String message) {
		messages.add(" [INFO]: " + message);
	}

	protected void addFatalMessage(String message) {
		messages.add("[FATAL]: " + message);
	}

	protected void addWarnMessage(String message) {
		messages.add(" [WARN]: " + message);
	}

	protected void addValidityMessage() {
		if(isValid) {
			addInfoMessage("Valid.");
		} else {
			addFatalMessage("Invalid.");
		}
	}

	public ArrayList<String> getMessages() {
		return(messages);
	}
}
