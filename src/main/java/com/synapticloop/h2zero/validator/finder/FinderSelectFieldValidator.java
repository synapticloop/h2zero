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
import com.synapticloop.h2zero.model.field.BaseField;
import com.synapticloop.h2zero.model.util.JSONKeyConstants;
import com.synapticloop.h2zero.validator.BaseValidator;

@H2ZeroValidator
public class FinderSelectFieldValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {

		for (Table table : database.getTables()) {
			for (Finder finder : table.getFinders()) {
				String selectClause = finder.getSelectClause();

				for (BaseField selectField : finder.getSelectFields()) {
					if(!selectClause.toLowerCase().contains(selectField.getName())) {
						addWarnMessage("Finder '" +
								table.getName() +
								"." + finder.getName() +
								"' has a " +
								JSONKeyConstants.SELECT_CLAUSE +
								" with a selectField of '" +
								selectField.getName() +
								"' that does not appear in the statement.");
					}
				}


				if(null != selectClause && !selectClause.toLowerCase().contains("select")) {
					addWarnMessage("Finder '" + table.getName() + "." + finder.getName() + "' has a " + JSONKeyConstants.SELECT_CLAUSE + " that does not start with 'select', so I am going to add one.");
					finder.setSelectClause(" select " + selectClause);
				}
			}
		}
	}

}
