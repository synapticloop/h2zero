package synapticloop.h2zero.ant.generator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;


public class JavaGenerator extends Generator {
	public JavaGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_JAVA)) {
			return;
		}

		Parser javaCreateConstantsParser = getParser("/java-create-constants.templar");
		// The model
		Parser javaCreateModelParser = getParser("/java-create-model.templar");

		// The table actions
		Parser javaCreateFinderParser = getParser("/java-create-finder.templar");
		Parser javaCreateInserterParser = getParser("/java-create-inserter.templar");
		Parser javaCreateCounterParser = getParser("/java-create-counter.templar");
		Parser javaCreateQuestionParser = getParser("/java-create-question.templar");
		Parser javaCreateUpdaterParser = getParser("/java-create-updater.templar");
		Parser javaCreateDeleterParser = getParser("/java-create-deleter.templar");

		// the taglibs
		Parser javaCreateSelectClauseBeanParser = getParser("/java-create-select-clause-bean.templar");

		Parser javaCreateViewModelParser = getParser("/java-create-view-model.templar");
		Parser javaCreateViewFinderParser = getParser("/java-create-view-finder.templar");

		TemplarContext templarContext = getDefaultTemplarContext();

		String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Constants.java";
		renderToFile(templarContext, javaCreateConstantsParser, pathname);

		// now for the tables
		List<Table> tables = database.getTables();
		Iterator<Table> tableIterator = tables.iterator();

		while (tableIterator.hasNext()) {
			Table table = tableIterator.next();
			templarContext.add("table", table);
			SimpleLogger.logInfo(LoggerType.GENERATE_JAVA, "Generating for table '" + table.getName() + "'.");

			// the model
			pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/" + table.getJavaClassName() + ".java";
			renderToFile(templarContext, javaCreateModelParser, pathname);

			// the finder
			pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + table.getJavaClassName() + "Finder.java";
			renderToFile(templarContext, javaCreateFinderParser, pathname);

			// the inserter
			if(!table.getIsConstant()) {
				pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/inserter/" + table.getJavaClassName() + "Inserter.java";
				renderToFile(templarContext, javaCreateInserterParser, pathname);
			}

			// the counters
			pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/counter/" + table.getJavaClassName() + "Counter.java";
			renderToFile(templarContext, javaCreateCounterParser, pathname);

			// the questions - but only if we have some
			if(table.getHasQuestions()) {
				pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/question/" + table.getJavaClassName() + "Question.java";
				renderToFile(templarContext, javaCreateQuestionParser, pathname);
			}

			List<Finder> finders = table.getFinders();
			Iterator<Finder> finderIterator = finders.iterator();;

			while (finderIterator.hasNext()) {
				Finder finder = finderIterator.next();
				templarContext.add("finder", finder);

				// don't forget the beans for the selectClause finders
				if(null != finder.getSelectClause()) {
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/bean/" + finder.getTagName() + "Bean.java";
					renderToFile(templarContext, javaCreateSelectClauseBeanParser, pathname);
				}
			}

			if(!table.getIsConstant()) {
				// the updater
				pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/updater/" + table.getJavaClassName() + "Updater.java";
				renderToFile(templarContext, javaCreateUpdaterParser, pathname);

				// the deleter
				pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/deleter/" + table.getJavaClassName() + "Deleter.java";
				renderToFile(templarContext, javaCreateDeleterParser, pathname);
			}
		}


		// now for the views
		List<View> views = database.getViews();
		Iterator<View> viewsIterator = views.iterator();
		while (viewsIterator.hasNext()) {
			View view = viewsIterator.next();
			templarContext.add("view", view);
			// hack for finder taglibs for views - should be split out
			templarContext.add("table", view);

			pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/view/" + view.getJavaClassName() + ".java";
			renderToFile(templarContext, javaCreateViewModelParser, pathname);

			pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + view.getJavaClassName() + "ViewFinder.java";
			renderToFile(templarContext, javaCreateViewFinderParser, pathname);

			List<Finder> finders = view.getFinders();
			Iterator<Finder> finderIterator = finders.iterator();;

			while (finderIterator.hasNext()) {
				Finder finder = finderIterator.next();
				templarContext.add("finder", finder);
				if(null != finder.getSelectClause()) {
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/bean/" + finder.getTagName() + "Bean.java";
					renderToFile(templarContext, javaCreateSelectClauseBeanParser, pathname);
				}
			}
		}
	}
}
