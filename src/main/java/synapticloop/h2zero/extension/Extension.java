package synapticloop.h2zero.extension;

/*
 * Copyright (c) 2018 synapticloop.
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public abstract class Extension {
	private Map<String, Integer> numFilesHashMap = new HashMap<String, Integer>();

	private int numFiles = 0;

	public Extension() {}

	/**
	 * Generate the output for this extension
	 * 
	 * @param extensionOptions The JSONObject that contains any extensions that 
	 * 		have been defined (or empty if not defined)
	 * @param database The database which contains the tables
	 * @param options The h2zero options
	 * @param outFile The output directory
	 * @param verbose Whether the user requested verbose output
	 * 
	 * @throws RenderException If there was an error templar rendering the extension
	 * @throws ParseException If there was an error parsing the templar file
	 */
	public abstract void generate(JSONObject extensionOptions, Database database, Options options, File outFile, boolean verbose) throws RenderException, ParseException;

	/**
	 * Get the default templar context, which contains the database and the 
	 * options objects.
	 * 
	 * @return a new templar context with the default configuration
	 * 
	 * @throws FunctionException if there was an error registering the new function
	 */
	protected TemplarContext getDefaultTemplarContext(JSONObject extensionOptions, Database database, Options options) {
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);

		TemplarContext templarContext = new TemplarContext(templarConfiguration);
		templarContext.add(JSONKeyConstants.DATABASE, database);                    // key is "database"
		templarContext.add(JSONKeyConstants.OPTIONS, options);                      // key is "options"
		templarContext.add(JSONKeyConstants.EXTENSION_OPTIONS, extensionOptions);   // key is "extensionOptions"

		return templarContext;
	}

	/**
	 * Get the parser for the templar template file from the classpath
	 * 
	 * @param templarTemplateFile the templar file to retrieve from the classpath
	 * 
	 * @return the parser for the templar template file
	 * 
	 * @throws ParseException if there was an error parsing the file
	 */
	protected Parser getParser(String templarTemplateFile, boolean verbose) throws ParseException {
		if(verbose) {
			SimpleLogger.logDebug(LoggerType.EXTENSION_LOAD, "Loading templar template '" + templarTemplateFile + "'.");
		}
		return(new Parser(this.getClass().getResourceAsStream(templarTemplateFile)));
	}

	/**
	 * A convenience method to keep track of the number of files rendered
	 * 
	 * @param templarContext the templar context
	 * @param templarParser the templar parser
	 * @param pathname the pathname of the file to render to
	 * 
	 * @throws RenderException if there was an error rendering the file
	 */
	protected void renderToFile(TemplarContext templarContext, Parser templarParser, String pathname, boolean verbose) throws RenderException {
		if(verbose) {
			SimpleLogger.logDebug(LoggerType.EXTENSION_RENDER, "Rendering to '" + pathname + "'");
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

	/**
	 * Get the number of files that has been generated
	 * 
	 * @return the number of files generated
	 */
	public int getNumFiles() { return numFiles; }

	/**
	 * Return the map of the number of files keyed on extension, with the value 
	 * being the number of files generated for that extension
	 *  
	 * @return the map of extension:#files_generated
	 */
	public Map<String, Integer> getNumFilesHashMap() { return numFilesHashMap; }

	/**
	 * Log an info message to the console
	 * 
	 * @param message The message to log
	 */
	protected void logInfo(String message) {
		SimpleLogger.logInfo(LoggerType.EXTENSIONS, this.getClass(), message);
	}

	/**
	 * Log an error message to the console
	 * 
	 * @param message The message to log
	 */
	protected void logError(String message) {
		SimpleLogger.logError(LoggerType.EXTENSIONS, this.getClass(), message);
	}

	/**
	 * Log a debug message to the console
	 * 
	 * @param message The message to log
	 */
	protected void logDebug(String message) {
		SimpleLogger.logDebug(LoggerType.EXTENSIONS, this.getClass(), message);
	}

	/**
	 * Log a fatal message to the console
	 * 
	 * @param message The message to log
	 */
	protected void logFatal(String message) {
		SimpleLogger.logFatal(LoggerType.EXTENSIONS, this.getClass(), message);
	}
}
