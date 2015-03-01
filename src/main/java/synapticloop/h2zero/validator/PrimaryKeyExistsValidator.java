package synapticloop.h2zero.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class PrimaryKeyExistsValidator extends Validator {

	@Override
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<BaseField> fields = table.getFields();
			int numPrimaryKeys = 0;
			for (BaseField baseField : fields) {
				if(baseField.getPrimary()) {
					numPrimaryKeys++;
				}
			}

			switch (numPrimaryKeys) {
			case 0:
				isValid = false;
				addFatalMessage("Table '" + table.getName() + "' __MUST__ define a primary key.");
				break;
			case 1:
				// all is good
				break;
			default:
				// more than one - oh oh
				isValid = false;
				addFatalMessage("Table '" + table.getName() + "' __MUST__ define only 1 primary key, it has '" + numPrimaryKeys + "'.");
				break;
			}
		}

		return(isValid);
	}

}
