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


import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class H2ZeroTask extends DefaultTask {
	/**
	 * Instantiate the task, setting the group and description
	 */
	public H2ZeroTask() {
		super.setGroup("Generation");
		super.setDescription("Generates a Java ORM for your project.");
	}

	/**
	 * Generate the README file from the documentr.json input file
	 * 
	 * @throws DocumentrException If there was an error parsing/rendering the 
	 *     README file
	 */
	@TaskAction
	public void generate() throws H2ZeroParseException{
		H2ZeroPluginExtension extension = getProject().getExtensions().findByType(H2ZeroPluginExtension.class);

		if (extension == null) {
			extension = new H2ZeroPluginExtension();
		}

	}
}