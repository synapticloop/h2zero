package synapticloop.h2zero.plugin;

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

public class H2ZeroPluginExtension {
	public static final String FILE_NAME_DOCUMENTR_JSON = "documentr.json";
	public static final String FILE_NAME_DOCUMENTR_DEFAULT_JSON = "/documentr-default.json";

	private boolean verbose = false;
	private String documentrFile = FILE_NAME_DOCUMENTR_JSON;
	private String directory = ".";
	private String extension = "md";

	/**
	 * Return the directory that the documentr plugin looks for the 'documentr.json'
	 * file, by default this is the current working directory
	 * 
	 * @return the directory that the 'documentr.json' file resides in
	 */
	public String getDirectory() { return(directory); }

	/**
	 * Set the directory that the 'documentr.json' file resides in
	 * 
	 * @param directory the directory that the 'documentr.json' file resides in
	 */
	public void setDirectory(String directory) { this.directory = directory; }

	/**
	 * Get whether there should be verbose output
	 * 
	 * @return whether documentr should be verbose
	 */
	public boolean getVerbose() { return this.verbose; }

	/**
	 * Set whether to have verbose output from documentr
	 * 
	 * @param verbose whether to output verbose information
	 */
	public void setVerbose(boolean verbose) { this.verbose = verbose; }

	/**
	 * Get the output extension, by default this is .md
	 * 
	 * @return get the  extension for the output
	 */
	public String getExtension() { return extension; }

	/**
	 * Set the extension for the output of the file
	 * 
	 * @param extension the extension for the output of the file
	 */
	public void setExtension(String extension) { this.extension = extension; }

	/**
	 * Get the documentr file name to be parsed (by default this is 'documentr.json'
	 * 
	 * @return the file name to be parsed by documentr
	 */
	public String getDocumentrFile() { return documentrFile; }

	/**
	 * Set the file name to be parsed
	 * 
	 * @param documentrFile the file name to be parsed
	 */
	public void setDocumentrFile(String documentrFile) { this.documentrFile = documentrFile; }
}
