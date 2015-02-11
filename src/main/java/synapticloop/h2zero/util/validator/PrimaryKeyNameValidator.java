package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class PrimaryKeyNameValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getPrimary()) {
					if(baseField.getName().compareToIgnoreCase("id_" + table.getName()) != 0) {
						addWarnMessage("Primary key for table '" + table.getName() + "' __SHOULD__ be named 'id_" + table.getName() + "', currently named '" + baseField.getName() + "'.");
					}
				}
			}
		}

		return(isValid);	}

}
