package synapticloop.h2zero;

/*
 * Copyright (c) 2013-2019 synapticloop.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.extension.Extension;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.h2zero.validator.BaseValidator;
import synapticloop.h2zero.validator.ForeignKeyTableValidator;
import synapticloop.h2zero.validator.OptionsGeneratorsValidator;
import synapticloop.h2zero.validator.UniqeAndIndexValidator;
import synapticloop.h2zero.validator.UniqueTableViewNameValidator;
import synapticloop.h2zero.validator.bean.Message;
import synapticloop.h2zero.validator.constant.ConstantDeleterValidator;
import synapticloop.h2zero.validator.constant.ConstantInserterValidator;
import synapticloop.h2zero.validator.constant.ConstantTableValidator;
import synapticloop.h2zero.validator.constant.ConstantUpdaterValidator;
import synapticloop.h2zero.validator.counter.CounterJsonUniqueKeyExistsValidator;
import synapticloop.h2zero.validator.counter.CounterKeyValidator;
import synapticloop.h2zero.validator.counter.CounterNameValidator;
import synapticloop.h2zero.validator.counter.CounterQueryParameterNameValidator;
import synapticloop.h2zero.validator.counter.CounterSelectClauseValidator;
import synapticloop.h2zero.validator.counter.CounterSelectFieldsValidator;
import synapticloop.h2zero.validator.database.TableNameDuplicateValidator;
import synapticloop.h2zero.validator.deleter.DeleterNameValidator;
import synapticloop.h2zero.validator.deleter.DeleterWhereClauseValidator;
import synapticloop.h2zero.validator.field.FieldDefaultValueValidator;
import synapticloop.h2zero.validator.field.FieldIgnoredKeysValidator;
import synapticloop.h2zero.validator.field.FieldNameDuplicateValidator;
import synapticloop.h2zero.validator.field.FieldNotNullLengthValidator;
import synapticloop.h2zero.validator.field.SQLite3FieldBlobValidator;
import synapticloop.h2zero.validator.field.SQLite3FieldClobValidator;
import synapticloop.h2zero.validator.field.SQLite3FieldPrimaryKeyValidator;
import synapticloop.h2zero.validator.finder.FinderAutoIndexValidator;
import synapticloop.h2zero.validator.finder.FinderInQueryValidator;
import synapticloop.h2zero.validator.finder.FinderNameValidator;
import synapticloop.h2zero.validator.finder.FinderOrderByClauseValidator;
import synapticloop.h2zero.validator.finder.FinderQueryParameterNameValidator;
import synapticloop.h2zero.validator.finder.FinderQueryParameterNumberValidator;
import synapticloop.h2zero.validator.finder.FinderSelectClauseBeanNameValidator;
import synapticloop.h2zero.validator.finder.FinderSelectClauseValidator;
import synapticloop.h2zero.validator.finder.FinderWhereClauseValidator;
import synapticloop.h2zero.validator.finder.FinderWhereFieldAliasValidator;
import synapticloop.h2zero.validator.inserter.InserterKeyValidator;
import synapticloop.h2zero.validator.inserter.InserterNameValidator;
import synapticloop.h2zero.validator.inserter.InserterQueryParameterNameValidator;
import synapticloop.h2zero.validator.question.QuestionJsonUniqueKeyExistsValidator;
import synapticloop.h2zero.validator.question.QuestionKeyValidator;
import synapticloop.h2zero.validator.question.QuestionNameValidator;
import synapticloop.h2zero.validator.question.QuestionQueryParameterNameValidator;
import synapticloop.h2zero.validator.question.QuestionSelectClauseValidator;
import synapticloop.h2zero.validator.question.QuestionSelectFieldsValidator;
import synapticloop.h2zero.validator.table.TableIgnoredKeysValidator;
import synapticloop.h2zero.validator.table.TablePrimaryKeyExistsValidator;
import synapticloop.h2zero.validator.table.TablePrimaryKeyNameValidator;
import synapticloop.h2zero.validator.table.TablePrimaryKeyTypeValidator;
import synapticloop.h2zero.validator.updater.UpdaterKeyValidator;
import synapticloop.h2zero.validator.updater.UpdaterNameValidator;
import synapticloop.h2zero.validator.updater.UpdaterQueryParameterNameValidator;
import synapticloop.h2zero.validator.updater.UpdaterSetClauseValidator;
import synapticloop.h2zero.validator.updater.UpdaterWhereClauseValidator;

/**
 * This is the parser for the h2zero generator
 * 
 * @author synapticloop
 */

public class H2ZeroParser {
	private Database database = null;
	private Options options = null;

	private int numWarn = 0;
	private int numFatal = 0;

	private static final String H2ZERO_KEY_INCLUDE = "include";

	private static List<BaseValidator> validators = new ArrayList<BaseValidator>();
	static {
		// options
		validators.add(new OptionsGeneratorsValidator());

		// overall validators
		validators.add(new UniqueTableViewNameValidator());
		validators.add(new ForeignKeyTableValidator());
		validators.add(new UniqeAndIndexValidator());

		// table validators
		validators.add(new TableNameDuplicateValidator());
		validators.add(new TablePrimaryKeyExistsValidator());
		validators.add(new TablePrimaryKeyNameValidator());
		validators.add(new TablePrimaryKeyTypeValidator());
		validators.add(new TableIgnoredKeysValidator());


		// field validators
		validators.add(new FieldDefaultValueValidator());
		validators.add(new FieldNameDuplicateValidator());
		validators.add(new FieldIgnoredKeysValidator());
		validators.add(new FieldNotNullLengthValidator());

		validators.add(new SQLite3FieldBlobValidator());
		validators.add(new SQLite3FieldClobValidator());
		validators.add(new SQLite3FieldPrimaryKeyValidator());


		// Finder validators
		validators.add(new FinderInQueryValidator());
		validators.add(new FinderNameValidator());
		validators.add(new FinderWhereClauseValidator());
		validators.add(new FinderOrderByClauseValidator());
		validators.add(new FinderSelectClauseValidator());
		validators.add(new FinderSelectClauseBeanNameValidator());
		validators.add(new FinderAutoIndexValidator());
		validators.add(new FinderWhereFieldAliasValidator());
		validators.add(new FinderQueryParameterNameValidator());
		validators.add(new FinderQueryParameterNumberValidator());


		// inserter validators
		validators.add(new InserterQueryParameterNameValidator());
		validators.add(new InserterNameValidator());
		validators.add(new InserterKeyValidator());


		// deleter validators
		validators.add(new DeleterNameValidator());
		validators.add(new DeleterWhereClauseValidator());


		// updater validators
		validators.add(new UpdaterQueryParameterNameValidator());
		validators.add(new UpdaterNameValidator());
		validators.add(new UpdaterWhereClauseValidator());
		validators.add(new UpdaterSetClauseValidator());
		validators.add(new UpdaterKeyValidator());


		// counter validators
		validators.add(new CounterQueryParameterNameValidator());
		validators.add(new CounterSelectClauseValidator());
		validators.add(new CounterSelectFieldsValidator());
		validators.add(new CounterJsonUniqueKeyExistsValidator());
		validators.add(new CounterKeyValidator());
		validators.add(new CounterNameValidator());


		// question validators
		validators.add(new QuestionQueryParameterNameValidator());
		validators.add(new QuestionSelectClauseValidator());
		validators.add(new QuestionSelectFieldsValidator());
		validators.add(new QuestionJsonUniqueKeyExistsValidator());
		validators.add(new QuestionKeyValidator());
		validators.add(new QuestionNameValidator());


		// constant validators
		validators.add(new ConstantTableValidator());
		validators.add(new ConstantDeleterValidator());
		validators.add(new ConstantInserterValidator());
		validators.add(new ConstantUpdaterValidator());
	}

	private static Map<String, BaseValidator> validator_map = new HashMap<String, BaseValidator>();
	static {
		for (BaseValidator validator : validators) {
			validator_map.put(validator.getClass().getSimpleName(), validator);
		}
	}

	/*
	 * The following is used to determine the max width of the validators, for 
	 * logging out the information through the SimpleLogger
	 */
	private static int maxValidatorClassNameLength = 0;
	static {
		for (BaseValidator validator : validators) {
			int validatorSimpleNameLength = validator.getClass().getSimpleName().length();
			if(validatorSimpleNameLength > maxValidatorClassNameLength) {
				maxValidatorClassNameLength = validatorSimpleNameLength;
			}
		}
	}

	/**
	 * Parse a .h2zero file
	 * 
	 * @param file The file to be parsed
	 * @throws H2ZeroParseException if there was an error parsing the file
	 */
	public H2ZeroParser(File file) throws H2ZeroParseException {
		JSONObject jsonObject = null;
		try {
			jsonObject = getJSONFileContents(file);
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("Error parsing JSON, message was '" + ojjsonex.getMessage() + "'.", ojjsonex);
		}

		// try and get the options
		this.options = new Options(jsonObject);

		// now do the actual parsing
		this.database = new Database(options, jsonObject);

		// now that we have parsed the file - go through and update the validator options
		Map<Extension, JSONObject> extensions = options.getExtensions();
		Iterator<Extension> extensionIterator = extensions.keySet().iterator();
		while (extensionIterator.hasNext()) {
			Extension extension = (Extension) extensionIterator.next();
			List<BaseValidator> extensionValidators = extension.getValidators();
			if(null != extensionValidators) {
				validators.addAll(extensionValidators);
			}
		}

		// now go through and run the validators
		boolean isValid = checkAndLogValidators();

		if(!isValid) {
			throw new H2ZeroParseException("Validators found FATAL warnings, exiting...");
		}
	}

	private boolean checkAndLogValidators() {
		boolean isValid = true;

		for (BaseValidator validator : validators) {
			validator.validate(database, options);

			if(!validator.isValid()) {
				isValid = false;
			}

			numWarn += validator.getNumWarn();
			numFatal += validator.getNumFatal();

			List<Message> messages = validator.getFormattedMessages();
			for (Message message: messages) {
				if(message.getType().equals(SimpleLogger.INFO)) {
					SimpleLogger.logInfo(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getContent()));
				} else if(message.getType().equals(SimpleLogger.WARN)){
					SimpleLogger.logWarn(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getContent()));
				} else if(message.getType().equals(SimpleLogger.FATAL)){
					SimpleLogger.logFatal(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getContent()));
				}
			}
		}
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
		BufferedReader bufferedReader = null;

		String line = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		} catch (IOException ioex) {
			throw new H2ZeroParseException("There was a problem reading the file '" + file.getAbsolutePath() + "'.", ioex);
		} finally {
			if(null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException ignored) {
				}
			}
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
	 * @throws H2ZeroParseException
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

	/**
	 * Retrieve a validator by name from the lookup cache (or null if it can not be found)
	 * 
	 * @param name the name of the validator to retrieve
	 * 
	 * @return The validator
	 */
	public static BaseValidator getValidatorByName(String name) { return(validator_map.get(name)); }
}
