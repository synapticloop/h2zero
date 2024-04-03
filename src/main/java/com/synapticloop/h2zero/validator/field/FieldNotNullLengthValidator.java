package com.synapticloop.h2zero.validator.field;

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

import java.util.List;

import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.h2zero.model.field.BaseField;
import com.synapticloop.h2zero.validator.BaseValidator;

/**
 * This validator checks to see whether there are some fields which are marked 
 * as nullable, however do not have a minimum length set on them 
 * 
 * @author synapticloop
 *
 */
public class FieldNotNullLengthValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(!baseField.getNullable() && baseField.getMinLength() == 0 && "String".equals(baseField.getJavaType())) {
					addWarnMessage("Table field '" + table.getName() + "." + baseField.getName() + "' is not allowed to be null, but has a minimum length of 0, consider setting \"minLength\": 1");
				}
			}
		}
	}

}
