package synapticloop.h2zero.validator.counter;

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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.validator.BaseNameValidator;

public class CounterNameValidator extends BaseNameValidator {

	public CounterNameValidator() {
		super("Counter");
		allowablePrefixNames.add("count");
		allowablePrefixList = "count";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, "count");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Counter> counters = table.getCounters();
			List<String> counterNames = new ArrayList<String>();
			for (Counter counter : counters) {
				String name = counter.getName();
				counterNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, counterNames);
		}
	}

}
