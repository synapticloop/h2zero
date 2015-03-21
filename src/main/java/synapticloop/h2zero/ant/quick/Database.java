package synapticloop.h2zero.ant.quick;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	private ArrayList<Table> tables = new ArrayList<Table>();
	private HashMap<String, Table> tableNames = new HashMap<String, Table>();

	public void addTable(Table table) {
		tables.add(table);
		tableNames.put(table.getName(), table);
	}

	public boolean addForeignKey(String from, String to) {
		if(!tableNames.containsKey(from) && !tableNames.containsKey(to)) {
			return(false);
		}

		tableNames.get(from).addForeignKey(to);
		return(true);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");
		stringBuilder.append("\"options\": {\n");
		stringBuilder.append("\t\"logging\": \"log4j\",\n");
		stringBuilder.append("\t\"statistics\": false,\n");
		stringBuilder.append("\t\"generators\": [ \"java\", \"sql\" ]\n");
		stringBuilder.append("},\n");
		stringBuilder.append("\"database\": {\n");
		stringBuilder.append("\t\"schema\": \"your_schema_here\",\n");
		stringBuilder.append("\t\"package\": \"your.package.here\",\n");
		stringBuilder.append("\t\"tables\": [\n");

		int size = tables.size();
		for(int i = 0; i < size; i++) {
			Table table = tables.get(i);
			stringBuilder.append(table.toString());
			if(i != size -1) {
				stringBuilder.append(",");
			}
			stringBuilder.append("\n");
		}

		stringBuilder.append("\t]\n");
		stringBuilder.append("}\n");
		stringBuilder.append("}\n");
		return(stringBuilder.toString());
	}

}
