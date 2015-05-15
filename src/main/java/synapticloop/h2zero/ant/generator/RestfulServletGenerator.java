package synapticloop.h2zero.ant.generator;

import java.io.File;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;

public class RestfulServletGenerator extends Generator {

	public RestfulServletGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		// TODO Auto-generated method stub

	}

}
