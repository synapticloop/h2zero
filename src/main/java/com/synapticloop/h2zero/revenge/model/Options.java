package com.synapticloop.h2zero.revenge.model;

/*
 * Copyright (c) 2013-2026 synapticloop.
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

/**
 * <p>Represents the 'options' section of the h2zero JSON configuration file.</p>
 *
 * <p>This class holds global configuration options such as the database type and
 * the list of generators to be used.</p>
 */
public class Options {
	private static final String JSON_OPTIONS = "  \"options\": {\n";
	private static final String JSON_DATABASE = "    \"database\": \"";
	private static final String JSON_GENERATORS = "    \"generators\": [\n";
	private static final String JSON_JAVA = "      \"java\",\n";
	private static final String JSON_SQL = "       \"sql\"\n";
	private static final String JSON_END_GENERATORS = "    ]\n";
	private static final String JSON_END_OPTIONS = "  },\n";

	private final String databaseType;

	/**
	 * <p>Constructs a new Options object with the specified database type.</p>
	 *
	 * @param databaseType the type of the database (e.g., mysql, postgresql)
	 */
	public Options(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * <p>Generates a JSON formatted string representation of the options.</p>
	 *
	 * @return a JSON string representing the options configuration
	 */
	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(JSON_OPTIONS)
				.append(JSON_DATABASE)
				.append(this.databaseType)
				.append("\",\n")
				.append(JSON_GENERATORS)
				.append(JSON_JAVA)
				.append(JSON_SQL)
				.append(JSON_END_GENERATORS)
				.append(JSON_END_OPTIONS);
		return (stringBuilder.toString());
	}

}
