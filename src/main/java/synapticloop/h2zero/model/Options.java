package synapticloop.h2zero.model;

import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;


public class Options {
	private boolean statistics = false;
	private String logging = "";
	private HashSet<String> generators = new HashSet<String>();

	private static HashSet<String> ALLOWABLE_GENERATORS = new HashSet<String>();
	static {
		ALLOWABLE_GENERATORS.add("sql");
		ALLOWABLE_GENERATORS.add("java");
		ALLOWABLE_GENERATORS.add("jsp");
		ALLOWABLE_GENERATORS.add("taglib");
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

		this.statistics = optionsJson.optBoolean("statistics", false);
		this.logging = optionsJson.optString("logging", "");
		JSONArray generatorArray = optionsJson.optJSONArray("generators");
		if(null == generatorArray) {
			// add them all
			System.out.println("WARN: You have not defined any generators - so we are going to generate all.");

			generators.addAll(ALLOWABLE_GENERATORS);
		} else {
			for (int i = 0; i < generatorArray.length(); i++) {
				String generator = generatorArray.optString(i, null);
				if(null != generator && ALLOWABLE_GENERATORS.contains(generator.trim().toLowerCase())) {
					generators.add(generator.trim().toLowerCase());
				} else {
					// TODO - auto generate
					throw new H2ZeroParseException("Unknown generator type of '" + generator + "'.  Allowable types are 'sql', 'java', 'taglib', 'jsp'");
				}
			}
		}

		if(!ALLOWABLE_LOGGERS.contains(this.getLogging())) {
			throw new H2ZeroParseException("Unknown logging type of '" + this.logging + "'.");
		}
	}

	public boolean hasGenerator(String generator) { return(generators.contains(generator)); }
	public boolean hasGenerators() { return(generators.size() != 0); }
	public boolean getStatistics() { return statistics; }
	public void setStatistics(boolean statistics) { this.statistics = statistics; }
	public String getLogging() { return logging; }
	public void setLogging(String logging) { this.logging = logging; }
	public boolean hasLogging() { return(!this.logging.equals("")); }
}
