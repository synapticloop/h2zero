package synapticloop.h2zero.exception;

import org.junit.Before;
import org.junit.Test;

public class H2ZeroParseExceptionTest {
	private H2ZeroParseException h2ZeroParseException;

	@Before
	public void setup() {
		h2ZeroParseException = new H2ZeroParseException("this is a test message");
	}

	@Test
	public void testException() {
		h2ZeroParseException = new H2ZeroParseException("this is a test message");
	}

}
