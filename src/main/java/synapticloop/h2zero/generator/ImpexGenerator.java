package synapticloop.h2zero.generator;

/*
 * Copyright (c) 2023 - 2024 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

import java.io.File;
import java.util.List;

public class ImpexGenerator extends Generator {

	public ImpexGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if (!options.hasGenerator(Options.OPTION_IMPEX)) {
			return;
		}

		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		try {
			generateUtils();
			generateImporter();
			generateExporter();
		} catch (FunctionException ex) {
			throw new RenderException(ex.getMessage(), ex);
		}
	}

	private void generateUtils() throws ParseException, RenderException, FunctionException {
		String pathname;

		TemplarContext templarContext = getDefaultTemplarContext();
		templarContext.add("tables", database.getTables());


		Parser converterParser = getParser("/impex/impex-converter.templar");
		SimpleLogger.logInfo(SimpleLogger.LoggerType.GENERATE_JAVA, "Generating impex converter.");
		pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/impex/ImpexConverter.java";
		renderToFile(templarContext, converterParser, pathname);

		templarContext = getDefaultTemplarContext();
		templarContext.add("tables", database.getTables());

		Parser importerParser = getParser("/impex/importer.templar");
		SimpleLogger.logInfo(SimpleLogger.LoggerType.GENERATE_JAVA, "Generating impex importer.");
		pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/impex/Importer.java";
		renderToFile(templarContext, importerParser, pathname);


		templarContext = getDefaultTemplarContext();
		templarContext.add("tables", database.getTables());

		Parser exporterParser = getParser("/impex/exporter.templar");
		SimpleLogger.logInfo(SimpleLogger.LoggerType.GENERATE_JAVA, "Generating impex importer.");
		pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/impex/Exporter.java";
		renderToFile(templarContext, exporterParser, pathname);

	}

	private void generateImporter() throws ParseException, RenderException, FunctionException {
		Parser exporterParser = getParser("/impex/impex-importer.templar");
		List<Table> tables = database.getTables();

		String pathname;
		for (Table table : tables) {
			TemplarContext templarContext = getDefaultTemplarContext();
			templarContext.add("table", table);

			SimpleLogger.logInfo(SimpleLogger.LoggerType.GENERATE_JAVA, "Generating importer table '" + table.getName() + "'.");

			// the model
			pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/impex/" + table.getJavaClassName() + "Importer.java";
			renderToFile(templarContext, exporterParser, pathname);
		}
	}

	private void generateExporter() throws ParseException, RenderException, FunctionException {
		String pathname;

		Parser exporterParser = getParser("/impex/impex-exporter.templar");
		List<Table> tables = database.getTables();

		for (Table table : tables) {
			TemplarContext templarContext = getDefaultTemplarContext();
			templarContext.add("table", table);
			SimpleLogger.logInfo(SimpleLogger.LoggerType.GENERATE_JAVA, "Generating exporter table '" + table.getName() + "'.");

			// the model
			pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/impex/" + table.getJavaClassName() + "Exporter.java";
			renderToFile(templarContext, exporterParser, pathname);
		}
	}
}
