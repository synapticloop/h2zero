package com.synapticloop.h2zero.base.monitor;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import java.sql.SQLException;

import com.synapticloop.h2zero.base.sql.mysql.ConnectionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0PoolStatistics {
	private int numBusyConnectionsDefaultUser;
	private long numFailedCheckinsDefaultUser;
	private long numFailedCheckoutsDefaultUser;
	private int numIdleConnectionsDefaultUser;
	private int numUnclosedOrphanedConnectionsDefaultUser;

	public C3P0PoolStatistics() throws SQLException {
		ComboPooledDataSource comboPooledDataSource = ConnectionManager.getComboPooledDataSource();
		numBusyConnectionsDefaultUser = comboPooledDataSource.getNumBusyConnectionsDefaultUser();
		numFailedCheckinsDefaultUser = comboPooledDataSource.getNumFailedCheckinsDefaultUser();
		numFailedCheckoutsDefaultUser = comboPooledDataSource.getNumFailedCheckoutsDefaultUser();
		numIdleConnectionsDefaultUser = comboPooledDataSource.getNumIdleConnectionsDefaultUser();
		numUnclosedOrphanedConnectionsDefaultUser = comboPooledDataSource.getNumUnclosedOrphanedConnectionsDefaultUser();
	}

	public int getNumBusyConnectionsDefaultUser() {
		return numBusyConnectionsDefaultUser;
	}

	public long getNumFailedCheckinsDefaultUser() {
		return numFailedCheckinsDefaultUser;
	}

	public long getNumFailedCheckoutsDefaultUser() {
		return numFailedCheckoutsDefaultUser;
	}

	public int getNumIdleConnectionsDefaultUser() {
		return numIdleConnectionsDefaultUser;
	}

	public int getNumUnclosedOrphanedConnectionsDefaultUser() {
		return numUnclosedOrphanedConnectionsDefaultUser;
	}

	public static String getMuninStats() throws SQLException {
		ComboPooledDataSource comboPooledDataSource = ConnectionManager.getComboPooledDataSource();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("busyConnections.value " + comboPooledDataSource.getNumBusyConnectionsDefaultUser() + "\n");
		stringBuilder.append("idleConnections.value " + comboPooledDataSource.getNumIdleConnectionsDefaultUser() + "\n");
		stringBuilder.append("unclosedOrphanedConnections.value " + comboPooledDataSource.getNumUnclosedOrphanedConnectionsDefaultUser() + "\n");
		stringBuilder.append("failedCheckins.value " + comboPooledDataSource.getNumFailedCheckinsDefaultUser() + "\n");
		stringBuilder.append("failedCheckouts.value " + comboPooledDataSource.getNumFailedCheckoutsDefaultUser() + "\n");
		return (stringBuilder.toString());
	}
}
