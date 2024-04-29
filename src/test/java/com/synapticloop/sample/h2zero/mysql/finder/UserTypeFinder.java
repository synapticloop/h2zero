package com.synapticloop.sample.h2zero.mysql.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

import com.synapticloop.sample.h2zero.mysql.model.UserType;

import com.synapticloop.h2zero.base.finder.limitoffset.MultiFinder;
import com.synapticloop.h2zero.base.finder.limitoffset.UniqueFinder;

public class UserTypeFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_user_type, 
				nm_user_type
			from 
				user_type
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_type = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> findAll_limit_statement_cache = new LruCache<>(1024);

	private UserTypeFinder() {}

	/**
	 * Find a UserType by its primary key
	 * 
	 * @param connection the connection item
	 * @param idUserType the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static UserType findByPrimaryKey(Connection connection, Long idUserType) throws H2ZeroFinderException {
		UserType userType = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if(null == idUserType) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idUserType] was null.");
		}

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_FIND_BY_PRIMARY_KEY);
			preparedStatement.setLong(1, idUserType);
			resultSet = preparedStatement.executeQuery();
			userType = uniqueResult(resultSet);
		} catch (H2ZeroFinderException | SQLException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idUserType:" + idUserType + "].");
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		if(null == userType) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idUserType:" + idUserType + "].");
		}
		return(userType);
	}

	/**
	 * Find a UserType by its primary key
	 * 
	 * @param idUserType the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static UserType findByPrimaryKey(Long idUserType) throws H2ZeroFinderException {

		if(null == idUserType) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idUserType] was null.");
		}

		UserType userType = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			userType = findByPrimaryKey(connection, idUserType);
		} catch (SQLException | H2ZeroFinderException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idUserType:" + idUserType + "].");
		}

		if(null == userType) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idUserType:" + idUserType + "].");
		}
		return(userType);
	}

	/**
	 * Find a UserType by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param connection the connection item
	 * @param idUserType the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static UserType findByPrimaryKeySilent(Connection connection, Long idUserType) {
		try {
			return(findByPrimaryKey(connection, idUserType));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idUserType + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find a UserType by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param idUserType the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static UserType findByPrimaryKeySilent(Long idUserType) {
		try {
			return(findByPrimaryKey(idUserType));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idUserType + "): " + h2zfex.getMessage());
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
	 * <p>
	 * If the limit parameter is null, there will be no limit applied.
	 * <p>
	 * If the offset is null, then this will be set to 0
	 * <p>
	 * If both limit and offset are null, then no limit and no offset will be applied
	 * to the statement.
	 * <p>
	 * The passed in connection object is usable for transactional SQL statements,
	 * where the connection has already had a transaction started on it.
	 * <p>
	 * If the connection object is null an new connection object will be created 
	 * and closed at the end of the method.
	 * <p>
	 * If the connection object is not null, then it will not be closed.
	 * 
	 * @param connection - the connection object to use (or null if not part of a transaction)
	 * @param limit - the limit for the result set
	 * @param offset - the offset for the start of the results.
	 * 
	 * @return a list of all the UserType objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserType> findAll(Connection connection, Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
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

		List<UserType> results = new ArrayList<UserType>();

		try {
			preparedStatement = connection.prepareStatement(statement);
			resultSet = preparedStatement.executeQuery();
			results = list(resultSet);
		} catch(SQLException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findAll(): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			throw ex;
		} finally {
			if(hasConnection) {
				ConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				ConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}

		if(results.size() == 0) {
			throw new H2ZeroFinderException("Could not find any results for findAll");
		}
		return(results);
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters.
	 * 
	 * @return The list of UserType model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserType> findAll() throws SQLException, H2ZeroFinderException {
		return(findAll(null, null, null));
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset
	 * parameters.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of UserType model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserType> findAll(Connection connection) throws SQLException, H2ZeroFinderException {
		return(findAll(connection, null, null));
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null connection parameter
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of UserType model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserType> findAll(Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
		return(findAll(null, limit, offset));
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * 
	 * @param connection - the connection to be used
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of UserType model objects, or an empty List on error
	 */
	public static List<UserType> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException | H2ZeroFinderException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("Exception findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			return(new ArrayList<UserType>());
		}
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of UserType model objects, or an empty List on error
	 */
	public static List<UserType> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of UserType model objects, or an empty List on error
	 */
	public static List<UserType> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	/**
	 * Find all the UserType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @return The list of UserType model objects, or an empty List on error
	 */
	public static List<UserType> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined finders which are generated
	 * through either the "finders" JSON key, or the "fieldFinders" JSON
	 * key.
	 * 
	 * There are 0 defined finders on the user_type table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Return a unique result for the query - in effect just the first result of
	 * query.  If there is a second result (i.e. the query did not return the 
	 * expected unique result), then an exception will be thrown.
	 * 
	 * @param resultSet The result set of the query
	 * 
	 * @return The UserType that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found or more than one result was found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static UserType uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			Long idUserType = ConnectionManager.getNullableResultLong(resultSet, 1);
			String nmUserType = ConnectionManager.getNullableResultString(resultSet, 2);

			UserType userType = new UserType(idUserType, nmUserType);

			if(resultSet.next()) {
				throw new H2ZeroFinderException("More than one result in resultset for unique finder.");
			} else {
				return(userType);
			}
		} else {
			// could not get a result
			return(null);
		}
	}

	/**
	 * Return the results as a list of UserType, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of UserType
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<UserType> list(ResultSet resultSet) throws SQLException {
		List<UserType> arrayList = new ArrayList<UserType>();
		while(resultSet.next()) {
			arrayList.add(new UserType(
					ConnectionManager.getNullableResultLong(resultSet, 1),
					ConnectionManager.getNullableResultString(resultSet, 2)));
		}
		return(arrayList);
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined select clause finders which are 
	 * generated through the "finders" JSON key, with a 'selectClause' 
	 * key on the finder.
	 * 
	 * All selectClause finders return a subset of the data from a row of the 
	 * database table (or tables if there is a join statement) as a generated
	 * bean
	 * 
	 * There are 0 defined finders on the user_type table, of those finders
	 * the following are the select clause finders:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}