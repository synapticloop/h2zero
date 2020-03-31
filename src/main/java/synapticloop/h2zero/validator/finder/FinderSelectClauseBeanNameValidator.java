package synapticloop.h2zero.validator.finder;

/*
 * Copyright (c) 2012-2020 synapticloop.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class FinderSelectClauseBeanNameValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		Map<String, String> selectClauseBeanNames = new HashMap<String, String>();

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				if(null != finder.getSelectClause()) {
					String name = finder.getName();
					List<BaseField> selectFields = finder.getSelectFields();

					StringBuilder stringBuilder = new StringBuilder();
					for (BaseField baseField : selectFields) {
						stringBuilder.append(":");
						stringBuilder.append(baseField.getType());
					}


					String params = stringBuilder.toString();

					if(selectClauseBeanNames.containsKey(name)) {
						// lets have a look at the parameters
						String value = selectClauseBeanNames.get(name);
						
						if(!value.equals(params)) {
							addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' with a select will generate and override a duplicate bean with different parameters.");
						}
					} else {
						selectClauseBeanNames.put(name, params);
					}
				}
			}
		}
	}

}
