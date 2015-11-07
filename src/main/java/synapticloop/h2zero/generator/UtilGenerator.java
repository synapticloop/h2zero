package synapticloop.h2zero.generator;

import java.io.File;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;


public class UtilGenerator extends Generator {
	public UtilGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_JAVA)) {
			return;
		}

		Parser javaCreateDatabaseCheckerHelperParser = getParser("/java-create-database-checker-helper.templar");

		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		String pathname = outFile.getAbsolutePath() + options.getOutputJava() + "/" + database.getPackagePath() + "/util/DatabaseCheckerHelper.java";
		SimpleLogger.logInfo(LoggerType.GENERATE_JAVA_UTIL, "Generating for 'DatabaseCheckerHelper'.");
		renderToFile(templarContext, javaCreateDatabaseCheckerHelperParser, pathname);
	}
}
