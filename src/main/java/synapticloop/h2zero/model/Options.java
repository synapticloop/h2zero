package synapticloop.h2zero.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.h2zero.validator.BaseValidator;


public class Options {
	private boolean metrics = false;
	private String logging = "";

	private Set<String> generators = new HashSet<String>();

	public static final String OPTION_FORMBEANS = "formbeans";
	public static final String OPTION_ADMINPAGES = "adminpages";
	public static final String OPTION_TAGLIB = "taglib";
	public static final String OPTION_JSP = "jsp";
	public static final String OPTION_JAVA = "java";
	public static final String OPTION_SQL = "sql";
	public static final String OPTION_RESTFUL_SERVLET = "restfulservlet";

	private static Set<String> ALLOWABLE_GENERATORS = new HashSet<String>();
	static {
		ALLOWABLE_GENERATORS.add(OPTION_SQL);
		ALLOWABLE_GENERATORS.add(OPTION_JAVA);
		ALLOWABLE_GENERATORS.add(OPTION_JSP);
		ALLOWABLE_GENERATORS.add(OPTION_TAGLIB);
		ALLOWABLE_GENERATORS.add(OPTION_ADMINPAGES);
		ALLOWABLE_GENERATORS.add(OPTION_FORMBEANS);
	}

	private static Set<String> ALLOWABLE_LOGGERS = new HashSet<String>();
	static {
		ALLOWABLE_LOGGERS.add("");
		ALLOWABLE_LOGGERS.add("log4j");
	}

	public Options(JSONObject jsonObject) throws H2ZeroParseException {
		JSONObject optionsJson = null;
		try {
			optionsJson = jsonObject.getJSONObject("options");
		} catch (JSONException jsonex) {
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
			enableGenerators(generatorArray);
		}

		Set<String> disabledGenerators = new HashSet<String>();
		disabledGenerators.addAll(ALLOWABLE_GENERATORS);

		Iterator<String> generatorsIterator = generators.iterator();
		while (generatorsIterator.hasNext()) {
			String next = generatorsIterator.next();
			SimpleLogger.logInfo(LoggerType.GENERATORS, "[ ENABLED  ] Generator '" + next + "'");
			disabledGenerators.remove(next);
		}

		Iterator<String> disabledIterator = disabledGenerators.iterator();
		while (disabledIterator.hasNext()) {
			String next = disabledIterator.next();
			SimpleLogger.logInfo(LoggerType.GENERATORS, "[ DISABLED ] Generator '" + next + "'");
		}

		if(!ALLOWABLE_LOGGERS.contains(this.getLogging())) {
			throw new H2ZeroParseException("Unknown logging type of '" + this.logging + "'.");
		}

		updateValidators(optionsJson.optJSONObject("validators"));
	}

	private void updateValidators(JSONObject validatorJson) {
		if(null == validatorJson) {
			SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, "No validator options found.");
		} else {
			Iterator<String> keys = validatorJson.keys();
			while (keys.hasNext()) {
				String validatorName = (String) keys.next();
				BaseValidator validator = H2ZeroParser.getValidatorByName(validatorName);
				if(null != validator) {
					SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, "Parsing options for validator '" + validatorName + "'.");
					validator.parseAndValidateOptions(validatorJson.getJSONObject(validatorName));
				} else {
					SimpleLogger.logError(LoggerType.OPTIONS_VALIDATOR, "Could not find validator for validator name '" + validatorName + "', ignoring...");
				}
			}
		}
		
	}

	private void enableGenerators(JSONArray generatorArray) throws H2ZeroParseException {
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

	public boolean hasGenerator(String generator) { return(generators.contains(generator)); }
	public boolean hasGenerators() { return(!generators.isEmpty()); }
	public boolean getMetrics() { return metrics; }
	public void setMetrics(boolean metrics) { this.metrics = metrics; }
	public String getLogging() { return logging; }
	public void setLogging(String logging) { this.logging = logging; }
	public boolean hasLogging() { return(!"".equals(this.logging)); }
}
