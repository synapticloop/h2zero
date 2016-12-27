package synapticloop.h2zero.plugin.ant.quick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

public class Database {
	private String schema = null;
	private String javaPackage = null;

	private List<Table> tables = new ArrayList<Table>();
	private Map<String, Table> tableNames = new HashMap<String, Table>();
	private String generators;

	public Database(String schema, String javaPackage, String generators) {
		this.schema = schema;
		this.javaPackage = javaPackage;
		this.generators = generators;
	}

	public void addTable(Table table) {
		tables.add(table);
		tableNames.put(table.getName(), table);
	}

	public boolean addForeignKey(String from, String to) {
		if(!tableNames.containsKey(from) && !tableNames.containsKey(to)) {
			return(false);
		}


		Table table = tableNames.get(from);
		if(null != table) {
			table.addForeignKey(to);
		} else {
			SimpleLogger.logFatal(LoggerType.GENERATE, "Table '" + from + "' was not defined before usage.");
			return(false);
		}
		return(true);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");
		stringBuilder.append("\"options\": {\n");
		stringBuilder.append("\t\"logging\": \"log4j\",\n");

		// now for the generators
		stringBuilder.append("\t\"generators\": [ ");
		String[] splits = generators.split(",");
		for (int i = 0; i < splits.length; i++) {
			String generator = splits[i];
			stringBuilder.append("\"" + generator +  "\"");
			if(i != splits.length - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(" ]\n");

		stringBuilder.append("},\n");
		stringBuilder.append("\"database\": {\n");
		stringBuilder.append("\t\"schema\": \"" + schema + "\",\n");
		stringBuilder.append("\t\"package\": \"" + javaPackage + "\",\n");
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
