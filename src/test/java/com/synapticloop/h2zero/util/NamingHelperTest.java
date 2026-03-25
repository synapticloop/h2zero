package com.synapticloop.h2zero.util;

import com.synapticloop.h2zero.generator.util.NamingHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NamingHelperTest {
	private static final String THIS_VARIABLE_NAME = "this_variable_name";
	private static final String THIS_VARIABLE_NAME_CAMEL = "thisVariableName";
	private static final String THIS_VARIABLE_NAME_PASCAL = "ThisVariableName";

	@Before
	public void setup() {
	}

	@Test
	public void testGetFirstUpper() {
		assertEquals("ThisVariableName", NamingHelper.getFirstUpper(THIS_VARIABLE_NAME));
		assertEquals("ThisVariableName", NamingHelper.getFirstUpper(THIS_VARIABLE_NAME_CAMEL));
		assertEquals("ThisVariableName", NamingHelper.getFirstUpper(THIS_VARIABLE_NAME_PASCAL));
		assertEquals("", NamingHelper.getFirstUpper(null));
		assertEquals("", NamingHelper.getFirstUpper(""));
		// Edge case: single underscore or multiple underscores
		assertEquals("", NamingHelper.getFirstUpper("_"));
		assertEquals("", NamingHelper.getFirstUpper("__"));
	}

	@Test
	public void testGetSecondUpper() {
		assertEquals("thisVariableName", NamingHelper.getSecondUpper(THIS_VARIABLE_NAME));
		assertEquals("thisVariableName", NamingHelper.getSecondUpper(THIS_VARIABLE_NAME_CAMEL));
		assertEquals("thisVariableName", NamingHelper.getSecondUpper(THIS_VARIABLE_NAME_PASCAL));
		assertEquals("variable", NamingHelper.getSecondUpper("Variable"));
		assertEquals("", NamingHelper.getSecondUpper(null));
		assertEquals("", NamingHelper.getSecondUpper(""));
		assertEquals("", NamingHelper.getSecondUpper("_"));
	}

	@Test
	public void testGetFirstPartUpper() {
		assertEquals("This", NamingHelper.getFirstPartUpper(THIS_VARIABLE_NAME));
		assertEquals("Variable", NamingHelper.getFirstPartUpper("variable"));
		assertEquals("", NamingHelper.getFirstPartUpper(null));
		assertEquals("", NamingHelper.getFirstPartUpper(""));
	}

	@Test
	public void testGetSecondPartUpper() {
		assertEquals("VariableName", NamingHelper.getSecondPartUpper(THIS_VARIABLE_NAME));
		assertEquals("VariableName", NamingHelper.getSecondPartUpper("variableName"));
		assertEquals("", NamingHelper.getSecondPartUpper(null));
		assertEquals("", NamingHelper.getSecondPartUpper(""));
	}

	@Test
	public void testGetStaticName() {
		assertEquals("THIS_VARIABLE_NAME", NamingHelper.getStaticName(THIS_VARIABLE_NAME));
		assertEquals("THIS_VARIABLE_NAME", NamingHelper.getStaticName(THIS_VARIABLE_NAME_CAMEL));
		assertEquals("THIS_VARIABLE_NAME", NamingHelper.getStaticName(THIS_VARIABLE_NAME_PASCAL));
		
		// Test idempotency and other cases
		assertEquals("THIS_VARIABLE_NAME", NamingHelper.getStaticName("THIS_VARIABLE_NAME"));
		assertEquals("A_B_C", NamingHelper.getStaticName("ABC"));
		assertEquals("A_B_C", NamingHelper.getStaticName("a_b_c"));
	}
	
	@Test
	public void testConvertToPath() {
		assertEquals("java/lang/String", NamingHelper.convertToPath("java.lang.String"));
		assertEquals("Simple", NamingHelper.convertToPath("Simple"));
	}
}
