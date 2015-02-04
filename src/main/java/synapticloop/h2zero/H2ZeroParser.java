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
import synapticloop.h2zero.util.validator.DefaultValueValidator;
import synapticloop.h2zero.util.validator.DeleterNameValidator;
import synapticloop.h2zero.util.validator.DuplicateFieldNameValidator;
import synapticloop.h2zero.util.validator.DuplicateTableNameValidator;
import synapticloop.h2zero.util.validator.FinderInQueryValidator;
import synapticloop.h2zero.util.validator.FinderNameValidator;
import synapticloop.h2zero.util.validator.ForeignKeyTableValidator;
import synapticloop.h2zero.util.validator.InserterNameValidator;
import synapticloop.h2zero.util.validator.OptionsGeneratorsValidator;
import synapticloop.h2zero.util.validator.PrimaryKeyExistsValidator;
import synapticloop.h2zero.util.validator.SelectClauseFinderValidator;
import synapticloop.h2zero.util.validator.UpdaterNameValidator;
import synapticloop.h2zero.util.validator.Validator;
import synapticloop.h2zero.util.validator.WhereClauseDeleterValidator;
import synapticloop.h2zero.util.validator.WhereClauseFinderValidator;
import synapticloop.h2zero.util.validator.WhereClauseUpdaterValidator;
import synapticloop.h2zero.util.validator.bean.Message;


public class H2ZeroParser {
	private Database database = null;
	private Options options = null;

	private int numWarn = 0;
	private int numFatal = 0;

	private static ArrayList<Validator> validators = new ArrayList<Validator>();
	static {
		validators.add(new ForeignKeyTableValidator());
		validators.add(new OptionsGeneratorsValidator());

		validators.add(new FinderInQueryValidator());
		validators.add(new FinderNameValidator());

		validators.add(new InserterNameValidator());
		validators.add(new DeleterNameValidator());
		validators.add(new UpdaterNameValidator());

		validators.add(new PrimaryKeyExistsValidator());

		validators.add(new DefaultValueValidator());

		validators.add(new WhereClauseFinderValidator());
		validators.add(new WhereClauseUpdaterValidator());
		validators.add(new WhereClauseDeleterValidator());
		
		validators.add(new SelectClauseFinderValidator());
		
		validators.add(new DuplicateTableNameValidator());
		validators.add(new DuplicateFieldNameValidator());
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
					SimpleLogger.logInfo(LoggerType.VALIDATOR, String.format("[ %" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getMessage()));
				} else if(message.getType().equals(SimpleLogger.WARN)){
					numWarn++;
					SimpleLogger.logWarn(LoggerType.VALIDATOR, String.format("[ %" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getMessage()));
				} else if(message.getType().equals(SimpleLogger.FATAL)){
					numFatal++;
					SimpleLogger.logFatal(LoggerType.VALIDATOR, String.format("[ %" + maxValidatorClassNameLength + "s ] %s", validator.getClass().getSimpleName(), message.getMessage()));
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
