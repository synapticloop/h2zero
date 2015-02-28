package synapticloop.h2zero.util.validator;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;

public class FinderAutoIndexValidator extends Validator {
	public boolean isValid(Database database, Options options) {
		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			ArrayList<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(baseField.getIsAutoGeneratedFinder() && !baseField.getIndex()) {
					addWarnMessage("Auto generated finder for field '" + table.getName() + "." + baseField.getName() + "' is not indexed which may slow down database lookups.");
				}
			}
			
		}
		return(isValid);
	}

}
