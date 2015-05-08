package synapticloop.h2zero.validator.constant;

import java.util.ArrayList;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

/**
 * Validate that a constant table does not have any 'inserter' objects on it.  This will add a FATAL warning to the 
 * validation stream
 * 
 * @author synapticloop
 *
 */
public class ConstantInserterValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {

		ArrayList<Table> tables = database.getTables();
		for (Table table : tables) {
			if(table.getIsConstant()) {
				// go through and validate the values
				if(table.getInserters().size() > 0) {
					addFatalMessage("Constant model '" + table.getName() + "' has '" + JSONKeyConstants.INSERTERS + "' which are not allowed, as you may not change the underlying database tables dynamically.");
				}
			}
		}
	}

}
