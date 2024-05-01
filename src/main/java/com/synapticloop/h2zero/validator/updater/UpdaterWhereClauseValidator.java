package com.synapticloop.h2zero.validator.updater;

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
import com.synapticloop.h2zero.model.Updater;
import com.synapticloop.h2zero.validator.BaseValidator;

import java.util.List;

@H2ZeroValidator
public class UpdaterWhereClauseValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Updater> updaters = table.getUpdaters();
			for (Updater updater : updaters) {
				String whereClause = updater.getWhereClause();

				if(null != whereClause && !whereClause.toLowerCase().contains("where")) {
					addWarnMessage("Updater '" + table.getName() + "." + updater.getName() + "' has a whereClause that does not start with 'where', so I am going to add one.");
					updater.setWhereClause(" where " + whereClause);
				}
			}
		}
	}

}
