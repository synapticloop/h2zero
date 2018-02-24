package synapticloop.h2zero.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.h2zero.H2ZeroParser;
import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.h2zero.util.SimpleLogger.LoggerType;
import synapticloop.h2zero.validator.BaseValidator;


public class Options {
	public static final String OPTION_FORMBEANS = "formbeans";
	public static final String OPTION_ADMINPAGES = "adminpages";
	public static final String OPTION_TAGLIB = "taglib";
	public static final String OPTION_JSP = "jsp";
	public static final String OPTION_JAVA = "java";
	public static final String OPTION_SQL = "sql";
	public static final String OPTION_WEBAPP = "webapp";
	public static final String OPTION_RESTFUL_SERVLET = "restfulservlet";
	public static final String OPTION_REPORTS = "reports";
	public static final String OPTION_DATABASE = "reports";

	public static final String OPTION_OUTPUT = "output";

	private static Set<String> ALLOWABLE_GENERATORS = new HashSet<String>();
	static {
		ALLOWABLE_GENERATORS.add(OPTION_SQL);
		ALLOWABLE_GENERATORS.add(OPTION_JAVA);
		ALLOWABLE_GENERATORS.add(OPTION_JSP);
		ALLOWABLE_GENERATORS.add(OPTION_TAGLIB);
		ALLOWABLE_GENERATORS.add(OPTION_ADMINPAGES);
		ALLOWABLE_GENERATORS.add(OPTION_FORMBEANS);
		ALLOWABLE_GENERATORS.add(OPTION_REPORTS);
	}

	private static Set<String> ALLOWABLE_DATABASES = new HashSet<String>();
	static {
		ALLOWABLE_DATABASES.add("mysql");
		ALLOWABLE_DATABASES.add("sqlite3");
	}

	/*
	 * INSTANCE VARIABLES
	 */
	private boolean metrics = false;
	private String database = "mysql";
	private String outputJava = "/src/main/java/";
	private String outputSql = "/src/main/sql/";
	private String outputWebapp = "/src/main/webapps/";
	private String outputReports = "/build/reports/";

	private Set<String> generators = new HashSet<String>();

	public Options(JSONObject jsonObject) throws H2ZeroParseException {
		JSONObject optionsJson = jsonObject.optJSONObject("options");
		if(null == optionsJson) {
			// options are optional
			return;
		}

		this.metrics = optionsJson.optBoolean("metrics", false);
		this.database = optionsJson.optString("database", "mysql");
		SimpleLogger.logInfo(LoggerType.OPTIONS, "Generating for database type '" + database + "'.");

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

		updateValidators(optionsJson.optJSONObject("validators"));

		// now we are going to update the output paths
		JSONObject outputJson = optionsJson.optJSONObject("output");
		if(null != outputJson) {
			outputJava = JsonHelper.getStringValue(outputJson, OPTION_JAVA, outputJava);
			outputSql = JsonHelper.getStringValue(outputJson, OPTION_SQL, outputSql);
			outputWebapp = JsonHelper.getStringValue(outputJson, OPTION_WEBAPP, outputWebapp);
		}

		// now ensure that there are slashes on both sides of the output directory
		outputJava = convertToAbsolutePath(outputJava);
		outputSql = convertToAbsolutePath(outputSql);
		outputWebapp = convertToAbsolutePath(outputWebapp);
	}

	private String convertToAbsolutePath(String name) {
		StringBuilder stringBuilder = new StringBuilder();

		if(!name.startsWith("/")) {
			stringBuilder.append("/");
		}

		stringBuilder.append(name);

		if(!name.endsWith("/")) {
			stringBuilder.append("/");
		}
		
		return(stringBuilder.toString());
	}

	private void updateValidators(JSONObject validatorJson) {
		if(null == validatorJson) {
			SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, "No validator options found.");
		} else {
			Iterator<String> keys = validatorJson.keys();
			while (keys.hasNext()) {
				String validatorName = keys.next();
				BaseValidator validator = H2ZeroParser.getValidatorByName(validatorName);
				if(null != validator) {
					SimpleLogger.logInfo(LoggerType.OPTIONS_VALIDATOR, "[ " + validatorName + " ] Parsing options...");
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
	public String getDatabase() { return database; }
	public void setDatabase(String database) { this.database = database; }

	public String getOutputJava() { return(outputJava); }
	public String getOutputWebapp() { return(outputWebapp); }
	public String getOutputSql() { return(outputSql); }
	public String getOutputReports() { return(outputReports); }
}
