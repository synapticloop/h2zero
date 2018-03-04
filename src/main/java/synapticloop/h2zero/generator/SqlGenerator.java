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

/**
 * The SQL Generator generates the database schema to the create-database.sql script.
 * 
 * @author synapticloop
 *
 */
public class SqlGenerator extends Generator {

	public SqlGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_SQL)) {
			return;
		}

		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		String databaseType = options.getDatabase();
		Parser sqlCreateDatabaseTypeParser = getParser("/sql-create-database-" + databaseType+ ".templar");

		SimpleLogger.logInfo(LoggerType.GENERATE_SQL, "Generating for database '" + database.getSchema() + "', of type '" + databaseType + ".");

		// first up the database creation script
		String pathname = outFile.getAbsolutePath() + options.getOutputResources() + "/create-database-" + databaseType + ".sql";
		renderToFile(templarContext, sqlCreateDatabaseTypeParser, pathname);
	}

}
