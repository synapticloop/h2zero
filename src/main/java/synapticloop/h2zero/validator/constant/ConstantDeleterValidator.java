package synapticloop.h2zero.validator.constant;

/*
 * Copyright (c) 2013-2023 synapticloop.
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
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.BaseValidator;

/**
 * Validate that a constant table does not have any 'deleter' objects on it.  This will add a FATAL warning to the 
 * validation stream
 * 
 * @author synapticloop
 *
 */
public class ConstantDeleterValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			if(table.getIsConstant() && !table.getDeleters().isEmpty()) {
				addFatalMessage("Constant model '" + table.getName() + "' has '" + JSONKeyConstants.DELETERS + "' which are not allowed, as you may not change the underlying database tables dynamically.");
			}
		}
	}

}
