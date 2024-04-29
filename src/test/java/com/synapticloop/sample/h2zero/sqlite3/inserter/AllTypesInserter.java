package com.synapticloop.sample.h2zero.sqlite3.inserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-inserter.templar)

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

/**
 * This class contains the methods to insert new rows into the: 
 *   <code>sample.all_types</code>
 * table.  The fields that are available are as follows:
 * 
 * <ul>
 *  <li><code>id_all_types</code> (bigint)  <strong>NOT</strong> nullable  (PRIMARY)</li>
 *  <li><code>test_bigint</code> (bigint)  nullable </li>
 *  <li><code>test_boolean</code> (boolean)  nullable </li>
 *  <li><code>test_date</code> (date)  nullable </li>
 *  <li><code>test_datetime</code> (datetime)  nullable </li>
 *  <li><code>test_double</code> (double)  nullable </li>
 *  <li><code>test_float</code> (float)  nullable </li>
 *  <li><code>test_int</code> (int)  nullable </li>
 *  <li><code>test_integer</code> (integer)  nullable </li>
 *  <li><code>test_mediumint</code> (mediumint)  nullable </li>
 *  <li><code>test_numeric</code> (numeric)  nullable </li>
 *  <li><code>test_smallint</code> (smallint)  nullable </li>
 *  <li><code>test_text</code> (text)  nullable </li>
 *  <li><code>test_tinyint</code> (tinyint)  nullable </li>
 *  <li><code>test_varchar</code> (varchar)  nullable </li>
 * </ul>
 * 
 * @author synapticloop h2zero
 */

public class AllTypesInserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

		private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesInserter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_INSERT_ALL = "insert into all_types(id_all_types, test_bigint, test_boolean, test_date, test_datetime, test_double, test_float, test_int, test_integer, test_mediumint, test_numeric, test_smallint, test_text, test_tinyint, test_varchar)";
	private static final String SQL_BUILTIN_INSERT_VALUES = SQL_BUILTIN_INSERT_ALL + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// static inserter SQL generated from the user input

	private AllTypesInserter() {}

	/**
	 * Insert a new AllTypes into the database utilising the passed in connection.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idAllTypes  maps to id_all_types
	 * @param testBigint  maps to test_bigint
	 * @param testBoolean  maps to test_boolean
	 * @param testDate  maps to test_date
	 * @param testDatetime  maps to test_datetime
	 * @param testDouble  maps to test_double
	 * @param testFloat  maps to test_float
	 * @param testInt  maps to test_int
	 * @param testInteger  maps to test_integer
	 * @param testMediumint  maps to test_mediumint
	 * @param testNumeric  maps to test_numeric
	 * @param testSmallint  maps to test_smallint
	 * @param testText  maps to test_text
	 * @param testTinyint  maps to test_tinyint
	 * @param testVarchar  maps to test_varchar
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, String testText, Boolean testTinyint, String testVarchar) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigint(preparedStatement, 1, idAllTypes);
			ConnectionManager.setBigint(preparedStatement, 2, testBigint);
			ConnectionManager.setBoolean(preparedStatement, 3, testBoolean);
			ConnectionManager.setDate(preparedStatement, 4, testDate);
			ConnectionManager.setDatetime(preparedStatement, 5, testDatetime);
			ConnectionManager.setDouble(preparedStatement, 6, testDouble);
			ConnectionManager.setFloat(preparedStatement, 7, testFloat);
			ConnectionManager.setInt(preparedStatement, 8, testInt);
			ConnectionManager.setInteger(preparedStatement, 9, testInteger);
			ConnectionManager.setMediumint(preparedStatement, 10, testMediumint);
			ConnectionManager.setNumeric(preparedStatement, 11, testNumeric);
			ConnectionManager.setSmallint(preparedStatement, 12, testSmallint);
			ConnectionManager.setText(preparedStatement, 13, testText);
			ConnectionManager.setTinyint(preparedStatement, 14, testTinyint);
			ConnectionManager.setVarchar(preparedStatement, 15, testVarchar);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Insert a new AllTypes into the database utilising the passed in connection 
	 * with only the fields that are allowed to be not null.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idAllTypes  maps to id_all_types
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idAllTypes) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigint(preparedStatement, 1, idAllTypes);
			ConnectionManager.setBigint(preparedStatement, 2, null);
			ConnectionManager.setBoolean(preparedStatement, 3, null);
			ConnectionManager.setDate(preparedStatement, 4, null);
			ConnectionManager.setDatetime(preparedStatement, 5, null);
			ConnectionManager.setDouble(preparedStatement, 6, null);
			ConnectionManager.setFloat(preparedStatement, 7, null);
			ConnectionManager.setInt(preparedStatement, 8, null);
			ConnectionManager.setInteger(preparedStatement, 9, null);
			ConnectionManager.setMediumint(preparedStatement, 10, null);
			ConnectionManager.setNumeric(preparedStatement, 11, null);
			ConnectionManager.setSmallint(preparedStatement, 12, null);
			String testTextString = null;
			ConnectionManager.setText(preparedStatement, 13, testTextString);
			ConnectionManager.setTinyint(preparedStatement, 14, null);
			ConnectionManager.setVarchar(preparedStatement, 15, null);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Insert a new AllTypes into the database a new connection will be retrieved 
	 * from the pool, used and then closed.
	 * 
	 * @param idAllTypes  maps to id_all_types
	 * @param testBigint  maps to test_bigint
	 * @param testBoolean  maps to test_boolean
	 * @param testDate  maps to test_date
	 * @param testDatetime  maps to test_datetime
	 * @param testDouble  maps to test_double
	 * @param testFloat  maps to test_float
	 * @param testInt  maps to test_int
	 * @param testInteger  maps to test_integer
	 * @param testMediumint  maps to test_mediumint
	 * @param testNumeric  maps to test_numeric
	 * @param testSmallint  maps to test_smallint
	 * @param testText  maps to test_text
	 * @param testTinyint  maps to test_tinyint
	 * @param testVarchar  maps to test_varchar
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, String testText, Boolean testTinyint, String testVarchar) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar));
		}
	}

	/**
	 * Insert a new AllTypes into the database a new connection will be retrieved 
	 * from the pool, used and then closed. This is for fields which have a nullable allowed default
	 * 
	 * @param idAllTypes  maps to id_all_types
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idAllTypes) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idAllTypes));
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new AllTypes into the 
	 * database utilising the passed in connection. If an exception is thrown by the
	 * method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idAllTypes  maps to id_all_types
	 * @param testBigint  maps to test_bigint
	 * @param testBoolean  maps to test_boolean
	 * @param testDate  maps to test_date
	 * @param testDatetime  maps to test_datetime
	 * @param testDouble  maps to test_double
	 * @param testFloat  maps to test_float
	 * @param testInt  maps to test_int
	 * @param testInteger  maps to test_integer
	 * @param testMediumint  maps to test_mediumint
	 * @param testNumeric  maps to test_numeric
	 * @param testSmallint  maps to test_smallint
	 * @param testText  maps to test_text
	 * @param testTinyint  maps to test_tinyint
	 * @param testVarchar  maps to test_varchar
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, String testText, Boolean testTinyint, String testVarchar) {
		try {
			return(insert(connection, idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new AllTypes into the 
	 * database utilising the passed in connection. If an exception is thrown by the
	 * method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * This is only for Non-Nullable fields
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idAllTypes  maps to id_all_types
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idAllTypes) {
		try {
			return(insert(connection, idAllTypes));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new AllTypes into the 
	 * database, creating and closing a connection in the process. If an exception is thrown 
	 * by the method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param idAllTypes  maps to id_all_types
	 * @param testBigint  maps to test_bigint
	 * @param testBoolean  maps to test_boolean
	 * @param testDate  maps to test_date
	 * @param testDatetime  maps to test_datetime
	 * @param testDouble  maps to test_double
	 * @param testFloat  maps to test_float
	 * @param testInt  maps to test_int
	 * @param testInteger  maps to test_integer
	 * @param testMediumint  maps to test_mediumint
	 * @param testNumeric  maps to test_numeric
	 * @param testSmallint  maps to test_smallint
	 * @param testText  maps to test_text
	 * @param testTinyint  maps to test_tinyint
	 * @param testVarchar  maps to test_varchar
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, String testText, Boolean testTinyint, String testVarchar) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(insert(connection, idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new AllTypes into the 
	 * database, creating and closing a connection in the process. If an exception is thrown 
	 * by the method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * This is for non-nullable fields only
	 * 
	 * @param idAllTypes  maps to id_all_types
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idAllTypes) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idAllTypes));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	public static int insert(Connection connection, Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, InputStream testText, Boolean testTinyint, String testVarchar) throws SQLException {
		int numResults = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES);
			ConnectionManager.setBigint(preparedStatement, 1, idAllTypes);
			ConnectionManager.setBigint(preparedStatement, 2, testBigint);
			ConnectionManager.setBoolean(preparedStatement, 3, testBoolean);
			ConnectionManager.setDate(preparedStatement, 4, testDate);
			ConnectionManager.setDatetime(preparedStatement, 5, testDatetime);
			ConnectionManager.setDouble(preparedStatement, 6, testDouble);
			ConnectionManager.setFloat(preparedStatement, 7, testFloat);
			ConnectionManager.setInt(preparedStatement, 8, testInt);
			ConnectionManager.setInteger(preparedStatement, 9, testInteger);
			ConnectionManager.setMediumint(preparedStatement, 10, testMediumint);
			ConnectionManager.setNumeric(preparedStatement, 11, testNumeric);
			ConnectionManager.setSmallint(preparedStatement, 12, testSmallint);
			ConnectionManager.setTextInputStream(preparedStatement, 13, testText);
			ConnectionManager.setTinyint(preparedStatement, 14, testTinyint);
			ConnectionManager.setVarchar(preparedStatement, 15, testVarchar);
			numResults = preparedStatement.executeUpdate();
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(preparedStatement);
		}
		return(numResults);
	}

	public static int insert(Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, InputStream testText, Boolean testTinyint, String testVarchar) throws SQLException {
		int numResults = -1;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = insert(connection, idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

	public static int insertSilent(Connection connection, Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, InputStream testText, Boolean testTinyint, String testVarchar) {
		int numResults = -1;
		try {
			numResults = insert(connection, idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar);
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
		}
		return(numResults);
	}

	public static int insertSilent(Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, InputStream testText, Boolean testTinyint, String testVarchar) {
		int numResults = 0;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = insert(connection, idAllTypes, testBigint, testBoolean, testDate, testDatetime, testDouble, testFloat, testInt, testInteger, testMediumint, testNumeric, testSmallint, testText, testTinyint, testVarchar);
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

}