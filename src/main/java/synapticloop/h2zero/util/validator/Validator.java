package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.validator.bean.Message;

public abstract class Validator {
	protected boolean isValid = true;
	private ArrayList<Message> messages = new ArrayList<Message>();
	private int numInfo = 0;
	private int numWarn = 0;
	private int numFatal = 0;

	public abstract boolean isValid(Database database, Options options);
	
	protected void addInfoMessage(String message) {
		numInfo++;
		messages.add(new Message(SimpleLogger.INFO, message));
	}

	protected void addFatalMessage(String message) {
		numFatal++;
		messages.add(new Message(SimpleLogger.FATAL, message));
	}

	protected void addWarnMessage(String message) {
		numWarn++;
		messages.add(new Message(SimpleLogger.WARN, message));
	}

	private void addSummaryMessage(String message) {
		messages.add(new Message(SimpleLogger.INFO, "+-> " + message));
	}

	public ArrayList<Message> getMessages() {
		if(isValid) {
			addSummaryMessage("Valid.    [ info: " + numInfo + ", warn: " + numWarn + ", fatal: " + numFatal + " ]");
		} else {
			addSummaryMessage("INVALID.  [ info: " + numInfo + ", warn: " + numWarn + ", fatal: " + numFatal + " ]");
		}
		return(messages);
	}
}
