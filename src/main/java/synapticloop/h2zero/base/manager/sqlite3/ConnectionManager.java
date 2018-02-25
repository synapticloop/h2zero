package synapticloop.h2zero.base.manager.sqlite3;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import synapticloop.h2zero.base.manager.BaseConnectionManager;

public class ConnectionManager extends BaseConnectionManager {

	public static Clob getNullableResultClob(ResultSet resultSet, int index) throws SQLException { 
		throw new SQLException("Unsupported opperation by this JDBC driver - sorry.");
	}

	public static Blob getNullableResultBlob(ResultSet resultSet, int index) throws SQLException {
		throw new SQLException("Unsupported opperation by this JDBC driver - sorry.");
	}

}
