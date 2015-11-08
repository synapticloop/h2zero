package synapticloop.h2zero.generator;

import java.io.File;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class ReportGenerator extends Generator {

	public ReportGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_REPORT)) {
			return;
		}
		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		generateDatabaseReport(templarContext);
	}

	private void generateDatabaseReport(TemplarContext templarContext) throws ParseException, RenderException {
		Parser reportCreateDatabaseMd = getParser("/report-create-database-md.templar");
		String pathname = outFile + options.getOutputReports() + "/report-database-" + database.getSchema() + "-" + options.getDatabase() + ".md";
		renderToFile(templarContext, reportCreateDatabaseMd, pathname);
	}

}
