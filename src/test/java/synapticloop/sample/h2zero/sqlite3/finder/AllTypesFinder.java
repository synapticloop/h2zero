package synapticloop.sample.h2zero.sqlite3.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import synapticloop.sample.h2zero.sqlite3.model.AllTypes;

public class AllTypesFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesFinder.class);
	private static final String SQL_SELECT_START = "select id_all_types, test_bigint, test_boolean, test_date, test_datetime, test_double, test_float, test_int, test_integer, test_mediumint, test_numeric, test_smallint, test_text, test_tinyint, test_varchar from all_types";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_all_types = ?";


	// now for the statement limit cache(s)
	private static LruCache<String, String> findAll_limit_statement_cache = new LruCache<String, String>(1024);

	private AllTypesFinder() {}

	/**
	 * Find a AllTypes by its primary key
	 * 
	 * @param connection the connection item
	 * @param idAllTypes the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static AllTypes findByPrimaryKey(Connection connection, Long idAllTypes) throws H2ZeroFinderException {
		AllTypes allTypes = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if(null == idAllTypes) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idAllTypes] was null.");
		}

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_FIND_BY_PRIMARY_KEY);
			preparedStatement.setLong(1, idAllTypes);
			resultSet = preparedStatement.executeQuery();
			allTypes = uniqueResult(resultSet);
		} catch (SQLException sqlex) {
			throw new H2ZeroFinderException(sqlex);
		} catch (H2ZeroFinderException h2zfex) {
			throw new H2ZeroFinderException(h2zfex.getMessage() + "  Additionally, the parameters were [idAllTypes:" + idAllTypes + "].");
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		if(null == allTypes) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idAllTypes:" + idAllTypes + "].");
		}
		return(allTypes);
	}

	/**
	 * Find a AllTypes by its primary key
	 * 
	 * @param idAllTypes the primary key
	 * 
	 * @return the unique result or throw an exception if one coudn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static AllTypes findByPrimaryKey(Long idAllTypes) throws H2ZeroFinderException {
		AllTypes allTypes = null;
		Connection connection = null;

		if(null == idAllTypes) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idAllTypes] was null.");
		}

		try {
			connection = ConnectionManager.getConnection();
			allTypes = findByPrimaryKey(connection, idAllTypes);
		} catch (SQLException sqlex) {
			throw new H2ZeroFinderException(sqlex);
		} catch (H2ZeroFinderException h2zfex) {
			throw new H2ZeroFinderException(h2zfex.getMessage() + "  Additionally, the parameters were [idAllTypes:" + idAllTypes + "].");
		} finally {
			ConnectionManager.closeAll(connection);
		}

		if(null == allTypes) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idAllTypes:" + idAllTypes + "].");
		}
		return(allTypes);
	}

	/**
	 * Find a AllTypes by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param connection the connection item
	 * @param idAllTypes the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static AllTypes findByPrimaryKeySilent(Connection connection, Long idAllTypes) {
		try {
			return(findByPrimaryKey(connection, idAllTypes));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idAllTypes + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find a AllTypes by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param idAllTypes the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static AllTypes findByPrimaryKeySilent(Long idAllTypes) {
		try {
			return(findByPrimaryKey(idAllTypes));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idAllTypes + "): " + h2zfex.getMessage());
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
	public static List<AllTypes> findAll(Connection connection, Integer limit, Integer offset) throws SQLException {
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

		List<AllTypes> results = new ArrayList<AllTypes>();

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

	public static List<AllTypes> findAll() throws SQLException {
		return(findAll(null, null, null));
	}

	public static List<AllTypes> findAll(Connection connection) throws SQLException {
		return(findAll(connection, null, null));
	}

	public static List<AllTypes> findAll(Integer limit, Integer offset) throws SQLException {
		return(findAll(null, limit, offset));
	}

	public static List<AllTypes> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException sqlex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(new ArrayList<AllTypes>());
		}
	}

	public static List<AllTypes> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	public static List<AllTypes> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	public static List<AllTypes> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/**
	 * Return a unique result for the query - in effect just the first result of
	 * query.
	 * 
	 * @param resultSet The result set of the query
	 * 
	 * @return The AllTypes that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static AllTypes uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			Long idAllTypes = ConnectionManager.getNullableResultLong(resultSet, 1);
			Long testBigint = ConnectionManager.getNullableResultLong(resultSet, 2);
			Boolean testBoolean = ConnectionManager.getNullableResultBoolean(resultSet, 3);
			Date testDate = ConnectionManager.getNullableResultDate(resultSet, 4);
			Timestamp testDatetime = ConnectionManager.getNullableResultTimestamp(resultSet, 5);
			Double testDouble = ConnectionManager.getNullableResultDouble(resultSet, 6);
			Float testFloat = ConnectionManager.getNullableResultFloat(resultSet, 7);
			Integer testInt = ConnectionManager.getNullableResultInt(resultSet, 8);
			Integer testInteger = ConnectionManager.getNullableResultInt(resultSet, 9);
			Integer testMediumint = ConnectionManager.getNullableResultInt(resultSet, 10);
			BigDecimal testNumeric = ConnectionManager.getNullableResultBigDecimal(resultSet, 11);
			Short testSmallint = ConnectionManager.getNullableResultShort(resultSet, 12);
			String testText = ConnectionManager.getNullableResultString(resultSet, 13);
			Boolean testTinyint = ConnectionManager.getNullableResultBoolean(resultSet, 14);
			String testVarchar = ConnectionManager.getNullableResultString(resultSet, 15);

			AllTypes allTypes = new AllTypes(idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar);

			if(resultSet.next()) {
				throw new H2ZeroFinderException("More than one result in resultset for unique finder.");
			} else {
				return(allTypes);
			}
		} else {
			// could not get a result
			return(null);
		}
	}

	/**
	 * Return the results as a list of AllTypes, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of AllTypes
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<AllTypes> list(ResultSet resultSet) throws SQLException {
		List<AllTypes> arrayList = new ArrayList<AllTypes>();
		while(resultSet.next()) {
			arrayList.add(new AllTypes(
					ConnectionManager.getNullableResultLong(resultSet, 1),
					ConnectionManager.getNullableResultLong(resultSet, 2),
					ConnectionManager.getNullableResultBoolean(resultSet, 3),
					ConnectionManager.getNullableResultDate(resultSet, 4),
					ConnectionManager.getNullableResultTimestamp(resultSet, 5),
					ConnectionManager.getNullableResultDouble(resultSet, 6),
					ConnectionManager.getNullableResultFloat(resultSet, 7),
					ConnectionManager.getNullableResultInt(resultSet, 8),
					ConnectionManager.getNullableResultInt(resultSet, 9),
					ConnectionManager.getNullableResultInt(resultSet, 10),
					ConnectionManager.getNullableResultBigDecimal(resultSet, 11),
					ConnectionManager.getNullableResultShort(resultSet, 12),
					ConnectionManager.getNullableResultString(resultSet, 13),
					ConnectionManager.getNullableResultBoolean(resultSet, 14),
					ConnectionManager.getNullableResultString(resultSet, 15)));
		}
		return(arrayList);
	}

}