package com.synapticloop.h2zero.generator.validator.field;

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

import com.synapticloop.h2zero.generator.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.model.Table;
import com.synapticloop.h2zero.generator.model.field.BaseField;
import com.synapticloop.h2zero.generator.validator.BaseValidator;

import java.util.List;

@H2ZeroValidator
public class FieldSerialNonPrimaryKeyValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		if(options.getDatabase().compareToIgnoreCase(Options.DATABASE_MYSQL) == 0 ||
				options.getDatabase().compareToIgnoreCase(Options.DATABASE_SQLITE3) == 0) {
			// all good here
			return;
		}

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				String type = baseField.getType();
				if(!baseField.getPrimary() && 
						(type.equalsIgnoreCase(BaseField.PRIMARY_KEY_POSTGRESQL_BIGSERIAL) ||
						type.equalsIgnoreCase(BaseField.PRIMARY_KEY_POSTGRESQL_SERIAL) ||
						type.equalsIgnoreCase(BaseField.PRIMARY_KEY_POSTGRESQL_SMALLSERIAL))) {

					addWarnMessage("The field '" + table.getName() + "." + baseField.getName() + "' is set as a '" + type + "' but it is not a primary key, there may be interesting results...");
				}
			}
		}
	}
}
