package synapticloop.h2zero.validator.question;

/*
 * Copyright (c) 2012-2020 synapticloop.
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
		allowablePrefixNames.add("do");
		allowablePrefixList = "is, has, does, do";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, allowablePrefixList);
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
