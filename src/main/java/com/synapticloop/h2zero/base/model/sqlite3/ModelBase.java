package com.synapticloop.h2zero.base.model.sqlite3;

/*
 * Copyright (c) 2018 - 2026 synapticloop.
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

import com.synapticloop.h2zero.base.manager.sqlite3.C3P0ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ModelBase extends com.synapticloop.h2zero.base.model.ModelBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelBase.class);

	@Override
	protected Connection getConnection() throws SQLException {
		LOGGER.warn("Getting a default connection from SQLite3 - you should use really " +
				"getReadConnection() or getWriteConnection()");
		return(C3P0ConnectionManager.getConnection());
	}

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
	protected Connection getReadConnection() throws SQLException {
		return(C3P0ConnectionManager.getReadConnection());
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
	protected Connection getWriteConnection() throws SQLException {
		return(C3P0ConnectionManager.getWriteConnection());
	}
}
