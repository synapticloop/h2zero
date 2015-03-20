package synapticloop.h2zero.ant;

/*
 * Copyright (c) 2012-2015 synapticloop.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.Counter;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Question;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.model.form.Form;
import synapticloop.h2zero.util.FileUtils;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class H2ZeroTask extends Task {
	private String in = null;
	private String outDir = null;
	private int numFiles = 0;
	private int numTables = 0;

	private boolean verbose = true; // whether tyo be verbose with the logging

	private HashMap<String, Integer> numFilesHashMap = new HashMap<String, Integer>();


	@Override
	public void execute() throws BuildException {
		if(null == outDir || null == in) {
			getProject().log("Both attributes 'in' and 'outDir' are required, exiting...", Project.MSG_ERR);
			return;
		}

		File h2zeroFile = new File(in);
		if(!h2zeroFile.exists()|| !h2zeroFile.canRead()) {
			getProject().log("h2zero file 'in' does not exist, or is not readable, exiting...", Project.MSG_ERR);
			return;
		}

		File outFile = new File(outDir);
		if(!outFile.exists() || !outFile.isDirectory()) {
			getProject().log("'outDir' does not exists or is not a directory, exiting...", Project.MSG_ERR);
			return;
		}

		SimpleLogger.logInfo(LoggerType.OPTIONS, "In file: " + in);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "Out dir: " + outDir);

		// otherwise we are good to go
		H2ZeroParser h2zeroParser = null;
		try {
			h2zeroParser = new H2ZeroParser(h2zeroFile);

			if(verbose) {
				SimpleLogger.logInfo(LoggerType.PARSE, "Found database '" + h2zeroParser.getDatabase().getSchema() + "'.");
				ArrayList<Table> tableDebug = h2zeroParser.getDatabase().getTables();
				for (Table table : tableDebug) {
					SimpleLogger.logInfo(LoggerType.PARSE, "Found table '" + table.getName() + 
							"' ( " + table.getFields().size() + " fields, " + 
							table.getFinders().size() + " finders, " + 
							table.getDeleters().size() + " deleters, " + 
							table.getUpdaters().size() + " updaters )");
				}
			}

			TemplarConfiguration templarConfiguration = new TemplarConfiguration();
			templarConfiguration.setExplicitNewLines(true);
			templarConfiguration.setExplicitTabs(true);

			TemplarContext templarContext = new TemplarContext(templarConfiguration);

			Database database = h2zeroParser.getDatabase();
			numTables = database.getTables().size();

			templarContext.add("database", database);

			Options options = h2zeroParser.getOptions();
			templarContext.add("options", options);

			if(!options.hasGenerators()) {
				getProject().log("FATAL: You have not defined an 'options' section, and therefore no generators will be executed. Exiting...");
				return;
			}

//			new JavaGenerator(options, database).generate();

			// The model
			Parser javaCreateModelParser = getParser("/java-create-model.templar");

			// The table actions
			Parser javaCreateFinderParser = getParser("/java-create-finder.templar");
			Parser javaCreateInserterParser = getParser("/java-create-inserter.templar");
			Parser javaCreateCounterParser = getParser("/java-create-counter.templar");
			Parser javaCreateQuestionParser = getParser("/java-create-question.templar");
			Parser javaCreateUpdaterParser = getParser("/java-create-updater.templar");
			Parser javaCreateDeleterParser = getParser("/java-create-deleter.templar");

			Parser javaCreateDefaultFormBeanParser = getParser("/java-create-default-form-bean-create.templar");

			// the taglibs
			Parser javaCreateTaglibFinderParser = getParser("/java-create-taglib-finder.templar");
			Parser javaCreateSelectClauseBeanParser = getParser("/java-create-select-clause-bean.templar");
			Parser javaCreateTaglibCounterParser = getParser("/java-create-taglib-counter.templar");
			Parser javaCreateTaglibQuestionParser = getParser("/java-create-taglib-question.templar");
			Parser javaCreateTaglibFinderFindByPrimaryKeyParser = getParser("/java-create-taglib-finder-find-by-primary-key.templar");
			Parser javaCreatetaglibFinderFindAllParser = getParser("/java-create-taglib-finder-find-all.templar");
			Parser javaCreateTaglibCounterCountAllParser = getParser("/java-create-taglib-counter-count-all.templar");
			Parser javaCreateViewModelParser = getParser("/java-create-view-model.templar");
			Parser javaCreateViewFinderParser = getParser("/java-create-view-finder.templar");
			Parser javaCreateFormBeanParser = getParser("/java-create-form-bean.templar");

			// the TLD
			Parser tldCreateLibraryParser = getParser("/tld-create-library.templar");

			// the JSPs
			Parser jspCreateFinderParser = getParser("/jsp-create-finder.templar");
			Parser jspCreateIndexParser = getParser("/jsp-create-index.templar");
			Parser jspCreateIndexTableParser = getParser("/jsp-create-index-table.templar");
			Parser jspCreateFindAllParser = getParser("/jsp-create-find-all.templar");

			// the CSS
			Parser cssCreateAllParser = getParser("/css-create-all.templar");

			Parser javaCreateStatisticsParser = getParser("/java-create-statistics.templar");
			Parser javaCreateConstantsParser = getParser("/java-create-constants.templar");

//			new SqlGenerator(templarContext).generate();
			// the sql generator
			Parser sqlCreateDatabaseParser = getParser("/sql-create-database.templar");

			if(options.hasGenerator(Options.OPTION_SQL)) {
				// first up the database creation script
				String pathname = outFile + "/src/main/sql/create-database.sql";
				renderToFile(templarContext, sqlCreateDatabaseParser, pathname);
			}

			if(options.hasGenerator(Options.OPTION_JAVA)) {
				String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Constants.java";
				renderToFile(templarContext, javaCreateConstantsParser, pathname);
			}

			// now for the tables
			ArrayList<Table> tables = database.getTables();
			Iterator<Table> tableIterator = tables.iterator();

			while (tableIterator.hasNext()) {
				Table table = tableIterator.next();
				templarContext.add("table", table);
				SimpleLogger.logInfo(LoggerType.GENERATE, "Generating for table '" + table.getName() + "'.");

				
				if(options.hasGenerator(Options.OPTION_JAVA)) {
					// the model
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/" + table.getJavaClassName() + ".java";
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
				}

				if(options.hasGenerator(Options.OPTION_FORMBEANS)) {
					// the default form beans
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/form/auto/" + table.getJavaClassName() + "CreateFormBean.java";
					renderToFile(templarContext, javaCreateDefaultFormBeanParser, pathname);
				}

				ArrayList<Finder> finders = table.getFinders();
				Iterator<Finder> finderIterator = finders.iterator();;

				while (finderIterator.hasNext()) {
					Finder finder = finderIterator.next();
					templarContext.add("finder", finder);

					if(options.hasGenerator(Options.OPTION_TAGLIB)) {
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + finder.getFinderTagName() + "Tag.java";
						renderToFile(templarContext, javaCreateTaglibFinderParser, pathname);
					}

					if(options.hasGenerator(Options.OPTION_JAVA)) {
						// don't forget the beans for the selectClause finders
						if(null != finder.getSelectClause()) {
							String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/bean/" + finder.getFinderTagName() + "Bean.java";
							renderToFile(templarContext, javaCreateSelectClauseBeanParser, pathname);
						}
					}

					if(options.hasGenerator(Options.OPTION_ADMINPAGES)) {
						// now for the jsp finder pages
						String pathname = outFile + "/src/main/webapps/admin/" + table.getName() + "-" + finder.getName() + ".html";
						renderToFile(templarContext, jspCreateFinderParser, pathname);
					}
				}

				if(options.hasGenerator(Options.OPTION_TAGLIB)) {
					ArrayList<Counter> counters = table.getCounters();
					Iterator<Counter> counterIterator = counters.iterator();

					while(counterIterator.hasNext()) {
						Counter counter = counterIterator.next();
						templarContext.add("counter", counter);
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + counter.getTagName() + "Tag.java";
						renderToFile(templarContext, javaCreateTaglibCounterParser, pathname);
					}

					ArrayList<Question> questions = table.getQuestions();
					Iterator<Question> questionIterator = questions.iterator();

					while(questionIterator.hasNext()) {
						Question question = questionIterator.next();
						templarContext.add("question", question);
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + question.getTagName() + "Tag.java";
						renderToFile(templarContext, javaCreateTaglibQuestionParser, pathname);
					}
				}

				if(options.hasGenerator(Options.OPTION_TAGLIB)) {
					// the extra 'missing' finders
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + "FindByPrimaryKeyTag.java";
					renderToFile(templarContext, javaCreateTaglibFinderFindByPrimaryKeyParser, pathname);

					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + "FindAllTag.java";
					renderToFile(templarContext, javaCreatetaglibFinderFindAllParser, pathname);

					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/CountAllTag.java";
					renderToFile(templarContext, javaCreateTaglibCounterCountAllParser, pathname);

				}

				if(options.hasGenerator(Options.OPTION_JAVA)) {
					// the updater
					if(!table.getIsConstant()) {
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/updater/" + table.getJavaClassName() + "Updater.java";
						renderToFile(templarContext, javaCreateUpdaterParser, pathname);
					}

					// the deleter
					if(!table.getIsConstant()) {
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/deleter/" + table.getJavaClassName() + "Deleter.java";
						renderToFile(templarContext, javaCreateDeleterParser, pathname);
					}
				}

				if(options.hasGenerator(Options.OPTION_ADMINPAGES)) {
					// need to copy over the favicons
					// make sure that the directories are created...
					new File(outFile + "/src/main/webapps/admin/static/img/").mkdirs();
					FileUtils.copyResourceToFile("/favicon.png", outFile + "/src/main/webapps/admin/static/img/favicon.png");
					FileUtils.copyResourceToFile("/favicon.ico", outFile + "/src/main/webapps/admin/static/img/favicon.ico");
					// each jsp index page for each table
					String pathname = outFile + "/src/main/webapps/admin/" + table.getName() + ".html";
					renderToFile(templarContext, jspCreateIndexTableParser, pathname);

					// The jsp findAll page
					pathname = outFile + "/src/main/webapps/admin/" + table.getName() + "-findAll.html";
					renderToFile(templarContext, jspCreateFindAllParser, pathname);
				}
			}

			// now for the views
			ArrayList<View> views = database.getViews();
			Iterator<View> viewsIterator = views.iterator();
			while (viewsIterator.hasNext()) {
				View view = viewsIterator.next();
				templarContext.add("view", view);

				if(options.hasGenerator(Options.OPTION_JAVA)) {
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/view/" + view.getJavaClassName() + ".java";
					renderToFile(templarContext, javaCreateViewModelParser, pathname);

					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + view.getJavaClassName() + "ViewFinder.java";
					renderToFile(templarContext, javaCreateViewFinderParser, pathname);
				}
			}


			if(options.hasGenerator(Options.OPTION_TAGLIB)) {
				// the finder tld
				String pathname = outFile + "/src/main/webapps/WEB-INF/tld/" + database.getSchema() + ".tld";
				renderToFile(templarContext, tldCreateLibraryParser, pathname);
			}

			// now for the forms
			ArrayList<Form> forms = database.getForms();
			Iterator<Form> formsIterator = forms.iterator();

			while (formsIterator.hasNext()) {
				Form form = formsIterator.next();
				templarContext.add("form", form);

				if(options.hasGenerator(Options.OPTION_FORMBEANS)) {
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/form/" + form.getName() + "Bean.java";
					renderToFile(templarContext, javaCreateFormBeanParser, pathname);
				}
			}

			if(options.hasGenerator(Options.OPTION_ADMINPAGES)) {
				// now for the jsp index page
				String pathname = outFile + "/src/main/webapps/admin/index.html";
				renderToFile(templarContext, jspCreateIndexParser, pathname);

				// now for the CSS
				pathname = outFile + "/src/main/webapps/admin/static/css/style.css";
				renderToFile(templarContext, cssCreateAllParser, pathname);
			}

			if(options.hasGenerator(Options.OPTION_JAVA)) {
				// now for the statistics
				if(options.getStatistics()) {
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Statistics.java";
					renderToFile(templarContext, javaCreateStatisticsParser, pathname);
				}
			}
		} catch (H2ZeroParseException shepex) {
			SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, "H2ZeroParseException: There was an error parsing the '" + h2zeroFile.getName() + "'.");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, "The message was:");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, "  " + shepex.getMessage());
			return;
		} catch (synapticloop.templar.exception.ParseException stepex) {
			stepex.printStackTrace();
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now that we are done - print out the overview
		if(null != h2zeroParser) {
			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("h2zero just generated code for %d tables!", numTables));
			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("h2zero just saved you typing %d files!  Messages [ warn: %d, fatal: %d ]", numFiles, h2zeroParser.getNumWarn(), h2zeroParser.getNumFatal()));
			Iterator<String> iterator = numFilesHashMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Integer count = numFilesHashMap.get(key);
				String multiple = "s";
				if(count == 1) {
					multiple = "";
				}
				SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("     %d %s file%s", count, key, multiple));
			}
		}

	}

	/**
	 * A convenience method to keep track of the number of files rendered
	 * 
	 * @param templarContext
	 * @param templarParser
	 * @param pathname
	 * @throws RenderException
	 */
	private void renderToFile(TemplarContext templarContext, Parser templarParser, String pathname) throws RenderException {
		if(verbose) {
			SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
		}

		numFiles++;
		int lastIndexOf = pathname.lastIndexOf(".");
		if(lastIndexOf != -1) {
			String key = pathname.substring(lastIndexOf);
			int count = 0;
			if(numFilesHashMap.containsKey(key)) {
				count = numFilesHashMap.get(key);
			}
			count++;
			numFilesHashMap.put(key, count);
		}

		templarParser.renderToFile(templarContext, new File(pathname));
	}

	private Parser getParser(String templarTemplateFile) throws ParseException {
		if(verbose) {
			SimpleLogger.logInfo(LoggerType.TEMPLAR_LOAD, "Loading templar template '" + templarTemplateFile + "'.");
		}
		return(new Parser(this.getClass().getResourceAsStream(templarTemplateFile)));
	}

	public void setIn(String in) { this.in = in; }
	public String getIn() { return in; }
	public void setOutDir(String outDir) { this.outDir = outDir; }
	public String getOutDir() { return outDir; }
	public boolean getVerbose() { return verbose; }
	public void setVerbose(boolean verbose) { this.verbose = verbose; }
}
