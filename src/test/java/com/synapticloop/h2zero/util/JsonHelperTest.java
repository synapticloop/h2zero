package com.synapticloop.h2zero.util;
import static org.junit.Assert.*;

import com.synapticloop.h2zero.util.JsonHelper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class JsonHelperTest {
	private JSONObject jsonObject;
	@Before
	public void setup() {
		jsonObject = new JSONObject();
		jsonObject.put("string", "string");
		jsonObject.put("boolean", true);
		jsonObject.put("integer", -1);
	}

	@Test
	public void testGetString() {
		assertEquals("string", JsonHelper.getStringValue(jsonObject, "string", "something"));
		assertEquals("something", JsonHelper.getStringValue(jsonObject, "stringNotFound", "something"));
	}

	@Test
	public void testGetBoolean() {
		assertEquals(true, JsonHelper.getBooleanValue(jsonObject, "boolean", false));
		assertEquals(false, JsonHelper.getBooleanValue(jsonObject, "booleanNotFound", false));
	}

	@Test
	public void testGetInteger() {
		assertEquals(-1, JsonHelper.getIntValue(jsonObject, "integer", -2));
		assertEquals(-2, JsonHelper.getIntValue(jsonObject, "integerNotFound", -2));
	}
}
