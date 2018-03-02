package synapticloop.h2zero.util;

/*
 * Copyright (c) 2012-2018 synapticloop.
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

/**
 * A super simple logger - so that the package can be as light-weight as possible
 * 
 * @author synapticloop
 */
public class SimpleLogger {
	public enum LoggerType {
		MAIN,
		OPTIONS,
		OPTIONS_VALIDATOR,
		EXTENSIONS,
		GENERATORS,
		GENERATE,
		GENERATE_SQL,
		GENERATE_FORM_BEANS,
		GENERATE_JAVA,
		GENERATE_JAVA_UTIL,
		GENERATE_TAGLIB,
		GENERATE_METRICS,
		TEMPLAR_LOAD,
		TEMPLAR_RENDER,
		TEMPLAR_PARSE,
		VALIDATOR,
		ANALYSER,
		PARSE,
		SUMMARY,
		REVENGE,
		FUNCTION_REGISTER,
		H2ZERO_GENERATE
	}

	private static int maxLength = 0;
	static {
		LoggerType[] values = LoggerType.values();
		for (LoggerType loggerType : values) {
			int length = loggerType.name().length();
			if(length > maxLength) {
				maxLength = length;
			}
		}
	}

	public static final String DEBUG = "DEBUG";
	public static final String INFO = " INFO";
	public static final String WARN = " WARN";
	public static final String ERROR = "ERROR";
	public static final String FATAL = "FATAL";

	private SimpleLogger() {}

	public static void logDebug(LoggerType loggerType, String message) { log(DEBUG, loggerType, message); }
	@SuppressWarnings("rawtypes")
	public static void logDebug(LoggerType loggerType, Class clazz, String message) { log(DEBUG, loggerType, clazz, message); }

	public static void logInfo(LoggerType loggerType, String message) { log(INFO, loggerType, message); }
	@SuppressWarnings("rawtypes")
	public static void logInfo(LoggerType loggerType, Class clazz, String message) { log(INFO, loggerType, clazz, message); }

	public static void logWarn(LoggerType loggerType, String message) { log(WARN, loggerType, message); }
	@SuppressWarnings("rawtypes")
	public static void logWarn(LoggerType loggerType, Class clazz, String message) { log(WARN, loggerType, clazz, message); }
	
	public static void logError(LoggerType loggerType, String message) { log(ERROR, loggerType, message); }
	@SuppressWarnings("rawtypes")
	public static void logError(LoggerType loggerType, Class clazz, String message) { log(ERROR, loggerType, clazz, message); }
	
	public static void logFatal(LoggerType loggerType, String message) { log(FATAL, loggerType, message); }
	@SuppressWarnings("rawtypes")
	public static void logFatal(LoggerType loggerType, Class clazz, String message) { log(FATAL, loggerType, clazz, message); }

	private static void log(String type, LoggerType loggerType, String message) {
		System.out.println(String.format("[ %" + maxLength + "s ] [ %s ] %s", loggerType.name(), type, message));
	}
	@SuppressWarnings("rawtypes")
	private static void log(String type, LoggerType loggerType, Class clazz, String message) {
		System.out.println(String.format("[ %" + maxLength + "s ] [ %s ] [ %s ] %s", loggerType.name(), type, clazz.getSimpleName(), message));
	}

}
