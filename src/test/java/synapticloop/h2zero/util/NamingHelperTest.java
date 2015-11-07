package synapticloop.h2zero.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NamingHelperTest {
	private static final String THIS_VARIABLE_NAME = "this_variable_name";
	private NamingHelper namingHelper = null;

	@Before
	public void setup() {
	}

	@Test
	public void testGetFirstUpper() {
		assertEquals("ThisVariableName", NamingHelper.getFirstUpper(THIS_VARIABLE_NAME));
		assertEquals("ThisVariableName", NamingHelper.getFirstUpper("thisVariableName"));
		assertEquals("", NamingHelper.getFirstUpper(null));
	}

	@Test
	public void testGetSecondUpper() {
		assertEquals("thisVariableName", NamingHelper.getSecondUpper(THIS_VARIABLE_NAME));
		assertEquals("thisVariableName", NamingHelper.getSecondUpper("thisVariableName"));
		assertEquals("thisVariableName", NamingHelper.getSecondUpper("ThisVariableName"));
		assertEquals("variable", NamingHelper.getSecondUpper("Variable"));
		assertEquals("", NamingHelper.getFirstUpper(null));
	}

	@Test
	public void testGetFirstPartUpper() {
		assertEquals("This", NamingHelper.getFirstPartUpper(THIS_VARIABLE_NAME));
	}

	@Test
	public void testGetSecondPartUpper() {
		assertEquals("VariableName", NamingHelper.getSecondPartUpper(THIS_VARIABLE_NAME));
		assertEquals("VariableName", NamingHelper.getSecondPartUpper("variableName"));
		assertEquals("", NamingHelper.getSecondPartUpper(null));
	}

	@Test
	public void testGetStaticName() {
		assertEquals(THIS_VARIABLE_NAME.toUpperCase(), NamingHelper.getStaticName(THIS_VARIABLE_NAME));
		assertEquals(THIS_VARIABLE_NAME.toUpperCase(), NamingHelper.getStaticName("thisVariableName"));
		assertEquals(THIS_VARIABLE_NAME.toUpperCase(), NamingHelper.getStaticName("ThisVariableName"));
	}
	
	@Test
	public void testConvertToPath() {
		assertEquals("java/lang/String", NamingHelper.convertToPath("java.lang.String"));
	}
}
