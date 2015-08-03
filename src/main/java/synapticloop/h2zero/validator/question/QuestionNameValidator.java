package synapticloop.h2zero.validator.question;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseNameValidator;

public class QuestionNameValidator extends BaseNameValidator {

	public QuestionNameValidator() {
		super("Question");
		allowablePrefixNames.add("is");
		allowablePrefixNames.add("has");
		allowablePrefixNames.add("does");
		allowablePrefixList = "is, has, does";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, "is, has, does");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Question> questions = table.getQuestions();
			List<String> questionNames = new ArrayList<String>();
			for (Question question : questions) {
				String name = question.getName();
				questionNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, questionNames);
		}
	}

}
