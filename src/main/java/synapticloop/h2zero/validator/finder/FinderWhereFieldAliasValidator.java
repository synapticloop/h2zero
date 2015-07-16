package synapticloop.h2zero.validator.finder;

import java.util.List;

import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Finder;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.Table;
import synapticloop.h2zero.model.field.BaseField;
import synapticloop.h2zero.validator.Validator;

public class FinderWhereFieldAliasValidator extends Validator {

	@Override
	public void validate(Database database, Options options) {
		List<Table> tables = database.getTables();
		for (Table table : tables) {
			List<Finder> finders = table.getFinders();
			for (Finder finder : finders) {
				if(finder.getHasWhereFieldAliases()) {
					// go through and ensure that all of the finder where fields has an alias
					List<BaseField> whereFields = finder.getWhereFields();
					for (BaseField baseField : whereFields) {
						if(!baseField.getHasAlias()) {
							addFatalMessage("'" + table.getName() + "." + finder.getName() + " has aliased whereFields, however '" + baseField.getName() + "' does not have an alias.");
						}
					}
				}
			}
		}
	}

}
