package com.synapticloop.h2zero.base.sql;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseSQLNoLimitOrOffsetExecutor extends BaseSQLExecutor {

	public BaseSQLNoLimitOrOffsetExecutor(Logger logger, String sqlStatement, Object ... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * Get a connection from the connection manager.
	 *
	 * @return The connection from the correct connection manager
	 */
	protected abstract Connection getConnection() throws SQLException;
}
