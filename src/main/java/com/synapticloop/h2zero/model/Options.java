package com.synapticloop.h2zero.model;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.H2ZeroParser;
import com.synapticloop.h2zero.exception.H2ZeroParseException;
import com.synapticloop.h2zero.extension.Extension;
import com.synapticloop.h2zero.model.record.DatabaseTypeProperty;
import com.synapticloop.h2zero.model.util.JSONKeyConstants;
import com.synapticloop.h2zero.util.JsonHelper;
import com.synapticloop.h2zero.util.SimpleLogger;
import com.synapticloop.h2zero.util.SimpleLogger.LoggerType;
import com.synapticloop.h2zero.validator.BaseValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Options {

	public static final String OPTION_CODE = "code";
	public static final String OPTION_RESOURCES = "resources";
	public static final String OPTION_BUILD = "build";

	public static final String OPTION_JAVA = "java";
	public static final String OPTION_TESTS = "tests";
	public static final String OPTION_SQL = "sql";
	public static final String OPTION_REPORTS = "reports";
	public static final String OPTION_IMPEX = "impex";

	private final static Set<String> ALLOWABLE_GENERATORS = new HashSet<>();
	static {
		ALLOWABLE_GENERATORS.add(OPTION_SQL);
		ALLOWABLE_GENERATORS.add(OPTION_JAVA);
		ALLOWABLE_GENERATORS.add(OPTION_TESTS);
		ALLOWABLE_GENERATORS.add(OPTION_REPORTS);
		ALLOWABLE_GENERATORS.add(OPTION_IMPEX);
	}

	public static final String DATABASE_COCKROACH = "cockroach";
	public static final String DATABASE_MARIADB = "mariadb";
	public static final String DATABASE_MYSQL = "mysql";
	public static final String DATABASE_POSTGRESQL = "postgresql";
	public static final String DATABASE_SQLITE3 = "sqlite3";
	public static final String DATABASE_SQLSERVER = "sqlserver";


	private static final Map<String, DatabaseTypeProperty> DATABASE_TYPE_PROPERTIES = new HashMap<>();
	static {
		DATABASE_TYPE_PROPERTIES.put(DATABASE_COCKROACH,
				new DatabaseTypeProperty(
						DATABASE_COCKROACH,
						"jdbc:postgresql://localhost:5432/h2zero-test",
						"jdbc:postgresql://localhost:5432/",
						"org.postgresql.Driver",
						true));

		DATABASE_TYPE_PROPERTIES.put(DATABASE_MARIADB,
				new DatabaseTypeProperty(
						DATABASE_MARIADB,
						"jdbc:mariadb://localhost:3306/h2zero-test",
						"jdbc:mariadb://localhost:3306/",
						"org.mariadb.jdbc.Driver",
						true));

		DATABASE_TYPE_PROPERTIES.put(DATABASE_MYSQL,
				new DatabaseTypeProperty(
						DATABASE_MYSQL,
						"jdbc:mysql://127.0.0.1:3306/h2zero-test",
						"jdbc:mysql://127.0.0.1:3306/",
						"com.mysql.cj.jdbc.Driver",
						true));

		DATABASE_TYPE_PROPERTIES.put(DATABASE_POSTGRESQL,
				new DatabaseTypeProperty(
						DATABASE_POSTGRESQL,
						"jdbc:postgresql://localhost:5432/h2zero-test",
						"jdbc:postgresql://localhost:5432/",
						"org.postgresql.Driver",
						true));

		DATABASE_TYPE_PROPERTIES.put(DATABASE_SQLITE3,
				new DatabaseTypeProperty(
						DATABASE_SQLITE3,
						"jdbc:sqlite:./h2zero-test.db",
						"jdbc:sqlite:./h2zero-test.db",
						"org.sqlite.JDBC",
						true));

		DATABASE_TYPE_PROPERTIES.put(DATABASE_SQLSERVER,
				new DatabaseTypeProperty(
						DATABASE_SQLSERVER,
						"jdbc:sqlserver://localhost:1433\\H2ZERO;encrypt=false;databaseName=h2zero-test;integratedSecurity=false;",
						"jdbc:sqlserver://localhost:1433\\H2ZERO;encrypt=false;integratedSecurity=false;",
						"com.microsoft.sqlserver.jdbc.SQLServerDriver",
						false));
	}

	/*
	 * INSTANCE VARIABLES
	 */
	private String database = DATABASE_MYSQL;
	private boolean isDefault = true;

	private String outputCode = "/src/main/java/";
	private String outputTestCode = "/src/test/java/";
	private String outputResource = "/src/main/resources/";
	private String outputTestResource = "/src/test/resources/";
	private String outputBuild = "/build/";

	private final Set<String> generators = new HashSet<>();
	private final Map<Extension, JSONObject> extensions = new HashMap<>();
	private final Map<String, String> extensionOptions = new HashMap<>();

	public Options(JSONObject jsonObject) throws H2ZeroParseException {
		if(null == jsonObject) {
			throw new H2ZeroParseException("Somehow we have a null h2zero JSON object");
		}

		JSONObject optionsJson = jsonObject.optJSONObject(JSONKeyConstants.OPTIONS);
		if(null == optionsJson) {
			// options are optional
			return;
		}

		if(null == optionsJson.optString(JSONKeyConstants.DATABASE, null)) {
			isDefault = true;
		}

		this.database = optionsJson.optString(JSONKeyConstants.DATABASE, DATABASE_MYSQL);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "Generating for database type '" + database + "'.");

		parseExtensions(optionsJson);

		parseGenerators(optionsJson);

		// now we are going to update the output paths
		JSONObject outputJson = optionsJson.optJSONObject(JSONKeyConstants.OUTPUT);
		if(null != outputJson) {
			outputCode = JsonHelper.getStringValue(outputJson, OPTION_CODE, outputCode);
			outputResource = JsonHelper.getStringValue(outputJson, OPTION_RESOURCES, outputResource);

			outputTestCode = JsonHelper.getStringValue(outputJson, OPTION_CODE, outputTestCode);
			outputTestResource = JsonHelper.getStringValue(outputJson, OPTION_RESOURCES, outputTestResource);

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

		// now we need to go through the extension options
		// TODO

		// now ensure that there are slashes on both sides of the output directory
		outputCode = convertToAbsolutePath(outputCode);
		outputResource = convertToAbsolutePath(outputResource);

		outputTestCode = convertToAbsolutePath(outputTestCode);
		outputTestResource = convertToAbsolutePath(outputTestResource);

		outputBuild = convertToAbsolutePath(outputBuild);

		SimpleLogger.logInfo(LoggerType.OPTIONS, "Parsed options are as follows:");
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\t          Output code to: " + outputCode);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\t     Output test code to: " + outputTestCode);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\t     Output resources to: " + outputResource);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tOutput test resources to: " + outputTestResource);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\t         Output build to: " + outputBuild);
		jsonObject.remove(JSONKeyConstants.OPTIONS);
	}

	private void parseExtensions(JSONObject optionsJson) throws H2ZeroParseException {
		JSONArray extensionArray = optionsJson.optJSONArray(JSONKeyConstants.EXTENSIONS);
		if(null == extensionArray) {
			SimpleLogger.logInfo(LoggerType.EXTENSIONS, "No extensions found, ignoring...");
			return;
		}

		boolean hasExtensionError = false;
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
				// now we need to make sure that within the extensionJSONObject we have the required options
				for (String requiredOutputOption : extensionClass.getRequiredOutputOptions()) {
					if(extensionJSONObject.opt(requiredOutputOption) == null) {
						SimpleLogger.logFatal(LoggerType.EXTENSIONS, "Extension '" + extension + "'requires option of '" + requiredOutputOption + "'.  It describes this option as '" + extensionClass.getRequiredOutputOptionDescription(requiredOutputOption) + "'");
						hasExtensionError = true;
					}
				}

				extensions.put(extensionClass, extensionJSONObject);

			} catch (Exception ex) {
				String message = "Could not instantiate the extension '" + extension + "', message was: " + ex.getMessage();
				SimpleLogger.logFatal(LoggerType.EXTENSIONS, message);
				throw new H2ZeroParseException(message, ex);
			}

			if(hasExtensionError) {
				String message = "Could not instantiate the extension '" + extension + "'.";
				SimpleLogger.logFatal(LoggerType.EXTENSIONS, message);
				throw new H2ZeroParseException(message);
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
		Set<String> disabledGenerators = new HashSet<>(ALLOWABLE_GENERATORS);

    for (String next : generators) {
      SimpleLogger.logInfo(LoggerType.GENERATORS, "[ ENABLED  ] Generator '" + next + "'");
      disabledGenerators.remove(next);
    }

    for (String next : disabledGenerators) {
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
	public String getDatabase() {
		return(DATABASE_TYPE_PROPERTIES.get(database).databaseType());
	}
	public void setDatabase(String database) { this.database = database; }

	public String getOutputCode() { return(outputCode); }
	public String getOutputResources() { return(outputResource); }
	public String getOutputTestCode() { return(outputTestCode); }
	public String getOutputTestResources() { return(outputTestResource); }
	public String getOutputBuild() { return(outputBuild); }
	public boolean getIsAllowableDatabase() {
		return(DATABASE_TYPE_PROPERTIES.containsKey(database));
	}
	public boolean getIsDefault() { return(this.isDefault); }

	public String getLimitOffsetType() {
		if(DATABASE_TYPE_PROPERTIES.get(database).isLimitOffset()) {
			return("limitoffset");
		} else {
			return("offsetfetch");
		}
	}

	public String getJdbcUrl() {
		return(DATABASE_TYPE_PROPERTIES.get(database).jdbcUrl());
	}

	public String getInitialJdbcUrl() {
		return(DATABASE_TYPE_PROPERTIES.get(database).initialJdbcUrl());
	}

	public String getDriverClassName() {
		return(DATABASE_TYPE_PROPERTIES.get(database).driverClassName());
	}
}
