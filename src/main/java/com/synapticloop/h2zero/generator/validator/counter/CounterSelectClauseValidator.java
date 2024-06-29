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

import java.util.List;

import com.synapticloop.h2zero.generator.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.generator.model.Counter;
import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.model.Table;
import com.synapticloop.h2zero.generator.validator.BaseValidator;

@H2ZeroValidator
public class CounterSelectClauseValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Counter> counters = table.getCounters();
			for (Counter counter : counters) {
				String selectClause = counter.getSelectClause();
				if(null != selectClause && !selectClause.toLowerCase().contains("select")) {
					addWarnMessage("Counter '" + table.getName() + "." + counter.getName() + "' has a selectClause that does not start with 'select', so I am going to add one.");
					counter.setSelectClause(" select " + selectClause);
				}
			}
		}
	}

}