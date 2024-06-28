package com.synapticloop.h2zero.generator.generator;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;

import java.io.File;

public class ReportGenerator extends Generator {
	public ReportGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_REPORTS)) {
			return;
		}

		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		generateDatabaseSchemaReport(templarContext);
	}

	private void generateDatabaseSchemaReport(TemplarContext templarContext) throws ParseException, RenderException {
		Parser mdReportCreateDatabaseSchema = getParser("/java/util/md-report-create-database-schema.templar");
		String pathname = outFile + options.getOutputBuild() + "/reports/report-database-" + database.getSchema() + "-" + options.getDatabase() + ".md";
		renderToFile(templarContext, mdReportCreateDatabaseSchema, pathname);
	}

}
