package com.synapticloop.sample.h2zero.postgresql.counter;

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

import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

public class UserUserCounter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_BINDER;

		private static final Logger LOGGER = LoggerFactory.getLogger(UserUserCounter.class);


	private static final String SQL_BUILTIN_COUNT_ALL = "select count(*) from user_user";

	private static final String SQL_COUNT_NUMBER_OF_USERS = "select count(*) from user";
	private static final String SQL_COUNT_NUMBER_OF_USERS_OVER_AGE = "select count(*) from user" + " where num_age > ?";
	private static final String SQL_COUNT_NUMBER_OF_USERS_BETWEEN_AGE = "select count(*) from user" + " where num_age > ? and num_age < ?";
	private static final String SQL_COUNT_USERS_IN_AGES = "select count(*) from user" + " where num_age in (...)";

	private static Map<String, String> countUsersInAges_statement_cache = new HashMap<String, String>();

	private UserUserCounter() {}

	/**
	 * Find the count of all UserUser objects
	 * 
	 * @param connection the passed in connection object, useful for queries within
	 * a transaction.
	 * 
	 * @return the count of UserUser objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static int countAll(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_COUNT_ALL);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return(resultSet.getInt(1));
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

		return(-1);
	}

	/**
	 * Find the count of all UserUser objects
	 * 
	 * @return the count of UserUser objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static int countAll() throws SQLException {

		try (Connection connection = ConnectionManager.getConnection()) {
			return(countAll(connection));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countAll(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			throw sqlex;
		}
	}

	/**
	 * Find the count of all UserUser objects and if there is an error
	 * fail silently and log the error.
	 * 
	 * @param connection the passed in connection object, useful for queries within
	 * a transaction.
	 * 
	 * @return the count of UserUser objects
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
	 * Find the count of all UserUser objects and if there is an error
	 * fail silently and log the error.
	 * 
	 * @return the count of UserUser objects
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

	public static int countNumberOfUsers(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_COUNT_NUMBER_OF_USERS);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return(resultSet.getInt(1));
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(-1);
	}

	public static int countNumberOfUsers() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(countNumberOfUsers(connection));
		}
	}

	public static int countNumberOfUsersSilent(Connection connection) {
		try {
			return(countNumberOfUsers(connection));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countNumberOfUsersSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countNumberOfUsersSilent() {
		try {
			return(countNumberOfUsers());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countNumberOfUsersSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countNumberOfUsersOverAge(Connection connection, Integer numAge) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_COUNT_NUMBER_OF_USERS_OVER_AGE);
			ConnectionManager.setInt(preparedStatement, 1, numAge);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return(resultSet.getInt(1));
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(-1);
	}

	public static int countNumberOfUsersOverAge(Integer numAge) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(countNumberOfUsersOverAge(connection, numAge));
		}
	}

	public static int countNumberOfUsersOverAgeSilent(Connection connection, Integer numAge) {
		try {
			return(countNumberOfUsersOverAge(connection, numAge));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countNumberOfUsersOverAgeSilent(" + numAge + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countNumberOfUsersOverAgeSilent(Integer numAge) {
		try {
			return(countNumberOfUsersOverAge(numAge));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countNumberOfUsersOverAgeSilent(" + numAge + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countNumberOfUsersBetweenAge(Connection connection, Integer numAgeFrom, Integer numAgeTo) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_COUNT_NUMBER_OF_USERS_BETWEEN_AGE);
			ConnectionManager.setInt(preparedStatement, 1, numAgeFrom);
			ConnectionManager.setInt(preparedStatement, 2, numAgeTo);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return(resultSet.getInt(1));
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(-1);
	}

	public static int countNumberOfUsersBetweenAge(Integer numAgeFrom, Integer numAgeTo) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(countNumberOfUsersBetweenAge(connection, numAgeFrom, numAgeTo));
		}
	}

	public static int countNumberOfUsersBetweenAgeSilent(Connection connection, Integer numAgeFrom, Integer numAgeTo) {
		try {
			return(countNumberOfUsersBetweenAge(connection, numAgeFrom, numAgeTo));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countNumberOfUsersBetweenAgeSilent(" + numAgeFrom + ", " + numAgeTo + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countNumberOfUsersBetweenAgeSilent(Integer numAgeFrom, Integer numAgeTo) {
		try {
			return(countNumberOfUsersBetweenAge(numAgeFrom, numAgeTo));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countNumberOfUsersBetweenAgeSilent(" + numAgeFrom + ", " + numAgeTo + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countUsersInAges(Connection connection, List<Integer> numAgeList) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			if(countUsersInAges_statement_cache.containsKey(numAgeList.size() + ":" )) {
				preparedStatement = connection.prepareStatement(countUsersInAges_statement_cache.get(numAgeList.size() + ":" ));
			} else {
				String preparedStatementTemp = SQL_COUNT_USERS_IN_AGES;
				StringBuilder stringBuilder = null;
				stringBuilder = new StringBuilder();
				for(int i = 0; i < numAgeList.size(); i++) {
					if(i > 0) {
						stringBuilder.append(", ");
					}
					stringBuilder.append("?");
				}
				preparedStatementTemp = SQL_COUNT_USERS_IN_AGES.replaceFirst("\\.\\.\\.", stringBuilder.toString());
				countUsersInAges_statement_cache.put(numAgeList.size() + ":" , preparedStatementTemp);
				preparedStatement = connection.prepareStatement(preparedStatementTemp);
			}
			int i = 1;
			for (Integer numAgeIn : numAgeList) {
				ConnectionManager.setInt(preparedStatement, i, numAgeIn);
				i++;
			}

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return(resultSet.getInt(1));
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(-1);
	}

	public static int countUsersInAges(List<Integer> numAgeList) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(countUsersInAges(connection, numAgeList));
		}
	}

	public static int countUsersInAgesSilent(Connection connection, List<Integer> numAgeList) {
		try {
			return(countUsersInAges(connection, numAgeList));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countUsersInAgesSilent(" + numAgeList + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

	public static int countUsersInAgesSilent(List<Integer> numAgeList) {
		try {
			return(countUsersInAges(numAgeList));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException countUsersInAgesSilent(" + numAgeList + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		}
		return(-1);
	}

}