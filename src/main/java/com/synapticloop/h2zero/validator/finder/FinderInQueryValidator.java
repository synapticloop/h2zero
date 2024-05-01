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

import com.synapticloop.h2zero.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Finder;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.h2zero.validator.BaseValidator;

import java.util.List;

@H2ZeroValidator
public class FinderInQueryValidator extends BaseValidator {

	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				// in some instances - there is no where clause - especially if it is all in the selectClause
				if(null == finder.getWhereClause()) {
					continue;
				}

				if(finder.getHasInFields() && !finder.getWhereClause().contains("...")) {
					isValid = false;
					addFatalMessage("Finder '" + finder.getName() + "' has in fields, but no '...' in the where clause '" + finder.getWhereClause() + "'.");
				} else if(!finder.getHasInFields() && finder.getWhereClause().contains("...")) {
					isValid = false;
					addFatalMessage("Finder '" + finder.getName() + "' has '...' in the where clause '" + finder.getWhereClause() + "', but no 'in' fields.");
				}
			}
		}
	}

}
