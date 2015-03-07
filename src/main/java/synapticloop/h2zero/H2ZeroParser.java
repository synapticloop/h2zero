package synapticloop.h2zero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.h2zero.validator.CounterSelectClauseValidator;
import synapticloop.h2zero.validator.DefaultValueValidator;
import synapticloop.h2zero.validator.DeleterNameValidator;
import synapticloop.h2zero.validator.DeleterWhereClauseValidator;
import synapticloop.h2zero.validator.FieldNameDuplicateValidator;
import synapticloop.h2zero.validator.FinderAutoIndexValidator;
import synapticloop.h2zero.validator.FinderInQueryValidator;
import synapticloop.h2zero.validator.FinderNameValidator;
import synapticloop.h2zero.validator.FinderOrderByClauseValidator;
import synapticloop.h2zero.validator.FinderSelectClauseBeanNameValidator;
import synapticloop.h2zero.validator.FinderSelectClauseValidator;
import synapticloop.h2zero.validator.FinderWhereClauseValidator;
import synapticloop.h2zero.validator.FinderWhereFieldAliasValidator;
import synapticloop.h2zero.validator.ForeignKeyTableValidator;
import synapticloop.h2zero.validator.InserterNameValidator;
import synapticloop.h2zero.validator.OptionsGeneratorsValidator;
import synapticloop.h2zero.validator.PrimaryKeyExistsValidator;
import synapticloop.h2zero.validator.PrimaryKeyNameValidator;
import synapticloop.h2zero.validator.QuestionSelectClauseValidator;
import synapticloop.h2zero.validator.TableFinderKeyValidator;
import synapticloop.h2zero.validator.TableNameDuplicateValidator;
import synapticloop.h2zero.validator.UniqeAndIndexValidator;
import synapticloop.h2zero.validator.UpdaterNameValidator;
import synapticloop.h2zero.validator.UpdaterSetClauseValidator;
import synapticloop.h2zero.validator.UpdaterWhereClauseValidator;
import synapticloop.h2zero.validator.Validator;
import synapticloop.h2zero.validator.bean.Message;


public class H2ZeroParser {
	private Database database = null;
	private Options options = null;

	private int numWarn = 0;
	private int numFatal = 0;

	private static ArrayList<Validator> validators = new ArrayList<Validator>();
	static {
		// options
		validators.add(new OptionsGeneratorsValidator());

		// overall validators
		validators.add(new PrimaryKeyExistsValidator());
		validators.add(new ForeignKeyTableValidator());
		validators.add(new UniqeAndIndexValidator());

		validators.add(new PrimaryKeyNameValidator());

		validators.add(new DefaultValueValidator());

		validators.add(new TableNameDuplicateValidator());
		validators.add(new TableFinderKeyValidator());

		validators.add(new FieldNameDuplicateValidator());

		// Finder validators
		validators.add(new FinderInQueryValidator());
		validators.add(new FinderNameValidator());
		validators.add(new FinderWhereClauseValidator());
		validators.add(new FinderOrderByClauseValidator());
		validators.add(new FinderSelectClauseValidator());
		validators.add(new FinderSelectClauseBeanNameValidator());
		validators.add(new FinderAutoIndexValidator());
		validators.add(new FinderWhereFieldAliasValidator());

		// inserter validators
		validators.add(new InserterNameValidator());

		// deleter validators
		validators.add(new DeleterNameValidator());
		validators.add(new DeleterWhereClauseValidator());

		// updater validators
		validators.add(new UpdaterNameValidator());
		validators.add(new UpdaterWhereClauseValidator());
		validators.add(new UpdaterSetClauseValidator());

		// counter validators
		validators.add(new CounterSelectClauseValidator());

		// question validators
		validators.add(new QuestionSelectClauseValidator());
	}

	private static int maxValidatorClassNameLength = 0;
	static {
		for (Validator validator : validators) {
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
			throw new H2ZeroParseException("Error parsing JSON, message was '" + ojjsonex.getMessage() + "'.");
		}

		// try and get the options
		this.options = new Options(jsonObject);

		// now do the actual parsing
		this.database = new Database(jsonObject);

		// now go through and run the validators
		boolean isValid = true;

		for (Validator validator : validators) {
			if(!validator.isValid(database, options)) {
				isValid = false;
			}

			ArrayList<Message> messages = validator.getMessages();
			for (Message message: messages) {
				if(message.getType().equals(SimpleLogger.INFO)) {
					SimpleLogger.logInfo(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getMessage()));
				} else if(message.getType().equals(SimpleLogger.WARN)){
					numWarn++;
					SimpleLogger.logWarn(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getMessage()));
				} else if(message.getType().equals(SimpleLogger.FATAL)){
					numFatal++;
					SimpleLogger.logFatal(LoggerType.VALIDATOR, String.format("[ %-" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getMessage()));
				}
			}
		}


		if(!isValid) {
			throw new H2ZeroParseException("Validators found FATAL warnings, exiting...");
		}
	}

	private String getFileContents(File file) throws H2ZeroParseException {
		if(null == file) {
			throw new H2ZeroParseException("Cannot parse, file is null.");
		}

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException jifnfex) {
			throw new H2ZeroParseException("The file '" + file.getAbsolutePath() + "' does not exist.");
		}

		String line = null;
		try {
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
	public int getNumWarn() { return(numWarn); };
	public int getNumFatal() { return(numFatal); };
}
