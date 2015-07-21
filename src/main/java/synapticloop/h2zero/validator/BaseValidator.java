package synapticloop.h2zero.validator;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.validator.bean.Message;

public abstract class BaseValidator {
	protected boolean isValid = true;
	private List<Message> messages = new ArrayList<Message>();
	private int numInfo = 0;
	private int numWarn = 0;
	private int numFatal = 0;

	/**
	 * Return whether the validator is in a valid state
	 * 
	 * @return whether the validator is 
	 */
	public boolean isValid() {
		return(isValid);
	}
	/**
	 * 
	 * @param database the database object from which all other objects may be retrieved
	 * @param options the options for the generation
	 */
	public abstract void validate(Database database, Options options);

	/**
	 * Return whether the passed in options are valid for the current validator.  By default this always returns true
	 * unless over-ridden by the child class.
	 * 
	 * @param optionsObject the options for the validator
	 * 
	 * @return whether the options are correct for this validator.
	 */
	public void parseAndValidateOptions(JSONObject optionsObject) {
	}

	/**
	 * Count the number of occurrences of the needle parameter in the haystack parameter
	 * 
	 * @param haystack The string to search in
	 * @param needle The string to search for
	 * 
	 * @return the number found.
	 */
	public int countOccurrences(String haystack, String needle) {
		int count = 0;
		int startPoint = 0;

		int needleLength = needle.length();
		int indexOf = haystack.indexOf(needle, startPoint);
		while(indexOf != -1) {
			count++;
			startPoint = indexOf;
			indexOf = haystack.indexOf(needle, startPoint + needleLength);
		}
		return(count);
	}

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

	public List<Message> getMessages() {
		if(isValid) {
			addSummaryMessage("Valid.    [ info: " + numInfo + ", warn: " + numWarn + ", fatal: " + numFatal + " ]");
		} else {
			addSummaryMessage("INVALID.  [ info: " + numInfo + ", warn: " + numWarn + ", fatal: " + numFatal + " ]");
		}
		return(messages);
	}

	public int getNumInfo() { return(numInfo); }
	public int getNumWarn() {return(numWarn); }
	public int getNumFatal() { return(numFatal); }
}
