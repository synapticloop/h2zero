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
				addWarnMessage("Table contains fields with a deprecated key of 'finder', please remove and use the '" + JSONKeyConstants.FIELD_FINDERS + "' JSON array as below:");
				addWarnMessage("\"fieldFinders\": [");
				LinkedHashMap<String,String> deprecatedFinders = table.getDeprecatedFinders();
				Iterator<String> iterator = deprecatedFinders.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("  {\"");
					stringBuffer.append(key);
					stringBuffer.append(" \": \"");
					stringBuffer.append(deprecatedFinders.get(key));
					stringBuffer.append("\" }");
					if(iterator.hasNext()) {
						stringBuffer.append(",");
					}
					addWarnMessage(stringBuffer.toString());
				}
				addWarnMessage("]");
			}
		}
		return(isValid);
	}

}
