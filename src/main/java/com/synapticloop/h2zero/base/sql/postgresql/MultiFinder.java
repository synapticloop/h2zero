package com.synapticloop.h2zero.base.sql.postgresql;

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

import com.synapticloop.h2zero.base.sql.base.finder.LimitOffsetMultiFinder;
import com.synapticloop.h2zero.base.manager.postgresql.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class MultiFinder<T> extends LimitOffsetMultiFinder<T> {

	/**
	 * <p>Create a new multi finder - <strong>NOTE</strong> this method should
	 * probably not be used independently, but through the static Finder
	 * methods.</p>
	 *
	 * <p>A MultiFinder only ever returns multiple results (i.e. a List of
	 * results).</p>
	 *
	 * @param logger the logger for any errors that occur when the executeSilent()
	 *          method is called.
	 * @param sqlStatement The SQL statement to execute
	 * @param resultsFunction The function to call that will return the object
	 *          created from the result set
	 * @param parameters the array of parameters that are required to be set
	 *          on the SQL statement
	 */
	public MultiFinder(Logger logger, String sqlStatement, Function<ResultSet, List<T>> resultsFunction, Object ... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
