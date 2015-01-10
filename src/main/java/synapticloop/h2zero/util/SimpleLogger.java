package synapticloop.h2zero.util;

public class SimpleLogger {
	public enum LoggerType {
		H2ZERO_OPTIONS,
		GENERATORS,
		H2ZERO,
		TEMPLAR_LOAD,
		TEMPLAR_RENDER,
		VALIDATOR,
		H2ZERO_PARSE,
		SUMMARY
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

	public static final String INFO = " INFO";
	public static final String WARN = " WARN";
	public static final String ERROR = "ERROR";
	public static final String FATAL = "FATAL";

	public static void logInfo(LoggerType loggerType, String message) {
		log(INFO, loggerType, message);
	}

	public static void logWarn(LoggerType loggerType, String message) {
		log(WARN, loggerType, message);
	}

	public static void logError(LoggerType loggerType, String message) {
		log(ERROR, loggerType, message);
	}

	public static void logFatal(LoggerType loggerType, String message) {
		log(FATAL, loggerType, message);
	}

	private static void log(String type, LoggerType loggerType, String message) {
		System.out.println(String.format("[ %" + maxLength + "s ] [ %s ] %s", loggerType.name(), type, message));
	}
}
