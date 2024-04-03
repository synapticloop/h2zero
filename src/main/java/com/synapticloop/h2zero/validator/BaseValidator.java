package com.synapticloop.h2zero.validator;

/*
 * Copyright (c) 2013-2024 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import java.util.ArrayList;
import java.util.List;

import com.synapticloop.h2zero.validator.bean.Message;
import org.json.JSONObject;

import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.util.SimpleLogger;

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

	/**
	 * Add an informational message to the messages array
	 * 
	 * @param infoMessages the informational messages to add
	 */
	protected void addInfoMessage(String ... infoMessages) {
		numInfo++;
		for (int i = 0; i < infoMessages.length; i++) {
			messages.add(new Message(SimpleLogger.INFO, infoMessages[i]));
		}
	}

	/**
	 * Add a warning message (or messages) to the messages array
	 * 
	 * @param warnMessages the warning message (or messages) to be added
	 */
	protected void addWarnMessage(String ... warnMessages) {
		numWarn++;
		for (int i = 0; i < warnMessages.length; i++) {
			messages.add(new Message(SimpleLogger.WARN, warnMessages[i]));
		}
	}

	/**
	 * Add a fatal message to the messages array
	 * 
	 * @param fatalMessages the fatal messages to add
	 */
	protected void addFatalMessage(String ... fatalMessages) {
		numFatal++;
		for (int i = 0; i < fatalMessages.length; i++) {
			messages.add(new Message(SimpleLogger.FATAL, fatalMessages[i]));
		}
		isValid = false;
	}

	/**
	 * Add a summary message to the messages array
	 * 
	 * @param message the message to be added
	 */
	private void addSummaryMessage(String message) {
		messages.add(new Message(SimpleLogger.INFO, "+-> " + message));
	}

	/**
	 * Get all of the messages that have been added in a format which indicates whether the message is valid/invalid and
	 * the number of info, warning and fatal messages.
	 * 
	 * @return all of the formatted messages
	 */
	public List<Message> getFormattedMessages() {
		if(isValid) {
			addSummaryMessage(String.format("  VALID:  [ info: %4d, warn: %4d, fatal: %4d ]", numInfo, numWarn, numFatal));
		} else {
			addSummaryMessage(String.format("INVALID:  [ info: %4d, warn: %4d, fatal: %4d ]", numInfo, numWarn, numFatal));
		}
		return(messages);
	}

	public int getNumInfo() { return(numInfo); }
	public int getNumWarn() {return(numWarn); }
	public int getNumFatal() { return(numFatal); }
}
