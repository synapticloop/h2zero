package synapticloop.sample.h2zero.sqlite3.counter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-counter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.sql.*;

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

public class UserTypeCounter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TYPE_BINDER;

		private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeCounter.class);


	private static final String SQL_BUILTIN_COUNT_ALL = "select count(*) from user_type";



	private UserTypeCounter() {}

	/**
	 * Find the count of all UserType objects
	 * 
	 * @param connection the passed in connection object, useful for queries within
	 * a transaction.
	 * 
	 * @return the count of UserType objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static int countAll(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = -1;

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_COUNT_ALL);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countAll(connection): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		return(count);
	}

	/**
	 * Find the count of all UserType objects
	 * 
	 * @return the count of UserType objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static int countAll() throws SQLException {
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			return(countAll(connection));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countAll(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
	}

	/**
	 * Find the count of all UserType objects and if there is an error
	 * fail silently and log the error.
	 * 
	 * @param connection the passed in connection object, useful for queries within
	 * a transaction.
	 * 
	 * @return the count of UserType objects
	 * 
	 */
	public static int countAllSilent(Connection connection) {
		try {
			return(countAll(connection));
		} catch(SQLException sqlex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countAllSilent(connection): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

	/**
	 * Find the count of all UserType objects and if there is an error
	 * fail silently and log the error.
	 * 
	 * @return the count of UserType objects
	 * 
	 */
	public static int countAllSilent() {
		try {
			return(countAll());
		} catch(SQLException sqlex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countAllSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(-1);
		}
	}

}