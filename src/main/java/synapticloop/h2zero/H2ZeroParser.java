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
import synapticloop.h2zero.util.validator.ForeignKeyTableValidator;
import synapticloop.h2zero.util.validator.OptionsGeneratorsValidator;
import synapticloop.h2zero.util.validator.Validator;


public class H2ZeroParser {
	private Database database = null;
	private Options options = null;

	private static ArrayList<Validator> validators = new ArrayList<Validator>();
	static {
		validators.add(new ForeignKeyTableValidator());
		validators.add(new OptionsGeneratorsValidator());
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

			ArrayList<String> messages = validator.getMessages();
			for (String message: messages) {
				System.out.println(validator.getClass().getSimpleName() + " said: " + message);
			}
		}
		

		if(!isValid) {
			throw new H2ZeroParseException("Validators found some problems.");
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
}
