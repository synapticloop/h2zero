package com.synapticloop.h2zero.base.finder;

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
import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
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
public abstract class BaseFinder<T> {
	protected final Logger logger;
	protected final String sqlStatement;
	protected final Object[] parameters;
	protected final Function<ResultSet, List<T>> resultsFunction;
	protected Connection connection = null;
	protected Integer limit = null;
	protected Integer offset = null;

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
	protected BaseFinder(Logger logger, String sqlStatement, Function<ResultSet, List<T>> resultsFunction, Object ... parameters) {
		this.logger = logger;
		this.sqlStatement = sqlStatement;
		this.parameters = parameters;
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
	 * @throws H2ZeroFinderException If no results could be found
	 */
	protected List<T> executeInternal() throws SQLException, H2ZeroFinderException {
		if(null == connection) {
			connection = ConnectionManager.getConnection();
		}

		// we are going to add the limit/offset or offset/fetch statement first,
		// which may be a blank string
		String preparedStatementTemp = sqlStatement + getLimitedResultsStatement();

		// look for any parameters which are a list - this means that they are in fields
		List<Integer> inFieldOffsets = new ArrayList<>();

		int i = 0;
		for(Object object: parameters) {
			if(object instanceof List<?>) {
				inFieldOffsets.add(i);
			}
			i++;
		}

		if(inFieldOffsets.size() != 0) {
			for(Integer offset: inFieldOffsets) {
				StringBuilder whereFieldStringBuilder = null;
				whereFieldStringBuilder = new StringBuilder();
				int size = ((List<?>) parameters[offset]).size();
				for(int j = 0; j < size; j++) {
					if(j > 0) {
						whereFieldStringBuilder.append(", ");
					}
					whereFieldStringBuilder.append("?");
				}

				preparedStatementTemp = preparedStatementTemp.replaceFirst("\\.\\.\\.", whereFieldStringBuilder.toString());
			}
		}

		// now we prepare the statement
		PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementTemp);

		// now we set all of the parameters
		int k = 1;
		for(Object object: parameters) {
			preparedStatement.setObject(k, object);
			k++;

			if(object instanceof List<?> listObjects) {
				for(Object listObject: listObjects) {
					preparedStatement.setObject(i, listObject);
					k++;
				}
			}
		}

		// finally execute the statement
		return(resultsFunction.apply(preparedStatement.executeQuery()));
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
		} catch (H2ZeroFinderException ignored) {
		}

		return(new ArrayList<T>());
	}

	/**
	 * Get the limit and offset statement for the database type.
	 *
	 * @return the limit and offset statement with the prepared variables set, or
	 *   an empty string if the limit and offset are both null
	 */
	protected abstract String getLimitedResultsStatement();

	/**
	 * Return the SQL statement with a limit and offset clause
	 *
	 * @return the SQL statement limit and offset
	 */
	protected String getLimitOffsetStatementInternal() {
		// if offset is set, limit is mandatory - this will be caught
		// when the SQL statement execution is attempted to be performed
		StringBuilder stringBuilder = new StringBuilder();
		if(null != limit) {
			stringBuilder.append(" LIMIT ");
			stringBuilder.append(limit);
		}

		if(null != offset) {
			stringBuilder.append(" OFFSET ");
			stringBuilder.append(offset);
		}

		return(stringBuilder.toString());
	}

	/**
	 * <p>Return the SQL statement with the offset/fetch next clause</p>
	 *
	 * @return the SQL statement with an OFFSET/Fetch Next clause
	 */
	protected String getOffsetFetchStatementInternal() {
		// offset is optional, but, limit is mandatory - this will be caught
		// when the SQL statement execution is attempted to be performed
		StringBuilder stringBuilder = new StringBuilder();
		if(null != offset) {
			stringBuilder.append(" OFFSET ");
			stringBuilder.append(offset);
			stringBuilder.append(" ROWS ");
		}

		if(null != limit) {
			stringBuilder.append(" FETCH NEXT ");
			stringBuilder.append(limit);
			stringBuilder.append(" ROWS ONLY ");
		}

		return(stringBuilder.toString());
	}

}
