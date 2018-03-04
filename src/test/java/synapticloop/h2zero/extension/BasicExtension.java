package synapticloop.h2zero.extension;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.validator.BaseValidator;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class BasicExtension extends Extension {

	@Override
	public void generate(JSONObject extensionOptions, Database database, Options options, File outFile, boolean verbose)
			throws RenderException, ParseException {
		logInfo("Generation of an extension.");

	}

	@Override
	public List<BaseValidator> getValidators() {
		return null;
	}
}
