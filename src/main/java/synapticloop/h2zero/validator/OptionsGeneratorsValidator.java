package synapticloop.h2zero.validator;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public class OptionsGeneratorsValidator extends Validator {

	public void validate(Database database, Options options) {
		isValid = options.hasGenerators();

		if(!isValid) {
			addFatalMessage("You __MUST__ have at least one item in the generators array.");
		}
	}
}
