package synapticloop.h2zero.generator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

/**
 * This generator will generate all of the java classes for the tag libraries and the tag library descriptor file
 * 
 * <ul>
 *   <li>Finders</li>
 *   <li>Counters</li>
 *   <li>Questions</li>
 *   <li>FindByPrimaryKey</li>
 *   <li>FindAll</li>
 *   <li>CountAll</li>
 * </ul>
 * 
 * @author synapticloop
 *
 */
public class TaglibGenerator extends Generator {

	public TaglibGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_TAGLIB)) {
			return;
		}

		Parser javaCreateTaglibFinderParser = getParser("/java-create-taglib-finder.templar");
		Parser javaCreateTaglibCounterParser = getParser("/java-create-taglib-counter.templar");
		Parser javaCreateTaglibQuestionParser = getParser("/java-create-taglib-question.templar");
		Parser javaCreateTaglibFinderFindByPrimaryKeyParser = getParser("/java-create-taglib-finder-find-by-primary-key.templar");
		Parser javaCreateTaglibFinderFindAllParser = getParser("/java-create-taglib-finder-find-all.templar");
		Parser javaCreateTaglibCounterCountAllParser = getParser("/java-create-taglib-counter-count-all.templar");

		// the TLD
		Parser tldCreateLibraryParser = getParser("/tld-create-library.templar");

		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		// now for the tables
		List<Table> tables = database.getTables();
		Iterator<Table> tableIterator = tables.iterator();

		while (tableIterator.hasNext()) {
			Table table = tableIterator.next();
			templarContext.add("table", table);
			SimpleLogger.logInfo(LoggerType.GENERATE_TAGLIB, "Generating for table '" + table.getName() + "'.");

			List<Finder> finders = table.getFinders();
			Iterator<Finder> finderIterator = finders.iterator();

			while (finderIterator.hasNext()) {
				Finder finder = finderIterator.next();
				templarContext.add("finder", finder);

				String pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + finder.getTagName() + "Tag.java";
				renderToFile(templarContext, javaCreateTaglibFinderParser, pathname);
			}

			List<Counter> counters = table.getCounters();
			Iterator<Counter> counterIterator = counters.iterator();

			while(counterIterator.hasNext()) {
				Counter counter = counterIterator.next();
				templarContext.add("counter", counter);
				String pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + counter.getTagName() + "Tag.java";
				renderToFile(templarContext, javaCreateTaglibCounterParser, pathname);
			}

			List<Question> questions = table.getQuestions();
			Iterator<Question> questionIterator = questions.iterator();

			while(questionIterator.hasNext()) {
				Question question = questionIterator.next();
				templarContext.add("question", question);
				String pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + question.getTagName() + "Tag.java";
				renderToFile(templarContext, javaCreateTaglibQuestionParser, pathname);
			}


			// the extra 'missing' finders
			String pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + "FindByPrimaryKeyTag.java";
			renderToFile(templarContext, javaCreateTaglibFinderFindByPrimaryKeyParser, pathname);

			pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + "FindAllTag.java";
			renderToFile(templarContext, javaCreateTaglibFinderFindAllParser, pathname);

			pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/CountAllTag.java";
			renderToFile(templarContext, javaCreateTaglibCounterCountAllParser, pathname);
		}

		// now for the views
		List<View> views = database.getViews();
		Iterator<View> viewsIterator = views.iterator();
		while (viewsIterator.hasNext()) {
			View view = viewsIterator.next();
			templarContext.add("view", view);
			// hack for finder taglibs for views - should be split out
			templarContext.add("table", view);

			List<Finder> finders = view.getFinders();
			Iterator<Finder> finderIterator = finders.iterator();

			while (finderIterator.hasNext()) {
				Finder finder = finderIterator.next();
				templarContext.add("finder", finder);

				String pathname = outFile.getAbsolutePath() + options.getOutputJava() + database.getPackagePath() + "/taglib/" + view.getJavaFieldName() + "/" + finder.getTagName() + "Tag.java";
				renderToFile(templarContext, javaCreateTaglibFinderParser, pathname);
			}
		}


		// the finder tld
		String pathname = outFile.getAbsolutePath() + options.getOutputWebapp() + "/WEB-INF/tld/" + database.getSchema() + ".tld";
		renderToFile(templarContext, tldCreateLibraryParser, pathname);
	}
}
