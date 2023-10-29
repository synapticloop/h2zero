package synapticloop.sample.h2zero.sqlite3.finder;

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

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.sqlite3.model.util.Constants;
import synapticloop.sample.h2zero.sqlite3.bean.UserTitleFindIdUserTitleNmUserTitleOrderedBean;

import synapticloop.sample.h2zero.sqlite3.model.UserTitle;

public class UserTitleFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TITLE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTitleFinder.class);
	private static final String SQL_SELECT_START = "select id_user_title, nm_user_title, num_order_by from user_title";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_title = ?";

	private static final String SQL_FIND_ID_USER_TITLE_NM_USER_TITLE_ORDERED = "select id_user_title, nm_user_title from user_title order by num_order_by";
	private static final String SQL_FIND_ALL_ORDERED = SQL_SELECT_START + " order by num_order_by";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> findAll_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> findIdUserTitleNmUserTitleOrdered_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> findAllOrdered_limit_statement_cache = new LruCache<>(1024);

	private UserTitleFinder() {}

	/**
	 * Find a UserTitle by its primary key
	 * 
	 * @param connection the connection item
	 * @param idUserTitle the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static UserTitle findByPrimaryKey(Connection connection, Long idUserTitle) throws H2ZeroFinderException {
		UserTitle userTitle = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if(null == idUserTitle) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idUserTitle] was null.");
		}

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_FIND_BY_PRIMARY_KEY);
			preparedStatement.setLong(1, idUserTitle);
			resultSet = preparedStatement.executeQuery();
			userTitle = uniqueResult(resultSet);
		} catch (H2ZeroFinderException | SQLException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idUserTitle:" + idUserTitle + "].");
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		if(null == userTitle) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idUserTitle:" + idUserTitle + "].");
		}
		return(userTitle);
	}

	/**
	 * Find a UserTitle by its primary key
	 * 
	 * @param idUserTitle the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static UserTitle findByPrimaryKey(Long idUserTitle) throws H2ZeroFinderException {

		if(null == idUserTitle) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idUserTitle] was null.");
		}

		UserTitle userTitle = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			userTitle = findByPrimaryKey(connection, idUserTitle);
		} catch (SQLException | H2ZeroFinderException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idUserTitle:" + idUserTitle + "].");
		}

		if(null == userTitle) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idUserTitle:" + idUserTitle + "].");
		}
		return(userTitle);
	}

	/**
	 * Find a UserTitle by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param connection the connection item
	 * @param idUserTitle the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static UserTitle findByPrimaryKeySilent(Connection connection, Long idUserTitle) {
		try {
			return(findByPrimaryKey(connection, idUserTitle));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idUserTitle + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find a UserTitle by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param idUserTitle the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static UserTitle findByPrimaryKeySilent(Long idUserTitle) {
		try {
			return(findByPrimaryKey(idUserTitle));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idUserTitle + "): " + h2zfex.getMessage());
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
	 * @return a list of all the UserTitle objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserTitle> findAll(Connection connection, Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
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

		List<UserTitle> results = new ArrayList<UserTitle>();

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
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters.
	 * 
	 * @return The list of UserTitle model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserTitle> findAll() throws SQLException, H2ZeroFinderException {
		return(findAll(null, null, null));
	}

	/**
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset
	 * parameters.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of UserTitle model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserTitle> findAll(Connection connection) throws SQLException, H2ZeroFinderException {
		return(findAll(connection, null, null));
	}

	/**
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null connection parameter
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of UserTitle model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<UserTitle> findAll(Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
		return(findAll(null, limit, offset));
	}

	/**
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * 
	 * @param connection - the connection to be used
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of UserTitle model objects, or an empty List on error
	 */
	public static List<UserTitle> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException | H2ZeroFinderException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("Exception findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			return(new ArrayList<UserTitle>());
		}
	}

	/**
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of UserTitle model objects, or an empty List on error
	 */
	public static List<UserTitle> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	/**
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of UserTitle model objects, or an empty List on error
	 */
	public static List<UserTitle> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	/**
	 * Find all the UserTitle objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @return The list of UserTitle model objects, or an empty List on error
	 */
	public static List<UserTitle> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined finders which are generated
	 * through either the "finders" JSON key, or the "fieldFinders" JSON
	 * key.
	 * 
	 * There are 2 defined finders on the user_title table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * - findAllOrdered - Generated from the 'finders' JSON key
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * findAllOrdered 
	 * <p>
	 * (This finder was generated through the 'finders' JSON key)
	 * <p>
	 * Note that if a limit and offset are passed through, then the generated statement 
	 * will be cached for further use
	 * 
	 * @param connection - the connection to the database
	 * @param limit - The maximum number of rows to return
	 * @param offset - The row offset to start with
	 * 
	 * @return the list of UserTitle results found
	 * 
	 * @throws H2ZeroFinderException if no results could be found
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static List<UserTitle> findAllOrdered(Connection connection, Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		boolean hasConnection = (null != connection);
		String statement = null;

		// first find the statement that we want - or cache it if it doesn't exist

		String cacheKey = limit + ":" + offset;
		if(!findAllOrdered_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_FIND_ALL_ORDERED);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
				if(null != offset) {
					stringBuilder.append(" offset ");
					stringBuilder.append(offset);
				}
			}

			statement = stringBuilder.toString();
			findAllOrdered_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = findAllOrdered_limit_statement_cache.get(cacheKey);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UserTitle> results = null;
		try {
			if(!hasConnection) {
				connection = ConnectionManager.getConnection();
			}
			preparedStatement = connection.prepareStatement(statement);

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

	public static List<UserTitle> findAllOrdered(Connection connection) throws H2ZeroFinderException, SQLException {
		return(findAllOrdered(connection, null, null));
	}

	public static List<UserTitle> findAllOrdered(Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		return(findAllOrdered(null, limit, offset));
	}

	public static List<UserTitle> findAllOrdered() throws H2ZeroFinderException, SQLException {
		return(findAllOrdered(null, null, null));
	}

	// silent connection, params..., limit, offset
	public static List<UserTitle> findAllOrderedSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAllOrdered(connection, limit, offset));
		} catch(H2ZeroFinderException h2zfex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findAllOrderedSilent(connection: " + connection + ", limit: " + limit + ", offset: " + offset + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(new ArrayList<UserTitle>());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findAllOrderedSilent(connection: " + connection + ", limit: " + limit + ", offset: " + offset + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(new ArrayList<UserTitle>());
		}
	}

	// silent connection, params...
	public static List<UserTitle> findAllOrderedSilent(Connection connection) {
		return(findAllOrderedSilent(connection, null, null));
	}

	// silent params..., limit, offset
	public static List<UserTitle> findAllOrderedSilent(Integer limit, Integer offset) {
		return(findAllOrderedSilent(null, limit, offset));
	}

	public static List<UserTitle> findAllOrderedSilent() {
		return(findAllOrderedSilent(null, null, null));
	}

	/**
	 * Return a unique result for the query - in effect just the first result of
	 * query.  If there is a second result (i.e. the query did not return the 
	 * expected unique result), then an exception will be thrown.
	 * 
	 * @param resultSet The result set of the query
	 * 
	 * @return The UserTitle that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found or more than one result was found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static UserTitle uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			Long idUserTitle = ConnectionManager.getNullableResultLong(resultSet, 1);
			String nmUserTitle = ConnectionManager.getNullableResultString(resultSet, 2);
			Integer numOrderBy = ConnectionManager.getNullableResultInt(resultSet, 3);

			UserTitle userTitle = new UserTitle(idUserTitle, nmUserTitle, numOrderBy);

			if(resultSet.next()) {
				throw new H2ZeroFinderException("More than one result in resultset for unique finder.");
			} else {
				return(userTitle);
			}
		} else {
			// could not get a result
			return(null);
		}
	}

	/**
	 * Return the results as a list of UserTitle, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of UserTitle
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<UserTitle> list(ResultSet resultSet) throws SQLException {
		List<UserTitle> arrayList = new ArrayList<UserTitle>();
		while(resultSet.next()) {
			arrayList.add(new UserTitle(
					ConnectionManager.getNullableResultLong(resultSet, 1),
					ConnectionManager.getNullableResultString(resultSet, 2),
					ConnectionManager.getNullableResultInt(resultSet, 3)));
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
	 * There are 2 defined finders on the user_title table, of those finders
	 * the following are the select clause finders:
	 * 
	 * - findIdUserTitleNmUserTitleOrdered
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	// SELECTBEAN - CONNECTION, PARAMS..., LIMIT, OFFSET
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrdered(Connection connection, Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		boolean hasConnection = (null != connection);
		if(!hasConnection) {
			connection = ConnectionManager.getConnection();
		}

		String statement = null;

		// first find the statement that we want

		String cacheKey = limit + ":" + offset;
		if(!findIdUserTitleNmUserTitleOrdered_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_FIND_ID_USER_TITLE_NM_USER_TITLE_ORDERED);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
				if(null != offset) {
					stringBuilder.append(" offset ");
					stringBuilder.append(offset);
				}
			}

			statement = stringBuilder.toString();
			findIdUserTitleNmUserTitleOrdered_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = findIdUserTitleNmUserTitleOrdered_limit_statement_cache.get(cacheKey);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(statement);

			resultSet = preparedStatement.executeQuery();
			List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> results = listFindIdUserTitleNmUserTitleOrderedBean(resultSet);
			return(results);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			if(hasConnection) {
				ConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				ConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}

	}

	// SELECTBEAN - PARAMS..., LIMIT, OFFSET 
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrdered(Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		return(findIdUserTitleNmUserTitleOrdered(null, limit, offset));
	}

	// SELECTBEAN - CONNECTION, PARAMS...
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrdered(Connection connection) throws H2ZeroFinderException, SQLException {
		return(findIdUserTitleNmUserTitleOrdered(null, null, null));
	}

	// SELECTBEAN - PARAMS...
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrdered() throws H2ZeroFinderException, SQLException {
		return(findIdUserTitleNmUserTitleOrdered(null, null, null));
	}

	// SILENT SELECTBEAN: CONNECTION, PARAMS..., LIMIT, OFFSET
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrderedSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findIdUserTitleNmUserTitleOrdered(connection, limit, offset));
		} catch(H2ZeroFinderException h2zfex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findIdUserTitleNmUserTitleOrderedSilent(connection: " + connection  + ", limit: " + limit + ", offset: " + offset + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(new ArrayList<UserTitleFindIdUserTitleNmUserTitleOrderedBean>());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findIdUserTitleNmUserTitleOrderedSilent(connection: " + connection  + ", limit: " + limit + ", offset: " + offset + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(new ArrayList<UserTitleFindIdUserTitleNmUserTitleOrderedBean>());
		}
	}

// CONNECTION, PARAMS...
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrderedSilent(Connection connection) {
		return(findIdUserTitleNmUserTitleOrderedSilent(connection, null, null));
	}

// PARAMS..., LIMIT, OFFSET
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrderedSilent(Integer limit, Integer offset) {
		return(findIdUserTitleNmUserTitleOrderedSilent(null, limit, offset));
	}

// PARAMS...
	public static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrderedSilent() {
		return(findIdUserTitleNmUserTitleOrderedSilent(null, null, null));
	}

	/**
	 * Return the results as a list of UserTitleFindIdUserTitleNmUserTitleOrderedBeans, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of UserTitleFindIdUserTitleNmUserTitleOrderedBean
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> listFindIdUserTitleNmUserTitleOrderedBean(ResultSet resultSet) throws SQLException {
		List<UserTitleFindIdUserTitleNmUserTitleOrderedBean> arrayList = new ArrayList<UserTitleFindIdUserTitleNmUserTitleOrderedBean>();
		while(resultSet.next()) {
			arrayList.add(new UserTitleFindIdUserTitleNmUserTitleOrderedBean(
					resultSet.getLong(1),
					resultSet.getString(2)));
		}
		return(arrayList);
	}

}