package com.synapticloop.h2zero.base.sql.base.deleter;

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
import com.synapticloop.h2zero.base.sql.BaseSQLLimitedExecutor;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @param <T> The return type for the SQL statement
 */
public abstract class BaseDeleterExecutor<T> extends BaseSQLLimitedExecutor<Integer> {

	/**
	 * <p>Instantiate a Deleter.  A Deleter always return an Integer type</p>
	 *
	 * @param logger The logger to use for any messages
	 * @param sqlStatement The SQL statement to execute
	 * @param parameters The parameters to set on the SQL statement
	 */
	public BaseDeleterExecutor(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * <p>Execute the SQL statement.</p>
	 *
	 * <p>This will evaluate the (String) SQL statement and perform the following
	 * operations on the statement to set it up before it gets executed</p>
	 *
	 * <ol>
	 *   <li>Call the base method to prepare the statement which will add the
	 *   limited results statement for the database (if allowed).</li>
	 *   <li>Execute the statement and return the result.</li>
	 * </ol>
	 *
	 * @return The number of rows that were updated
	 * @throws SQLException          If there was an error executing the SQL statement
	 */
	protected Integer executeInternal() throws SQLException {
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

			// if the table does not exist - then it may return null
			return (preparedStatement.executeUpdate());

		} finally {
			if (hasProvidedConnection) {
				// the caller has provided a connection - so they must close it themselves
				BaseConnectionManager.closeAll(preparedStatement, null);
			} else {
				BaseConnectionManager.closeAll(preparedStatement, connection);
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
	 * @return The deleter with the set connection
	 */
	public BaseDeleterExecutor<T> withConnection(Connection connection) {
		this.connection = connection;
		return(this);
	}

	public BaseDeleterExecutor withLimit(Integer limit) {
		this.limit = limit;
		return(this);
	}

	/**
	 * Get the limited results statement for the specific SQL dialect - e.g.
	 * either limit offset or offset fetch next.
	 *
	 * @return The limited results statement for the SQL dialect
	 */
	protected abstract String getLimitedResultsStatement();

	/**
	 * <p>Get a connection to the specific database.</p>
	 *
	 * @return The connection to the database
	 *
	 * @throws SQLException If a connection could not be made
	 */
	protected abstract Connection getConnection() throws SQLException;
}
