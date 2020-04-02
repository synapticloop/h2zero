package synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2020 synapticloop.
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.extension.Extension;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.h2zero.validator.BaseValidator;

public class Options {

	public static final String OPTION_CODE = "code";
	public static final String OPTION_RESOURCES = "resources";
	public static final String OPTION_BUILD = "build";

	public static final String OPTION_JAVA = "java";
	public static final String OPTION_SQL = "sql";
	public static final String OPTION_REPORTS = "reports";

	public static final String OPTION_OUTPUT = "output";

	private static Set<String> ALLOWABLE_GENERATORS = new HashSet<String>();
	static {
		ALLOWABLE_GENERATORS.add(OPTION_SQL);
		ALLOWABLE_GENERATORS.add(OPTION_JAVA);
		ALLOWABLE_GENERATORS.add(OPTION_REPORTS);
	}

	public static final String DATABASE_MYSQL = "mysql";
	public static final String DATABASE_SQLITE3 = "sqlite3";

	private static Set<String> ALLOWABLE_DATABASES = new HashSet<String>();
	static {
		ALLOWABLE_DATABASES.add(DATABASE_MYSQL);
		ALLOWABLE_DATABASES.add(DATABASE_SQLITE3);
	}

	/*
	 * INSTANCE VARIABLES
	 */
	private String database = DATABASE_MYSQL;

	private String outputCode = "/src/main/java/";
	private String outputResource = "/src/main/resources/";
	private String outputBuild = "/build/";

	private Set<String> generators = new HashSet<String>();
	private Map<Extension, JSONObject> extensions = new HashMap<Extension, JSONObject>();

	public Options(JSONObject jsonObject) throws H2ZeroParseException {
		if(null == jsonObject) {
			throw new H2ZeroParseException("Somehow have a null h2zero JSON object");
		}

		JSONObject optionsJson = jsonObject.optJSONObject(JSONKeyConstants.OPTIONS);
		if(null == optionsJson) {
			// options are optional
			return;
		}

		this.database = optionsJson.optString(JSONKeyConstants.DATABASE, DATABASE_MYSQL);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "Generating for database type '" + database + "'.");

		parseGenerators(optionsJson);
		parseExtensions(optionsJson);

		// now we are going to update the output paths
		JSONObject outputJson = optionsJson.optJSONObject(JSONKeyConstants.OUTPUT);
		if(null != outputJson) {
			outputCode = JsonHelper.getStringValue(outputJson, OPTION_CODE, outputCode);
			outputResource = JsonHelper.getStringValue(outputJson, OPTION_RESOURCES, outputResource);
			outputBuild = JsonHelper.getStringValue(outputJson, OPTION_BUILD, outputBuild);

			if(null != JsonHelper.getStringValue(outputJson, "java", null)) {
				SimpleLogger.logFatal(LoggerType.OPTIONS, "The 'output' with key 'java' is no longer in use and has been ignored, use 'code' instead.");
			}

			if(null != JsonHelper.getStringValue(outputJson, "webapp", null)) {
				SimpleLogger.logFatal(LoggerType.OPTIONS, "The 'output' with key 'webapp' is no longer in use and has been ignored, use 'resources' instead.");
			}

			if(null != JsonHelper.getStringValue(outputJson, "sql", null)) {
				SimpleLogger.logFatal(LoggerType.OPTIONS, "The 'output' with key 'sql' is no longer in use and has been ignored, use 'resources' instead.");
			}

		}

		// now ensure that there are slashes on both sides of the output directory
		outputCode = convertToAbsolutePath(outputCode);
		outputResource = convertToAbsolutePath(outputResource);
		outputBuild = convertToAbsolutePath(outputBuild);

		jsonObject.remove(JSONKeyConstants.OPTIONS);
	}

	private void parseExtensions(JSONObject optionsJson) throws H2ZeroParseException {
		JSONArray extensionArray = optionsJson.optJSONArray(JSONKeyConstants.EXTENSIONS);
		if(null == extensionArray) {
			SimpleLogger.logInfo(LoggerType.EXTENSIONS, "No extensions found, ignoring...");
			return;
		}

		// at this point we have the array with all of the extensions
		for (int i = 0; i < extensionArray.length(); i++) {
			String extension = extensionArray.optString(i, null);
			try {
				Extension extensionClass = (Extension)Class.forName(extension).getDeclaredConstructor().newInstance();

				// now look for the extension options
				JSONObject extensionJSONObject = optionsJson.optJSONObject(extension);
				if(null == extensionJSONObject) {
					extensionJSONObject = new JSONObject();
				}

				SimpleLogger.logInfo(LoggerType.EXTENSIONS, "Adding extension '" + extension + "' with options '" + extensionJSONObject.toString() + "'.");
				extensions.put(extensionClass, extensionJSONObject);

			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | 
					IllegalArgumentException | InvocationTargetException | SecurityException ex) {
				ex.printStackTrace();
				String message = "Could not instantiate the extension '" + extension + "', message was: " + ex.getMessage();
				SimpleLogger.logFatal(LoggerType.EXTENSIONS, message);
				throw new H2ZeroParseException(message, ex);
			}

		}
	}

	private void parseGenerators(JSONObject optionsJson) throws H2ZeroParseException {
		JSONArray generatorArray = optionsJson.optJSONArray(JSONKeyConstants.GENERATORS);
		if(null == generatorArray) {
			// add them all
			SimpleLogger.logWarn(LoggerType.GENERATORS, "You have not defined any generators - so we are going to generate all");

			generators.addAll(ALLOWABLE_GENERATORS);
		} else {
			enableGenerators(generatorArray);
		}

		// here we add all generators to the disabled generators, then when we 
		// iterate over the generators and print whether they are enabled, we
		// remove the enabled generator from the disabledGenerator list
		Set<String> disabledGenerators = new HashSet<String>();
		disabledGenerators.addAll(ALLOWABLE_GENERATORS);

		Iterator<String> generatorsIterator = generators.iterator();
		while (generatorsIterator.hasNext()) {
			String next = generatorsIterator.next();
			SimpleLogger.logInfo(LoggerType.GENERATORS, "[ ENABLED  ] Generator '" + next + "'");
			disabledGenerators.remove(next);
		}

		Iterator<String> disabledIterator = disabledGenerators.iterator();
		while (disabledIterator.hasNext()) {
			String next = disabledIterator.next();
			SimpleLogger.logInfo(LoggerType.GENERATORS, "[ DISABLED ] Generator '" + next + "'");
		}

		updateValidators(optionsJson.optJSONObject(JSONKeyConstants.VALIDATORS));
	}

	private String convertToAbsolutePath(String name) {
		StringBuilder stringBuilder = new StringBuilder();

		if(!name.startsWith("/")) {
			stringBuilder.append("/");
		}

		stringBuilder.append(name);

		if(!name.endsWith("/")) {
			stringBuilder.append("/");
		}

		return(stringBuilder.toString());
	}

	private void updateValidators(JSONObject validatorJson) {
		if(null == validatorJson) {
			SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, "No validator options found.");
		} else {
			Iterator<String> keys = validatorJson.keys();
			while (keys.hasNext()) {
				String validatorName = keys.next();
				BaseValidator validator = H2ZeroParser.getValidatorByName(validatorName);
				if(null != validator) {
					SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, "[ " + validatorName + " ] Parsing options...");
					validator.parseAndValidateOptions(validatorJson.getJSONObject(validatorName));
				} else {
					SimpleLogger.logError(LoggerType.OPTIONS_VALIDATOR, "Could not find validator for validator name '" + validatorName + "', ignoring...");
				}
			}
		}

	}

	private void enableGenerators(JSONArray generatorArray) throws H2ZeroParseException {
		for (int i = 0; i < generatorArray.length(); i++) {
			String generator = generatorArray.optString(i, null);
			if(null != generator && ALLOWABLE_GENERATORS.contains(generator.trim().toLowerCase())) {
				generators.add(generator.trim().toLowerCase());
			} else {
				StringBuilder stringBuilder = new StringBuilder();
				int j = 0;
				for (String allowableGenerator : ALLOWABLE_GENERATORS) {
					if(j != 0) {
						stringBuilder.append(", ");
					}
					j = 1;
					stringBuilder.append("'");
					stringBuilder.append(allowableGenerator);
					stringBuilder.append("'");
				}
				SimpleLogger.logFatal(LoggerType.GENERATORS, "Unknown generator type of '" + generator + "'.  Allowable types are " + stringBuilder.toString() + ".");
				throw new H2ZeroParseException("Unknown generator type of '" + generator + "'.");
			}
		}
	}

	public boolean hasExtensions() {return(!extensions.isEmpty()); }
	public Map<Extension, JSONObject> getExtensions() { return(extensions); }
	public boolean hasGenerator(String generator) { return(generators.contains(generator)); }
	public boolean hasGenerators() { return(!generators.isEmpty()); }
	public String getDatabase() { return database; }
	public void setDatabase(String database) { this.database = database; }

	public String getOutputCode() { return(outputCode); }
	public String getOutputResources() { return(outputResource); }
	public String getOutputBuild() { return(outputBuild); }
}
