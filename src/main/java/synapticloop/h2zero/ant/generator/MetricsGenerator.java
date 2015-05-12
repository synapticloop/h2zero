package synapticloop.h2zero.ant.generator;

import java.io.File;
import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class MetricsGenerator extends Generator {

	public MetricsGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	public void generate() throws RenderException, ParseException {
		if(!options.getMetrics()) {
			return;
		}

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			Parser javaCreateMetricsRegistry = getParser("/java-create-metrics-table.templar");

			TemplarContext templarContext = getDefaultTemplarContext();
			templarContext.add("table", table);

			SimpleLogger.logInfo(LoggerType.GENERATE_METRICS, "Generating for database '" + database.getSchema() + "'.");

			// first up the database creation script
			String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/metrics/" + table.getJavaClassName() + "Metrics.java";
			renderToFile(templarContext, javaCreateMetricsRegistry, pathname);
		}
	}

}
