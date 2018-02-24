package synapticloop.h2zero.base.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public abstract class BaseConnectionManager {
	protected static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

	public static Connection getConnection() throws SQLException {
		return(comboPooledDataSource.getConnection());
	}

	public static void closeAll(Connection connection) {
		closeAll(null, null, connection);
	}

	public static void closeAll(Statement statement, Connection connection) {
		closeAll(null, statement, connection);
	}

	public static void closeAll(ResultSet resultSet, Statement statement) {
		closeAll(resultSet, statement, null);
	}

	public static void closeAll(Statement statement) {
		closeAll(null, statement, null);
	}

	public static void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
		if(null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException jssqlex) {
				// do nothing
			} finally {
				resultSet = null;
			}
		}

		if(null != statement) {
			try {
				statement.close();
			} catch (SQLException jssqlex) {
				// do nothing
			} finally {
				statement = null;
			}
		}

		if(null != connection) {
			try {
				connection.close();
			} catch (SQLException jssqlex) {
				// do nothing
			} finally {
				connection = null;
			}
		}
	}
}
