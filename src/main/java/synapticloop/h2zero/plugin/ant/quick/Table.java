package synapticloop.h2zero.plugin.ant.quick;

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
