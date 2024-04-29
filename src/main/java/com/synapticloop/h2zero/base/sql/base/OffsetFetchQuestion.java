package com.synapticloop.h2zero.base.sql.base;

/*
 * Copyright (c) 2024 synapticloop.
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

import com.synapticloop.h2zero.base.sql.BaseBooleanExecutor;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.util.function.Function;

public abstract class OffsetFetchQuestion extends BaseBooleanExecutor {
	public OffsetFetchQuestion(Logger logger, String sqlStatement, Function<ResultSet, Boolean> resultsFunction, Object... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	@Override protected String getLimitedResultsStatement() {
		return(getOffsetFetchStatement());
	}
}
