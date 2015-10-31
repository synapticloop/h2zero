package synapticloop.h2zero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/*
 * Copyright (c) 2012-2015 synapticloop.
 * 
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

public class Main {
	private static final String USAGE_TXT = "/usage.txt";

	/**
	 * Simple usage message
	 */

	private Main() {}

	private static void usage() throws IOException {
		InputStream inputStream = Main.class.getResourceAsStream(USAGE_TXT);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}
	}

	private static void parseParamaters(String[] args) {
	}

	public static void main(String[] args) {
//		parseParamaters(args);
		try {
			usage();
		} catch (IOException ex) {
			System.err.println("FATAL!!! could not find the file '" + USAGE_TXT + "' within the jar for reading");
		}
	}
}
