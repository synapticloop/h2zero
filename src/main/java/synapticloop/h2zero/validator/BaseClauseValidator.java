package synapticloop.h2zero.validator;

/*
 * Copyright (c) 2013-2024 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

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

		// now to see whether the
		// the select clause is a little bit different in that there may be multiple sub-selects and is harder to validate
	}
}
