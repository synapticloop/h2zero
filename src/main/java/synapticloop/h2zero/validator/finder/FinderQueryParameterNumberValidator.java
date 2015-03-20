package synapticloop.h2zero.validator.finder;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

public class FinderQueryParameterNumberValidator extends Validator {
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String whereClause = finder.getWhereClause();
				int numQuestionMarks = 0;
				int numQuestionMarksWhereFields = 0;

				int numInClauses = 0;
				int numInClausesWhereFields = 0;

				if(null != whereClause) {
					numQuestionMarks = countOccurrences(whereClause, "?");

					// need to also check the in clauses
					numInClauses = countOccurrences(whereClause, "...");

					ArrayList<BaseField> whereFields = finder.getWhereFields();
					for (BaseField baseField : whereFields) {
						if(baseField.getIsInField()) {
							numInClausesWhereFields++;
						} else {
							numQuestionMarksWhereFields++;
						}
					}
				}

				if(numQuestionMarks != numQuestionMarksWhereFields) {
					addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' " + JSONKeyConstants.WHERE_CLAUSE + " has " + numQuestionMarks + " parameters, but only " + numQuestionMarksWhereFields + " " + JSONKeyConstants.WHERE_FIELDS + " entries.");
				}

				if(numInClauses != numInClausesWhereFields) {
					addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' " + JSONKeyConstants.WHERE_CLAUSE + " has " + numInClauses + " in: parameters, but only " + numInClausesWhereFields + " " + JSONKeyConstants.WHERE_FIELDS + " in: entries.");
				}

			}
		}
		return(isValid);
	}
}
