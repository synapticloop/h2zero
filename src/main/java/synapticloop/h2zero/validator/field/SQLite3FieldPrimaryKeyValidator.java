package synapticloop.h2zero.validator.field;

/*
 * Copyright (c) 2013-2019 synapticloop.
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


import java.util.Iterator;
import java.util.List;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.model.field.IntegerField;
import synapticloop.h2zero.validator.BaseValidator;

public class SQLite3FieldPrimaryKeyValidator extends BaseValidator {
	public void validate(Database database, Options options) {
		if ("sqlite3".equals(options.getDatabase())) {
			List<Table> tables = database.getTables();
			for (Iterator<Table> tableIterator = tables.iterator(); tableIterator.hasNext();) {
				Table table = (Table)tableIterator.next();
				List<BaseField> fields = table.getFields();
				for (BaseField baseField : fields) {
					if ((baseField.getPrimary()) && (!(baseField instanceof IntegerField))) {
						addWarnMessage(new String[] { "Table field '" + table.getName() + "." + baseField.getName() + "' is a primary key of type '" + baseField.getType() + "' - which will be converted to an INTEGER upon table creation, you should update this to 'int' type." });
					}
				}
			}
		}
	}
}
