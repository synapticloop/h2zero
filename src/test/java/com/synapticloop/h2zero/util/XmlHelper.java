package com.synapticloop.h2zero.util;

public class XmlHelper {
	public static String escapeXml(String s) {
		return s.replaceAll("&", "&amp;")
				.replaceAll(">", "&gt;")
				.replaceAll("<", "&lt;")
				.replaceAll("\"", "&quot;")
				.replaceAll("'", "&apos;");
	}
}
