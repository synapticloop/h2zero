package com.synapticloop.h2zero.validator.sqlite;

/*
 * Copyright (c) 2013-2024 synapticloop.
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

import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.h2zero.model.field.BaseField;
import com.synapticloop.h2zero.model.field.BlobField;
import com.synapticloop.h2zero.validator.BaseValidator;

import java.util.Iterator;
import java.util.List;

//@H2ZeroValidator
public class SQLite3FieldBlobValidator extends BaseValidator {
	public void validate(Database database, Options options) {
		if ("sqlite3".equals(options.getDatabase())) {
			List<Table> tables = database.getTables();
			for (Iterator<Table> tableIterator = tables.iterator(); tableIterator.hasNext();) {
				Table table = tableIterator.next();
				List<BaseField> fields = table.getFields();
				for (BaseField baseField : fields) {
					if ((baseField instanceof BlobField)) {
						this.isValid = false;
						addFatalMessage(new String[] { "Table field '" + table.getName() + "." + baseField.getName() + "' is a BLOB - which, alas, is __NOT__ supported by the sqlite3 JDBC driver - apologies." });
					}
				}
			}
		}
	}
}
