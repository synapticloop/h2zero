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

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class FieldPrimaryKeyTypeValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getPrimary() && 
						(options.getDatabase().equalsIgnoreCase(Options.DATABASE_COCKROACH) ||
								options.getDatabase().equalsIgnoreCase(Options.DATABASE_POSTGRESQL))) {
					String type = baseField.getType();
					if(!type.equalsIgnoreCase(BaseField.PRIMARY_KEY_POSTGRESQL_BIGSERIAL) ||
							!type.equalsIgnoreCase(BaseField.PRIMARY_KEY_POSTGRESQL_SERIAL) ||
							!type.equalsIgnoreCase(BaseField.PRIMARY_KEY_POSTGRESQL_SMALLSERIAL)) {
						addWarnMessage("The primary key field '" + table.getName() + "." + baseField.getName() + "' has a type: of '" + type + "', it should be one of '" + BaseField.PRIMARY_KEY_POSTGRESQL_BIGSERIAL+ "', '" + BaseField.PRIMARY_KEY_POSTGRESQL_SERIAL+ "', or '" + BaseField.PRIMARY_KEY_POSTGRESQL_SMALLSERIAL+ "'.  This has been updated.");
						boolean setPostgreSQLType = baseField.setPostgreSQLType(type);
						if(!setPostgreSQLType) {
							addFatalMessage("The primary key field '" + table.getName() + "." + baseField.getName() + "' has a type: of '" + type + "', This could not be mapped to a 'serial' type");
						} else {
							addWarnMessage("The primary key field '" + table.getName() + "." + baseField.getName() + "' had a type: of '" + type + "', This has been updated to '" + baseField.getType() + "'.");
						}
					}
				}
			}
		}
	}
}
