package synapticloop.h2zero.ant.generator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.templar.function.FunctionRequiresImport;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public abstract class Generator {
	private static final String FUNCTION_NAME_HAS_IMPORT = "requiresImport";
	protected static final String SRC_MAIN_JAVA = "/src/main/java/";
	protected Database database;
	protected Options options;
	protected File outFile;

	private boolean verbose = false;

	private Map<String, Integer> numFilesHashMap = new HashMap<String, Integer>();

	private int numFiles = 0;

	public Generator(Database database, Options options, File outFile, boolean verbose) {
		this.database = database;
		this.options = options;
		this.outFile = outFile;
		this.verbose = verbose;
	}

	public abstract void generate() throws RenderException, ParseException;

	protected TemplarContext getDefaultTemplarContext() throws FunctionException {
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);

		TemplarContext templarContext = new TemplarContext(templarConfiguration);
		templarContext.add("database", database);
		templarContext.add("options", options);

		if(!templarContext.hasFunction(FUNCTION_NAME_HAS_IMPORT)) {
			templarContext.addFunction(FUNCTION_NAME_HAS_IMPORT, new FunctionRequiresImport());
			SimpleLogger.logInfo(LoggerType.FUNCTION_REGISTER, "Registered new Function '" + FUNCTION_NAME_HAS_IMPORT + "'.");
		}

		return templarContext;
	}
	protected Parser getParser(String templarTemplateFile) throws ParseException {
		if(verbose) {
			SimpleLogger.logInfo(LoggerType.TEMPLAR_LOAD, "Loading templar template '" + templarTemplateFile + "'.");
		}
		return(new Parser(this.getClass().getResourceAsStream(templarTemplateFile)));
	}

	/**
	 * A convenience method to keep track of the number of files rendered
	 * 
	 * @param templarParser
	 * @param pathname
	 * @throws RenderException
	 */
	protected void renderToFile(TemplarContext templarContext, Parser templarParser, String pathname) throws RenderException {
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

	public int getNumFiles() { return numFiles; }
	public Map<String, Integer> getNumFilesHashMap() { return numFilesHashMap; }
}
