package com.synapticloop.h2zero.generator.validator.field;

/*
 * Copyright (c) 2012-2026 synapticloop.
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

import com.synapticloop.h2zero.generator.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.model.Table;
import com.synapticloop.h2zero.generator.model.field.BaseField;
import com.synapticloop.h2zero.generator.util.SimpleLogger;
import com.synapticloop.h2zero.generator.validator.BaseValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@H2ZeroValidator
public class FieldNameIsTableNameValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();

		// get all the table names so that we can match them to any fields
		// that we have
		Set<String> tableNames = new HashSet<>();
		for (Table table : tables) {
			tableNames.add(table.getName().toLowerCase());
		}

		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				String fieldName = baseField.getName();

				if(tableNames.contains(fieldName.toLowerCase())) {
					addFatalMessage(
							"Table '" +
									table.getName() +
									"' has a field named '" +
									fieldName +
									"', which is the name of a table - this is not allowed.");
				}

				numChecked++;
			}
		}
	}


	@Override
	public String getShortDescription() {
		return "Check fields that have the same name as a foreign key table.";
	}

	@Override
	public List<String> getMessageTypes() {
		return(List.of(SimpleLogger.FATAL));
	}
}
