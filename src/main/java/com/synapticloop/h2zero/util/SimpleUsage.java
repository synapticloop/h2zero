package com.synapticloop.h2zero.util;

import com.synapticloop.h2zero.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SimpleUsage {
	public static final String USAGE_H2ZERO_TXT = "/usage_h2zero.txt";
	public static final String USAGE_GENERATE_TXT = "/usage_generate.txt";

	public static void usageGenerate() {
		InputStream inputStream = Main.class.getResourceAsStream(USAGE_H2ZERO_TXT);
		if(null == inputStream) {
			// do nothing
			// return
		}

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException ignored) {
			// do nothing
		}
	}

	public static void h2zeroUsageAndExit(String message) throws IOException {
		if(null != message) {
			System.out.println(message);
		}

		InputStream inputStream = Main.class.getResourceAsStream(USAGE_H2ZERO_TXT);
		if(null == inputStream) {
			System.err.println("Cannot load the usage text file from the package...");
			System.exit(-1);
		}

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}
		System.exit(0);
	}

}
