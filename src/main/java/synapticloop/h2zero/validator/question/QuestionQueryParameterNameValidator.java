package synapticloop.h2zero.validator.question;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseClauseValidator;

public class QuestionQueryParameterNameValidator extends BaseClauseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Question> questions = table.getQuestions();
			for (Question question : questions) {
				validateClausesAndFields(question);
			}
		}
	}

}
