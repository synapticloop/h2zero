package com.synapticloop.h2zero.extension;

import org.json.JSONObject;
import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.validator.BaseValidator;
import com.synapticloop.h2zero.validator.BasicValidator;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<String> getRequiredOutputOptions() {
		return(new ArrayList<>());
	}

	@Override public String getRequiredOutputOptionDescription(String optionName) {
		return "No description here";
	}

	@Override
	public Map<String, String> getDefaultOutputOptions() {
		return(new HashMap<>());
	}

	@Override
	public List<BaseValidator> getValidators() {
		List<BaseValidator> validators = new ArrayList<BaseValidator>();
		validators.add(new BasicValidator());
		return(validators);
	}
}
