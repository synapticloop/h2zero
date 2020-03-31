package synapticloop.h2zero.validator.field;

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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class FieldNameDuplicateValidator extends BaseValidator {
	private Set<String> names = new HashSet<String>();

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			names.clear();
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				String fieldName = baseField.getName();
				if(names.contains(fieldName)) {
					addFatalMessage("Table '" + table.getName() + "' has a duplicate field named '" + fieldName + "'.");
				} else {
					names.add(fieldName);
				}
			}
		}
	}
}
