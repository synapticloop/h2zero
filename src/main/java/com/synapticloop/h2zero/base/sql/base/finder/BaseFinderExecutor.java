package com.synapticloop.h2zero.base.sql.base.finder;

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

import com.synapticloop.h2zero.base.manager.BaseConnectionManager;
import com.synapticloop.h2zero.base.sql.BaseSQLExecutor;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * The BaseFinder
 *
 * @param <T> The type of result to return, either as a single object, or a
 *          list of results
 */
public abstract class BaseFinderExecutor<T> extends BaseSQLExecutor {
	protected final Function<ResultSet, List<T>> resultsFunction;

	/**
	 * <p>Create the base finder</p>
	 *
	 * <p>Finder only ever return one result</p>
	 *
	 * @param logger the logger for errors
	 * @param sqlStatement The SQL statement to execute
	 * @param resultsFunction The function to call that will return the object
	 *                        created from the result set
	 * @param parameters the array of parameters that are required to be set
	 *                   on the SQL statement
	 */
	protected BaseFinderExecutor(Logger logger, String sqlStatement, Function<ResultSet, List<T>> resultsFunction, Object ... parameters) {
		super(logger, sqlStatement, parameters);
		this.resultsFunction = resultsFunction;
	}

	/**
	 * <p>Execute the SQL statement.</p>
	 *
	 * <p>This will evaluate the (String) SQL statement and perform the following
	 * operations on the statement to set it up before it gets executed</p>
	 *
	 * <ol>
	 *   <li>Look at any inField (i.e. <code>where id_some_number in (...)</code>
	 *   and replace them with the correct number of parameterised variables.</li>
	 *   <li>Add a limit statement with or without and offset statement.</li>
	 *   <li>Safely add all of the parameters to the prepared statement.</li>
	 *   <li>Finally, execute the statement and return the results.</li>
	 * </ol>
	 *
	 * @return the object, or null if one wasn't found
	 *
	 * @throws SQLException If there was an error executing the SQL statement
	 */
	protected List<T> executeInternal() throws SQLException {
		List<T> results = new ArrayList<>();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		// whether we have a connection provided - i.e. we don't need to create
		// or cleanup the connection - this is left to the user
		boolean hasProvidedConnection = true;

		try {
			if (null == connection) {
				connection = getConnection();
				hasProvidedConnection = false;
			}

			preparedStatement = prepareStatement(connection, sqlStatement + getLimitedResultsStatement());

			// finally execute the statement
			resultSet = preparedStatement.executeQuery();
			results = resultsFunction.apply(resultSet);
		} finally {
			if(hasProvidedConnection) {
				// the caller has provided a connection - so they must close it themselves
				BaseConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				BaseConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}

		return results;
	}

	/**
	 * <p>Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.</p>
	 *
	 * <p>This is just a chain of the call to the <code>executeInternal</code>
	 * catching any exceptions, and logging them where necessary.</p>
	 *
	 * @return List the list of object, or an empty list if none were found.
	 */
	protected List<T> executeSilentInternal() {
		try {
			return(executeInternal());
		} catch (SQLException e) {
			logger.error("SQLException executing statement '{}'", sqlStatement);
		}

		return(new ArrayList<T>());
	}

	protected abstract String getLimitedResultsStatement() throws SQLException;
}
