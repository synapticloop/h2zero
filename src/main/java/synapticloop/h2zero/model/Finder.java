package synapticloop.h2zero.model;

/*
 * Copyright (c) 2013-2015 synapticloop.
 * 
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

import org.json.JSONObject;

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.model.util.JSONKeyConstants;
import synapticloop.h2zero.util.JsonHelper;
import synapticloop.h2zero.util.NamingHelper;

public class Finder extends BaseQueryObject {
	private boolean unique = false;

	public Finder(BaseSchemaObject baseSchemaObject, JSONObject finderObject) throws H2ZeroParseException {
		super(baseSchemaObject, finderObject);
		// if we have a select clause then we are returning a bean...

		// now for the select fields
		if(null != selectClause) {
			populateFields(finderObject, JSONKeyConstants.SELECT_FIELDS, selectFields, uniqueSelectFields);
		}

		this.unique = JsonHelper.getBooleanValue(finderObject, JSONKeyConstants.UNIQUE, unique);

		// we may not have any whereFields
		if(null != whereClause) {
			populateWhereFields(finderObject);
		}

		if(null == name) {
			throw new H2ZeroParseException("The finder '" + JSONKeyConstants.NAME + "' attribute cannot be null.");
		}
	}

	public String getFinderTagName() { return(NamingHelper.getFirstUpper(name)); }
	public void setOrderBy(String orderBy) { this.orderBy = orderBy; }
	public boolean getUnique() { return(unique); }

	public String getBaseQueryObjectType() { return("Finder"); }
}
