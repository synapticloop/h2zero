package synapticloop.h2zero.validator.question;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

public class QuestionSelectFieldsValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Question> questions = table.getQuestions();
			for (Question question : questions) {
				if(question.getSelectFields().size() > 0) {
					addWarnMessage("Question '" + table.getName() + "." + question.getName() + "' has '" + JSONKeyConstants.SELECT_FIELDS + "' which are ignored, and therefore un-neccessary.");
				}
			}
		}

		return(isValid);
	}

}
