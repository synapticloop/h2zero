package com.synapticloop.h2zero.base.sql.db.mariadb;

import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.inserter.BaseInserterExecuter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Inserter extends BaseInserterExecuter {
	/**
	 * <p>This is the base SQL executor that executes the SQL statement.</p>
	 *
	 * @param logger       The logger to output messages to
	 * @param sqlStatement The SQL statement to execute
	 * @param parameters   The parameters to set on the SQL statement
	 */
	public Inserter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected String getLimitedResultsStatement() throws SQLException {
		return("");
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
