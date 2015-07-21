package synapticloop.h2zero.validator.question;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.BaseValidator;

public class QuestionJsonUniqueKeyExistsValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Question> questions = table.getQuestions();
			for (Question question : questions) {
				if(question.getHasJsonUniqueKey()) {
					addWarnMessage("Question '" + table.getName() + "." + question.getName() + "' has a key od '" + JSONKeyConstants.UNIQUE+ "' which is ignored, invalid, and therefore un-neccessary.");
				}
			}
		}
	}

}
