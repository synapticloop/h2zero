package synapticloop.h2zero.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;

/*
 * Copyright (c) 2016 Synapticloop.
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


import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.generator.AdminPagesGenerator;
import synapticloop.h2zero.generator.Generator;
import synapticloop.h2zero.generator.JavaGenerator;
import synapticloop.h2zero.generator.MetricsGenerator;
import synapticloop.h2zero.generator.ReportGenerator;
import synapticloop.h2zero.generator.SqlGenerator;
import synapticloop.h2zero.generator.TaglibGenerator;
import synapticloop.h2zero.generator.UtilGenerator;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarConfiguration;
import synapticloop.templar.utils.TemplarContext;

public class H2ZeroTask extends DefaultTask {
	private boolean verbose = false;
	private String inFile;
	private String outDir;

	private File h2ZeroFile;
	private File outFile;
	private int numTables;

	private List<Generator> generators = new ArrayList<Generator>();
	
	/**
	 * Instantiate the task, setting the group and description
	 */
	public H2ZeroTask() {
		super.setGroup("Generation");
		super.setDescription("Generates a Java ORM for your project.");
	}

	/**
	 * Generate the H2Zero artefacts
	 * 
	 * @throws H2ZeroParseException If there was an error parsing/rendering the 
	 *     artefacts
	 */
	@TaskAction
	public void generate() throws H2ZeroParseException{
		H2ZeroPluginExtension extension = getProject().getExtensions().findByType(H2ZeroPluginExtension.class);

		if (extension == null) {
			extension = new H2ZeroPluginExtension();
		}

		this.verbose = extension.getVerbose();
		this.inFile = extension.getInFile();
		this.outDir = extension.getOutDir();

		if(!areParametersCorrect()) {
			throw new BuildException("Passed in parameters are incorrect.");
		}

		// otherwise we are good to go
		H2ZeroParser h2zeroParser = null;
		try {
			h2zeroParser = new H2ZeroParser(h2ZeroFile);

			logDatabaseInfo(h2zeroParser);

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
				throw new BuildException("FATAL: You have not defined an 'options' section, and therefore no generators will be executed. Exiting...");
			}

			generators.add(new SqlGenerator(database, options, outFile, verbose));
			generators.add(new JavaGenerator(database, options, outFile, verbose));
			generators.add(new TaglibGenerator(database, options, outFile, verbose));
			generators.add(new AdminPagesGenerator(database, options, outFile, verbose));
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
		} catch (synapticloop.templar.exception.ParseException pex) {
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

		logSummaryInformation(h2zeroParser);
	}

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

			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("h2zero just generated code for %d tables!", numTables));
			SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("h2zero just saved you typing %d files!  Messages [ warn: %d, fatal: %d ]", numFiles, h2zeroParser.getNumWarn(), h2zeroParser.getNumFatal()));
			Iterator<String> iterator = numFilesHashMap.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				Integer count = numFilesHashMap.get(key);
				String multiple = "s";
				if(count == 1) {
					multiple = "";
				}
				SimpleLogger.logInfo(LoggerType.SUMMARY, String.format("     %d %s file%s", count, key, multiple));
			}
		}
	}

	private boolean areParametersCorrect() {
		if(null == outDir || null == inFile) {
			String message = "Both attributes 'h2ZeroFile' and 'outDir' are required, exiting...";
			if(null != getProject()) {
				getProject().getLogger().error(message);
			} else {
				SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
			}
			return(false);
		}

		h2ZeroFile = new File(inFile);
		if(!h2ZeroFile.exists()|| !h2ZeroFile.canRead()) {
			String message = "h2zero file 'h2ZeroFile': '" + inFile + "' does not exist, or is not readable, exiting...";
			if(null != getProject()) {
				getProject().getLogger().error(message);
			} else {
				SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
			}
			return(false);
		}

		outFile = new File(outDir);
		if(!outFile.exists() || !outFile.isDirectory()) {
			String message = "'outDir': '" + outDir + "' does not exists or is not a directory, exiting...";
			if(null != getProject()) {
				getProject().getLogger().error(message);
			} else {
				SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
			}
			return(false);
		}

		SimpleLogger.logInfo(LoggerType.OPTIONS, "Parameters are correct");
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tIn file: " + inFile);
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tOut dir: " + outDir);

		return(true);
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
		}
	}}