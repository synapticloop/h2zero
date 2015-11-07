package synapticloop.h2zero.model;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import synapticloop.h2zero.exception.H2ZeroParseException;

public class OptionsTest {
	private Options options;

	@Before
	public void setup() {
	}

	@Test
	public void testOutputPaths() throws H2ZeroParseException {
		JSONObject jsonObject = new JSONObject();
		JSONObject optionsObject = new JSONObject();
		JSONObject outputObject = new JSONObject();
		outputObject.put(Options.OPTION_JAVA, "something/java");
		outputObject.put(Options.OPTION_SQL, "something/sql");
		outputObject.put(Options.OPTION_WEBAPP, "something/webapp");

		optionsObject.put("output", outputObject);
		jsonObject.put("options", optionsObject);

		options = new Options(jsonObject);
		assertEquals("/something/java/", options.getOutputJava());
		assertEquals("/something/sql/", options.getOutputSql());
		assertEquals("/something/webapp/", options.getOutputWebapp());
	}

	@Test
	public void testOutputPathsAgain() throws H2ZeroParseException {
		JSONObject jsonObject = new JSONObject();
		JSONObject optionsObject = new JSONObject();
		JSONObject outputObject = new JSONObject();
		outputObject.put(Options.OPTION_JAVA, "/something/java");
		outputObject.put(Options.OPTION_SQL, "something/sql/");
		outputObject.put(Options.OPTION_WEBAPP, "/something/webapp/");

		optionsObject.put("output", outputObject);
		jsonObject.put("options", optionsObject);

		options = new Options(jsonObject);
		assertEquals("/something/java/", options.getOutputJava());
		assertEquals("/something/sql/", options.getOutputSql());
		assertEquals("/something/webapp/", options.getOutputWebapp());
	}
}
