package com.synapticloop.h2zero.model;

import static org.junit.Assert.*;

import com.synapticloop.h2zero.model.Options;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.h2zero.exception.H2ZeroParseException;
import com.synapticloop.h2zero.extension.Extension;

public class OptionsTest {
	private Options options;
	public static String JSONExtensionString = "{\n" + 
			"	\"options\": {\n" + 
			"		\"extensions\": [\n" + 
			"			\"synapticloop.h2zero.extension.BasicExtension\"\n" + 
			"		],\n" + 
			"\n" + 
			"		\"synapticloop.h2zero.extension.BasicExtension\": {\n" + 
			"			\"something\": \"else\",\n" + 
			"			\"warning\": false\n" + 
			"		}\n" + 
			"	}\n" + 
			"}";

	public static String JSONMissingExtensionString = "{\n" + 
			"	\"options\": {\n" + 
			"		\"extensions\": [\n" + 
			"			\"synapticloop.h2zero.extension.ThisIsAnExceptionDoesNotExistBasicExtension\"\n" + 
			"		]\n" + 
			"	}\n" + 
			"}";

	@Before
	public void setup() {
	}

	@Test
	public void testOutputPaths() throws H2ZeroParseException {
		JSONObject jsonObject = new JSONObject();
		JSONObject optionsObject = new JSONObject();
		JSONObject outputObject = new JSONObject();
		outputObject.put(Options.OPTION_CODE, "something/java");
		outputObject.put(Options.OPTION_RESOURCES, "something/resources");
		outputObject.put(Options.OPTION_BUILD, "something/build");

		optionsObject.put("output", outputObject);
		jsonObject.put("options", optionsObject);

		options = new Options(jsonObject);
		assertEquals("/something/java/", options.getOutputCode());
		assertEquals("/something/resources/", options.getOutputResources());
		assertEquals("/something/build/", options.getOutputBuild());
	}

	@Test
	public void testOutputPathsAgain() throws H2ZeroParseException {
		JSONObject jsonObject = new JSONObject();
		JSONObject optionsObject = new JSONObject();
		JSONObject outputObject = new JSONObject();
		outputObject.put(Options.OPTION_CODE, "something/java");
		outputObject.put(Options.OPTION_RESOURCES, "something/resources");
		outputObject.put(Options.OPTION_BUILD, "something/build");

		optionsObject.put("output", outputObject);
		jsonObject.put("options", optionsObject);

		options = new Options(jsonObject);
		assertEquals("/something/java/", options.getOutputCode());
		assertEquals("/something/resources/", options.getOutputResources());
		assertEquals("/something/build/", options.getOutputBuild());
	}

	@Test
	public void testExtension() throws JSONException, H2ZeroParseException {
		options = new Options(new JSONObject(JSONExtensionString));
		assertEquals(1, options.getExtensions().size());
		assertTrue(options.hasExtensions());
		Object[] extensionArray = options.getExtensions().keySet().toArray();
		Extension extension = (Extension)extensionArray[0];
		assertEquals("BasicExtension", extension.getClass().getSimpleName()); 
	}

	@Test(expected = H2ZeroParseException.class)
	public void testMissingExtension() throws JSONException, H2ZeroParseException {
		options = new Options(new JSONObject(JSONMissingExtensionString));
	}

	@Test(expected = H2ZeroParseException.class)
	public void testNullOptions() throws H2ZeroParseException {
		options = new Options(null);
	}

	@Test
	public void testEmptyOptions() throws H2ZeroParseException {
		options = new Options(new JSONObject());
	}
}
