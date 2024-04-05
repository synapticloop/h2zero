package com.synapticloop.h2zero.plugin.gradle;

/*
 * Copyright (c) 2016-2024 synapticloop.
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


import com.synapticloop.h2zero.exception.H2ZeroParseException;
import com.synapticloop.h2zero.plugin.BaseH2ZeroGenerator;
import com.synapticloop.h2zero.util.SimpleLogger;
import com.synapticloop.h2zero.util.SimpleLogger.LoggerType;
import org.apache.tools.ant.BuildException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class H2ZeroTask extends DefaultTask {
	private List<String> inFiles = new ArrayList<>();
	private String outDir;

	private List<File> h2ZeroFiles = new ArrayList<>();
	private File outFile;

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

		this.inFiles = extension.getInFiles();
		this.outDir = extension.getOutDir();

		if(!areParametersCorrect()) {
			throw new BuildException("Passed in parameters are incorrect.");
		}

		for(File h2ZeroFile: h2ZeroFiles) {
			BaseH2ZeroGenerator baseH2ZeroGenerator = new BaseH2ZeroGenerator(h2ZeroFile, outFile, extension.getVerbose());
			baseH2ZeroGenerator.generateH2zero();
		}
	}



	private boolean areParametersCorrect() {
		System.out.println(inFiles);

		if(null == outDir || inFiles.size() == 0) {
			String message = "Both attributes 'inFile' and 'outDir' are required, exiting...";
			if(null != getProject()) {
				getProject().getLogger().error(message);
			} else {
				SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
			}
			return(false);
		}

		for(String inFile: inFiles) {
			File h2ZeroFile = new File(getProject().getProjectDir().getAbsolutePath() + "/" + inFile);
			if (!h2ZeroFile.exists() || !h2ZeroFile.canRead()) {
				String message = "h2zero file 'h2ZeroFile': '" + inFile + "' does not exist, or is not readable, exiting...";
				if (null != getProject()) {
					getProject().getLogger().error(message);
				} else {
					SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
				}
				return (false);
			}
			h2ZeroFiles.add(h2ZeroFile);
		}

		outFile = new File(getProject().getProjectDir().getAbsolutePath() + "/" + outDir);
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
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tIn file(s): ");
		for(String inFile: inFiles) {
			SimpleLogger.logInfo(LoggerType.OPTIONS, "\t          - " + inFile);
		}
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tOut dir: " + outDir);

		return(true);
	}
}
