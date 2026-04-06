package com.synapticloop.h2zero.base.manager;

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

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This is the base connection manager for the hikari connection manager.</p>
 *
 * @author synapticloop
 */
public abstract class BaseHikariConnectionManager {
	protected static HikariDataSource hikariDataSource = new HikariDataSource();
	private static final Map<String, HikariDataSource> CONNECTION_POOL_NAME_MAP = new HashMap<>();

	/**
	 * Get a named connection to the database from the combo pooled datasource
	 *
	 * @return the connection from the underlying database
	 *
	 * @throws SQLException If there was an error getting the connection
	 */
	public static Connection getConnection(String comboPoolName) throws SQLException {
		if(CONNECTION_POOL_NAME_MAP.containsKey(comboPoolName)) {
			return (CONNECTION_POOL_NAME_MAP.get(comboPoolName).getConnection());
		} else {
			return(getConnection());
		}
	}

	/**
	 * <p>
	 * Add a combo pool to the manager - in effect, this adds it to the HashMap
	 * for easy lookup.
	 * </p>
	 *
	 * <pre>
	 *   WARNING:  THE FIRST COMBO POOL ADDED TO THE HASHMAP BECOMES THE DEFAULT
	 *             COMBO POOL USED IF NO getConnection(String comboPoolName)
	 *             CALL IS USED
	 * </pre>
	 *
	 * @param connectionPoolName the name of the combo pooled data source
	 * @param hikariDataSource the combo pooled data source
	 */
	public static void addConnectionPool(String connectionPoolName, HikariDataSource hikariDataSource) {
		if(CONNECTION_POOL_NAME_MAP.isEmpty()) {
			BaseHikariConnectionManager.hikariDataSource = hikariDataSource;
		}

		CONNECTION_POOL_NAME_MAP.put(connectionPoolName, hikariDataSource);
	}

	/**
	 * Get a connection to the database from the combo pooled datasource
	 * 
	 * @return the connection from the underlying database
	 * 
	 * @throws SQLException If there was an error getting the connection
	 */
	@Deprecated
	public static Connection getConnection() throws SQLException {
		return(hikariDataSource.getConnection());
	}

	/**
	 * Get the underlying combo pooled result set
	 * 
	 * @return The underlying combo pooled result set
	 */
	public static HikariDataSource getHikariDatasource() { return hikariDataSource; }
}
