package synapticloop.h2zero.ant.generator;

import java.io.File;
import java.util.List;

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

		SimpleLogger.logInfo(LoggerType.GENERATE_METRICS, "Generating for database '" + database.getSchema() + "'.");

		TemplarContext templarContext = getDefaultTemplarContext();

		Parser javaCreateMetricsServletMunin = getParser("/java-create-metrics-servlet-munin.templar");
		String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/servlet/MuninMetricsServlet.java";
		renderToFile(templarContext, javaCreateMetricsServletMunin, pathname);

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			Parser javaCreateMetricsTable = getParser("/java-create-metrics-table.templar");

			templarContext.add("table", table);

			// first up the database creation script
			pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/metrics/" + table.getJavaClassName() + "Metrics.java";
			renderToFile(templarContext, javaCreateMetricsTable, pathname);
		}
	}

}
