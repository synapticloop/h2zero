package synapticloop.h2zero.revenge.model;

public class Options {

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("  \"options\": {\n");
		stringBuilder.append("    \"generators\": [ \"java\", \"sql\" ]\n");
		stringBuilder.append("  },\n");
		return(stringBuilder.toString());
	}

}
