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
import com.synapticloop.h2zero.generator.model.util.JSONKeyConstants;
import com.synapticloop.h2zero.generator.templar.function.FunctionExists;
import com.synapticloop.h2zero.generator.templar.function.FunctionRequiresImport;
import com.synapticloop.h2zero.generator.util.SimpleLogger;
import com.synapticloop.h2zero.generator.util.SimpleLogger.LoggerType;
import com.synapticloop.templar.Parser;
import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarConfiguration;
import com.synapticloop.templar.utils.TemplarContext;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class Generator {
	private static final String FUNCTION_NAME_HAS_IMPORT = "requiresImport";
	private static final String FUNCTION_NAME_EXISTS = "exists";

	protected Database database;
	protected Options options;
	protected File outFile;
	protected boolean verbose = false;

	private Map<String, Integer> numFilesHashMap = new HashMap<String, Integer>();

	private int numFiles = 0;

	/**
	 * Constructor for the generator
	 * 
	 * @param database the database object that the generator will act on
	 * @param options the options for the generator
	 * @param outFile the base file to output the generated assets
	 * @param verbose whether to output debugging
	 */
	public Generator(Database database, Options options, File outFile, boolean verbose) {
		this.database = database;
		this.options = options;
		this.outFile = outFile;
		this.verbose = verbose;
	}

	/**
	 * Generate the output file from the inputs
	 * 
	 * @throws RenderException if there was an error rendering the file
	 * @throws ParseException if there was an error parsing the templar template file
	 */
	public abstract void generate() throws RenderException, ParseException;

	public void generate(Database database, Options options, File outFile, boolean verbose) throws RenderException, ParseException {
		this.database = database;
		this.options = options;
		this.outFile = outFile;
		this.verbose = verbose;
		generate();
	}

	/**
	 * Get the default templar context, which contains the database and the 
	 * options objects.
	 * 
	 * @return a new templar context with the default configuration
	 * 
	 * @throws FunctionException if there was an error registering the new function
	 */
	protected TemplarContext getDefaultTemplarContext() throws FunctionException {
		TemplarConfiguration templarConfiguration = new TemplarConfiguration();
		templarConfiguration.setExplicitNewLines(true);
		templarConfiguration.setExplicitTabs(true);

		TemplarContext templarContext = new TemplarContext(templarConfiguration);
		templarContext.add(JSONKeyConstants.DATABASE, database);
		templarContext.add(JSONKeyConstants.OPTIONS, options);

		if(!templarContext.hasFunction(FUNCTION_NAME_HAS_IMPORT)) {
			templarContext.addFunction(FUNCTION_NAME_HAS_IMPORT, new FunctionRequiresImport());
			SimpleLogger.logInfo(LoggerType.FUNCTION_REGISTER, "Registered new Function '" + FUNCTION_NAME_HAS_IMPORT + "'.");
		}

		if(!templarContext.hasFunction(FUNCTION_NAME_EXISTS)) {
			templarContext.addFunction(FUNCTION_NAME_EXISTS, new FunctionExists());
			SimpleLogger.logInfo(LoggerType.FUNCTION_REGISTER, "Registered new Function '" + FUNCTION_NAME_EXISTS + "'.");
		}

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
	protected Parser getParser(String templarTemplateFile) throws ParseException {
		if(verbose) {
			SimpleLogger.logDebug(LoggerType.TEMPLAR_LOAD, "Loading templar template '" + templarTemplateFile + "'.");
		}

		InputStream inputStream = this.getClass().getResourceAsStream(templarTemplateFile);
		if(null == inputStream) {
			throw new ParseException("Could not load the templar template '" + templarTemplateFile + "' resource.");
		}

		return(new Parser(inputStream));
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
	protected void renderToFile(TemplarContext templarContext, Parser templarParser, String pathname) throws RenderException {
		if(verbose) {
			SimpleLogger.logDebug(LoggerType.TEMPLAR_RENDER, "Rendering to '" + pathname + "'");
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
}
