package com.synapticloop.h2zero.base.sql;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseSQLNoLimitOffsetExecutor extends BaseSQLExecutor {

	public BaseSQLNoLimitOffsetExecutor(Logger logger, String sqlStatement, Object ... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * Get the limit and offset statement for the database type.
	 *
	 * @return the limit and offset statement with the prepared variables set, or
	 *   an empty string if the limit and offset are both null
	 */
	protected abstract String getLimitedResultsStatement();

	/**
	 * Get a connection from the connection manager.
	 *
	 * @return The connection from the correct connection manager
	 */
	protected abstract Connection getConnection() throws SQLException;

	/**
	 * <p><strong>NOTE:</strong> Limit and offset are not available for
	 * this database type so this will return an empty string.</p>
	 *
	 * @return an empty String - limit and offset is not available on this
	 *   database type
	 */
	protected String getLimitOffsetStatement() {
		return("");
	}

	/**
	 * <p><strong>NOTE:</strong> Limit and offset are not available for
	 * this database type so this will return an empty string.</p>
	 *
	 * @return an empty String - limit and offset is not available on this
	 *   database type
	 */
	protected String getOffsetFetchStatement() {
		return("");
	}
}
