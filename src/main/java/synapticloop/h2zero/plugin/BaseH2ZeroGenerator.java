package synapticloop.h2zero.plugin;

/*
 * Copyright (c) 2016-2018 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.json.JSONObject;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.extension.Extension;
import synapticloop.h2zero.generator.Generator;
import synapticloop.h2zero.generator.JavaGenerator;
import synapticloop.h2zero.generator.MetricsGenerator;
import synapticloop.h2zero.generator.ReportGenerator;
import synapticloop.h2zero.generator.SqlGenerator;
import synapticloop.h2zero.generator.UtilGenerator;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.View;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class BaseH2ZeroGenerator {
	private boolean verbose = false;
	private int numTables;

	private List<Generator> generators = new ArrayList<Generator>();
	private File h2ZeroFile;
	private File outFile;

	/**
	 * Instantiate the generator
	 * 
	 * @param h2ZeroFile The input file (is not parsed)
	 * @param outFile The output directory
	 * @param verbose whether to do verbose logging
	 */
	public BaseH2ZeroGenerator(File h2ZeroFile, File outFile, boolean verbose) {
		this.h2ZeroFile = h2ZeroFile;
		this.outFile = outFile;
		this.verbose = verbose;
	}

	/**
	 * Generate h2zero files and outputs
	 */
	public void generateH2zero() {
		// otherwise we are good to go
		H2ZeroParser h2zeroParser = null;
		TemplarConfiguration templarConfiguration = null;
		TemplarContext templarContext = null;
		Database database = null;
		Options options = null;

		try {
			h2zeroParser = new H2ZeroParser(h2ZeroFile);

			logDatabaseInfo(h2zeroParser);

			templarConfiguration = new TemplarConfiguration();
			templarConfiguration.setExplicitNewLines(true);
			templarConfiguration.setExplicitTabs(true);

			templarContext = new TemplarContext(templarConfiguration);

			database = h2zeroParser.getDatabase();
			numTables = database.getTables().size();

			templarContext.add(JSONKeyConstants.DATABASE, database);

			options = h2zeroParser.getOptions();
			templarContext.add(JSONKeyConstants.OPTIONS, options);

			if(!options.hasGenerators()) {
				throw new BuildException("FATAL: You have not defined an 'options' section, and therefore no generators will be executed. Exiting...");
			}

			generators.add(new SqlGenerator(database, options, outFile, verbose));
			generators.add(new JavaGenerator(database, options, outFile, verbose));
			generators.add(new MetricsGenerator(database, options, outFile, verbose));
			generators.add(new UtilGenerator(database, options, outFile, verbose));
			generators.add(new ReportGenerator(database, options, outFile, verbose));

			for (Generator generator : generators) {
				generator.generate();
			}

		} catch (H2ZeroParseException h2zpex) {
			SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, "H2ZeroParseException: There was an error parsing the '" + h2ZeroFile.getName() + "'.");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, "The message was:");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.PARSE, "  " + h2zpex.getMessage());
			throw new BuildException(h2zpex);
		} catch (ParseException pex) {
			SimpleLogger.logFatal(SimpleLogger.LoggerType.TEMPLAR_PARSE, "ParseException: There was an error parsing the '" + h2ZeroFile.getName() + "'.");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.TEMPLAR_PARSE, "The message was:");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.TEMPLAR_PARSE, "  " + pex.getMessage());
			throw new BuildException(pex);
		} catch (RenderException rex) {
			SimpleLogger.logFatal(SimpleLogger.LoggerType.TEMPLAR_RENDER, "RenderException: There was an error rendering the '" + h2ZeroFile.getName() + "'.");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.TEMPLAR_RENDER, "The message was:");
			SimpleLogger.logFatal(SimpleLogger.LoggerType.TEMPLAR_RENDER, "  " + rex.getMessage());
			throw new BuildException(rex);
		}

		// now for the extensions
		if(!options.hasExtensions()) {
			SimpleLogger.logInfo(LoggerType.EXTENSIONS, "No extensions found, rendering nothing.");
		} else {
			Map<Extension, JSONObject> extensions = options.getExtensions();
			Iterator<Extension> iterator = extensions.keySet().iterator();
			try {

				while (iterator.hasNext()) {
					Extension extension = (Extension) iterator.next();
					extension.generate(extensions.get(extension), database, options, outFile, verbose);
				}

			} catch (RenderException rex) {
				SimpleLogger.logFatal(SimpleLogger.LoggerType.EXTENSION_RENDER, "RenderException: There was an error rendering the '" + h2ZeroFile.getName() + "'.");
				SimpleLogger.logFatal(SimpleLogger.LoggerType.EXTENSION_RENDER, "The message was:");
				SimpleLogger.logFatal(SimpleLogger.LoggerType.EXTENSION_RENDER, "  " + rex.getMessage());
				throw new BuildException(rex);
			} catch (ParseException pex) {
				SimpleLogger.logFatal(SimpleLogger.LoggerType.EXTENSION_PARSE, "ParseException: There was an error parsing the '" + h2ZeroFile.getName() + "'.");
				SimpleLogger.logFatal(SimpleLogger.LoggerType.EXTENSION_PARSE, "The message was:");
				SimpleLogger.logFatal(SimpleLogger.LoggerType.EXTENSION_PARSE, "  " + pex.getMessage());
				throw new BuildException(pex);
			}
		}

		logSummaryInformation(h2zeroParser);
	}

	/**
	 * Everybody loves statistics (well at least we do) so lets see what we have
	 * generated and output it
	 * 
	 * @param h2zeroParser The parser that was used for the generator
	 */
	private void logSummaryInformation(H2ZeroParser h2zeroParser) {
		// now that we are done - print out the overview
		if(null != h2zeroParser) {

			// go through the generators and get the summary information

			Map<String, Integer> numFilesHashMap = new HashMap<String, Integer>();
			int numFiles = 0;

			for (Generator generator : generators) {
				numFiles += generator.getNumFiles();
				Map<String, Integer> generatorNumFilesHashMap = generator.getNumFilesHashMap();
				Iterator<String> iterator = generatorNumFilesHashMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Integer generatorNumFiles = generatorNumFilesHashMap.get(key);
					if(numFilesHashMap.containsKey(key)) {
						numFilesHashMap.put(key, numFilesHashMap.get(key) + generatorNumFiles);
					} else {
						numFilesHashMap.put(key, generatorNumFiles);
					}
				}
			}

			// now go through the extensions and get the summary information
			Map<Extension, JSONObject> extensions = h2zeroParser.getOptions().getExtensions();
			Iterator<Extension> extensionsIterator = extensions.keySet().iterator();
			while (extensionsIterator.hasNext()) {
				Extension extension = (Extension) extensionsIterator.next();
				Map<String, Integer> extensionNumFilesHashMap = extension.getNumFilesHashMap();
				Iterator<String> iterator = extensionNumFilesHashMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Integer extensionNumFiles = extensionNumFilesHashMap.get(key);
					if(numFilesHashMap.containsKey(key)) {
						numFilesHashMap.put(key, numFilesHashMap.get(key) + extensionNumFiles);
					} else {
						numFilesHashMap.put(key, extensionNumFiles);
					}
				}
			}

			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("h2zero just generated code for %d tables!", numTables));
			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("h2zero just saved you typing %d files!  Messages [ warn: %3d, fatal: %3d ]", numFiles, h2zeroParser.getNumWarn(), h2zeroParser.getNumFatal()));

			Iterator<String> iterator = numFilesHashMap.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				Integer count = numFilesHashMap.get(key);
				String multiple = "s";
				if(count == 1) {
					multiple = "";
				}
				SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("%8d %s file%s", count, key, multiple));
			}
			SimpleLogger.logInfo(LoggerType.SUMMARY, "--------");
			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("%8d TOTAL", numFiles));
		}
	}

	/**
	 * Log the database information
	 * 
	 * @param h2zeroParser the h2zero parser
	 */
	private void logDatabaseInfo(H2ZeroParser h2zeroParser) {
		if(verbose) {
			SimpleLogger.logDebug(LoggerType.PARSE, "Found database '" + h2zeroParser.getDatabase().getSchema() + "'.");
			List<Table> tables = h2zeroParser.getDatabase().getTables();
			List<View> views = h2zeroParser.getDatabase().getViews();

			int maxTableNameLength = 0;

			int maxFields = 0;
			int maxFinders = 0;
			int maxDeleters = 0;
			int maxUpdaters = 0;
			int maxInserters = 0;
			int maxQuestions = 0;
			int maxCounters = 0;

			for (Table table : tables) {
				int tableLength = table.getName().length();
				if(tableLength > maxTableNameLength) {
					maxTableNameLength = tableLength;
				}

				int fieldsSize = table.getFields().size();
				if(fieldsSize > maxFields) { maxFields = fieldsSize; }

				int findersSize = table.getFinders().size();
				if(findersSize > maxFinders) { maxFinders = findersSize; }

				int deletersSize = table.getDeleters().size();
				if(deletersSize > maxDeleters) { maxDeleters = deletersSize; }

				int updatersSize = table.getUpdaters().size();
				if(updatersSize > maxUpdaters) { maxUpdaters = updatersSize; }

				int insertersSize = table.getUpdaters().size();
				if(insertersSize > maxInserters) { maxInserters = insertersSize; }

				int questionsSize = table.getQuestions().size();
				if(questionsSize > maxQuestions) { maxQuestions = questionsSize; }

				int countersSize = table.getCounters().size();
				if(countersSize > maxCounters) { maxCounters = countersSize; }
			}

			for (Table table : tables) {
				SimpleLogger.logDebug(LoggerType.PARSE, "Found table " + String.format("%-" + maxTableNameLength + "s", table.getName()) + 
						" [ " + 
						String.format("%" + (Integer.toString(maxFields)).length() + "s fields, ", table.getFields().size()) + 
						String.format("%" + (Integer.toString(maxFinders)).length() + "s finders, ", table.getFinders().size()) + 
						String.format("%" + (Integer.toString(maxDeleters)).length() + "s deleters, ", table.getDeleters().size()) + 
						String.format("%" + (Integer.toString(maxUpdaters)).length() + "s updaters, ", table.getUpdaters().size()) + 
						String.format("%" + (Integer.toString(maxInserters)).length() + "s inserters, ", table.getInserters().size()) + 
						String.format("%" + (Integer.toString(maxQuestions)).length() + "s questions, ", table.getQuestions().size()) + 
						String.format("%" + (Integer.toString(maxCounters)).length() + "s counters", table.getCounters().size()) + 
						" ] ");
			}

			for (View view : views) {
				SimpleLogger.logDebug(LoggerType.PARSE, "Found view " + String.format("%-" + maxTableNameLength + "s", view.getName()) + 
						"  [ " + 
						String.format("%" + (Integer.toString(maxFields)).length() + "s fields, ", view.getFields().size()) + 
						String.format("%" + (Integer.toString(maxFinders)).length() + "s finders, ", view.getFinders().size()) + 
						String.format("- deleters, ") + 
						String.format("- updaters, ") + 
						String.format("- inserters, ") + 
						String.format("%" + (Integer.toString(maxQuestions)).length() + "s questions, ", view.getQuestions().size()) + 
						String.format("%" + (Integer.toString(maxCounters)).length() + "s counters", view.getCounters().size()) + 
						" ] ");
			}
		}
	}}
