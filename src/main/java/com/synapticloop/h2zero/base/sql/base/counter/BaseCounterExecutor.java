package com.synapticloop.h2zero.base.sql.base.counter;

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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseCounterExecutor<T> extends BaseSQLExecutor<Integer> {
	public BaseCounterExecutor(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * <p>Execute the SQL statement.</p>
	 *
	 * <p>This will evaluate the (String) SQL statement and perform the following
	 * operations on the statement to set it up before it gets executed</p>
	 *
	 * <ol>
	 *   <li>Add a limit statement with or without and offset statement.</li>
	 *   <li>Call the base method to prepare the statement.</li>
	 *   <li>Finally, execute the statement and return the results.</li>
	 * </ol>
	 *
	 * @return the object, or null if one wasn't found
	 * @throws SQLException If there was an error executing the SQL statement
	 */
	protected Integer executeInternal() throws SQLException {
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

			preparedStatement = prepareStatement(connection, sqlStatement);

			resultSet = preparedStatement.executeQuery();
			// for a counting operation - there should only be one field - which is the count
			if (resultSet.next()) {
				return (resultSet.getInt(1));
			} else {
				throw new SQLException("Result set returned no rows, expecting exactly one.");
			}

		} finally {
			if (hasProvidedConnection) {
				// the caller has provided a connection - so they must close it themselves
				BaseConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				BaseConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}
	}

	/**
	 * <p>Execute this statement with a connection - this will be used, rather
	 * than creating a new connection from the connection pool.  This is
	 * useful when you want to be able to start a transaction and commit
	 * or rollback.</p>
	 *
	 * @param connection The connection to use
	 *
	 * @return The counter with the set connection
	 */
	public BaseCounterExecutor<T> withConnection(Connection connection) {
		this.connection = connection;
		return(this);
	}

	protected abstract Connection getConnection() throws SQLException;
}