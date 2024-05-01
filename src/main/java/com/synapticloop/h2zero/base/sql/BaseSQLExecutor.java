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

public abstract class BaseSQLExecutor {
	protected final Logger logger;
	protected final String sqlStatement;
	protected final Object[] parameters;
	protected Connection connection = null;
	protected Integer limit = null;
	protected Integer offset = null;

	/**
	 * <p>This is the base SQL executor that executes the SQL statement.</p>
	 *
	 * @param logger The logger to output messages to
	 * @param sqlStatement The SQL statement to execute
	 * @param parameters The parameters to set on the SQL statement
	 */
	public BaseSQLExecutor(Logger logger, String sqlStatement, Object ... parameters) {
		this.logger = logger;
		this.sqlStatement = sqlStatement;
		this.parameters = parameters;
	}

	/**
	 * <p>Get the limit and offset statement for the database type. For example
	 * LIMIT x OFFSET y, or FETCH x ROWS FETCH NEXT y ROWS ONLY.</p>
	 *
	 * @return the limit and offset statement with the prepared variables set, or
	 *   an empty string if the limit and offset are both null
	 */
	protected abstract String getLimitedResultsStatement() throws SQLException;

	/**
	 * <p>Get a connection from the connection manager for the specific database
	 * type.</p>
	 *
	 * @return The connection from the correct connection manager
	 */
	protected abstract Connection getConnection() throws SQLException;

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
	 * @param connection The connection to be used
	 * @param sqlStatement The SQL statement to prepare
	 *
	 * @return The prepared statement
	 *
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

		// now we set all of the parameters
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

		return(preparedStatement);
	}

	/**
	 * Return the SQL statement with a limit and offset clause
	 *
	 * @return the SQL statement limit and offset
	 *
	 * @throws SQLException if an offset was set, but no limit was set
	 */
	protected String getLimitOffsetStatement() throws SQLException {
		if(null != offset && null == limit) {
			throw new SQLException("Cannot have an offset without a limit.");
		}
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
