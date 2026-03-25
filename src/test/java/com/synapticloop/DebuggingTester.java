package com.synapticloop;

import com.synapticloop.h2zero.Main;


public class DebuggingTester {
	/**
	 * Command line usage:
	 * ===================
	 *
	 *   java -jar h2zero-all.jar <mode> <options>
	 *
	 * There are three (3) modes of operation, namely:
	 *
	 *   generate  - this will generate the source code from the provided .h2zero file
	 *   revenge   - this will reverse engineer a database to an .h2zero file
	 *   quick     - this will generate a quick .h2zero file and output it to the console
	 *
	 *
	 * generate options:
	 * -----------------
	 *   -in <arg>   the input file - or a comma separated list of files
	 *   -out <arg>  the directory to output the generated files
	 *   -verbose    turn on verbose output
	 * @param args
	 */
	public static void main(String[] args) {
		Main.main(new String[] {
				"generate",
				"-in",
				"src/test/resources/debugging-tester.json",
				"-out",
				"src/debugger/java/"});
	}
}
