package synapticloop.h2zero.validator.constant;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.validator.Validator;

/**
 * Validate that a constant table does not have any 'updater' objects on it.  This will add a FATAL warning to the 
 * validation stream
 * 
 * @author synapticloop
 *
 */

public class ConstantUpdaterValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			if(table.getIsConstant() && !table.getUpdaters().isEmpty()) {
				addFatalMessage("Constant model '" + table.getName() + "' has '" + JSONKeyConstants.UPDATERS + "' which are not allowed, as you may not change the underlying database tables dynamically.");
			}
		}
	}

}
