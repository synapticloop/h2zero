package com.synapticloop.h2zero.generator;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.model.*;
import com.synapticloop.h2zero.util.SimpleLogger;
import com.synapticloop.h2zero.util.SimpleLogger.LoggerType;
import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;

import java.io.File;
import java.util.List;

/**
 * This class generates all things that are java (i.e. ends in a .java extension)
 * 
 * @author synapticloop
 */
public class JavaGenerator extends Generator {

	public JavaGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_JAVA)) {
			return;
		}

		try {
      TemplarContext templarContext = getDefaultTemplarContext();
      generateTables(templarContext);
      generateViews(templarContext);
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

	}

	private void generateTables(TemplarContext templarContext) throws ParseException, RenderException {
		Parser javaCreateConstantsParser = getParser("/java-create-constants.templar");
		Parser javaCreateConnectionManagerInitialiser = getParser("/java/java-create-connection-manager-initialiser.templar");
		Parser javaCreateConnectionManagerInitialiserOverride = getParser("/java/java-create-connection-manager-initialiser-override.templar");

		// The model
		Parser javaCreateModelParser = getParser("/java-create-model.templar");
		Parser javaCreateModelStatisticsParser = getParser("/java-create-model-statistics.templar");

		// The table actions
		Parser javaCreateFinderParser = getParser("/java-create-finder.templar");
		Parser javaCreateInserterParser = getParser("/java-create-inserter.templar");

		Parser javaCreateCounterParser = getParser("/java/counter/java-create-counter.templar");
		Parser javaCreateQuestionParser = getParser("/java/question/java-create-question.templar");
		Parser javaCreateDeleterParser = getParser("/java/deleter/java-create-deleter.templar");

		Parser javaCreateUpdaterParser = getParser("/java-create-updater.templar");
		Parser javaCreateUpserterParser = getParser("/java-create-upserter.templar");

		// the select clause bean
		Parser javaCreateSelectClauseBeanParser = getParser("/java-create-select-clause-bean.templar");

		String pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/model/util/Constants.java";
		renderToFile(templarContext, javaCreateConstantsParser, pathname);

		pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/ConnectionManagerInitialiser.java";
		renderToFile(templarContext, javaCreateConnectionManagerInitialiser, pathname);

		pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/ConnectionManagerInitialiserOverride.java";
		File testFile = new File(pathname);
		if(testFile.exists()) {
			if(verbose) {
				SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "__NOT__ rendering to '" + pathname + "', as the file already exists");
			}
		} else {
			renderToFile(templarContext, javaCreateConnectionManagerInitialiserOverride, pathname);
		}

		pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/model/util/Statistics.java";
		renderToFile(templarContext, javaCreateModelStatisticsParser, pathname);

		// now for the tables
		List<Table> tables = database.getTables();

    for (Table table : tables) {
      templarContext.add("table", table);
      SimpleLogger.logInfo(LoggerType.GENERATE_JAVA, "Generating for table '" + table.getName() + "'.");

      // the model
      pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/model/" + table.getJavaClassName() + ".java";
      renderToFile(templarContext, javaCreateModelParser, pathname);

      // the finder
      pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/finder/" + table.getJavaClassName() + "Finder.java";
      renderToFile(templarContext, javaCreateFinderParser, pathname);

      if (!table.getIsConstant()) {
        // the inserter
        pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/inserter/" + table.getJavaClassName() + "Inserter.java";
        renderToFile(templarContext, javaCreateInserterParser, pathname);

        // the upserter
        pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/upserter/" + table.getJavaClassName() + "Upserter.java";
        renderToFile(templarContext, javaCreateUpserterParser, pathname);
      }


      // the counters
      pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/counter/" + table.getJavaClassName() + "Counter.java";
      renderToFile(templarContext, javaCreateCounterParser, pathname);

      // the questions - we always have an internal question
      pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/question/" + table.getJavaClassName() + "Question.java";
      renderToFile(templarContext, javaCreateQuestionParser, pathname);

      List<Finder> finders = table.getFinders();

      for (Finder finder : finders) {
        templarContext.add("finder", finder);

        // don't forget the beans for the selectClause finders
        if (null != finder.getSelectClause()) {
          pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/bean/" + table.getJavaClassName() + finder.getTagName() + "Bean.java";
          renderToFile(templarContext, javaCreateSelectClauseBeanParser, pathname);
        }
      }

      if (!table.getIsConstant()) {
        // the updater
        pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/updater/" + table.getJavaClassName() + "Updater.java";
        renderToFile(templarContext, javaCreateUpdaterParser, pathname);

        // the deleter
        pathname = outFile + options.getOutputCode() + database.getPackagePath() + "/deleter/" + table.getJavaClassName() + "Deleter.java";
        renderToFile(templarContext, javaCreateDeleterParser, pathname);
      }
    }
	}

	private void generateViews(TemplarContext templarContext) throws ParseException, RenderException {
		Parser javaCreateViewModelParser = getParser("/java-create-view-model.templar");
		Parser javaCreateViewFinderParser = getParser("/java-create-view-finder.templar");
		Parser javaCreateSelectClauseBeanParser = getParser("/java-create-select-clause-bean.templar");

		Parser javaCreateViewCounterParser = getParser("/java/counter/java-create-view-counter.templar");
		Parser javaCreateViewQuestionParser = getParser("/java/question/java-create-view-question.templar");

		String pathname;

		// now for the views
		List<View> views = database.getViews();
    for (View view : views) {
      templarContext.add("view", view);

      // hack for finder taglibs for views - should be split out
      templarContext.add("table", view);

      String pathPrefix = outFile + options.getOutputCode() + database.getPackagePath();
      String viewJavaClassName = view.getJavaClassName();

      pathname = pathPrefix + "/view/" + viewJavaClassName + ".java";
      renderToFile(templarContext, javaCreateViewModelParser, pathname);

      pathname = pathPrefix + "/finder/" + viewJavaClassName + "ViewFinder.java";
      renderToFile(templarContext, javaCreateViewFinderParser, pathname);

      pathname = pathPrefix + "/counter/" + viewJavaClassName + "ViewCounter.java";
      renderToFile(templarContext, javaCreateViewCounterParser, pathname);

      pathname = pathPrefix + "/question/" + viewJavaClassName + "ViewQuestion.java";
      renderToFile(templarContext, javaCreateViewQuestionParser, pathname);

      List<Finder> finders = view.getFinders();
      for (Finder finder : finders) {
        templarContext.add("finder", finder);
        if (null != finder.getSelectClause()) {
          pathname = pathPrefix + "/bean/" + view.getJavaClassName() + finder.getTagName() + "Bean.java";
          renderToFile(templarContext, javaCreateSelectClauseBeanParser, pathname);
        }
      }
    }
	}
}
