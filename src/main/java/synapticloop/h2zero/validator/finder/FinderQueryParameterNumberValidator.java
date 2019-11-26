package synapticloop.h2zero.validator.finder;

/*
 * Copyright (c) 2012-2019 synapticloop.
 * 
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderQueryParameterNumberValidator extends BaseValidator {
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
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

					List<BaseField> whereFields = finder.getWhereFields();
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
	}
}
