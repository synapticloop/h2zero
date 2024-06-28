package com.synapticloop.h2zero.util;

import com.synapticloop.h2zero.generator.util.AssertionHelper;
import org.junit.Test;

import com.synapticloop.h2zero.generator.exception.H2ZeroParseException;

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
