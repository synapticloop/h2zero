package synapticloop.h2zero.ant;

/*
 * Copyright (c) 2012-2013 synapticloop.
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

		SimpleLogger.logInfo(LoggerType.H2ZERO_OPTIONS, "In file: " + in);
		SimpleLogger.logInfo(LoggerType.H2ZERO_OPTIONS, "Out dir: " + outDir);

		// otherwise we are good to go
		H2ZeroParser h2zeroParser = null;
		try {
			h2zeroParser = new H2ZeroParser(h2zeroFile);

			SimpleLogger.logInfo(LoggerType.H2ZERO_PARSE, "Found database '" + h2zeroParser.getDatabase().getSchema() + "'.");
			ArrayList<Table> tableDebug = h2zeroParser.getDatabase().getTables();
			for (Table table : tableDebug) {
				SimpleLogger.logInfo(LoggerType.H2ZERO_PARSE, "Found table '" + table.getName() + "'.");
			}

			TemplarConfiguration templarConfiguration = new TemplarConfiguration();
			templarConfiguration.setExplicitNewLines(true);
			templarConfiguration.setExplicitTabs(true);

			TemplarContext templarContext = new TemplarContext(templarConfiguration);

			Database database = h2zeroParser.getDatabase();
			templarContext.add("database", database);

			Options options = h2zeroParser.getOptions();
			templarContext.add("options", options);
			
			if(!options.hasGenerators()) {
				getProject().log("FATAL: You have not defined an 'options' section, and therefore no generators will be executed. Exiting...");
				return;
			}

			Parser templarParser = null;

			if(options.hasGenerator("sql")) {
				// first up the database creation script
				templarParser = getParser("/sql-create-database.templar");
				String pathname = outFile + "/src/main/sql/create-database.sql";
				SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
				renderToFile(templarContext, templarParser, pathname);
			}

			if(options.hasGenerator("java")) {
				templarParser = getParser("/java-create-constants.templar");
				String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Constants.java";
				SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
				renderToFile(templarContext, templarParser, pathname);
			}

			// now for the tables
			ArrayList<Table> tables = database.getTables();
			Iterator<Table> tableIterator = tables.iterator();
			while (tableIterator.hasNext()) {
				Table table = tableIterator.next();
				templarContext.add("table", table);
				SimpleLogger.logInfo(LoggerType.H2ZERO, "Generating for table '" + table.getName() + "'.");

				if(options.hasGenerator("java")) {
					// the model
					templarParser = getParser("/java-create-model.templar");
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/" + table.getJavaClassName() + ".java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					// the finder
					templarParser = getParser("/java-create-finder.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + table.getJavaClassName() + "Finder.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					// the default form beans
					templarParser = getParser("/java-create-default-form-bean-create.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/form/auto/" + table.getJavaClassName() + "CreateFormBean.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					// the counters
					templarParser = getParser("/java-create-counter.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/counter/" + table.getJavaClassName() + "Counter.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);
				}

				// the finder tag libraries
				ArrayList<Finder> finders = table.getFinders();
				Iterator<Finder> finderIterator = finders.iterator();;

				while (finderIterator.hasNext()) {
					Finder finder = finderIterator.next();
					templarContext.add("finder", finder);

					if(options.hasGenerator("taglib")) {
						templarParser = getParser("/java-create-taglib-finder.templar");
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + finder.getFinderTagName() + "Tag.java";
						SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
						renderToFile(templarContext, templarParser, pathname);
					}

					if(options.hasGenerator("java")) {
						// don't forget the beans for the selectClause finders
						if(null != finder.getSelectClause()) {
							templarParser = getParser("/java-create-select-clause-bean.templar");
							String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/bean/" + finder.getFinderTagName() + "Bean.java";
							SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
							renderToFile(templarContext, templarParser, pathname);
						}
					}


					if(options.hasGenerator("adminpages")) {
						// now for the jsp finder pages
						templarParser = getParser("/jsp-create-finder.templar");
						String pathname = outFile + "/src/main/webapps/admin/" + table.getName() + "-" + finder.getName() + ".html";
						SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
						renderToFile(templarContext, templarParser, pathname);
					}
				}

				ArrayList<Counter> counters = table.getCounters();
				Iterator<Counter> counterIterator = counters.iterator();

				while(counterIterator.hasNext()) {
					Counter counter = counterIterator.next();
					templarContext.add("counter", counter);
					if(options.hasGenerator("taglib")) {
						templarParser = getParser("/java-create-taglib-counter.templar");
						String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + counter.getCounterTagName() + "Tag.java";
						SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
						renderToFile(templarContext, templarParser, pathname);
					}
				}

				if(options.hasGenerator("taglib")) {
					// the extra 'missing' finders
					templarParser = getParser("/java-create-taglib-finder-find-by-primary-key.templar");
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + "FindByPrimaryKeyTag.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					templarParser = getParser("/java-create-taglib-finder-find-all.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/" + "FindAllTag.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					templarParser = getParser("/java-create-taglib-counter-count-all.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaFieldName() + "/CountAllTag.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

				}

				if(options.hasGenerator("java")) {
					// the updater
					templarParser = getParser("/java-create-updater.templar");
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/updater/" + table.getJavaClassName() + "Updater.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					// the deleter
					templarParser = getParser("/java-create-deleter.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/deleter/" + table.getJavaClassName() + "Deleter.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);
				}

				if(options.hasGenerator("adminpages")) {
					// need to copy over the favicons
					// make sure that the directories are created...
					new File(outFile + "/src/main/webapps/admin/static/img/").mkdirs();
					FileUtils.copyResourceToFile("/favicon.png", outFile + "/src/main/webapps/admin/static/img/favicon.png");
					FileUtils.copyResourceToFile("/favicon.ico", outFile + "/src/main/webapps/admin/static/img/favicon.ico");
					// each jsp index page for each table
					templarParser = getParser("/jsp-create-index-table.templar");
					String pathname = outFile + "/src/main/webapps/admin/" + table.getName() + ".html";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					// The jsp findAll page
					templarParser = getParser("/jsp-create-find-all.templar");
					pathname = outFile + "/src/main/webapps/admin/" + table.getName() + "-findAll.html";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);
				}
			}

			// now for the views
			ArrayList<View> views = database.getViews();
			Iterator<View> viewsIterator = views.iterator();
			while (viewsIterator.hasNext()) {
				View view = viewsIterator.next();
				templarContext.add("view", view);

				if(options.hasGenerator("java")) {
					templarParser = getParser("/java-create-view-model.templar");
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/view/" + view.getJavaClassName() + ".java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);

					templarParser = getParser("/java-create-view-finder.templar");
					pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + view.getJavaClassName() + "ViewFinder.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);
				}
			}


			if(options.hasGenerator("taglib")) {
				// the finder tld
				templarParser = getParser("/tld-create-library.templar");
				String pathname = outFile + "/src/main/webapps/WEB-INF/tld/" + database.getSchema() + ".tld";
				SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
				renderToFile(templarContext, templarParser, pathname);
			}

			// now for the forms
			ArrayList<Form> forms = database.getForms();
			Iterator<Form> formsIterator = forms.iterator();

			while (formsIterator.hasNext()) {
				Form form = formsIterator.next();
				templarContext.add("form", form);

				if(options.hasGenerator("forms")) {
					templarParser = getParser("/java-create-form-bean.templar");
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/form/" + form.getName() + "Bean.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);
				}
			}

			if(options.hasGenerator("adminpages")) {
				// now for the jsp index page
				templarParser = getParser("/jsp-create-index.templar");
				String pathname = outFile + "/src/main/webapps/admin/index.html";
				SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
				renderToFile(templarContext, templarParser, pathname);

				// now for the CSS
				templarParser = getParser("/css-create-all.templar");
				pathname = outFile + "/src/main/webapps/admin/static/css/style.css";
				SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
				renderToFile(templarContext, templarParser, pathname);
			}

			if(options.hasGenerator("java")) {
				// now for the statistics
				if(options.getStatistics()) {
					templarParser = getParser("/java-create-statistics.templar");
					String pathname = outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Statistics.java";
					SimpleLogger.logInfo(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
					renderToFile(templarContext, templarParser, pathname);
				}
			}
		} catch (H2ZeroParseException shepex) {
			shepex.printStackTrace();
			return;
		} catch (synapticloop.templar.exception.ParseException stepex) {
			stepex.printStackTrace();
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now that we are done - print out the overview
		if(null != h2zeroParser) {
			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("Generated %d files, messages [ warn: %d, fatal: %d ]", numFiles, h2zeroParser.getNumWarn(), h2zeroParser.getNumFatal()));
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
		SimpleLogger.logInfo(LoggerType.TEMPLAR_LOAD, "Loading templar template '" + templarTemplateFile + "'.");
		return(new Parser(this.getClass().getResourceAsStream(templarTemplateFile)));
	}

	public void setIn(String in) {
		this.in = in;
	}

	public String getIn() {
		return in;
	}

	public void setOutDir(String outDir) {
		this.outDir = outDir;
	}

	public String getOutDir() {
		return outDir;
	}

}
