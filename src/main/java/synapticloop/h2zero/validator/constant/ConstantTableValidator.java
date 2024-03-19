package synapticloop.h2zero.validator.constant;

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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import synapticloop.h2zero.model.Constant;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseValidator;

/**
 * Validate that values to be inserted into the constant table do not have duplicate primary key or duplicate 
 * constant label values.  Any errors will add a FATAL message to the validation stream.
 * 
 * @author synapticloop
 *
 */
public class ConstantTableValidator extends BaseValidator {
	private Set<String> primaryKeyValues = new HashSet<String>();
	private Set<String> names = new HashSet<String>();

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			primaryKeyValues.clear();
			names.clear();
			if(table.getIsConstant()) {
				// go through and validate the values
				List<Constant> constants = table.getConstants();
				for (Constant constant : constants) {
					String primaryKeyValue = constant.getPrimaryKeyValue().toString();
					String name = constant.getName();
					if(primaryKeyValues.contains(primaryKeyValue)) {
						addFatalMessage("Constant model '" + table.getName() + "' has a duplicate primary key value of '" + primaryKeyValue + "'.");
					} else {
						primaryKeyValues.add(primaryKeyValue);
					}

					if(names.contains(name)) {
						addFatalMessage("Constant model '" + table.getName() + "' has a duplicate name of '" + name + "'.");
					} else {
						names.add(name);
					}
				}
			}
		}
	}

}
