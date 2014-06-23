package synapticloop.h2zero.model;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;


public class Options {
	private boolean statistics = false;
	private String logging = "";

	private static HashSet<String> allowableLoggers = new HashSet<String>();
	static {
		allowableLoggers.add("");
		allowableLoggers.add("log4j");
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

		if(!allowableLoggers.contains(this.getLogging())) {
			throw new H2ZeroParseException("Unknown logging type of '" + this.logging + "'.");
		}
	}

	public boolean getStatistics() { return statistics; }
	public void setStatistics(boolean statistics) { this.statistics = statistics; }
	public String getLogging() { return logging; }
	public void setLogging(String logging) { this.logging = logging; }
	public boolean hasLogging() { return(!this.logging.equals("")); }
}
