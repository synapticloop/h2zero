package com.synapticloop.h2zero.base.sql;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseSQLExecutor {
	protected final Logger logger;
	protected final String sqlStatement;
	protected final Object[] parameters;
	protected Connection connection = null;
	protected Integer limit = null;
	protected Integer offset = null;

	public BaseSQLExecutor(Logger logger, String sqlStatement, Object ... parameters) {
		this.logger = logger;
		this.sqlStatement = sqlStatement;
		this.parameters = parameters;
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
	 * Return the SQL statement with a limit and offset clause
	 *
	 * @return the SQL statement limit and offset
	 */
	protected String getLimitOffsetStatement() {
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
	protected String getOffsetFetchStatement() {
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
