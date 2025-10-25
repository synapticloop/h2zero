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

/**
 * <p>The base Limited SQL Executor that extends the <code>BaseSQLExecutor</code>
 * to allow statements to be limited in returning results.</p>
 *
 * <p><strong>NOTE:</strong> This will include <code>LIMIT ... OFFSET</code>
 * or <code>OFFSET n ROWS ... FETCH NEXT n ROWS ONLY</code> SQL statements to
 * the passed in <code>sqlStatement</code></p>
 *
 * <p>If you do not want to have the results limited see the <code>BaseSQLExecutor</code>
 * class, which will not automatically add the limited results SQL statement,</p>
 *
 * @author synapticloop
 *
 * @see BaseSQLExecutor which will not add a limit and offset to the statement
 */

public abstract class BaseSQLLimitedExecutor<T> extends BaseSQLExecutor<T> {

	/**
	 * <p>This is the base SQL executor that executes the SQL statement.</p>
	 *
	 * @param logger The logger to output messages to
	 * @param sqlStatement The SQL statement to execute
	 * @param parameters The parameters to set on the SQL statement
	 */
	public BaseSQLLimitedExecutor(Logger logger, String sqlStatement, Object ... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * <p>Get the limit and offset statement for the database type. For example
	 * LIMIT x OFFSET y, or FETCH x ROWS FETCH NEXT y ROWS ONLY.</p>
	 */
	protected abstract String getLimitedResultsStatement();

	protected PreparedStatement prepareStatement(Connection connection, String sqlStatement) throws SQLException {
		return(super.prepareStatement(connection, sqlStatement + getLimitedResultsStatement()));
	}

	/**
	 * Return the SQL statement with a limit and offset clause
	 *
	 * @return the SQL statement limit and offset
	 */
	protected String getLimitOffsetStatement()  {
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

	@Override protected T executeSilentInternal() {
		try {
			return(executeInternal());
		} catch (SQLException e) {
			logger.error(
					"Exception executing the SQL statement, message was: {}.  SQL statement was: {}",
					e.getMessage(),
					sqlStatement + getLimitedResultsStatement(),
					e);
			return(null);
		}
	}
}
