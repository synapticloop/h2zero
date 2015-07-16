package synapticloop.h2zero.validator.table;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class TablePrimaryKeyExistsValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
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
	}

}
