package com.synapticloop.h2zero.generator.validator.counter;

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

import com.synapticloop.h2zero.generator.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.generator.model.Counter;
import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.model.Table;
import com.synapticloop.h2zero.generator.model.util.JSONKeyConstants;
import com.synapticloop.h2zero.generator.validator.BaseValidator;

import java.util.List;

/**
 * Validate that there isn't a 'unique' key on the counter json object, the result of a counter is always a single
 * result (and therefore a unique result).  THis will add a 'WARN' message to the validation stream.
 *
 */
@H2ZeroValidator
public class CounterJsonUniqueKeyExistsValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Counter> counters = table.getCounters();
			for (Counter counter : counters) {
				if(counter.getHasJsonUniqueKey()) {
					addWarnMessage("Counter '" + table.getName() + "." + counter.getName() + "' has a key of '" + JSONKeyConstants.UNIQUE + "' which is ignored, invalid, and therefore un-neccessary.");
				}
			}
		}
	}

}
