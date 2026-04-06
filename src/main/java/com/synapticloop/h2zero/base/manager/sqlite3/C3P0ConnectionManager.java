package com.synapticloop.h2zero.base.manager.sqlite3;

/*
 * Copyright (c) 2012-2026 synapticloop.
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

import com.synapticloop.h2zero.base.manager.BaseC3P0ConnectionManager;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C3P0ConnectionManager extends BaseC3P0ConnectionManager {
	private static final ExecutorService writerExecutor = Executors.newSingleThreadExecutor();

	/**
	 * <p>Get a read connection to the database from the combo pooled datasource
	 * - i.e. this will be used for read only operations.  This is only really
	 * used for sqlite3 type databases and the default implementation is just a
	 * chained method call to the getConnection() method.</p>
	 *
	 * @return the connection from the underlying database
	 *
	 * @throws SQLException If there was an error getting the connection
	 */
	public static Connection getReadConnection() throws SQLException {
		Connection connection = comboPooledDataSource.getConnection();
		connection.setReadOnly(true);
		return connection;
	}

	/**
	 * <p>Get a write connection to the database from the combo pooled datasource
	 * - i.e. this will be used for write operations.  This is only really used
	 * for sqlite3 type databases and the default implementation is just a chained
	 * method call to the getConnection() method</p>
	 *
	 * @return the connection from the underlying database
	 *
	 * @throws SQLException If there was an error getting the connection
	 */
	public static Connection getWriteConnection() throws SQLException {
		Connection connection = comboPooledDataSource.getConnection();
		connection.setReadOnly(false);
		return connection;
	}

	public static Clob getNullableResultClob(ResultSet resultSet, int index) throws SQLException {
		throw new SQLException("Unsupported operation by this JDBC driver - sorry.");
	}

	public static Blob getNullableResultBlob(ResultSet resultSet, int index) throws SQLException {
		throw new SQLException("Unsupported operation by this JDBC driver - sorry.");
	}
}
