package synapticloop.h2zero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.h2zero.validator.BaseValidator;
import synapticloop.h2zero.validator.DefaultValueValidator;
import synapticloop.h2zero.validator.FieldNameDuplicateValidator;
import synapticloop.h2zero.validator.ForeignKeyTableValidator;
import synapticloop.h2zero.validator.OptionsGeneratorsValidator;
import synapticloop.h2zero.validator.TableNameDuplicateValidator;
import synapticloop.h2zero.validator.UniqeAndIndexValidator;
import synapticloop.h2zero.validator.bean.Message;
import synapticloop.h2zero.validator.constant.ConstantDeleterValidator;
import synapticloop.h2zero.validator.constant.ConstantInserterValidator;
import synapticloop.h2zero.validator.constant.ConstantTableValidator;
import synapticloop.h2zero.validator.constant.ConstantUpdaterValidator;
import synapticloop.h2zero.validator.counter.CounterJsonUniqueKeyExistsValidator;
import synapticloop.h2zero.validator.counter.CounterKeyValidator;
import synapticloop.h2zero.validator.counter.CounterNameValidator;
import synapticloop.h2zero.validator.counter.CounterSelectClauseValidator;
import synapticloop.h2zero.validator.counter.CounterSelectFieldsValidator;
import synapticloop.h2zero.validator.deleter.DeleterNameValidator;
import synapticloop.h2zero.validator.deleter.DeleterWhereClauseValidator;
import synapticloop.h2zero.validator.finder.FinderAutoIndexValidator;
import synapticloop.h2zero.validator.finder.FinderInQueryValidator;
import synapticloop.h2zero.validator.finder.FinderNameValidator;
import synapticloop.h2zero.validator.finder.FinderOrderByClauseValidator;
import synapticloop.h2zero.validator.finder.FinderQueryParameterNumberValidator;
import synapticloop.h2zero.validator.finder.FinderSelectClauseBeanNameValidator;
import synapticloop.h2zero.validator.finder.FinderSelectClauseValidator;
import synapticloop.h2zero.validator.finder.FinderWhereClauseValidator;
import synapticloop.h2zero.validator.finder.FinderWhereFieldAliasValidator;
import synapticloop.h2zero.validator.inserter.InserterKeyValidator;
import synapticloop.h2zero.validator.inserter.InserterNameValidator;
import synapticloop.h2zero.validator.question.QuestionJsonUniqueKeyExistsValidator;
import synapticloop.h2zero.validator.question.QuestionKeyValidator;
import synapticloop.h2zero.validator.question.QuestionNameValidator;
import synapticloop.h2zero.validator.question.QuestionSelectClauseValidator;
import synapticloop.h2zero.validator.question.QuestionSelectFieldsValidator;
import synapticloop.h2zero.validator.table.TableIgnoredKeysValidator;
import synapticloop.h2zero.validator.table.TablePrimaryKeyExistsValidator;
import synapticloop.h2zero.validator.table.TablePrimaryKeyNameValidator;
import synapticloop.h2zero.validator.table.TablePrimaryKeyTypeValidator;
import synapticloop.h2zero.validator.updater.UpdaterKeyValidator;
import synapticloop.h2zero.validator.updater.UpdaterNameValidator;
import synapticloop.h2zero.validator.updater.UpdaterSetClauseValidator;
import synapticloop.h2zero.validator.updater.UpdaterWhereClauseValidator;


public class H2ZeroParser {
	private Database database = null;
	private Options options = null;

	private int numWarn = 0;
	private int numFatal = 0;

	private static List<BaseValidator> validators = new ArrayList<BaseValidator>();
	static {
		// options
		validators.add(new OptionsGeneratorsValidator());

		// overall validators

		validators.add(new ForeignKeyTableValidator());
		validators.add(new UniqeAndIndexValidator());


		validators.add(new DefaultValueValidator());

		validators.add(new TableNameDuplicateValidator());

		validators.add(new FieldNameDuplicateValidator());

		// table validators
		validators.add(new TablePrimaryKeyExistsValidator());
		validators.add(new TablePrimaryKeyNameValidator());
		validators.add(new TablePrimaryKeyTypeValidator());
		validators.add(new TableIgnoredKeysValidator());

		// Finder validators
		validators.add(new FinderInQueryValidator());
		validators.add(new FinderNameValidator());
		validators.add(new FinderWhereClauseValidator());
		validators.add(new FinderOrderByClauseValidator());
		validators.add(new FinderSelectClauseValidator());
		validators.add(new FinderSelectClauseBeanNameValidator());
		validators.add(new FinderAutoIndexValidator());
		validators.add(new FinderWhereFieldAliasValidator());
		validators.add(new FinderQueryParameterNumberValidator());

		// inserter validators
		validators.add(new InserterNameValidator());
		validators.add(new InserterKeyValidator());

		// deleter validators
		validators.add(new DeleterNameValidator());
		validators.add(new DeleterWhereClauseValidator());

		// updater validators
		validators.add(new UpdaterNameValidator());
		validators.add(new UpdaterWhereClauseValidator());
		validators.add(new UpdaterSetClauseValidator());
		validators.add(new UpdaterKeyValidator());

		// counter validators
		validators.add(new CounterSelectClauseValidator());
		validators.add(new CounterSelectFieldsValidator());
		validators.add(new CounterJsonUniqueKeyExistsValidator());
		validators.add(new CounterKeyValidator());
		validators.add(new CounterNameValidator());

		// question validators
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

	private static int maxValidatorClassNameLength = 0;
	static {
		for (BaseValidator validator : validators) {
			int validatorSimpleNameLength = validator.getClass().getSimpleName().length();
			if(validatorSimpleNameLength > maxValidatorClassNameLength) {
				maxValidatorClassNameLength = validatorSimpleNameLength;
			}
		}
	}

	public H2ZeroParser(File file) throws H2ZeroParseException {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(getFileContents(file));
		} catch (JSONException ojjsonex) {
			throw new H2ZeroParseException("Error parsing JSON, message was '" + ojjsonex.getMessage() + "'.", ojjsonex);
		}

		// try and get the options
		this.options = new Options(jsonObject);

		// now do the actual parsing
		this.database = new Database(jsonObject);

		// now that we have parsed the file - go through and update the validator options
		
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

			List<Message> messages = validator.getMessages();
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
		} catch (IOException jiioex) {
			throw new H2ZeroParseException("There was a problem reading the file '" + file.getAbsolutePath() + "'.");
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

	public Database getDatabase() { return(this.database); }
	public Options getOptions() { return(this.options); }
	public int getNumWarn() { return(numWarn); }
	public int getNumFatal() { return(numFatal); }
	public static BaseValidator getValidatorByName(String name) { return(validator_map.get(name)); }
}
