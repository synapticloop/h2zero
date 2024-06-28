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

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The base SQL Executor holds all of the information required to prepare a
 * statement.</p>
 *
 * <p><strong>NOTE:</strong> This does not include any <code>LIMIT ... OFFSET</code>
 * or <code>OFFSET n ROWS ... FETCH NEXT n ROWS ONLY</code> SQL statements.</p>
 *
 * <p>If you want to have the results limited see the <code>BaseSQLLimitedExecutor</code>
 * class, which will automatically add the correct limited results SQL statement,</p>
 *
 * @author synapticloop
 *
 * @see BaseSQLLimitedExecutor which will add a limit and offset to the statement
 * in the correct format for the database
 */
public abstract class BaseSQLExecutor<T> {
	protected final Logger logger;
	protected final String sqlStatement;
	protected final Object[] parameters;
	protected Connection connection = null;
	protected Integer limit = null;
	protected Integer offset = null;

	/**
	 * <p>This is the base SQL executor that executes the SQL statement.</p>
	 *
	 * @param logger       The logger to output messages to
	 * @param sqlStatement The SQL statement to execute
	 * @param parameters   The parameters to set on the SQL statement
	 */
	public BaseSQLExecutor(Logger logger, String sqlStatement, Object... parameters) {
		this.logger = logger;
		this.sqlStatement = sqlStatement;
		this.parameters = parameters;
	}

	/**
	 * <p>Prepare a statement ready for execution.  The following is performed
	 * on the passed in String statement</p>
	 *
	 * <ol>
	 *   <li>Look at any inField (i.e. <code>where id_some_number in (...)</code>
	 *   and replace them with the correct number of parameterised variables.</li>
	 *   <li>Safely add all of the parameters to the prepared statement.</li>
	 * </ol>
	 *
	 * @param connection   The connection to be used
	 * @param sqlStatement The SQL statement to prepare
	 * @return The prepared statement
	 * @throws SQLException If there was an error in the statement
	 */
	protected PreparedStatement prepareStatement(Connection connection, String sqlStatement) throws SQLException {
		// look for any parameters which are a list - this means that they are in fields
		List<Integer> inFieldOffsets = new ArrayList<>();

		int parameterIndex = 0;
		for (Object object : parameters) {
			if (object instanceof List<?>) {
				inFieldOffsets.add(parameterIndex);
			}
			parameterIndex++;
		}

		if (inFieldOffsets.size() != 0) {
			for (Integer offset : inFieldOffsets) {
				StringBuilder whereFieldStringBuilder = null;
				whereFieldStringBuilder = new StringBuilder();
				int size = ((List<?>) parameters[offset]).size();
				for (int whereBuilder = 0; whereBuilder < size; whereBuilder++) {
					if (whereBuilder > 0) {
						whereFieldStringBuilder.append(", ");
					}
					whereFieldStringBuilder.append("?");
				}

				sqlStatement = sqlStatement.replaceFirst("\\.\\.\\.", whereFieldStringBuilder.toString());
			}
		}

		// now we prepare the statement
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

		// now we set all the parameters
		int k = 1;
		for (Object object : parameters) {
			if (object instanceof List<?> listObjects) {
				for (Object listObject : listObjects) {
					preparedStatement.setObject(k, listObject);
				}
			} else {
				preparedStatement.setObject(k, object);
			}
			k++;
		}

		return (preparedStatement);
	}

	/**
	 * Execute the SQL statement.
	 *
	 * @return the number of rows deleted
	 *
	 * @throws SQLException If there was an error executing the SQL statement
	 */
	public T execute() throws SQLException {
		return(executeInternal());
	}

	/**
	 * Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.
	 *
	 * @return The number of rows deleted, or null if there was an error.
	 */
	public T executeSilent() {
		return(executeSilentInternal());
	}
	/**
	 * Get a connection from the connection manager.
	 *
	 * @return The connection from the correct connection manager
	 */
	protected abstract Connection getConnection() throws SQLException;

	protected abstract T executeInternal() throws SQLException;

	/**
	 * <p>Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.</p>
	 *
	 * <p>This is just a chain of the call to the <code>executeInternal</code>
	 * catching any exceptions, and logging them where necessary.</p>
	 *
	 * @return T the object, or null if there was an error.  If the return type
	 * is a List, then an empty list will be returned.
	 */
	protected T executeSilentInternal() {
		try {
			return(executeInternal());
		} catch (SQLException e) {
			logger.error(
					"Exception executing the SQL statement, message was: {}.  SQL statement was: {}",
					e.getMessage(),
					sqlStatement,
					e);
			return(null);
		}
	}
}
