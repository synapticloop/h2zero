package com.synapticloop.sample.h2zero.mariadb.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//         (/java/deleter/java-create-deleter.templar)

import java.sql.Timestamp;
import java.sql.*;
import java.math.BigDecimal;

import com.synapticloop.h2zero.generator.util.LruCache;

import com.synapticloop.h2zero.base.sql.limitoffset.Deleter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;

public class AllTypesDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesDeleter.class);

	// static fields generated by synapticloop h2zero
	//private static final String SQL_BUILTIN_DELETE_ALL = "truncate table all_types";
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from all_types";
	private static final String SQL_DELETE_START = "delete from all_types ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_all_types = ?";

	// static fields generated from the user input
	private static final String SQL_DELETE_BY_ID_ALL_TYPES = SQL_DELETE_START + " where id_all_types = ?";
	private static final String SQL_DELETE_BY_TEST_BIGINT = SQL_DELETE_START + " where test_bigint = ?";
	private static final String SQL_DELETE_BY_TEST_BOOL = SQL_DELETE_START + " where test_bool = ?";
	private static final String SQL_DELETE_BY_TEST_CHAR = SQL_DELETE_START + " where test_char = ?";
	private static final String SQL_DELETE_BY_TEST_BOOLEAN = SQL_DELETE_START + " where test_boolean = ?";
	private static final String SQL_DELETE_BY_TEST_DATE = SQL_DELETE_START + " where test_date = ?";
	private static final String SQL_DELETE_BY_TEST_DATETIME = SQL_DELETE_START + " where test_datetime = ?";
	private static final String SQL_DELETE_BY_TEST_DEC = SQL_DELETE_START + " where test_dec = ?";
	private static final String SQL_DELETE_BY_TEST_DECIMAL = SQL_DELETE_START + " where test_decimal = ?";
	private static final String SQL_DELETE_BY_TEST_DOUBLE = SQL_DELETE_START + " where test_double = ?";
	private static final String SQL_DELETE_BY_TEST_FLOAT = SQL_DELETE_START + " where test_float = ?";
	private static final String SQL_DELETE_BY_TEST_INT = SQL_DELETE_START + " where test_int = ?";
	private static final String SQL_DELETE_BY_TEST_INTEGER = SQL_DELETE_START + " where test_integer = ?";
	private static final String SQL_DELETE_BY_TEST_LONGTEXT = SQL_DELETE_START + " where test_longtext = ?";
	private static final String SQL_DELETE_BY_TEST_MEDIUMINT = SQL_DELETE_START + " where test_mediumint = ?";
	private static final String SQL_DELETE_BY_TEST_MEDIUMTEXT = SQL_DELETE_START + " where test_mediumtext = ?";
	private static final String SQL_DELETE_BY_TEST_NUMERIC = SQL_DELETE_START + " where test_numeric = ?";
	private static final String SQL_DELETE_BY_TEST_SMALLINT = SQL_DELETE_START + " where test_smallint = ?";
	private static final String SQL_DELETE_BY_TEST_TIME = SQL_DELETE_START + " where test_time = ?";
	private static final String SQL_DELETE_BY_TEST_TEXT = SQL_DELETE_START + " where test_text = ?";
	private static final String SQL_DELETE_BY_TEST_TIMESTAMP = SQL_DELETE_START + " where test_timestamp = ?";
	private static final String SQL_DELETE_BY_TEST_TINYINT = SQL_DELETE_START + " where test_tinyint = ?";
	private static final String SQL_DELETE_BY_TEST_TINYTEXT = SQL_DELETE_START + " where test_tinytext = ?";
	private static final String SQL_DELETE_BY_TEST_VARCHAR = SQL_DELETE_START + " where test_varchar = ?";
	private static final String SQL_DELETE_BY_TEST_YEAR = SQL_DELETE_START + " where test_year = ?";
	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByIdAllTypes_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestBigint_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestBool_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestChar_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestBoolean_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestDate_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestDatetime_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestDec_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestDecimal_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestDouble_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestFloat_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestInt_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestInteger_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestLongtext_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestMediumint_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestMediumtext_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestNumeric_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestSmallint_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestTime_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestText_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestTimestamp_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestTinyint_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestTinytext_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestVarchar_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByTestYear_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private AllTypesDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the ALL_TYPES table by its primary key
	 * 
	 * @param idAllTypes the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 */
	public static Deleter deleteByPrimaryKey(Long idAllTypes) {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_BY_PRIMARY_KEY,
				new Object[] { idAllTypes }
		));
	}
	/**
	 * Delete all of the rows in the table 'all_types'.
	 * 
	 * This table has no foreign key relationships and consequently can be truncated.
	 * 
	 * 
	 * @return The number of rows affected by this statement
	 */
	public static Deleter deleteAll() {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_ALL,
				new Object[] { }
		));
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 *     USER DEFINED DELETERS FOR THE TABLE: all_types
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 25 defined Deleters on the all_types table:
	 * 
	 * - deleteByIdAllTypes - 'fieldDeleters' JSON key 
	 * - deleteByTestBigint - 'fieldDeleters' JSON key 
	 * - deleteByTestBool - 'fieldDeleters' JSON key 
	 * - deleteByTestChar - 'fieldDeleters' JSON key 
	 * - deleteByTestBoolean - 'fieldDeleters' JSON key 
	 * - deleteByTestDate - 'fieldDeleters' JSON key 
	 * - deleteByTestDatetime - 'fieldDeleters' JSON key 
	 * - deleteByTestDec - 'fieldDeleters' JSON key 
	 * - deleteByTestDecimal - 'fieldDeleters' JSON key 
	 * - deleteByTestDouble - 'fieldDeleters' JSON key 
	 * - deleteByTestFloat - 'fieldDeleters' JSON key 
	 * - deleteByTestInt - 'fieldDeleters' JSON key 
	 * - deleteByTestInteger - 'fieldDeleters' JSON key 
	 * - deleteByTestLongtext - 'fieldDeleters' JSON key 
	 * - deleteByTestMediumint - 'fieldDeleters' JSON key 
	 * - deleteByTestMediumtext - 'fieldDeleters' JSON key 
	 * - deleteByTestNumeric - 'fieldDeleters' JSON key 
	 * - deleteByTestSmallint - 'fieldDeleters' JSON key 
	 * - deleteByTestTime - 'fieldDeleters' JSON key 
	 * - deleteByTestText - 'fieldDeleters' JSON key 
	 * - deleteByTestTimestamp - 'fieldDeleters' JSON key 
	 * - deleteByTestTinyint - 'fieldDeleters' JSON key 
	 * - deleteByTestTinytext - 'fieldDeleters' JSON key 
	 * - deleteByTestVarchar - 'fieldDeleters' JSON key 
	 * - deleteByTestYear - 'fieldDeleters' JSON key 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByIdAllTypes - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param idAllTypes - maps to the id_all_types field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByIdAllTypes(Long idAllTypes ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_ID_ALL_TYPES,
				new Object[] {idAllTypes }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestBigint - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testBigint - maps to the test_bigint field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestBigint(Long testBigint ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_BIGINT,
				new Object[] {testBigint }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestBool - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testBool - maps to the test_bool field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestBool(Boolean testBool ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_BOOL,
				new Object[] {testBool }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestChar - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testChar - maps to the test_char field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestChar(String testChar ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_CHAR,
				new Object[] {testChar }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestBoolean - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testBoolean - maps to the test_boolean field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestBoolean(Boolean testBoolean ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_BOOLEAN,
				new Object[] {testBoolean }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestDate - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testDate - maps to the test_date field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestDate(Date testDate ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_DATE,
				new Object[] {testDate }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestDatetime - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testDatetime - maps to the test_datetime field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestDatetime(Timestamp testDatetime ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_DATETIME,
				new Object[] {testDatetime }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestDec - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testDec - maps to the test_dec field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestDec(BigDecimal testDec ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_DEC,
				new Object[] {testDec }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestDecimal - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testDecimal - maps to the test_decimal field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestDecimal(BigDecimal testDecimal ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_DECIMAL,
				new Object[] {testDecimal }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestDouble - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testDouble - maps to the test_double field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestDouble(Double testDouble ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_DOUBLE,
				new Object[] {testDouble }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestFloat - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testFloat - maps to the test_float field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestFloat(Float testFloat ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_FLOAT,
				new Object[] {testFloat }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestInt - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testInt - maps to the test_int field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestInt(Integer testInt ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_INT,
				new Object[] {testInt }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestInteger - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testInteger - maps to the test_integer field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestInteger(Integer testInteger ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_INTEGER,
				new Object[] {testInteger }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestLongtext - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testLongtext - maps to the test_longtext field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestLongtext(String testLongtext ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_LONGTEXT,
				new Object[] {testLongtext }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestMediumint - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testMediumint - maps to the test_mediumint field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestMediumint(Integer testMediumint ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_MEDIUMINT,
				new Object[] {testMediumint }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestMediumtext - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testMediumtext - maps to the test_mediumtext field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestMediumtext(String testMediumtext ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_MEDIUMTEXT,
				new Object[] {testMediumtext }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestNumeric - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testNumeric - maps to the test_numeric field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestNumeric(BigDecimal testNumeric ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_NUMERIC,
				new Object[] {testNumeric }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestSmallint - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testSmallint - maps to the test_smallint field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestSmallint(Short testSmallint ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_SMALLINT,
				new Object[] {testSmallint }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestTime - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testTime - maps to the test_time field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestTime(Time testTime ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_TIME,
				new Object[] {testTime }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestText - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testText - maps to the test_text field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestText(String testText ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_TEXT,
				new Object[] {testText }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestTimestamp - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testTimestamp - maps to the test_timestamp field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestTimestamp(Timestamp testTimestamp ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_TIMESTAMP,
				new Object[] {testTimestamp }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestTinyint - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testTinyint - maps to the test_tinyint field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestTinyint(Boolean testTinyint ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_TINYINT,
				new Object[] {testTinyint }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestTinytext - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testTinytext - maps to the test_tinytext field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestTinytext(String testTinytext ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_TINYTEXT,
				new Object[] {testTinytext }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestVarchar - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testVarchar - maps to the test_varchar field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestVarchar(String testVarchar ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_VARCHAR,
				new Object[] {testVarchar }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByTestYear - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param testYear - maps to the test_year field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByTestYear(Integer testYear ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_TEST_YEAR,
				new Object[] {testYear }
		));
	}
}