package synapticloop.h2zero.util;

import org.junit.Test;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class AssertionHelperTest {
	@Test(expected = H2ZeroParseException.class)
	public void testAssertedNull() throws H2ZeroParseException {
		AssertionHelper.assertNotNull("hello", null);
	}

	@Test
	public void testAssertedNotNull() throws H2ZeroParseException {
		AssertionHelper.assertNotNull("hello", "there");
	}
}
