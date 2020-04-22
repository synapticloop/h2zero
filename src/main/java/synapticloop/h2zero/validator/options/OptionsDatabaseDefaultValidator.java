package synapticloop.h2zero.validator.options;

/*
 * Copyright (c) 2013-2020 synapticloop.
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

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.validator.BaseValidator;

public class OptionsDatabaseDefaultValidator extends BaseValidator {
	@Override
	public void validate(Database database, Options options) {
		if(!options.getIsDefault()) {
			addWarnMessage("The database type was not set, this defaults to '" + options.getDatabase() + "'.  Consider setting a JSON key of \"database\": \"" + options.getDatabase() + "\".");
		}
	}

}
