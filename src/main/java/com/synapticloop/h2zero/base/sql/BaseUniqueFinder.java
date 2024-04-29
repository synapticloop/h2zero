package com.synapticloop.h2zero.base.sql;

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

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public abstract class BaseUniqueFinder<T> extends BaseFinder<T> {

	/**
	 * <p>Create a new finder - <strong>NOTE</strong> this method should probably
	 * <strong>NOT</strong> be used independently, but through the static Finder
	 * methods.</p>
	 *
	 * <p>A Finder only ever returns a single result</p>
	 *
	 * @param logger the logger for errors
	 * @param sqlStatement The SQL statement to execute
	 * @param resultsFunction The function to call that will return the object
	 *          created from the result set
	 * @param parameters the array of parameters that are required to be set
	 *          on the SQL statement
	 */
	public BaseUniqueFinder(Logger logger, String sqlStatement, Function<ResultSet, List<T>> resultsFunction, Object ... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	/**
	 * Execute this statement with a connection - this will be used, rather
	 * than creating a new connection from the connection pool.  This is
	 * useful when you want to be able to start a transaction and commit
	 * or rollback.
	 *
	 * @param connection The connection to use
	 *
	 * @return The finder with the set connection
	 */
	public BaseUniqueFinder<T> withConnection(Connection connection) {
		this.connection = connection;
		return(this);
	}

	/**
	 * Execute the SQL statement.
	 *
	 * @return the object, or null if one wasn't found
	 *
	 * @throws SQLException If there was an error executing the SQL statement
	 * @throws H2ZeroFinderException If no results could be found, or if more
	 * 	         than one result was found for the query which, by definition
	 * 	         should be a unique result.
	 */
	public T execute() throws SQLException, H2ZeroFinderException {
		List<T> ts = super.executeInternal();
		if(ts.size() > 1) {
			throw new H2ZeroFinderException("Looking for a single result, found " + ts.size() + ".");
		} else {
			return(ts.get(0));
		}
	}

	/**
	 * Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.
	 *
	 * @return List the list of object, or an empty list if none were found.
	 */
	public T executeSilent() {
		List<T> ts = super.executeSilentInternal();
		if(ts.size() == 1) {
			return(ts.get(0));
		} else {
			return(null);
		}
	}
}
