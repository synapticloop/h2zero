package synapticloop.h2zero.plugin.gradle;

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

public class H2ZeroPluginExtension {
	public static final String FILE_NAME_H2ZERO = "h2zero.h2zero";

	private boolean verbose = false;
	private String inFile = FILE_NAME_H2ZERO;
	private String outDir = ".";

	/**
	 * Return the directory that the generated resources will be placed, by 
	 * default, this will be '.' with the src/main/java appended
	 * 
	 * @return the directory that the generated artefacts will be placed
	 */
	public String getOutDir() { return(outDir); }

	/**
	 * Set the directory that the generated artefacts will be placed
	 * 
	 * @param outDir the directory that the generated artefacts will be placed
	 */
	public void setOutDir(String outDir) { this.outDir = outDir; }

	/**
	 * Get whether there should be verbose output
	 * 
	 * @return whether h2zero should be verbose
	 */
	public boolean getVerbose() { return this.verbose; }

	/**
	 * Set whether to have verbose output from h2zero
	 * 
	 * @param verbose whether to output verbose information
	 */
	public void setVerbose(boolean verbose) { this.verbose = verbose; }

	/**
	 * Get the h2ZeroFile file name to be parsed (by default this is 'h2zero.h2zero'
	 * 
	 * @return the file name to be parsed by h2Zero
	 */
	public String getInFile() { return inFile; }

	/**
	 * Set the file name to be parsed
	 * 
	 * @param inFile the file name to be parsed
	 */
	public void setInFile(String inFile) { this.inFile = inFile; }
}
