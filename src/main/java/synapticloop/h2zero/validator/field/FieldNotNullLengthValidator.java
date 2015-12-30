package synapticloop.h2zero.validator.field;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.BaseValidator;

/**
 * This validator checks to see whether there are some fields which are marked 
 * as nullable, however do not have a minimum length set on them 
 * 
 * @author synapticloop
 *
 */
public class FieldNotNullLengthValidator extends BaseValidator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<BaseField> fields = table.getFields();
			for (BaseField baseField : fields) {
				if(!baseField.getNullable() && baseField.getMinLength() == 0 && "String".equals(baseField.getJavaType())) {
					addWarnMessage("Table field '" + table.getName() + "." + baseField.getName() + "' is not allowed to be null, but has a minimum length of 0");
				}
			}
		}
	}

}
