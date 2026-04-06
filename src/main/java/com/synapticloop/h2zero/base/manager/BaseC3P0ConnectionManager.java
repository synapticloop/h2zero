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

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This is the base connection manager for the c3p0 connection pool</p>
 *
 * @author synapticloop
 */
public abstract class BaseC3P0ConnectionManager extends BaseConnectionManager {
	protected static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
	private static final Map<String, ComboPooledDataSource> CONNECTION_POOL_NAME_MAP = new HashMap<>();

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
	 * @param comboPoolName the name of the combo pooled data source
	 * @param comboPooledDataSource the combo pooled data source
	 */
	public static void addConnectionPool(String comboPoolName, ComboPooledDataSource comboPooledDataSource) {
		if(CONNECTION_POOL_NAME_MAP.isEmpty()) {
			BaseC3P0ConnectionManager.comboPooledDataSource = comboPooledDataSource;
		}

		CONNECTION_POOL_NAME_MAP.put(comboPoolName, comboPooledDataSource);
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
		return(comboPooledDataSource.getConnection());
	}

	/**
	 * Get the underlying combo pooled result set
	 * 
	 * @return The underlying combo pooled result set
	 */
	public static ComboPooledDataSource getComboPooledDataSource() { return comboPooledDataSource; }
}
