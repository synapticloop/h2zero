package synapticloop.h2zero.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.validator.bean.Message;

public abstract class Validator {
	protected boolean isValid = true;
	private ArrayList<Message> messages = new ArrayList<Message>();
	private int numInfo = 0;
	private int numWarn = 0;
	private int numFatal = 0;

	public abstract boolean isValid(Database database, Options options);
	
	protected void addInfoMessage(String ... infoMessages) {
		numInfo++;
		for (int i = 0; i < infoMessages.length; i++) {
			messages.add(new Message(SimpleLogger.INFO, infoMessages[i]));
		}
	}

	protected void addFatalMessage(String ... fatalMessages) {
		numFatal++;
		for (int i = 0; i < fatalMessages.length; i++) {
			messages.add(new Message(SimpleLogger.FATAL, fatalMessages[i]));
		}
		isValid = false;
	}

	protected void addWarnMessage(String ... warnMessages) {
		numWarn++;
		for (int i = 0; i < warnMessages.length; i++) {
			messages.add(new Message(SimpleLogger.WARN, warnMessages[i]));
		}
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
