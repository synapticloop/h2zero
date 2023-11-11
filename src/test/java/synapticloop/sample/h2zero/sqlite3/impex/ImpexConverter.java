package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - -
//    with the use of synapticloop templar templating language
//                 (/impex/impex-converter.templar)


import synapticloop.h2zero.exception.H2ZeroParseException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ImpexConverter {
	private static void throwIfNotAllowableNullRepresentation(String value, boolean allowNulls) throws H2ZeroParseException {
		if (value.length() == 0 && !allowNulls) {
			throw new H2ZeroParseException("Value for field is an empty string (i.e. null), but nulls are not allowed.");
		}
	}

	public static Long convertLong(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);
		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		// now parse
		try {
			return (Long.valueOf(value));
		} catch (NumberFormatException ex) {
			throw new H2ZeroParseException(String.format("Could not parse value '%s' to a Long.", value));
		}
	}

	public static Boolean convertBoolean(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		// now parse
		return switch (value) {
			case "1" -> (true);
			case "0" -> (false);
			default ->
					throw new H2ZeroParseException("Could not parse Boolean value of '%s', allowable values are '1' for 'true' and '0' for 'false'.");
		};
	}

	public static Date convertDate(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		// this should not happen as we have checked above - however we may not be
		// able to parse the long
		try {
			return (new Date(convertLong(value, allowNulls)));
		} catch (H2ZeroParseException ex) {
			throw new H2ZeroParseException(String.format("Could not parse a Date from its component long value. Original exception: %s", ex.getMessage()));
		}
	}

	public static Timestamp convertTimestamp(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		// this should not happen as we have checked above - however we may not be
		// able to parse the long
		try {
			return (new Timestamp(convertLong(value, allowNulls)));
		} catch (H2ZeroParseException ex) {
			throw new H2ZeroParseException(String.format("Could not parse a Date from its component long value. Original exception: %s", ex.getMessage()));
		}

	}

	public static Double convertDouble(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		try {
			return(Double.valueOf(value));
		} catch (NumberFormatException ex) {
			throw new H2ZeroParseException(String.format("Could not parse Double value of '%s'.", value));
		}
	}

	public static Float convertFloat(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		try {
			return(Float.valueOf(value));
		} catch (NumberFormatException ex) {
			throw new H2ZeroParseException(String.format("Could not parse Float value of '%s'.", value));
		}
	}

	public static Integer convertInteger(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		try {
			return(Integer.valueOf(value));
		} catch (NumberFormatException ex) {
			throw new H2ZeroParseException(String.format("Could not parse Integer value of '%s'.", value));
		}
	}

	public static BigDecimal convertBigDecimal(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		try {
			return(new BigDecimal(value));
		} catch (NumberFormatException ex) {
			throw new H2ZeroParseException(String.format("Could not parse BigDecimal value of '%s'.", value));
		}
	}

	public static Short convertShort(String value, boolean allowNulls) throws H2ZeroParseException {
		throwIfNotAllowableNullRepresentation(value, allowNulls);

		// we only get here if we haven't thrown
		if (value.length() == 0) {
			return (null);
		}

		try {
			return(Short.valueOf(value));
		} catch (NumberFormatException ex) {
			throw new H2ZeroParseException(String.format("Could not parse Short value of '%s'.", value));
		}
	}

	public static String convertString(String value, boolean allowNulls) throws H2ZeroParseException {
		// string always start and end with a '"' character
		if (value.length() == 2 && !allowNulls) {
			throw new H2ZeroParseException("Value for field is an empty string (i.e. null), but nulls are not allowed.");
		}

		// we only get here if we haven't thrown
		if (value.length() == 2) {
			return (null);
		}

		return(value.substring(0, value.length() -1)
				.replace("\\r", "\r")
				.replace("\\f", "\f")
				.replace("\\t", "\t"));
	}

}
