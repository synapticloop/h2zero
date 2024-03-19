package synapticloop.h2zero.validator;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

public abstract class BaseNameValidator extends BaseValidator {
	private String queryObjectType = null;
	protected Set<String> queryObjectNames = new HashSet<String>();

	protected List<String> allowablePrefixNames = new ArrayList<String>();
	protected String allowablePrefixList = null;

	public BaseNameValidator(String queryObjectType) {
		this.queryObjectType = queryObjectType;
	}

	@Override
	public abstract void validate(Database database, Options options);

	protected void parseAndValidateAllowablePrefixes(JSONObject optionsObject, String defaultPrefix) {
		allowablePrefixList = null;
		allowablePrefixNames.clear();

		String allowablePrefixes = optionsObject.optString("allowablePrefixes", "");
		String[] allowablePrefixSplit = allowablePrefixes.split(",");
		for (int i = 0; i < allowablePrefixSplit.length; i++) {
			String allowablePrefix = allowablePrefixSplit[i].trim();
			if(allowablePrefix.length() == 0) {
				SimpleLogger.logWarn(LoggerType.OPTIONS_VALIDATOR, this.getClass(), "An empty allowable prefix was found, which is not allowed... ignoring.");
			} else {
				SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, this.getClass(), "Adding in allowable prefix of '" + allowablePrefix + "'.");
				allowablePrefixNames.add(allowablePrefix);
			}
		}

		if(allowablePrefixNames.isEmpty()) {
			SimpleLogger.logWarn(LoggerType.OPTIONS_VALIDATOR, this.getClass(), "No allowable prefixes were found, adding in 'update' prefix");
			String[] splits = defaultPrefix.split(",");
			for (int i = 0; i < splits.length; i++) {
				String split = splits[i].trim();
				if(split.length() != 0) {
					allowablePrefixNames.add(split);
				}
			}
		}

		StringBuilder stringBuilder = new StringBuilder();
		int i = 0;
		for (String allowablePrefix : allowablePrefixNames) {
			if(i != 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append("'" + allowablePrefix + "'");
			i++;
		}

		allowablePrefixList = stringBuilder.toString();
	}

	protected void validateQueryName(Table table, List<String> queryNames) {
		queryObjectNames.clear();
		for (String queryName : queryNames) {
			if(queryName.contains(" ")) {
				addFatalMessage(queryObjectType + " '" + table.getName() + "." + queryName + "' contains a ' ' (whitespace) character.");
			}

			
			if(queryObjectNames.contains(queryName)) {
				addFatalMessage(queryObjectType + " '" + table.getName() + "." + queryName + "' is a duplicate.");
			}
			queryObjectNames.add(queryName);
		}
		queryObjectNames.clear();
	}

	protected void validateAllowablePrefixes(Table table, String fieldName) {
		boolean isValidPrefix = false;
		for (String allowablePrefix : allowablePrefixNames) {
			if(fieldName.startsWith(allowablePrefix)) {
				isValidPrefix = true;
			}
		}

		if(!isValidPrefix) {
			addWarnMessage(queryObjectType + " '" + table.getName() + "." + fieldName + "' should really start with one of the following prefixes: " + allowablePrefixList + ".");
		}
	}
}
