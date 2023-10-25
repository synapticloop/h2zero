package synapticloop.sample.h2zero.sqlite3.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//               (java-create-view-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import synapticloop.sample.h2zero.sqlite3.view.UserUserType;


public class UserUserTypeViewFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserUserTypeViewFinder.class);
	private static final String SQL_SELECT_START = "select nm_user, nm_user_type from user_user_type";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_pet = ?";

	private static final String SQL_FIND_BY_NM_USER = SQL_SELECT_START + " where nm_user = ?";

	// now for the statement limit cache(s)
	private static LruCache<String, String> findAll_limit_statement_cache = new LruCache<String, String>(1024);
	private static LruCache<String, String> findByNmUser_limit_statement_cache = new LruCache<String, String>(1024);

	private UserUserTypeViewFinder() {}

	/**
	 * Find a UserUserType by its primary key
	 * 
	 * @param connection the connection item
	 * @param idUserPet the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static UserUserType findByPrimaryKey(Connection connection, Long idUserPet) throws H2ZeroFinderException {
		UserUserType userUserType = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if(null == idUserPet) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idUserPet] was null.");
		}

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_FIND_BY_PRIMARY_KEY);
			preparedStatement.setLong(1, idUserPet);
			resultSet = preparedStatement.executeQuery();
			userUserType = uniqueResult(resultSet);
		} catch (SQLException sqlex) {
			throw new H2ZeroFinderException(sqlex);
		} catch (H2ZeroFinderException h2zfex) {
			throw new H2ZeroFinderException(h2zfex.getMessage() + "  Additionally, the parameters were [idUserPet:" + idUserPet + "].");
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		if(null == userUserType) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idUserPet:" + idUserPet + "].");
		}
		return(userUserType);
	}

	/**
	 * Find a UserUserType by its primary key
	 * 
	 * @param idUserPet the primary key
	 * 
	 * @return the unique result or throw an exception if one coudn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static UserUserType findByPrimaryKey(Long idUserPet) throws H2ZeroFinderException {
		UserUserType userUserType = null;
		Connection connection = null;

		if(null == idUserPet) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idUserPet] was null.");
		}

		try {
			connection = ConnectionManager.getConnection();
			userUserType = findByPrimaryKey(connection, idUserPet);
		} catch (SQLException sqlex) {
			throw new H2ZeroFinderException(sqlex);
		} catch (H2ZeroFinderException h2zfex) {
			throw new H2ZeroFinderException(h2zfex.getMessage() + "  Additionally, the parameters were [idUserPet:" + idUserPet + "].");
		} finally {
			ConnectionManager.closeAll(connection);
		}

		if(null == userUserType) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idUserPet:" + idUserPet + "].");
		}
		return(userUserType);
	}

	/**
	 * Find a UserUserType by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param connection the connection item
	 * @param idUserPet the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static UserUserType findByPrimaryKeySilent(Connection connection, Long idUserPet) {
		try {
			return(findByPrimaryKey(connection, idUserPet));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idUserPet + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find a UserUserType by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param idUserPet the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static UserUserType findByPrimaryKeySilent(Long idUserPet) {
		try {
			return(findByPrimaryKey(idUserPet));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idUserPet + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find all UserTitle objects with the passed in connection, with limited
	 * results starting at a particular offset.
	 * 
	 * If the limit parameter is null, there will be no limit applied.
	 * 
	 * If the offset is null, then this will be set to 0
	 * 
	 * If both limit and offset are null, then no limit and no offset will be applied
	 * to the statement.
	 * 
	 * The passed in connection object is usable for transactional SQL statements,
	 * where the connection has already had a transaction started on it.
	 * 
	 * If the connection object is null an new connection object will be created 
	 * and closed at the end of the method.
	 * 
	 * If the connection object is not null, then it will not be closed.
	 * 
	 * @param connection - the connection object to use (or null if not part of a transaction)
	 * @param limit - the limit for the result set
	 * @param offset - the offset for the start of the results.
	 * 
	 * @return a list of all of the UserTitle objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static List<UserUserType> findAll(Connection connection, Integer limit, Integer offset) throws SQLException {
		boolean hasConnection = (null != connection);
		String statement = null;
		// first find the statement that we want

		String cacheKey = limit + ":" + offset;
		if(!findAll_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_SELECT_START);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
				if(null != offset) {
					stringBuilder.append(" offset ");
					stringBuilder.append(offset);
				}
			}


			statement = stringBuilder.toString();
			findAll_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = findAll_limit_statement_cache.get(cacheKey);
		}

		// now set up the statement
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if(connection == null) {
			connection = ConnectionManager.getConnection();
		}

		List<UserUserType> results = new ArrayList<UserUserType>();

		try {
			preparedStatement = connection.prepareStatement(statement);
			resultSet = preparedStatement.executeQuery();
			results = list(resultSet);
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findAll(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			throw sqlex;
		} finally {
			if(hasConnection) {
				ConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				ConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}

		return(results);
	}

	public static List<UserUserType> findAll() throws SQLException {
		return(findAll(null, null, null));
	}

	public static List<UserUserType> findAll(Connection connection) throws SQLException {
		return(findAll(connection, null, null));
	}

	public static List<UserUserType> findAll(Integer limit, Integer offset) throws SQLException {
		return(findAll(null, limit, offset));
	}

	public static List<UserUserType> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException sqlex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(new ArrayList<UserUserType>());
		}
	}

	public static List<UserUserType> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	public static List<UserUserType> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	public static List<UserUserType> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/**
	 * findByNmUser
	 * @param nmUser
	 * 
	 * @return the list of UserUserType results found
	 * 
	 * @throws H2ZeroFinderException if no results could be found
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static List<UserUserType> findByNmUser(Connection connection, String nmUser, Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		boolean hasConnection = (null != connection);
		String statement = null;

		// first find the statement that we want

		String cacheKey = limit + ":" + offset;
		if(!findByNmUser_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_FIND_BY_NM_USER);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
				if(null != offset) {
					stringBuilder.append(" offset ");
					stringBuilder.append(offset);
				}
			}

			statement = stringBuilder.toString();
			findByNmUser_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = findByNmUser_limit_statement_cache.get(cacheKey);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UserUserType> results = null;
		try {
			if(!hasConnection) {
				connection = ConnectionManager.getConnection();
			}
			preparedStatement = connection.prepareStatement(statement);
			ConnectionManager.setVarchar(preparedStatement, 1, nmUser);

			resultSet = preparedStatement.executeQuery();
			results = list(resultSet);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			if(hasConnection) {
				ConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				ConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}


		if(results.size() == 0) {
			throw new H2ZeroFinderException("Could not find result.");
		}
		return(results);
	}

	public static List<UserUserType> findByNmUser(Connection connection, String nmUser) throws H2ZeroFinderException, SQLException {
		return(findByNmUser(connection, nmUser, null, null));
	}

	public static List<UserUserType> findByNmUser(String nmUser, Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		return(findByNmUser(null, nmUser, limit, offset));
	}

	public static List<UserUserType> findByNmUser(String nmUser) throws H2ZeroFinderException, SQLException {
		return(findByNmUser(null, nmUser, null, null));
	}

// silent connection, params..., limit, offset
	public static List<UserUserType> findByNmUserSilent(Connection connection, String nmUser, Integer limit, Integer offset) {
		try {
			return(findByNmUser(connection, nmUser, limit, offset));
		} catch(H2ZeroFinderException h2zfex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByNmUserSilent(connection: " + connection + ", " + nmUser + ", limit: " + limit + ", offset: " + offset + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(new ArrayList<UserUserType>());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findByNmUserSilent(connection: " + connection + ", " + nmUser + ", limit: " + limit + ", offset: " + offset + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(new ArrayList<UserUserType>());
		}
	}

// silent connection, params...
	public static List<UserUserType> findByNmUserSilent(Connection connection, String nmUser) {
		return(findByNmUserSilent(connection, nmUser, null, null));
	}

// silent params..., limit, offset
	public static List<UserUserType> findByNmUserSilent(String nmUser, Integer limit, Integer offset) {
		return(findByNmUserSilent(null , nmUser, limit, offset));
	}

	public static List<UserUserType> findByNmUserSilent(String nmUser) {
		return(findByNmUserSilent(null, nmUser, null, null));
	}

	/**
	 * Return a unique result for the query - in effect just the first result of
	 * query.
	 * 
	 * @param resultSet The result set of the query
	 * 
	 * @return The UserUserType that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static UserUserType uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			String nmUser = ConnectionManager.getNullableResultString(resultSet, 1);
			String nmUserType = ConnectionManager.getNullableResultString(resultSet, 2);

			UserUserType userUserType = new UserUserType(nmUser, nmUserType);

			if(resultSet.next()) {
				throw new H2ZeroFinderException("More than one result in resultset for unique finder.");
			} else {
				return(userUserType);
			}
		} else {
			// could not get a result
			return(null);
		}
	}

	/**
	 * Return the results as a list of UserUserType, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of UserUserType
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<UserUserType> list(ResultSet resultSet) throws SQLException {
		List<UserUserType> arrayList = new ArrayList<UserUserType>();
		while(resultSet.next()) {
			arrayList.add(new UserUserType(
					ConnectionManager.getNullableResultString(resultSet, 1),
					ConnectionManager.getNullableResultString(resultSet, 2)));
		}
		return(arrayList);
	}

}