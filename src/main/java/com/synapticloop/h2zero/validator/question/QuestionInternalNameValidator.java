package com.synapticloop.h2zero.validator.question;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.validator.BaseNameValidator;
import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Question;
import com.synapticloop.h2zero.model.Table;

public class QuestionInternalNameValidator extends BaseNameValidator {

	public QuestionInternalNameValidator() {
		super("Question");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Question> questions = table.getQuestions();
			for (Question question : questions) {
				String name = question.getName();

				if(name.equals("internalDoesPrimaryKeyExist")) {
					addFatalMessage("'" + table.getName() + "' has a question named 'internalDoesPrimaryKeyExist' which is reserved for internal purposes.");
				}
			}
		}
	}

}
