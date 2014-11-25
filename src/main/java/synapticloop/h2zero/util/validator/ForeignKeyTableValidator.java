package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class ForeignKeyTableValidator implements DatabaseValidator {
	private ArrayList<String> messages = new ArrayList<String>();

	public boolean isValid(Database database) {
		boolean isValid = true;

		ArrayList<Table> tables = database.getTables();

		for (Table table : tables) {
			ArrayList<BaseField> baseFields = table.getFields();
			for (BaseField baseField : baseFields) {
				if(null != baseField.getForeignKeyTable() && null == baseField.getForeignKeyTableLookup()) {
					isValid = false;
					messages.add("FATAL: Table '" + table.getName() + "' with field '" + baseField.getName() + "' foreign key references table '" + baseField.getForeignKeyTable() + "', which does not exist.");
				}
			}
		}

		if(isValid) {
			messages.add("INFO: Valid");
		}

		return isValid;
	}

	public ArrayList<String> getMessages() {
		return(messages);
	}

}
