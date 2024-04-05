package com.synapticloop.h2zero.templar.function;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.h2zero.model.BaseSchemaObject;
import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.Function;
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionRequiresImport extends Function {
	public static final String FUNCTION_NAME = "requiresImport";

	public FunctionRequiresImport() {
		super(2);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object tableObject = null;
		if(args[0] instanceof BaseSchemaObject) {
			tableObject = args[0];
		} else {
			ObjectHelper.evaluateObjectToDefaultBoolean(args[0], templarContext);
			tableObject = ObjectHelper.evaluateObjectToDefaultBoolean(args[0].toString(), templarContext);
		}

		Object fieldTypeObject = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);

		if(tableObject instanceof BaseSchemaObject && fieldTypeObject instanceof String) {
			BaseSchemaObject baseSchemaObject = (BaseSchemaObject)tableObject;
			String fieldType = (String)fieldTypeObject;
			return(baseSchemaObject.requiresImport(fieldType));
		} else {
			throw new FunctionException("The function '" + FUNCTION_NAME + "' takes exactly two arguments, which must be of type ['" + BaseSchemaObject.class.getCanonicalName() + "', '" + String.class.getCanonicalName() + "'], found arguments of ['" + tableObject.getClass().getCanonicalName() + "', '" + fieldTypeObject.getClass().getCanonicalName() + "']." );
		}
	}
}
