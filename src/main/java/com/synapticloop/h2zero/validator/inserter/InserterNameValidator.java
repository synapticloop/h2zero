package com.synapticloop.h2zero.validator.inserter;

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

import java.util.ArrayList;
import java.util.List;

import com.synapticloop.h2zero.validator.BaseNameValidator;
import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Inserter;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;

public class InserterNameValidator extends BaseNameValidator {
	public InserterNameValidator() {
		super("Inserter");
		allowablePrefixNames.add("insert");
		allowablePrefixList = "insert";
	}

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Inserter> inserters = table.getInserters();
			List<String> inserterNames = new ArrayList<String>();
			for (Inserter inserter : inserters) {
				String name = inserter.getName();
				inserterNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, inserterNames);
		}
	}
}
