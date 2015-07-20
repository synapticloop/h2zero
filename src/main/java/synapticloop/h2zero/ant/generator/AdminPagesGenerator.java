package synapticloop.h2zero.ant.generator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.util.FileUtils;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class AdminPagesGenerator extends Generator {

	public AdminPagesGenerator(Database database, Options options, File outFile, boolean verbose) {
		super(database, options, outFile, verbose);
	}

	@Override
	public void generate() throws RenderException, ParseException {
		if(!options.hasGenerator(Options.OPTION_ADMINPAGES)) {
			return;
		}

		TemplarContext templarContext = null;
		try {
			templarContext = getDefaultTemplarContext();
		} catch (FunctionException fex) {
			throw new RenderException("Could not instantiate the function.", fex);
		}

		Parser jspCreateFinderParser = getParser("/jsp-create-finder.templar");
		Parser jspCreateIndexParser = getParser("/jsp-create-index.templar");
		Parser jspCreateIndexTableParser = getParser("/jsp-create-index-table.templar");
		Parser jspCreateFindAllParser = getParser("/jsp-create-find-all.templar");

		// the CSS
		Parser cssCreateAllParser = getParser("/css-create-all.templar");

		// now for the tables
		List<Table> tables = database.getTables();
		Iterator<Table> tableIterator = tables.iterator();

		while (tableIterator.hasNext()) {
			Table table = tableIterator.next();
			templarContext.add("table", table);
			SimpleLogger.logInfo(LoggerType.GENERATE, "Generating for table '" + table.getName() + "'.");

			List<Finder> finders = table.getFinders();
			Iterator<Finder> finderIterator = finders.iterator();

			while (finderIterator.hasNext()) {
				Finder finder = finderIterator.next();
				templarContext.add("finder", finder);

				// now for the jsp finder pages
				String pathname = outFile + "/src/main/webapps/admin/" + table.getName() + "-" + finder.getName() + ".html";
				renderToFile(templarContext, jspCreateFinderParser, pathname);
			}



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

				if(options.hasGenerator(Options.OPTION_ADMINPAGES)) {
					// now for the jsp finder pages
					String pathname = outFile + "/src/main/webapps/admin/" + view.getName() + "-" + finder.getName() + ".html";
					renderToFile(templarContext, jspCreateFinderParser, pathname);
				}
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
	}

}
