	package synapticloop.h2zero;

/*
 * Copyright (c) 2012-2020 synapticloop.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import synapticloop.h2zero.plugin.ant.H2ZeroTask;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;

public class Main {
	private static final String USAGE_TXT = "/usage.txt";

	private static final String CLI_OPTION_GENERATE = "generate";
	private static final String CLI_OPTION_REVENGE = "revenge";
	private static final String CLI_OPTION_QUICK = "quick";

	private static final int CLI_GENERATE = 0;
	private static final int CLI_REVENGE = 1;
	private static final int CLI_QUICK = 2;

	private static final Map<String, Integer> COMMAND_LINE_OPTIONS = new HashMap<>();

	static {
		COMMAND_LINE_OPTIONS.put(CLI_OPTION_GENERATE, CLI_GENERATE);
		COMMAND_LINE_OPTIONS.put(CLI_OPTION_REVENGE, CLI_REVENGE);
		COMMAND_LINE_OPTIONS.put(CLI_OPTION_QUICK, CLI_QUICK);
	}

	private static final int GENERATE_VERBOSE = 0;
	private static final int GENERATE_IN = 1;
	private static final int GENERATE_OUT = 2;

	private static final String PARAMETER_VERBOSE = "-verbose";
	private static final String PARAMETER_IN = "-in";
	private static final String PARAMETER_OUT = "-out";

	private static final Map<String, Integer> GENERATE_COMMAND_LINE_OPTIONS = new HashMap<>();
	static {
		GENERATE_COMMAND_LINE_OPTIONS.put(PARAMETER_VERBOSE, GENERATE_VERBOSE);
		GENERATE_COMMAND_LINE_OPTIONS.put(PARAMETER_IN, GENERATE_IN);
		GENERATE_COMMAND_LINE_OPTIONS.put(PARAMETER_OUT, GENERATE_OUT);
	}

	// all of the instance(???) variables 
	private static boolean isVerbose = false;
	private static String inFile = null;
	private static String outDir = null;

	private Main() {}

	private static void usageAndExit(String message) throws IOException {
		if(null != message) {
			System.out.println(message);
		}

		InputStream inputStream = Main.class.getResourceAsStream(USAGE_TXT);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}
		System.exit(0);
	}

	private static void parseAndExecute(String[] args) throws IOException {
		if(args.length == 0) {
			usageAndExit(null);
		}
		// the first argument
		String mode = args[0];
		if(!COMMAND_LINE_OPTIONS.containsKey(mode)) {
			usageAndExit("Unknown mode of '" + mode + "'.");
		}

		// at this point - we know the mode and now we need to parse the options

		switch (COMMAND_LINE_OPTIONS.get(mode)) {
		case CLI_GENERATE:
			parseAndExecuteGenerate(args);
			break;
		case CLI_REVENGE:
		case CLI_QUICK:
			usageAndExit("Mode '" + mode + "' not fully implemented through the command line");
			break;
		default:
			// admittedly this should not happen as we have already done a lookup
			usageAndExit("Unknown mode of '" + mode + "'.");
			break;
		}
	}

	private static void parseAndExecuteGenerate(String[] args) throws IOException {
		String mode = args[0];
		// the first argument is 'generate'
		for(int i = 0; i < args.length; i++) {
			if(i == 0) {
				continue;
			}

			String arg = args[i];

			// the first option __MUST__ always start with a '-' (hyphen) character
			if(!arg.startsWith("-") || !GENERATE_COMMAND_LINE_OPTIONS.containsKey(arg)) {
				usageAndExit("Unknown argument '" + arg + "' for mode '" + mode + "'");
			}

			// all good to go 
			switch (GENERATE_COMMAND_LINE_OPTIONS.get(arg)) {
			case GENERATE_VERBOSE:
				isVerbose = true;
				break;
			case GENERATE_IN:
				i++;
				try {
					String argValue = args[i];
					inFile = argValue;
				} catch(ArrayIndexOutOfBoundsException aioobex) {
					usageAndExit("Found an argument of '" + arg + "', but no value for the option");
				}
				break;
			case GENERATE_OUT:
				i++;
				try {
					String argValue = args[i];
					outDir = argValue;
				} catch(ArrayIndexOutOfBoundsException aioobex) {
					usageAndExit("Found an argument of '" + arg + "', but no value for the option");
				}
				break;
			default:
				break;
			}
		}

		// check all of the parameters
		if(null == inFile) { usageAndExit("Parameter '" + PARAMETER_IN + "' cannot be null"); }
		if(null == outDir) { usageAndExit("Parameter '" + PARAMETER_OUT + "' cannot be null"); }

		// now it is time to kick things off 
		H2ZeroTask h2ZeroTask = new H2ZeroTask();
		h2ZeroTask.setVerbose(isVerbose);
		h2ZeroTask.setInFile(inFile);
		h2ZeroTask.setOutDir(outDir);
		h2ZeroTask.execute();
	}

	public static void main(String[] args) {
		try {
			if(null == args) {
				// unlikely but tested
				usageAndExit(null);
			}
			parseAndExecute(args);
		} catch (IOException ex) {
			SimpleLogger.logFatal(LoggerType.MAIN, "Could neither find, nor read the file '" + USAGE_TXT + "' within the jar.");
		}
	}
}
