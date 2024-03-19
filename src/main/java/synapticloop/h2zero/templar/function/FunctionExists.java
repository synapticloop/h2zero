package synapticloop.h2zero.templar.function;

/*
 * Copyright (c) 2023 - 2024 synapticloop.
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

import synapticloop.h2zero.model.BaseSchemaObject;
import synapticloop.h2zero.util.SimpleLogger;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.function.Function;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class FunctionExists extends Function {
	public static final String FUNCTION_NAME = "exists";

	public FunctionExists() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		return(null == args[0]);
	}
}
