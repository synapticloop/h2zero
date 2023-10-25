package synapticloop.h2zero.plugin.ant.quick;

/*
 * Copyright (c) 2013-2023 synapticloop.
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

public class Table {
	private String name;
	private List<String> foreignKeys = new ArrayList<String>();

	public Table(String name) {
		this.name = name;
	}

	public void addForeignKey(String to) {
		foreignKeys.add(to);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\t\t{\n");
		stringBuilder.append("\t\t\t\"name\": \"" + name + "\",\n");
		stringBuilder.append("\t\t\t\"cacheable\": false,\n");
		stringBuilder.append("\t\t\t\"cacheFindAll\": false,\n");
		stringBuilder.append("\t\t\t\"fields\": [\n");
		stringBuilder.append("\t\t\t\t{ \"name\": \"id_" + name + "\", \"type\": \"bigint\", \"nullable\": false, \"primary\": true },\n");

		// now for the foreign keys
		for (String foreignKey : foreignKeys) {
			stringBuilder.append("\t\t\t\t{ \"name\": \"id_" + foreignKey + "\", \"type\": \"bigint\", \"nullable\": false, \"foreignKey\": \"" + foreignKey + ".id_" + foreignKey + "\" },\n");
		}

		// add a name into it
		stringBuilder.append("\t\t\t\t{ \"name\": \"nm_" + name + "\", \"type\": \"varchar\", \"length\": \"256\", \"nullable\": false }\n");
		stringBuilder.append("\t\t\t],\n");
		stringBuilder.append("\t\t\t\"fieldFinders\": [\n");
		stringBuilder.append("\t\t\t],\n");
		stringBuilder.append("\t\t\t\"finders\": [\n");
		stringBuilder.append("\t\t\t],\n");
		stringBuilder.append("\t\t\t\"updaters\": [\n");
		stringBuilder.append("\t\t\t],\n");
		stringBuilder.append("\t\t\t\"deleters\": [\n");
		stringBuilder.append("\t\t\t],\n");
		stringBuilder.append("\t\t\t\"counters\": [\n");
		stringBuilder.append("\t\t\t],\n");
		stringBuilder.append("\t\t\t\"questions\": [\n");
		stringBuilder.append("\t\t\t]\n");
		stringBuilder.append("\t\t}");
		return(stringBuilder.toString());
	}

	public String getName() { return(name); }

}
