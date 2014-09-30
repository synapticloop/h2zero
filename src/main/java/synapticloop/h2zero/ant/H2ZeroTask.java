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
import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.model.form.Form;
import synapticloop.h2zero.util.FileUtils;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class H2ZeroTask extends Task {
	private String in = null;
	private String outDir = null;

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


		// otherwise we are good to go
		try {
			H2ZeroParser h2zeroParser = new H2ZeroParser(h2zeroFile);
			TemplarConfiguration templarConfiguration = new TemplarConfiguration();
			templarConfiguration.setExplicitNewLines(true);
			templarConfiguration.setExplicitTabs(true);

			TemplarContext templarContext = new TemplarContext(templarConfiguration);

			Database database = h2zeroParser.getDatabase();
			templarContext.add("database", database);

			Options options = h2zeroParser.getOptions();
			templarContext.add("options", options);

			Parser templarParser = null;

			if(options.hasGenerator("sql")) {
				// first up the database creation script
				templarParser = getParser("/sql-create-database.templar");
				templarParser.renderToFile(templarContext, new File(outFile + "/src/main/sql/create-database.sql"));
			}

			if(options.hasGenerator("java")) {
				templarParser = getParser("/java-create-constants.templar");
				templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Constants.java"));
			}

			// now for the tables
			ArrayList<Table> tables = database.getTables();
			Iterator<Table> tableIterator = tables.iterator();
			while (tableIterator.hasNext()) {
				Table table = tableIterator.next();
				templarContext.add("table", table);

				if(options.hasGenerator("java")) {
					// the model
					templarParser = getParser("/java-create-model.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/model/" + table.getJavaClassName() + ".java"));

					// the finder
					templarParser = getParser("/java-create-finder.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + table.getJavaClassName() + "Finder.java"));

					// the default form beans
					templarParser = getParser("/java-create-default-form-bean-create.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/form/auto/" + table.getJavaClassName() + "CreateFormBean.java"));

					// the counters
					templarParser = getParser("/java-create-counter.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/counter/" + table.getJavaClassName() + "Counter.java"));
				}

				// the finder tag libraries
				ArrayList<Finder> finders = table.getFinders();
				Iterator<Finder> finderIterator = finders.iterator();;

				while (finderIterator.hasNext()) {
					Finder finder = finderIterator.next();
					templarContext.add("finder", finder);

					if(options.hasGenerator("taglib")) {
						templarParser = getParser("/java-create-taglib.templar");
						templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaClassName() + finder.getFinderTagName() + "Tag.java"));
					}

					if(options.hasGenerator("java")) {
						// don't forget the beans for the selectClause finders
						if(null != finder.getSelectClause()) {
							templarParser = getParser("/java-create-select-clause-bean.templar");
							templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/bean/" + finder.getFinderTagName() + "Bean.java"));
						}
					}


					if(options.hasGenerator("jsp")) {
						// now for the jsp finder pages
						templarParser = getParser("/jsp-create-finder.templar");
						templarParser.renderToFile(templarContext, new File(outFile + "/src/main/webapps/admin/" + table.getName() + "-" + finder.getName() + ".html"));
					}
				}

				if(options.hasGenerator("taglib")) {
					// the extra 'missing' finders
					templarParser = getParser("/java-create-taglib-find-by-primary-key.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaClassName() + "FindByPrimaryKeyTag.java"));

					templarParser = getParser("/java-create-taglib-find-all.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/taglib/" + table.getJavaClassName() + "FindAllTag.java"));
				}
				if(options.hasGenerator("java")) {
					// the updater
					templarParser = getParser("/java-create-updater.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/updater/" + table.getJavaClassName() + "Updater.java"));

					// the deleter
					templarParser = getParser("/java-create-deleter.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/deleter/" + table.getJavaClassName() + "Deleter.java"));
				}

				if(options.hasGenerator("jsp")) {
					// need to copy over the favicons
					// make sure that the directories are created...
					new File(outFile + "/src/main/webapps/admin/static/img/").mkdirs();
					FileUtils.copyResourceToFile("/favicon.png", outFile + "/src/main/webapps/admin/static/img/favicon.png");
					FileUtils.copyResourceToFile("/favicon.ico", outFile + "/src/main/webapps/admin/static/img/favicon.ico");
					// each jsp index page for each table
					templarParser = getParser("/jsp-create-index-table.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/webapps/admin/" + table.getName() + ".html"));

					// The jsp findAll page
					templarParser = getParser("/jsp-create-find-all.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/webapps/admin/" + table.getName() + "-findAll.html"));
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
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/view/" + view.getJavaClassName() + ".java"));

					templarParser = getParser("/java-create-view-finder.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/finder/" + view.getJavaClassName() + "ViewFinder.java"));
				}
			}


			if(options.hasGenerator("taglib")) {
				// the finder tld
				templarParser = getParser("/tld-create-library.templar");
				templarParser.renderToFile(templarContext, new File(outFile + "/src/main/webapps/WEB-INF/tld/" + database.getSchema() + ".tld"));
			}
			// now for the forms
			ArrayList<Form> forms = database.getForms();
			Iterator<Form> formsIterator = forms.iterator();

			while (formsIterator.hasNext()) {
				Form form = formsIterator.next();
				templarContext.add("form", form);

				if(options.hasGenerator("java")) {
					templarParser = getParser("/java-create-form-bean.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/form/" + form.getName() + "Bean.java"));
				}
			}

			if(options.hasGenerator("jsp")) {
				// now for the jsp index page
				templarParser = getParser("/jsp-create-index.templar");
				templarParser.renderToFile(templarContext, new File(outFile + "/src/main/webapps/admin/index.html"));

				// now for the CSS
				templarParser = getParser("/css-create-all.templar");
				templarParser.renderToFile(templarContext, new File(outFile + "/src/main/webapps/admin/static/css/style.css"));
			}

			if(options.hasGenerator("java")) {
				// now for the statistics
				if(options.getStatistics()) {
					templarParser = getParser("/java-create-statistics.templar");
					templarParser.renderToFile(templarContext, new File(outFile + "/src/main/java/" + database.getPackagePath() + "/model/util/Statistics.java"));
				}
			}
		} catch (H2ZeroParseException shepex) {
			getProject().log("Parsing error", shepex, Project.MSG_ERR);
			shepex.printStackTrace();
			return;
		} catch (synapticloop.templar.exception.ParseException stepex) {
			stepex.printStackTrace();
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Parser getParser(String templarTemplateFile) throws ParseException {
		System.out.println("Loading templar template '" + templarTemplateFile + "'.");
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
