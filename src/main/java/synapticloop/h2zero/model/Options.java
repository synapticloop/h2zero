package synapticloop.h2zero.model;

import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;


public class Options {
	private boolean metrics = false;
	private String logging = "";

	private HashSet<String> generators = new HashSet<String>();

	public static final String OPTION_FORMBEANS = "formbeans";
	public static final String OPTION_ADMINPAGES = "adminpages";
	public static final String OPTION_TAGLIB = "taglib";
	public static final String OPTION_JSP = "jsp";
	public static final String OPTION_JAVA = "java";
	public static final String OPTION_SQL = "sql";

	private static HashSet<String> ALLOWABLE_GENERATORS = new HashSet<String>();
	static {
		ALLOWABLE_GENERATORS.add(OPTION_SQL);
		ALLOWABLE_GENERATORS.add(OPTION_JAVA);
		ALLOWABLE_GENERATORS.add(OPTION_JSP);
		ALLOWABLE_GENERATORS.add(OPTION_TAGLIB);
		ALLOWABLE_GENERATORS.add(OPTION_ADMINPAGES);
		ALLOWABLE_GENERATORS.add(OPTION_FORMBEANS);
	}

	private static HashSet<String> ALLOWABLE_LOGGERS = new HashSet<String>();
	static {
		ALLOWABLE_LOGGERS.add("");
		ALLOWABLE_LOGGERS.add("log4j");
	}

	public Options(JSONObject jsonObject) throws H2ZeroParseException {
		JSONObject optionsJson = null;
		try {
			optionsJson = jsonObject.getJSONObject("options");
		} catch (JSONException ojjsonex) {
			// do nothing - it is optional
			return;
		}

		this.metrics = optionsJson.optBoolean("metrics", false);
		this.logging = optionsJson.optString("logging", "");

		JSONArray generatorArray = optionsJson.optJSONArray("generators");
		if(null == generatorArray) {
			// add them all
			SimpleLogger.logWarn(LoggerType.GENERATORS, "You have not defined any generators - so we are going to generate all");

			generators.addAll(ALLOWABLE_GENERATORS);
		} else {
			for (int i = 0; i < generatorArray.length(); i++) {
				String generator = generatorArray.optString(i, null);
				if(null != generator && ALLOWABLE_GENERATORS.contains(generator.trim().toLowerCase())) {
					generators.add(generator.trim().toLowerCase());
				} else {
					StringBuilder stringBuilder = new StringBuilder();
					int j = 0;
					for (String allowableGenerator : ALLOWABLE_GENERATORS) {
						if(j != 0) {
							stringBuilder.append(", ");
						}
						j = 1;
						stringBuilder.append("'");
						stringBuilder.append(allowableGenerator);
						stringBuilder.append("'");
					}
					SimpleLogger.logFatal(LoggerType.GENERATORS, "Unknown generator type of '" + generator + "'.  Allowable types are " + stringBuilder.toString() + ".");
					throw new H2ZeroParseException("Unknown generator type of '" + generator + "'.");
				}
			}
		}

		HashSet<String> disabledGenerators = new HashSet<String>();
		disabledGenerators.addAll(ALLOWABLE_GENERATORS);

		Iterator<String> generatorsIterator = generators.iterator();
		while (generatorsIterator.hasNext()) {
			String next = (String) generatorsIterator.next();
			SimpleLogger.logInfo(LoggerType.GENERATORS, "ENABLED  Generator '" + next + "'");
			disabledGenerators.remove(next);
		}

		Iterator<String> disabledIterator = disabledGenerators.iterator();
		while (disabledIterator.hasNext()) {
			String next = (String) disabledIterator.next();
			SimpleLogger.logInfo(LoggerType.GENERATORS, "DISABLED Generator '" + next + "'");
		}

		if(!ALLOWABLE_LOGGERS.contains(this.getLogging())) {
			throw new H2ZeroParseException("Unknown logging type of '" + this.logging + "'.");
		}
	}

	public boolean hasGenerator(String generator) { return(generators.contains(generator)); }
	public boolean hasGenerators() { return(generators.size() != 0); }
	public boolean getMetrics() { return metrics; }
	public void setMetrics(boolean metrics) { this.metrics = metrics; }
	public String getLogging() { return logging; }
	public void setLogging(String logging) { this.logging = logging; }
	public boolean hasLogging() { return(!this.logging.equals("")); }
}
