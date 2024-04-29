package com.synapticloop.sample.h2zero.mysql.question;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-question.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * <p>This class contains all of the questions that are defined in the h2zero
 * file</p>
 * <p>A question returns a simple true/false response from a query.</p>
 * 
 * <p>Table name: <code>user</code></p>
 * 
 * <p>Questions defined:
 * <ul>
 * <li><code>doWeHaveMoreThanTwentyUsers</code> - SQL query run {@link #SQL_DO_WE_HAVE_MORE_THAN_TWENTY_USERS}</li>
 * <li><code>doesUserNameExist</code> - SQL query run {@link #SQL_DOES_USER_NAME_EXIST}</li>
 * <li><code>doWeHaveUsersBetweenAgeExclusive</code> - SQL query run {@link #SQL_DO_WE_HAVE_USERS_BETWEEN_AGE_EXCLUSIVE}</li>
 * <li><code>doWeHaveUsersInAges</code> - SQL query run {@link #SQL_DO_WE_HAVE_USERS_IN_AGES}</li>
 * </ul>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserQuestion {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserQuestion.class);


	// this is an internal SQL question select statement used by the validator
	private static final String SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST = "SELECT (COUNT(*) = 1) WHERE id_user = ?";

	private static final String SQL_DO_WE_HAVE_MORE_THAN_TWENTY_USERS = "select count(*) > 20 from user";
	private static final String SQL_DOES_USER_NAME_EXIST = "select count(*) > 0 from user";
	private static final String SQL_DO_WE_HAVE_USERS_BETWEEN_AGE_EXCLUSIVE = "select count(*) > 0 from user" + " where num_age > ? and num_age < ?";
	private static final String SQL_DO_WE_HAVE_USERS_IN_AGES = "select count(*) > 0 from user" + " where num_age in (...)";

	private static final Map<String, String> doWeHaveUsersInAges_statement_cache = new HashMap<String, String>();

	/** Private to deter instantiation */
	private UserQuestion() {}

	/**
	 * An internal method to check whether a specific primary key exists, 
	 * generated as part of the validation methods
	 * 
	 * @param idUser The primary key for this model
	 * 
	 * @return whether the primary key exists
	 */
	public static boolean internalDoesPrimaryKeyExist(Long idUser) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean answer = false;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST);
			ConnectionManager.setBigint(preparedStatement, 1, idUser);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				answer = resultSet.getBoolean(1);
			}
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException internalDoesPrimaryKeyExist(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement, connection);
		}
		return(answer);
	}

	public static boolean doWeHaveMoreThanTwentyUsers(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean answer = false;
		try {
			preparedStatement = connection.prepareStatement(SQL_DO_WE_HAVE_MORE_THAN_TWENTY_USERS);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				answer = resultSet.getBoolean(1);
			}
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(answer);
	}

	public static boolean doWeHaveMoreThanTwentyUsers() throws SQLException {
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			return(doWeHaveMoreThanTwentyUsers(connection));
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
	}

	public static boolean doWeHaveMoreThanTwentyUsersSilent(Connection connection) {
		try {
			return(doWeHaveMoreThanTwentyUsers(connection));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doWeHaveMoreThanTwentyUsersSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doWeHaveMoreThanTwentyUsersSilent() {
		try {
			return(doWeHaveMoreThanTwentyUsers());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doWeHaveMoreThanTwentyUsersSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doesUserNameExist(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean answer = false;
		try {
			preparedStatement = connection.prepareStatement(SQL_DOES_USER_NAME_EXIST);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				answer = resultSet.getBoolean(1);
			}
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(answer);
	}

	public static boolean doesUserNameExist() throws SQLException {
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			return(doesUserNameExist(connection));
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
	}

	public static boolean doesUserNameExistSilent(Connection connection) {
		try {
			return(doesUserNameExist(connection));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doesUserNameExistSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doesUserNameExistSilent() {
		try {
			return(doesUserNameExist());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doesUserNameExistSilent(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doWeHaveUsersBetweenAgeExclusive(Connection connection, Integer numAgeFrom, Integer numAgeTo) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean answer = false;
		try {
			preparedStatement = connection.prepareStatement(SQL_DO_WE_HAVE_USERS_BETWEEN_AGE_EXCLUSIVE);
			ConnectionManager.setInt(preparedStatement, 1, numAgeFrom);
			ConnectionManager.setInt(preparedStatement, 2, numAgeTo);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				answer = resultSet.getBoolean(1);
			}
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(answer);
	}

	public static boolean doWeHaveUsersBetweenAgeExclusive(Integer numAgeFrom, Integer numAgeTo) throws SQLException {
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			return(doWeHaveUsersBetweenAgeExclusive(connection, numAgeFrom, numAgeTo));
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
	}

	public static boolean doWeHaveUsersBetweenAgeExclusiveSilent(Connection connection, Integer numAgeFrom, Integer numAgeTo) {
		try {
			return(doWeHaveUsersBetweenAgeExclusive(connection, numAgeFrom, numAgeTo));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doWeHaveUsersBetweenAgeExclusiveSilent(" + numAgeFrom + ", " + numAgeTo + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doWeHaveUsersBetweenAgeExclusiveSilent(Integer numAgeFrom, Integer numAgeTo) {
		try {
			return(doWeHaveUsersBetweenAgeExclusive(numAgeFrom, numAgeTo));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doWeHaveUsersBetweenAgeExclusiveSilent(" + numAgeFrom + ", " + numAgeTo + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doWeHaveUsersInAges(Connection connection, List<Integer> numAgeList) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean answer = false;
		try {
			if(doWeHaveUsersInAges_statement_cache.containsKey(numAgeList.size() + ":" )) {
				preparedStatement = connection.prepareStatement(doWeHaveUsersInAges_statement_cache.get(numAgeList.size() + ":" ));
			} else {
				String preparedStatementTemp = SQL_DO_WE_HAVE_USERS_IN_AGES;
				StringBuilder stringBuilder = null;
				stringBuilder = new StringBuilder();
				for(int i = 0; i < numAgeList.size(); i++) {
					if(i > 0) {
						stringBuilder.append(", ");
					}
					stringBuilder.append("?");
				}
				preparedStatementTemp = SQL_DO_WE_HAVE_USERS_IN_AGES.replaceFirst("\\.\\.\\.", stringBuilder.toString());
				doWeHaveUsersInAges_statement_cache.put(numAgeList.size() + ":" , preparedStatementTemp);
				preparedStatement = connection.prepareStatement(preparedStatementTemp);
			}
			int i = 1;
			for (Integer numAgeIn : numAgeList) {
				ConnectionManager.setInt(preparedStatement, i, numAgeIn);
				i++;
			}

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				answer = resultSet.getBoolean(1);
			}
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
		return(answer);
	}

	public static boolean doWeHaveUsersInAges(List<Integer> numAgeList) throws SQLException {
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			return(doWeHaveUsersInAges(connection, numAgeList));
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
	}

	public static boolean doWeHaveUsersInAgesSilent(Connection connection, List<Integer> numAgeList) {
		try {
			return(doWeHaveUsersInAges(connection, numAgeList));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doWeHaveUsersInAgesSilent(" + numAgeList + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

	public static boolean doWeHaveUsersInAgesSilent(List<Integer> numAgeList) {
		try {
			return(doWeHaveUsersInAges(numAgeList));
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException doWeHaveUsersInAgesSilent(" + numAgeList + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(false);
		}
	}

}