package synapticloop.h2zero.validator.table;

/*
 * Copyright (c) 2013-2020 synapticloop.
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
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class TablePrimaryKeyExistsValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			int numPrimaryKeys = 0;
			for (BaseField baseField : fields) {
				if(baseField.getPrimary()) {
					numPrimaryKeys++;
				}
			}

			switch (numPrimaryKeys) {
			case 0:
				isValid = false;
				addFatalMessage("Table '" + table.getName() + "' __MUST__ define a primary key.");
				break;
			case 1:
				// all is good
				break;
			default:
				// more than one - oh oh
				isValid = false;
				addFatalMessage("Table '" + table.getName() + "' __MUST__ define only 1 primary key, it has '" + numPrimaryKeys + "'.");
				break;
			}
		}
	}

}
