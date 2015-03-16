package synapticloop.h2zero.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;

public class TableFinderKeyValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			if(table.getHasDeprecatedFinder()) {
				ArrayList<String> warnMessages = new ArrayList<String>();

				warnMessages.add("Table '" + table.getName() + "' contains fields with a deprecated key of 'finder', please remove and use the '" + JSONKeyConstants.FIELD_FINDERS + "' JSON array as below:");
				warnMessages.add("\"fieldFinders\": [");
				LinkedHashMap<String,String> deprecatedFinders = table.getDeprecatedFinders();
				Iterator<String> iterator = deprecatedFinders.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("  { \"");
					stringBuffer.append(key);
					stringBuffer.append("\": \"");
					stringBuffer.append(deprecatedFinders.get(key));
					stringBuffer.append("\" }");
					if(iterator.hasNext()) {
						stringBuffer.append(",");
					}
					warnMessages.add(stringBuffer.toString());
				}
				warnMessages.add("]");

				String[] messages = new String[warnMessages.size()];
				int i = 0;
				for (String warnMessage: warnMessages) {
					messages[i] = warnMessage;
					i++;
				}

				addWarnMessage(messages);
			}
		}
		return(isValid);
	}

}
