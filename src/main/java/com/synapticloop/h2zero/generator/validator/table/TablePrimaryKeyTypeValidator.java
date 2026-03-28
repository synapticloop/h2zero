package com.synapticloop.h2zero.generator.validator.table;

/*
 * Copyright (c) 2013-2026 synapticloop.
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@H2ZeroValidator
public class TablePrimaryKeyTypeValidator extends BaseValidator {
	private static final Set<String> PRIMARY_KEY_TYPES = new HashSet<>();

	static {
		PRIMARY_KEY_TYPES.add("bigint");
		PRIMARY_KEY_TYPES.add("bigserial");
		PRIMARY_KEY_TYPES.add("int");
		PRIMARY_KEY_TYPES.add("serial");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if (baseField.getPrimary() &&
						!PRIMARY_KEY_TYPES.contains(baseField.getType().toLowerCase())) {
					addFatalMessage(
							"Primary key '" +
									table.getName() +
									"." +
									baseField.getName() +
									"' __MUST__ be of SQL type 'bigint'/'int', it is of type '" +
									baseField.getType() +
									"'.");
				}
			}
		}
	}
}
