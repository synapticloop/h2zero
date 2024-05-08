package com.synapticloop.sample.h2zero.postgresql.inserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/java-create-inserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synapticloop.h2zero.base.manager.postgresql.ConnectionManager;
import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

/**
 * This class contains the methods to insert new rows into the: 
 *   <code>sample.all_types</code>
 * table.  The fields that are available are as follows:
 * 
 * <ul>
 *  <li><code>id_all_types</code> (bigserial)  <strong>NOT</strong> nullable  (PRIMARY)</li>
 *  <li><code>num_smallint</code> (smallint)  nullable </li>
 *  <li><code>num_integer</code> (integer)  nullable </li>
 *  <li><code>num_bigint</code> (bigint)  nullable </li>
 *  <li><code>num_decimal</code> (decimal)  nullable </li>
 *  <li><code>num_numeric</code> (numeric)  nullable </li>
 *  <li><code>flt_real</code> (real)  nullable </li>
 *  <li><code>dbl_real</code> (double)  nullable </li>
 *  <li><code>num_serial</code> (serial)  <strong>NOT</strong> nullable </li>
 *  <li><code>num_smallserial</code> (smallserial)  <strong>NOT</strong> nullable </li>
 *  <li><code>num_bigserial</code> (bigserial)  <strong>NOT</strong> nullable </li>
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
	private static final String SQL_BUILTIN_INSERT_ALL = "insert into all_types(id_all_types, num_smallint, num_integer, num_bigint, num_decimal, num_numeric, flt_real, dbl_real, num_serial, num_smallserial, num_bigserial)";
	private static final String SQL_BUILTIN_INSERT_VALUES = SQL_BUILTIN_INSERT_ALL + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// static inserter SQL generated from the user input

	private AllTypesInserter() {}

	/**
	 * Insert a new AllTypes into the database utilising the passed in connection.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idAllTypes  maps to id_all_types
	 * @param numSmallint  maps to num_smallint
	 * @param numInteger  maps to num_integer
	 * @param numBigint  maps to num_bigint
	 * @param numDecimal  maps to num_decimal
	 * @param numNumeric  maps to num_numeric
	 * @param fltReal  maps to flt_real
	 * @param dblReal  maps to dbl_real
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idAllTypes, Short numSmallint, Integer numInteger, Long numBigint, BigDecimal numDecimal, BigDecimal numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigserial(preparedStatement, 1, idAllTypes);
			ConnectionManager.setSmallint(preparedStatement, 2, numSmallint);
			ConnectionManager.setInteger(preparedStatement, 3, numInteger);
			ConnectionManager.setBigint(preparedStatement, 4, numBigint);
			ConnectionManager.setDecimal(preparedStatement, 5, numDecimal);
			ConnectionManager.setNumeric(preparedStatement, 6, numNumeric);
			ConnectionManager.setReal(preparedStatement, 7, fltReal);
			ConnectionManager.setDouble(preparedStatement, 8, dblReal);
			ConnectionManager.setSerial(preparedStatement, 9, numSerial);
			ConnectionManager.setSmallserial(preparedStatement, 10, numSmallserial);
			ConnectionManager.setBigserial(preparedStatement, 11, numBigserial);
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
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idAllTypes, Integer numSerial, Short numSmallserial, Long numBigserial) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigserial(preparedStatement, 1, idAllTypes);
			ConnectionManager.setSmallint(preparedStatement, 2, null);
			ConnectionManager.setInteger(preparedStatement, 3, null);
			ConnectionManager.setBigint(preparedStatement, 4, null);
			ConnectionManager.setDecimal(preparedStatement, 5, null);
			ConnectionManager.setNumeric(preparedStatement, 6, null);
			ConnectionManager.setReal(preparedStatement, 7, null);
			ConnectionManager.setDouble(preparedStatement, 8, null);
			ConnectionManager.setSerial(preparedStatement, 9, numSerial);
			ConnectionManager.setSmallserial(preparedStatement, 10, numSmallserial);
			ConnectionManager.setBigserial(preparedStatement, 11, numBigserial);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Insert a new AllTypes into the database a new connection will be retrieved 
	 * from the pool, used and then closed.
	 * 
	 * @param idAllTypes  maps to id_all_types
	 * @param numSmallint  maps to num_smallint
	 * @param numInteger  maps to num_integer
	 * @param numBigint  maps to num_bigint
	 * @param numDecimal  maps to num_decimal
	 * @param numNumeric  maps to num_numeric
	 * @param fltReal  maps to flt_real
	 * @param dblReal  maps to dbl_real
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idAllTypes, Short numSmallint, Integer numInteger, Long numBigint, BigDecimal numDecimal, BigDecimal numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idAllTypes, numSmallint, numInteger, numBigint, numDecimal, numNumeric, fltReal, dblReal, numSerial, numSmallserial, numBigserial));
		}
	}

	/**
	 * Insert a new AllTypes into the database a new connection will be retrieved 
	 * from the pool, used and then closed. This is for fields which have a nullable allowed default
	 * 
	 * @param idAllTypes  maps to id_all_types
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idAllTypes, Integer numSerial, Short numSmallserial, Long numBigserial) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idAllTypes, numSerial, numSmallserial, numBigserial));
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
	 * @param numSmallint  maps to num_smallint
	 * @param numInteger  maps to num_integer
	 * @param numBigint  maps to num_bigint
	 * @param numDecimal  maps to num_decimal
	 * @param numNumeric  maps to num_numeric
	 * @param fltReal  maps to flt_real
	 * @param dblReal  maps to dbl_real
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idAllTypes, Short numSmallint, Integer numInteger, Long numBigint, BigDecimal numDecimal, BigDecimal numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) {
		try {
			return(insert(connection, idAllTypes, numSmallint, numInteger, numBigint, numDecimal, numNumeric, fltReal, dblReal, numSerial, numSmallserial, numBigserial));
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
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idAllTypes, Integer numSerial, Short numSmallserial, Long numBigserial) {
		try {
			return(insert(connection, idAllTypes, numSerial, numSmallserial, numBigserial));
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
	 * @param numSmallint  maps to num_smallint
	 * @param numInteger  maps to num_integer
	 * @param numBigint  maps to num_bigint
	 * @param numDecimal  maps to num_decimal
	 * @param numNumeric  maps to num_numeric
	 * @param fltReal  maps to flt_real
	 * @param dblReal  maps to dbl_real
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idAllTypes, Short numSmallint, Integer numInteger, Long numBigint, BigDecimal numDecimal, BigDecimal numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(insert(connection, idAllTypes, numSmallint, numInteger, numBigint, numDecimal, numNumeric, fltReal, dblReal, numSerial, numSmallserial, numBigserial));
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
	 * @param numSerial  maps to num_serial
	 * @param numSmallserial  maps to num_smallserial
	 * @param numBigserial  maps to num_bigserial
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idAllTypes, Integer numSerial, Short numSmallserial, Long numBigserial) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idAllTypes, numSerial, numSmallserial, numBigserial));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

}