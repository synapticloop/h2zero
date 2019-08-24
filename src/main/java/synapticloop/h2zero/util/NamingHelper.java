package synapticloop.h2zero.util;

/*
 * Copyright (c) 2012-2019 synapticloop.
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

public class NamingHelper {

	private NamingHelper() {}

	/**
	 * Convert a java package like name to a path, e.g. java.lang.String would be converted to
	 * java/lang/String
	 * 
	 * @param toBeConverted the string to be converted
	 * 
	 * @return the converted path
	 */
	public static String convertToPath(String toBeConverted) {
		return(toBeConverted.replaceAll("\\.", "/"));
	}

	/**
	 * Return a converted name into a first upper-cased, camel-cased name, e.g.
	 * this_variable_name would return ThisVariableName
	 * 
	 * @param name The name to convert
	 * 
	 * @return the first uppercased camel-cased name
	 */
	public static String getFirstUpper(String name) {
		if(name == null) {
			return("");
		}
		return(getConversion(name, true));
	}

	/**
	 * Convert the second part of a variable name to uppercase, examples:
	 * <ul>
	 *   <li>thisVariableName -&gt; thisVariableName</li>
	 *   <li>ThisVariableName -&gt; thisVariableName</li>
	 *   <li>this_variable_name -&gt; thisVariableName</li>
	 * </ul>
	 * 
	 * @param name the name to convert
	 * 
	 * @return the converted name
	 */
	public static String getSecondUpper(String name) {
		if(name == null) {
			return("");
		}
		return(getConversion(name, false));
	}

	public static String getSecondPartUpper(String name) {
		if(name == null) {
			return("");
		}

		int indexOf = name.indexOf("_");

		if(indexOf != -1) {
			return(getConversion(name.substring(indexOf + 1), true));
		} else {
			return(getConversion(name, true));
		}
	}

	public static String getFirstPartUpper(String name) {
		if(name == null) {
			return("");
		}

		int indexOf = name.indexOf("_");

		if(indexOf != -1) {
			return(getConversion(name.substring(0, indexOf + 1), true));
		} else {
			return(getConversion(name, true));
		}
	}

	private static String getConversion(String name, boolean upperCaseFirst) {
		StringBuilder stringBuilder = new StringBuilder();
		String[] splits = name.split("_");
		// TODO - remove/refactor
		if(splits.length == 0) {
			if(upperCaseFirst) {
				stringBuilder.append(splits[0].substring(0,1).toUpperCase() + splits[0].substring(1));
			} else {
				stringBuilder.append(splits[0].substring(0,1).toLowerCase() + splits[0].substring(1));
			}
		} else {
			// TODO - do something here
		}

		for (int i = 0; i < splits.length; i++) {
			String split = splits[i];
			if(i == 0) {
				// TODO refactor - just extract the first char
				if(upperCaseFirst) {
					stringBuilder.append(split.substring(0,1).toUpperCase() + split.substring(1));
				} else {
					stringBuilder.append(split.substring(0,1).toLowerCase() + split.substring(1));
				}
			} else {
				stringBuilder.append(split.substring(0,1).toUpperCase() + split.substring(1));
			}
		}
		return (stringBuilder.toString());
	}

	/**
	 * Return a static constant name (i.e. ALL_UPPER_CASE).  Examples:
	 * <ul>
	 *   <li>thisVariableName -&gt; THIS_VARIABLE_NAME</li>
	 *   <li>ThisVariableName -&gt; THIS_VARIABLE_NAME</li>
	 *   <li>this_variable_name -&gt; THIS_VARIABLE_NAME</li>
	 * </ul>
	 * 
	 * @param name the name to convert
	 * 
	 * @return the converted name
	 */
	public static String getStaticName(String name) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] characters = name.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			char c = characters[i];
			if(Character.isUpperCase(c) && i != 0) {
				stringBuilder.append("_");
			}
			stringBuilder.append(c);
		}
		return(stringBuilder.toString().toUpperCase());
	}

}
