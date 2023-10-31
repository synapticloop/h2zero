package synapticloop.h2zero.validator.finder;

/*
 * Copyright (c) 2012-2023 synapticloop.
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

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderWhereFieldAliasValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			// TODO - I don't think that this logic is correct.....
//			for (Finder finder : finders) {
//
//				if(finder.getHasWhereFieldAliases()) {
//					// go through and ensure that all of the finder where fields has an alias
//					List<BaseField> whereFields = finder.getWhereFields();
//					for (BaseField baseField : whereFields) {
//
//
//						if(!baseField.getHasAlias()) {
//							addFatalMessage("'" + table.getName() + "." + finder.getName() + " has aliased whereFields, however '" + baseField.getName() + "' does not have an alias.");
//						}
//					}
//				}
//			}
		}
	}

}
