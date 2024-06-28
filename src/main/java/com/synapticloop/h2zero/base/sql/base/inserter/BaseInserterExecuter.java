package com.synapticloop.h2zero.base.sql.base.inserter;

import com.synapticloop.h2zero.base.sql.BaseSQLExecutor;
import org.slf4j.Logger;

public abstract class BaseInserterExecuter extends BaseSQLExecutor {

	/**
	 * <p>This is the base SQL executor that executes the SQL statement.</p>
	 *
	 * @param logger       The logger to output messages to
	 * @param sqlStatement The SQL statement to execute
	 * @param parameters   The parameters to set on the SQL statement
	 */
	public BaseInserterExecuter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}
}
