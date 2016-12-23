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

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class H2ZeroPlugin implements Plugin<Project> {
	private static final String H2ZERO = "h2zero";

	@Override
	public void apply(Project project) {
		project.getExtensions().create(H2ZERO, H2ZeroPluginExtension.class);
		project.getTasks().create(H2ZERO, H2ZeroTask.class);
	}
}
