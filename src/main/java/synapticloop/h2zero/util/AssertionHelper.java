package synapticloop.h2zero.util;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class AssertionHelper {
	public static void assertNotNull(String key, String value) throws H2ZeroParseException {
		if(null == value) {
			throw new H2ZeroParseException("Value '" + key + "' cannot be null.");
		}
	}
}
