package synapticloop.h2zero.util;

import java.util.HashSet;

public class NamingHelper {
	private static HashSet<Character> UPPER_CASE = new HashSet<Character>();
	static {
		UPPER_CASE.add('A');
		UPPER_CASE.add('B');
		UPPER_CASE.add('C');
		UPPER_CASE.add('D');
		UPPER_CASE.add('E');
		UPPER_CASE.add('F');
		UPPER_CASE.add('G');
		UPPER_CASE.add('H');
		UPPER_CASE.add('I');
		UPPER_CASE.add('J');
		UPPER_CASE.add('K');
		UPPER_CASE.add('L');
		UPPER_CASE.add('M');
		UPPER_CASE.add('N');
		UPPER_CASE.add('O');
		UPPER_CASE.add('P');
		UPPER_CASE.add('Q');
		UPPER_CASE.add('R');
		UPPER_CASE.add('S');
		UPPER_CASE.add('T');
		UPPER_CASE.add('U');
		UPPER_CASE.add('V');
		UPPER_CASE.add('W');
		UPPER_CASE.add('X');
		UPPER_CASE.add('Y');
		UPPER_CASE.add('Z');
	}

	public static String convertToPath(String toBeConverted) {
		return(toBeConverted.replaceAll("\\.", "/"));
	}

	public static String getFirstUpper(String name) {
		if(name == null) {
			return("");
		}
		return(getConversion(name, true));
	}

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
			return(getConversion(name.substring(indexOf + 1), false));
		} else {
			return(getConversion(name, false));
		}
	}

	public static String getFirstPartUpper(String name) {
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

	public static String getStaticName(String name) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] characters = name.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			char c = characters[i];
			if(UPPER_CASE.contains(c)) {
				stringBuilder.append("_");
			}
			stringBuilder.append(c);
		}
		return(stringBuilder.toString().toUpperCase());
	}

}
