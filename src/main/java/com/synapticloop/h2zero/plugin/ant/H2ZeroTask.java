package com.synapticloop.h2zero.plugin.ant;

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

import com.synapticloop.h2zero.plugin.BaseH2ZeroGenerator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import com.synapticloop.h2zero.util.SimpleLogger;
import com.synapticloop.h2zero.util.SimpleLogger.LoggerType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class H2ZeroTask extends Task {
	private String inFile = null;
	private final List<String> inFiles = new ArrayList<>();
	private String outDir = null;

	private boolean verbose = true; // whether to be verbose with the logging

	private final List<File> h2ZeroFiles = new ArrayList<>();
	private File outFile = null;

	@Override
	public void execute() throws BuildException {
		if(!areParametersCorrect()) {
			throw new BuildException("Passed in parameters are incorrect.");
		}

		for(File h2ZeroFile: h2ZeroFiles) {
			BaseH2ZeroGenerator baseH2ZeroGenerator = new BaseH2ZeroGenerator(h2ZeroFile, outFile, verbose);
			baseH2ZeroGenerator.generateH2zero();
		}
	}

	private boolean areParametersCorrect() {
		boolean isInError = false;
		if(null == outDir || (null == inFile && inFiles.size() == 0)) {
			String message = "Both attributes 'inFile' and 'outDir' are required, exiting...";
			if(null != getProject()) {
				getProject().log(message, Project.MSG_ERR);
			} else {
				SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
			}
			return(false);
		}

		for(String inFile: inFiles) {
			File h2ZeroFile = new File(inFile);
			if (!h2ZeroFile.exists() || !h2ZeroFile.canRead()) {
				String message = "h2zero file 'inFile': '" + inFile + "' does not exist, or is not readable, exiting...";
				if (null != getProject()) {
					getProject().log(message, Project.MSG_ERR);
				} else {
					SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
				}
				return (false);
			} else {
				h2ZeroFiles.add(h2ZeroFile);
			}
		}


		outFile = new File(outDir);
		if(!outFile.exists() || !outFile.isDirectory()) {
			String message = "'outDir': '" + outDir + "' does not exists or is not a directory, exiting...";
			if(null != getProject()) {
				getProject().log(message, Project.MSG_ERR);
			} else {
				SimpleLogger.logFatal(LoggerType.H2ZERO_GENERATE, message);
			}
			return(false);
		}

		SimpleLogger.logInfo(LoggerType.OPTIONS, "Parameters are present.");
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tIn file(s): ");
		for(String inFile: inFiles) {
			SimpleLogger.logInfo(LoggerType.OPTIONS, "\t          - " + inFile);
		}
		SimpleLogger.logInfo(LoggerType.OPTIONS, "\tOut dir: " + outDir);

		return(true);
	}

	public List getInFile() { return inFiles; }

	/**
	 * The infile may be actually a list of files
	 *
	 * @param inFile
	 */
	public void setInFile(String inFile) {
		this.inFile = inFile;

		if(null != inFile) {
			for (String s : inFile.split(",")) {
				inFiles.add(s.trim());
			}
		}
	}

	public void setOutDir(String outDir) { this.outDir = outDir; }
	public String getOutDir() { return outDir; }
	public boolean getVerbose() { return verbose; }
	public void setVerbose(boolean verbose) { this.verbose = verbose; }

}
