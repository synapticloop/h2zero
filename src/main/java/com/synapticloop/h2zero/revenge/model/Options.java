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

public class Options {
	private final String databaseType;

	public Options(String databaseType) {
		this.databaseType = databaseType;
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("  \"options\": {\n")
				.append("    \"database\": \"")
				.append(this.databaseType)
				.append("\",\n")
				.append("    \"generators\": [\n")
				.append("      \"java\",\n")
				.append("       \"sql\"\n")
				.append("    ]\n")
				.append("  },\n");
		return (stringBuilder.toString());
	}

}
