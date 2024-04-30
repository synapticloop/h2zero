package com.synapticloop.h2zero.base.sql.base;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.BaseConnectionManager;
import com.synapticloop.h2zero.base.sql.BaseSQLExecutor;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDeleterUpdaterExecutor extends BaseSQLExecutor {
	public BaseDeleterUpdaterExecutor(Logger logger, String sqlStatement, Object... parameters) {
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
	 * @return The number of rows that were updated
	 * @throws SQLException          If there was an error executing the SQL statement
	 * @throws H2ZeroFinderException If no results could be found
	 */
	protected int executeInternal() throws SQLException {
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

			return(preparedStatement.executeUpdate());

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
	 * <p>Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.</p>
	 *
	 * <p>This is just a chain of the call to the <code>executeInternal</code>
	 * catching any exceptions, and logging them where necessary.</p>
	 *
	 * @return List the list of object, or an empty list if none were found.
	 */
	protected Integer executeSilentInternal() {
		try {
			return (executeInternal());
		} catch (SQLException e) {
			logger.error("SQLException executing statement '{}', with limit '{}', with offset '{}'.", sqlStatement, limit, offset);
		}

		return (null);
	}



	/**
	 * <p>Execute this statement with a connection - this will be used, rather
	 * than creating a new connection from the connection pool.  This is
	 * useful when you want to be able to start a transaction and commit
	 * or rollback.</p>
	 *
	 * @param connection The connection to use
	 *
	 * @return The finder with the set connection
	 */
	public BaseDeleterUpdaterExecutor withConnection(Connection connection) {
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
	 *           than one result was found
	 */
	public int execute() throws SQLException {
		return(executeInternal());
	}

	/**
	 * Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.
	 *
	 * @return List the list of object, or an empty list if none were found.
	 */
	public int executeSilent() {
		return(executeSilentInternal());
	}
	protected abstract String getLimitedResultsStatement() throws SQLException;

	protected abstract Connection getConnection() throws SQLException;
}
