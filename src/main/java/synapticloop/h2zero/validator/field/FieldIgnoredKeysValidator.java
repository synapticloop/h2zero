package synapticloop.h2zero.validator.field;

/*
 * Copyright (c) 2012-2019 synapticloop.
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

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

public class FieldIgnoredKeysValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<String> foundIgnoredKeys = new ArrayList<String>();
			String replacementKey = "There is no replacement for this key.";

			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				foundIgnoredKeys = baseField.getFoundIgnoredKeys();
				for (String key : foundIgnoredKeys) {
					replacementKey = "There is no replacement for this key.";
					if(baseField.getReplacementForKey(key) != null) {
						replacementKey = "This should be replaced by key '" + baseField.getReplacementForKey(key) + "'.";
					}
					addWarnMessage("Field '" + table.getName() + "." + baseField.getName() +  "' has a json key of '" + key + "' which is no longer valid and consequently ignored.  " + replacementKey);
				}
			}
		}

	}

}
