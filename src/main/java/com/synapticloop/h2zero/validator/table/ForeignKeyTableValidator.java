package com.synapticloop.h2zero.validator.table;

/*
 * Copyright (c) 2013-2024 synapticloop.
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

import com.synapticloop.h2zero.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.h2zero.model.field.BaseField;
import com.synapticloop.h2zero.validator.BaseValidator;

import java.util.List;

@H2ZeroValidator
public class ForeignKeyTableValidator extends BaseValidator {

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();

		for (Table table : tables) {
			List<BaseField> baseFields = table.getFields();
			for (BaseField baseField : baseFields) {
				if(null != baseField.getForeignKeyTable() && null == baseField.getForeignKeyTableLookup()) {
					addFatalMessage("'" + table.getName() + "." + baseField.getName() + "' foreign key references table '" + baseField.getForeignKeyTable() + "', which does not exist.");
				}
			}
		}
	}
}
