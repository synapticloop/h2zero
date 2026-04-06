package com.synapticloop.h2zero.base.connectionmonitor;

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

import com.synapticloop.h2zero.base.manager.BaseHikariConnectionManager;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.sql.SQLException;

/**
 * <p>HikariPoolStatistics provides monitoring and statistics for the HikariCP
 * connection pool.</p>
 */
public class HikariPoolStatistics {
	private int activeConnections;
	private int idleConnections;
	private int totalConnections;
	private int threadsAwaitingConnection;

	/**
	 * <p>Instantiates a new HikariPoolStatistics object, capturing a snapshot of the current pool state.</p>
	 *
	 * @throws SQLException If an error occurs while accessing the data source
	 */
	public HikariPoolStatistics() throws SQLException {
		HikariDataSource hikariDataSource = BaseHikariConnectionManager.getHikariDatasource();
		HikariPoolMXBean mxBean = hikariDataSource.getHikariPoolMXBean();

		if (mxBean != null) {
			activeConnections = mxBean.getActiveConnections();
			idleConnections = mxBean.getIdleConnections();
			totalConnections = mxBean.getTotalConnections();
			threadsAwaitingConnection = mxBean.getThreadsAwaitingConnection();
		}
	}

	/**
	 * <p>Retrieves the number of active (busy) connections currently in use.</p>
	 *
	 * @return The number of active connections
	 */
	public int getActiveConnections() {
		return activeConnections;
	}

	/**
	 * <p>Retrieves the number of idle connections sitting in the pool.</p>
	 *
	 * @return The number of idle connections
	 */
	public int getIdleConnections() {
		return idleConnections;
	}

	/**
	 * <p>Retrieves the total number of connections currently managed by the pool.</p>
	 *
	 * @return The total number of connections
	 */
	public int getTotalConnections() {
		return totalConnections;
	}

	/**
	 * <p>Retrieves the number of threads currently waiting for a connection to become available.</p>
	 *
	 * <p>This serves as a good indicator of pool starvation, replacing the concept of failed checkouts.</p>
	 *
	 * @return The number of waiting threads
	 */
	public int getThreadsAwaitingConnection() {
		return threadsAwaitingConnection;
	}

	/**
	 * <p>Generates a string formatted for Munin monitoring.</p>
	 *
	 * @return A string containing the current pool statistics in Munin format
	 * @throws SQLException If an error occurs while accessing the data source
	 */
	public static String getMuninStats() throws SQLException {
		HikariDataSource hikariDataSource = BaseHikariConnectionManager.getHikariDatasource();
		HikariPoolMXBean mxBean = hikariDataSource.getHikariPoolMXBean();

		StringBuilder stringBuilder = new StringBuilder();

		if (mxBean != null) {
			stringBuilder.append("busyConnections.value ").append(mxBean.getActiveConnections()).append("\n");
			stringBuilder.append("idleConnections.value ").append(mxBean.getIdleConnections()).append("\n");
			stringBuilder.append("totalConnections.value ").append(mxBean.getTotalConnections()).append("\n");
			stringBuilder.append("threadsAwaitingConnection.value ").append(mxBean.getThreadsAwaitingConnection()).append("\n");
		}

		return stringBuilder.toString();
	}
}