package synapticloop.h2zero.generator;

/*
 * Copyright (c) 2013-2020 synapticloop.
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
