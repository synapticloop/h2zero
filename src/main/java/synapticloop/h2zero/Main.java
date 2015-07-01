package synapticloop.h2zero;


/*
 * Copyright (c) 2012-2015 synapticloop.
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

public class Main {
	/**
	 * Simple usage message
	 */

	private static void usage() {
		System.out.println("This can no longer be run from the command line - please use the following in your ant build scripts:\n\n");

		System.out.println("\t<path id=\"classpath-h2zero\">");
		System.out.println("\t\t<fileset dir=\"lib/compile\">");
		System.out.println("\t\t\t<include name=\"*.jar\"/>");
		System.out.println("\t\t</fileset>");
		System.out.println("\t</path>");
		System.out.println("\t");
		System.out.println("\t<target name=\"h2zero\" description=\"h2zero generate\">");
		System.out.println("\t\t<taskdef resource=\"h2zero.properties\" classpathref=\"classpath-h2zero\" />");
		System.out.println("\t\t<h2zero in=\"src/main/java/your_file_name_here.h2zero\" outDir=\".\" verbose=\"false\" />");
		System.out.println("\t</target>\n");

		System.out.println("Exiting...");
	}

	public static void main(String[] args) {
		usage();
	}
}
