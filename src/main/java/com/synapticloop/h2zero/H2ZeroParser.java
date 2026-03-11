package com.synapticloop.h2zero;

/*
 * Copyright (c) 2013-2025 synapticloop.
 * 
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

import com.synapticloop.h2zero.generator.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.generator.exception.H2ZeroParseException;
import com.synapticloop.h2zero.generator.extension.Extension;
import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.model.util.JSONKeyConstants;
import com.synapticloop.h2zero.generator.util.SimpleLogger;
import com.synapticloop.h2zero.generator.util.SimpleLogger.LoggerType;
import com.synapticloop.h2zero.generator.validator.BaseValidator;
import com.synapticloop.h2zero.generator.validator.bean.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This is the parser for the h2zero generator
 * 
 * @author synapticloop
 */

public class H2ZeroParser {
	private Database database = null;
	private Options options = null;

	private int numInfo = 0;
	private int numWarn = 0;
	private int numFatal = 0;

	private static final String H2ZERO_KEY_INCLUDE = "include";
	private static final List<BaseValidator> VALIDATORS = new ArrayList<>();
	private static final Map<String, BaseValidator> VALIDATOR_MAP = new HashMap<>();

	/*
	 * The following is used to determine the max width of the validators, for 
	 * logging out the information through the SimpleLogger
	 */
	private static int maxValidatorClassNameLength = 0;

	private static final List<String> FATAL_MESSAGES = new ArrayList<>();

	public H2ZeroParser() {
		Reflections reflections = new Reflections("com.synapticloop.h2zero");

		for (Class<?> aClass : reflections.getTypesAnnotatedWith(H2ZeroValidator.class)) {
			try {
				BaseValidator baseValidator = (BaseValidator) (aClass.getDeclaredConstructor().newInstance());
				VALIDATORS.add(baseValidator);
				String simpleName = baseValidator.getClass().getSimpleName();
				VALIDATOR_MAP.put(simpleName, baseValidator);
				if(simpleName.length() > maxValidatorClassNameLength) {
					maxValidatorClassNameLength = simpleName.length();
				}
			} catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
		// now we are going to sort the validators
		VALIDATORS.sort((o1, o2) -> o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName()));

	}

	/**
	 * Parse a .h2zero file
	 * 
	 * @param file The file to be parsed
	 * @throws H2ZeroParseException if there was an error parsing the file
	 */
	public void parse(File file) throws H2ZeroParseException {
		JSONObject jsonObject = null;
		try {
			jsonObject = getJSONFileContents(file);
		} catch (JSONException ex) {
			throw new H2ZeroParseException("Error parsing JSON, message was '" + ex.getMessage() + "'.", ex);
		}

		// try and get the options
		this.options = new Options(jsonObject);

		// now do the actual parsing
		this.database = new Database(options, jsonObject);

		// now that we have parsed the file - go through and update the validator options
		Map<Extension, JSONObject> extensions = options.getExtensions();

		for (Extension extension : extensions.keySet()) {
			List<BaseValidator> extensionValidators = extension.getValidators();
			if (null != extensionValidators) {
				VALIDATORS.addAll(extensionValidators);
			}
		}

		// now go through and run the validators
		boolean isValid = checkAndLogValidators();

		if(!isValid) {
			throw new H2ZeroParseException("Validators found the following FATAL warnings: (Exiting...)");
		}
	}

	private boolean checkAndLogValidators() {
		boolean isValid = true;

		for (BaseValidator validator : VALIDATORS) {
			validator.reset();
			validator.validate(database, options);

			if(!validator.isValid()) {
				isValid = false;
			}

			numInfo += validator.getNumInfo();
			numWarn += validator.getNumWarn();
			numFatal += validator.getNumFatal();

			List<Message> messages = validator.getFormattedMessages();
			for (Message message: messages) {
				switch (message.getType()) {
					case SimpleLogger.DEBUG ->
							SimpleLogger.logDebug(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getContent()));
					case SimpleLogger.WARN ->
							SimpleLogger.logWarn(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getContent()));
					case SimpleLogger.FATAL -> {
						String fatalMessage = String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getContent());
						FATAL_MESSAGES.add(fatalMessage);
						SimpleLogger.logFatal(LoggerType.VALIDATOR, fatalMessage);
					}
				}
			}
		}

		SimpleLogger.logInfo(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] +-----------> [ info: %4d, warn: %4d, fatal: %4d ]", "Validation statistics: ", numInfo, numWarn, numFatal));
		return isValid;
	}

	/**
	 * Get the contents of the file as a string
	 * 
	 * @param file the file to get the contents from
	 * 
	 * @return The contents of the file as a string
	 * 
	 * @throws H2ZeroParseException If there was an error getting the content of the file
	 */
	private String getFileContents(File file) throws H2ZeroParseException {
		if(null == file) {
			throw new H2ZeroParseException("Cannot parse, file is null.");
		}

		StringBuilder stringBuilder = new StringBuilder();

		String line = null;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		} catch (IOException ex) {
			throw new H2ZeroParseException("There was a problem reading the file '" + file.getAbsolutePath() + "'.", ex);
		}
		return (stringBuilder.toString());
	}

	/**
	 * Retrieve the contents of a file as a JSON object - with the file possibly 
	 * having include statements.
	 * 
	 * @param file the file that needs to be parsed.
	 * 
	 * @return The parsed JSON Object
	 * 
	 * @throws H2ZeroParseException if the h2zero file could not be parsed
	 */
	public JSONObject getJSONFileContents(File file) throws H2ZeroParseException {
		JSONArray newTablesArray = new JSONArray();
		JSONObject jsonObject = new JSONObject(getFileContents(file));
		// now go through and get all of the imports
		String absolutePath = file.getParentFile().getAbsolutePath();
		JSONObject databaseObject = jsonObject.getJSONObject(JSONKeyConstants.DATABASE);
		JSONArray tablesArray = databaseObject.getJSONArray(JSONKeyConstants.TABLES);

		int i = 0;
		for (Object object : tablesArray) {
			JSONObject tableObject = (JSONObject) object;
			if(tableObject.has(H2ZERO_KEY_INCLUDE)) {
				newTablesArray.put(i, new JSONObject(getFileContents(new File(absolutePath + "/" + tableObject.getString(H2ZERO_KEY_INCLUDE)))));
			} else {
				newTablesArray.put(tableObject);
			}
			i++;
		}

		databaseObject.put(JSONKeyConstants.TABLES, newTablesArray);

		JSONArray newViewsArray = new JSONArray();
		JSONArray viewsArray = databaseObject.optJSONArray(JSONKeyConstants.VIEWS);
		if(null != viewsArray) {
			int j = 0;
			for (Object object : viewsArray) {
				JSONObject viewObject = (JSONObject) object;
				if(viewObject.has(H2ZERO_KEY_INCLUDE)) {
					newViewsArray.put(j, new JSONObject(getFileContents(new File(absolutePath + "/" + viewObject.getString(H2ZERO_KEY_INCLUDE)))));
				} else {
					newViewsArray.put(viewObject);
				}
				j++;
			}

			databaseObject.put(JSONKeyConstants.VIEWS, newViewsArray);
		}

		return jsonObject;
	}

	/**
	 * Get the database object
	 * 
	 * @return the database object
	 */
	public Database getDatabase() { return(this.database); }

	/**
	 * Get the h2zero options
	 * 
	 * @return the h2zero options
	 */
	public Options getOptions() { return(this.options); }

	/**
	 * Return the number of warning messages that have occurred in the generator
	 * 
	 * @return the number of warning messages in generation
	 */
	public int getNumWarn() { return(numWarn); }

	/**
	 * Return the number of fatal messages that have occurred in the generator
	 * 
	 * @return the number of fatal messages in generation
	 */
	public int getNumFatal() { return(numFatal); }
	
	public List<String> getFatalMessages() { return(FATAL_MESSAGES); }

	/**
	 * Retrieve a validator by name from the lookup cache (or null if it can not be found)
	 * 
	 * @param name the name of the validator to retrieve
	 * 
	 * @return The validator
	 */
	public static BaseValidator getValidatorByName(String name) { return(VALIDATOR_MAP.get(name)); }
}
