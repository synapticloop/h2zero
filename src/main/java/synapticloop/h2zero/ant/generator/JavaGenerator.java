package synapticloop.h2zero.ant.generator;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;

public class JavaGenerator implements Generator {
	private Options options;
	private Database database;

	public JavaGenerator(Options options, Database database) {
		this.options = options;
		this.database = database;
	}

	public void generate() {
		
	}
}
