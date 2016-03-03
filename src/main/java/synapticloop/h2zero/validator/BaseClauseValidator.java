package synapticloop.h2zero.validator;

import java.util.List;

import synapticloop.h2zero.model.BaseQueryObject;
import synapticloop.h2zero.model.Database;
import synapticloop.h2zero.model.Options;
import synapticloop.h2zero.model.field.BaseField;

public abstract class BaseClauseValidator extends BaseValidator {

	@Override
	public abstract void validate(Database database, Options options);

	public void validateClausesAndFields(BaseQueryObject baseQueryObject) {
		String whereClause = baseQueryObject.getWhereClause();
		List<BaseField> whereFields = baseQueryObject.getWhereFields();
		for (BaseField baseField : whereFields) {
			String whereField = baseField.getName();
			int indexOf = whereClause.indexOf(whereField);
			if(indexOf == -1) {
				addFatalMessage(baseQueryObject.getType() + " '" + baseQueryObject.getName() + "' has a whereClause '" + whereClause + "' which does not contain referenced whereField '" + whereField + "'.");
			}
		}

		// the select clause is a little bit different in that there may be multiple sub-selects and is harder to validate
	}
}
