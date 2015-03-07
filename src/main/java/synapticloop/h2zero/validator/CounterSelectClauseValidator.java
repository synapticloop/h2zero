package synapticloop.h2zero.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class CounterSelectClauseValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Question> questions = table.getQuestions();
			for (Question question : questions) {
				String selectClause = question.getSelectClause();
				if(null != selectClause) {
					if(!selectClause.toLowerCase().contains("select")) {
						addWarnMessage("Question '" + table.getName() + "." + question.getName() + "' has a '" + JSONKeyConstants.SELECT_CLAUSE + "' that does not start with 'select', so I am going to add one.");
						question.setSelectClause(" select " + selectClause);
					}
				}
			}
		}

		return(isValid);
	}

}
