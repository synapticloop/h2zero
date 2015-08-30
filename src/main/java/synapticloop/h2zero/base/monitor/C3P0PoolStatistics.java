package synapticloop.h2zero.base.monitor;

import java.sql.SQLException;

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;

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
