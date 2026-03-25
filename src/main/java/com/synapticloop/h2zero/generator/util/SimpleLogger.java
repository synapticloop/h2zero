package com.synapticloop.h2zero.generator.util;

/*
 * Copyright (c) 2012-2026 synapticloop.
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
 * <p>A super simple logger - so that the package can be as light-weight as
 * possible - this was integrated explicitly for h2zero</p>
 * 
 * @author synapticloop
 */
public class SimpleLogger {
	/** Whether to log verbose output. */
	public static boolean verbose;

	/** The different types of loggers available. */
	public enum LoggerType {
		ANALYSER,
		BOOT,
		EXTENSIONS,
		EXTENSION_LOAD,
		EXTENSION_PARSE,
		EXTENSION_RENDER,
		FUNCTION_REGISTER,
		GENERATE,
		GENERATE_FORM_BEANS,
		GENERATE_JAVA,
		GENERATE_JAVA_PROPERTIES,
		GENERATE_JAVA_UTIL,
		GENERATE_METRICS,
		GENERATE_SQL,
		GENERATORS,
		H2ZERO_GENERATE,
		MAIN,
		OPTIONS,
		OPTIONS_VALIDATOR,
		PARSE,
		PARSE_ADDITIONAL,
		REVENGE,
		SUMMARY,
		TEMPLAR_LOAD,
		TEMPLAR_PARSE,
		TEMPLAR_RENDER,
		VALIDATOR,
		VALIDATOR_REGISTER
	}

	/** The maximum length of the LoggerType enum names, used for formatting the output. */
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

	/** The label for debug messages */
	public static final String DEBUG = "DEBUG";
	/** The label for info messages */
	public static final String INFO = " INFO";
	/** The label for warn messages */
	public static final String WARN = " WARN";
	/** The label for error messages */
	public static final String ERROR = "ERROR";
	/** The label for fatal messages */
	public static final String FATAL = "FATAL";

	/** Private constructor to prevent instantiation. */
	private SimpleLogger() {}

	/**
	 * <p>Log a debug message to the console</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param message The message to log
	 */
	public static void logDebug(LoggerType loggerType, String message) {
		if(verbose) {
			log(DEBUG, loggerType, message);
		}
	}

	/**
	 * <p>Log a debug message to the console with the calling class which is output
	 * in square braces '[]'</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param clazz the calling class
	 * @param message The message to log
	 */
	@SuppressWarnings("rawtypes")
	public static void logDebug(LoggerType loggerType, Class clazz, String message) {
		if(verbose) {
			log(DEBUG, loggerType, clazz, message);
		}
	}

	/**
	 * <p>Log an info message to the console</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param message The message to log
	 */
	public static void logInfo(LoggerType loggerType, String message) {
		log(INFO, loggerType, message);
	}

	/**
	 * <p>Log an info message to the console with the calling class which is output
	 * in square braces '[]'</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param clazz the calling class
	 * @param message The message to log
	 */
	@SuppressWarnings("rawtypes")
	public static void logInfo(LoggerType loggerType, Class clazz, String message) {
		log(INFO, loggerType, clazz, message);
	}

	/**
	 * <p>Log a warning message to the console</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param message The message to log
	 */
	public static void logWarn(LoggerType loggerType, String message) {
		log(WARN, loggerType, message);
	}

	/**
	 * <p>Log a warning message to the console with the calling class which is output
	 * in square braces '[]'</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param clazz the calling class
	 * @param message The message to log
	 */
	@SuppressWarnings("rawtypes")
	public static void logWarn(LoggerType loggerType, Class clazz, String message) { log(WARN, loggerType, clazz, message); }

	/**
	 * <p>Log an error message to the console</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param message The message to log
	 */
	public static void logError(LoggerType loggerType, String message) { log(ERROR, loggerType, message); }

	/**
	 * <p>Log an error message to the console with the calling class which is output
	 * in square braces '[]'</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param clazz the calling class
	 * @param message The message to log
	 */
	@SuppressWarnings("rawtypes")
	public static void logError(LoggerType loggerType, Class clazz, String message) { log(ERROR, loggerType, clazz, message); }

	/**
	 * <p>Log a fatal message to the console</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param message The message to log
	 */
	public static void logFatal(LoggerType loggerType, String message) { log(FATAL, loggerType, message); }

	/**
	 * <p>Log a fatal message to the console with the calling class which is output
	 * in square braces '[]'</p>
	 * 
	 * @param loggerType The type of the logger
	 * @param clazz the calling class
	 * @param message The message to log
	 */
	@SuppressWarnings("rawtypes")
	public static void logFatal(LoggerType loggerType, Class clazz, String message) { log(FATAL, loggerType, clazz, message); }

	/**
	 * <p>Log a message to the console.</p>
	 *
	 * @param type The level of the message (DEBUG, INFO, etc.)
	 * @param loggerType The type of the logger
	 * @param message The message to log
	 */
	private static void log(String type, LoggerType loggerType, String message) {
		System.out.printf("[ %" + maxLength + "s ] [ %s ] %s%n", loggerType.name(), type, message);
	}

	/**
	 * <p>Log a message to the console with the calling class.</p>
	 *
	 * @param type The level of the message (DEBUG, INFO, etc.)
	 * @param loggerType The type of the logger
	 * @param clazz The calling class
	 * @param message The message to log
	 */
	@SuppressWarnings("rawtypes")
	private static void log(String type, LoggerType loggerType, Class clazz, String message) {
		System.out.printf("[ %" + maxLength + "s ] [ %s ] [ %s ] %s%n", loggerType.name(), type, clazz.getSimpleName(), message);
	}
}
