package synapticloop.h2zero.validator.finder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class FinderSelectClauseBeanNameValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		Map<String, String> selectClauseBeanNames = new HashMap<String, String>();

		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				if(null != finder.getSelectClause()) {
					String name = finder.getName();
					List<BaseField> selectFields = finder.getSelectFields();

					StringBuilder stringBuilder = new StringBuilder();
					for (BaseField baseField : selectFields) {
						stringBuilder.append(":");
						stringBuilder.append(baseField.getType());
					}


					String params = stringBuilder.toString();

					if(selectClauseBeanNames.containsKey(name)) {
						// lets have a look at the parameters
						String value = selectClauseBeanNames.get(name);
						
						if(!value.equals(params)) {
							addFatalMessage("Finder '" + table.getName() + "." + finder.getName() + "' with a select will generate and override a duplicate bean with different parameters.");
						}
					} else {
						selectClauseBeanNames.put(name, params);
					}
				}
			}
		}
	}

}
