package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class UniqeAndIndexValidator extends Validator {

	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getIndex() && baseField.getUnique()) {
					String name = baseField.getName();
					addWarnMessage("Field '" + table.getName() + "." + name + "' is both 'unique' and has an 'index', which is redundant.");
				}
			}
		}

		return(isValid);
	}

}
