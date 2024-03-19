package synapticloop.h2zero.validator.updater;

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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.Updater;
import synapticloop.h2zero.validator.BaseNameValidator;

public class UpdaterNameValidator extends BaseNameValidator {

	public UpdaterNameValidator() {
		super("Updater");
		allowablePrefixNames.add("update");
		allowablePrefixList = "update";
	}

	@Override
	public void parseAndValidateOptions(JSONObject optionsObject) {
		parseAndValidateAllowablePrefixes(optionsObject, "update");
	}

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Updater> updaters = table.getUpdaters();
			List<String> updaterNames = new ArrayList<String>();
			for (Updater updater : updaters) {
				String name = updater.getName();
				updaterNames.add(name);
				validateAllowablePrefixes(table, name);
			}
			validateQueryName(table, updaterNames);
		}
	}
}
