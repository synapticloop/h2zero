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
import com.synapticloop.h2zero.model.Table;
import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;

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
			new File("." + options.getOutputTestResources()).mkdirs();
			generateDatabaseTestBase(templarContext);

			for(Table table: database.getTables()) {
				templarContext.add("table", table);
				generateFinderTest(templarContext, table);
				generateDeleterTest(templarContext, table);
			}
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}
	}

	private void generateDatabaseTestBase(TemplarContext templarContext) throws ParseException, RenderException {
		Parser javaGenerateDatabaseTestBaseParser = getParser("/java/test/java-database-test-base.templar");
		String pathname = outFile + options.getOutputTestCode() + database.getPackagePath() + "/test/DatabaseSetupTest.java";
		renderToFile(templarContext, javaGenerateDatabaseTestBaseParser, pathname);
	}

	private void generateFinderTest(TemplarContext templarContext, Table table) throws ParseException, RenderException {
		Parser javaGenerateDatabaseTestBaseParser = getParser("/java/test/java-finder-test.templar");
		String pathname = outFile +
				options.getOutputTestCode() +
				database.getPackagePath() +
				"/test/finder/" +
				table.getJavaClassName() +
				"Test.java";
		renderToFile(templarContext, javaGenerateDatabaseTestBaseParser, pathname);
	}

	private void generateDeleterTest(TemplarContext templarContext, Table table) throws ParseException, RenderException {
		Parser javaGenerateDatabaseTestBaseParser = getParser("/java/test/java-deleter-test.templar");
		String pathname = outFile +
				options.getOutputTestCode() +
				database.getPackagePath() +
				"/test/deleter/" +
				table.getJavaClassName() +
				"Test.java";
		renderToFile(templarContext, javaGenerateDatabaseTestBaseParser, pathname);
	}

}
