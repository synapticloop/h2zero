package synapticloop.sample.h2zero.mysql.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Blob;
import java.sql.Timestamp;
import java.sql.Time;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;

import synapticloop.sample.h2zero.mysql.model.AllTypes;

public class AllTypesFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_all_types, 
				test_bigint, 
				test_blob, 
				test_bool, 
				test_char, 
				test_boolean, 
				test_binary, 
				test_varbinary, 
				test_date, 
				test_datetime, 
				test_dec, 
				test_decimal, 
				test_double, 
				test_float, 
				test_int, 
				test_integer, 
				test_longtext, 
				test_mediumblob, 
				test_mediumint, 
				test_mediumtext, 
				test_numeric, 
				test_smallint, 
				test_time, 
				test_text, 
				test_timestamp, 
				test_tinyint, 
				test_tinytext, 
				test_varchar, 
				test_year
			from 
				all_types
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_all_types = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> findAll_limit_statement_cache = new LruCache<>(1024);

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
		} catch (H2ZeroFinderException | SQLException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idAllTypes:" + idAllTypes + "].");
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
	 * @return the unique result or throw an exception if one couldn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static AllTypes findByPrimaryKey(Long idAllTypes) throws H2ZeroFinderException {

		if(null == idAllTypes) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idAllTypes] was null.");
		}

		AllTypes allTypes = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			allTypes = findByPrimaryKey(connection, idAllTypes);
		} catch (SQLException | H2ZeroFinderException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idAllTypes:" + idAllTypes + "].");
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
	 * @return a list of all the AllTypes objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<AllTypes> findAll(Connection connection, Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
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
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters.
	 * 
	 * @return The list of AllTypes model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<AllTypes> findAll() throws SQLException, H2ZeroFinderException {
		return(findAll(null, null, null));
	}

	/**
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset
	 * parameters.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of AllTypes model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<AllTypes> findAll(Connection connection) throws SQLException, H2ZeroFinderException {
		return(findAll(connection, null, null));
	}

	/**
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null connection parameter
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of AllTypes model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<AllTypes> findAll(Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
		return(findAll(null, limit, offset));
	}

	/**
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * 
	 * @param connection - the connection to be used
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of AllTypes model objects, or an empty List on error
	 */
	public static List<AllTypes> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException | H2ZeroFinderException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("Exception findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			return(new ArrayList<AllTypes>());
		}
	}

	/**
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of AllTypes model objects, or an empty List on error
	 */
	public static List<AllTypes> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	/**
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of AllTypes model objects, or an empty List on error
	 */
	public static List<AllTypes> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	/**
	 * Find all the AllTypes objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @return The list of AllTypes model objects, or an empty List on error
	 */
	public static List<AllTypes> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined finders which are generated
	 * through either the "finders" JSON key, or the "fieldFinders" JSON
	 * key.
	 * 
	 * There are 0 defined finders on the all_types table, of those finders
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
	 * @return The AllTypes that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found or more than one result was found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static AllTypes uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			Long idAllTypes = ConnectionManager.getNullableResultLong(resultSet, 1);
			Long testBigint = ConnectionManager.getNullableResultLong(resultSet, 2);
			Blob testBlob = ConnectionManager.getNullableResultBlob(resultSet, 3);
			Boolean testBool = ConnectionManager.getNullableResultBoolean(resultSet, 4);
			String testChar = ConnectionManager.getNullableResultString(resultSet, 5);
			Boolean testBoolean = ConnectionManager.getNullableResultBoolean(resultSet, 6);
			String testBinary = ConnectionManager.getNullableResultString(resultSet, 7);
			String testVarbinary = ConnectionManager.getNullableResultString(resultSet, 8);
			Date testDate = ConnectionManager.getNullableResultDate(resultSet, 9);
			Timestamp testDatetime = ConnectionManager.getNullableResultTimestamp(resultSet, 10);
			BigDecimal testDec = ConnectionManager.getNullableResultBigDecimal(resultSet, 11);
			BigDecimal testDecimal = ConnectionManager.getNullableResultBigDecimal(resultSet, 12);
			Double testDouble = ConnectionManager.getNullableResultDouble(resultSet, 13);
			Float testFloat = ConnectionManager.getNullableResultFloat(resultSet, 14);
			Integer testInt = ConnectionManager.getNullableResultInt(resultSet, 15);
			Integer testInteger = ConnectionManager.getNullableResultInt(resultSet, 16);
			String testLongtext = ConnectionManager.getNullableResultString(resultSet, 17);
			Blob testMediumblob = ConnectionManager.getNullableResultBlob(resultSet, 18);
			Integer testMediumint = ConnectionManager.getNullableResultInt(resultSet, 19);
			String testMediumtext = ConnectionManager.getNullableResultString(resultSet, 20);
			BigDecimal testNumeric = ConnectionManager.getNullableResultBigDecimal(resultSet, 21);
			Short testSmallint = ConnectionManager.getNullableResultShort(resultSet, 22);
			Time testTime = ConnectionManager.getNullableResultTime(resultSet, 23);
			String testText = ConnectionManager.getNullableResultString(resultSet, 24);
			Timestamp testTimestamp = ConnectionManager.getNullableResultTimestamp(resultSet, 25);
			Boolean testTinyint = ConnectionManager.getNullableResultBoolean(resultSet, 26);
			String testTinytext = ConnectionManager.getNullableResultString(resultSet, 27);
			String testVarchar = ConnectionManager.getNullableResultString(resultSet, 28);
			Integer testYear = ConnectionManager.getNullableResultInt(resultSet, 29);

			AllTypes allTypes = new AllTypes(idAllTypes, testBigint, testBlob, testBool, testChar, testBoolean, testBinary, testVarbinary, testDate, testDatetime, testDec, testDecimal, testDouble, testFloat, testInt, testInteger, testLongtext, testMediumblob, testMediumint, testMediumtext, testNumeric, testSmallint, testTime, testText, testTimestamp, testTinyint, testTinytext, testVarchar, testYear);

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
					ConnectionManager.getNullableResultBlob(resultSet, 3),
					ConnectionManager.getNullableResultBoolean(resultSet, 4),
					ConnectionManager.getNullableResultString(resultSet, 5),
					ConnectionManager.getNullableResultBoolean(resultSet, 6),
					ConnectionManager.getNullableResultString(resultSet, 7),
					ConnectionManager.getNullableResultString(resultSet, 8),
					ConnectionManager.getNullableResultDate(resultSet, 9),
					ConnectionManager.getNullableResultTimestamp(resultSet, 10),
					ConnectionManager.getNullableResultBigDecimal(resultSet, 11),
					ConnectionManager.getNullableResultBigDecimal(resultSet, 12),
					ConnectionManager.getNullableResultDouble(resultSet, 13),
					ConnectionManager.getNullableResultFloat(resultSet, 14),
					ConnectionManager.getNullableResultInt(resultSet, 15),
					ConnectionManager.getNullableResultInt(resultSet, 16),
					ConnectionManager.getNullableResultString(resultSet, 17),
					ConnectionManager.getNullableResultBlob(resultSet, 18),
					ConnectionManager.getNullableResultInt(resultSet, 19),
					ConnectionManager.getNullableResultString(resultSet, 20),
					ConnectionManager.getNullableResultBigDecimal(resultSet, 21),
					ConnectionManager.getNullableResultShort(resultSet, 22),
					ConnectionManager.getNullableResultTime(resultSet, 23),
					ConnectionManager.getNullableResultString(resultSet, 24),
					ConnectionManager.getNullableResultTimestamp(resultSet, 25),
					ConnectionManager.getNullableResultBoolean(resultSet, 26),
					ConnectionManager.getNullableResultString(resultSet, 27),
					ConnectionManager.getNullableResultString(resultSet, 28),
					ConnectionManager.getNullableResultInt(resultSet, 29)));
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
	 * There are 0 defined finders on the all_types table, of those finders
	 * the following are the select clause finders:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}