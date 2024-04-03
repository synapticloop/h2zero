package com.synapticloop.h2zero.validator.finder;

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

import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Finder;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.h2zero.validator.BaseValidator;

import java.util.List;

public class FinderWhereClauseIncludesLimitOrOffsetValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				String whereClause = finder.getWhereClause();
				if(null != whereClause && (
						whereClause.toLowerCase().contains(" limit") ||
								whereClause.toLowerCase().contains(" offset"))
						) {
					addWarnMessage(
							"Finder '" + table.getName() + "." + finder.getName() + "' " +
									"has a whereClause that has the keyword 'limit' or 'offset' which is discouraged, " +
									"use the finder method with the limit/offset signature.");
					finder.setWhereClause(whereClause);
				}
			}
		}
	}

}
