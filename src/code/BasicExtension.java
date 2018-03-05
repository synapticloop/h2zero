package synapticloop.h2zero.extension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.validator.BaseValidator;
import synapticloop.h2zero.validator.BasicValidator;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class BasicExtension extends Extension {

	@Override
	public void generate(JSONObject extensionOptions, Database database, Options options, File outFile, boolean verbose)
			throws RenderException, ParseException {

		// logging
		logInfo("Log an information method.");
		logDebug("Log a debug message.");
		logError("Log an error message.");
		logFatal("Log a fatal message.");

		// getting the context
		TemplarContext templarContext = getDefaultTemplarContext(extensionOptions, database, options);

		// getting the templar file
		Parser parser = getParser("/path-and-name-of-template.templar", verbose);

		// options as to which output directory is required
		String outputBuild = options.getOutputBuild(); // goes to the build directory - for reports and statistics (e.g. build)
		String outputCode = options.getOutputCode(); // goes to the code directory (e.g.src/main/java)
		String outputResources = options.getOutputResources(); // goes to the resources directory (e.g. src/main/resources/)

		// render out the file
		renderToFile(templarContext, parser, "path/to/output/file.extension", verbose);
	}

	@Override
	public List<BaseValidator> getValidators() {
		List<BaseValidator> validators = new ArrayList<BaseValidator>();
		validators.add(new BasicValidator());
		return(validators);
	}
}
