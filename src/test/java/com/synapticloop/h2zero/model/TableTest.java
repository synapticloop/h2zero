package com.synapticloop.h2zero.model;

import com.synapticloop.h2zero.model.Options;
import com.synapticloop.h2zero.model.Table;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.synapticloop.h2zero.exception.H2ZeroParseException;

public class TableTest {
	private Table table;

	@Before
	public void setup() {
	}

	@Test(expected = H2ZeroParseException.class)
	public void testNullType() throws H2ZeroParseException {
		String test = "		{\n" + 
				"			\"name\": \"some_name\",\n" + 
				"			\"fields\": [\n" + 
				"				{\n" + 
				"					\"name\": \"one\"\n" + 
				"				}\n" + 
				"			]\n" + 
				"		}\n";
		JSONObject jsonObject = new JSONObject(test);
		JSONObject optionsObject = new JSONObject();
		optionsObject.put("database", "mysql");
		Options options = new Options(optionsObject);
		table = new Table(options, jsonObject, 0);
	}

	@Test(expected = H2ZeroParseException.class)
	public void testNullName() throws H2ZeroParseException {
		String test = "		{\n" + 
				"			\"name\": \"some_name\",\n" + 
				"			\"fields\": [\n" + 
				"				{\n" + 
				"					\"type\": \"one\"\n" + 
				"				}\n" + 
				"			]\n" + 
				"		}\n";
		JSONObject jsonObject = new JSONObject(test);
		JSONObject optionsObject = new JSONObject();
		optionsObject.put("database", "mysql");
		Options options = new Options(optionsObject);
		table = new Table(options, jsonObject, 0);
	}

}
