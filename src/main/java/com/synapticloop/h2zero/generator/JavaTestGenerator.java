package com.synapticloop.h2zero.generator;

/*
 * Copyright (c) 2023 - 2024 synapticloop.
 * 
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

import com.synapticloop.h2zero.model.Database;
import com.synapticloop.h2zero.model.Options;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

import java.io.File;

/**
 * This class generates all things that are java (i.e. ends in a .java extension)
 * 
 * @author synapticloop
 */
public class JavaTestGenerator extends Generator {

	public JavaTestGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_TESTS)) {
			return;
		}

		if(!options.getDatabase().equals("sqlite3")) {
			throw new RenderException("Generating tests is only available for sqlite3 databases at the moment.");
		}

		try {
      TemplarContext templarContext = getDefaultTemplarContext();
			templarContext.add("options", options);
			templarContext.add("database",database);
			templarContext.add("dbUrl", outFile + options.getOutputTestResources() + "/test.db");
			new File("." + options.getOutputTestResources()).mkdirs();
			generateDatabaseTestBase(templarContext);
			generateFinderTest(templarContext);
			generateDeleterTest(templarContext);
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}
	}

	private void generateDatabaseTestBase(TemplarContext templarContext) throws ParseException, RenderException {
		Parser javaGenerateDatabaseTestBaseParser = getParser("/tests/java-sqlite3-database-test-base.templar");
		String pathname = outFile + options.getOutputTestCode() + database.getPackagePath() + "/test/util/DatabaseSetupTest.java";
		renderToFile(templarContext, javaGenerateDatabaseTestBaseParser, pathname);
	}

	private void generateFinderTest(TemplarContext templarContext) throws ParseException, RenderException {
		Parser javaGenerateDatabaseTestBaseParser = getParser("/tests/java-finder-test.templar");
		String pathname = outFile + options.getOutputTestCode() + database.getPackagePath() + "/test/util/FinderTest.java";
		renderToFile(templarContext, javaGenerateDatabaseTestBaseParser, pathname);
	}

	private void generateDeleterTest(TemplarContext templarContext) throws ParseException, RenderException {
		Parser javaGenerateDatabaseTestBaseParser = getParser("/tests/java-deleter-test.templar");
		String pathname = outFile + options.getOutputTestCode() + database.getPackagePath() + "/test/util/DeleterTest.java";
		renderToFile(templarContext, javaGenerateDatabaseTestBaseParser, pathname);
	}

}
