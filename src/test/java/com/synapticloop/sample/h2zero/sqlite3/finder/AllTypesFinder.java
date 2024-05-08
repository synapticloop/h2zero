package com.synapticloop.sample.h2zero.sqlite3.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (/java/finder/java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import com.synapticloop.sample.h2zero.sqlite3.model.AllTypes;

import com.synapticloop.h2zero.base.sql.limitoffset.MultiFinder;
import com.synapticloop.h2zero.base.sql.limitoffset.UniqueFinder;

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
				test_boolean, 
				test_date, 
				test_datetime, 
				test_double, 
				test_float, 
				test_int, 
				test_integer, 
				test_mediumint, 
				test_numeric, 
				test_smallint, 
				test_text, 
				test_tinyint, 
				test_varchar
			from 
				all_types
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_all_types = ?";

	private static final String SQL_FIND_BY_ID_ALL_TYPES = SQL_SELECT_START + 
		"""
			where id_all_types = ?
		""";
	private static final String SQL_FIND_BY_TEST_BIGINT = SQL_SELECT_START + 
		"""
			where test_bigint = ?
		""";
	private static final String SQL_FIND_BY_TEST_BOOLEAN = SQL_SELECT_START + 
		"""
			where test_boolean = ?
		""";
	private static final String SQL_FIND_BY_TEST_DATE = SQL_SELECT_START + 
		"""
			where test_date = ?
		""";
	private static final String SQL_FIND_BY_TEST_DATETIME = SQL_SELECT_START + 
		"""
			where test_datetime = ?
		""";
	private static final String SQL_FIND_BY_TEST_DOUBLE = SQL_SELECT_START + 
		"""
			where test_double = ?
		""";
	private static final String SQL_FIND_BY_TEST_FLOAT = SQL_SELECT_START + 
		"""
			where test_float = ?
		""";
	private static final String SQL_FIND_BY_TEST_INT = SQL_SELECT_START + 
		"""
			where test_int = ?
		""";
	private static final String SQL_FIND_BY_TEST_INTEGER = SQL_SELECT_START + 
		"""
			where test_integer = ?
		""";
	private static final String SQL_FIND_BY_TEST_MEDIUMINT = SQL_SELECT_START + 
		"""
			where test_mediumint = ?
		""";
	private static final String SQL_FIND_BY_TEST_NUMERIC = SQL_SELECT_START + 
		"""
			where test_numeric = ?
		""";
	private static final String SQL_FIND_BY_TEST_SMALLINT = SQL_SELECT_START + 
		"""
			where test_smallint = ?
		""";
	private static final String SQL_FIND_BY_TEST_TEXT = SQL_SELECT_START + 
		"""
			where test_text = ?
		""";
	private static final String SQL_FIND_BY_TEST_TINYINT = SQL_SELECT_START + 
		"""
			where test_tinyint = ?
		""";
	private static final String SQL_FIND_BY_TEST_VARCHAR = SQL_SELECT_START + 
		"""
			where test_varchar = ?
		""";

	private AllTypesFinder() {}

	/**
	 * <p>Create a UniqueFinder that can find a AllTypes by its primary key</p>
	 * 
	 * <p>This will return a UniqueFinder, to execute the finder, either call</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * @param idAllTypes the primary key
	 * 
	 * @return the parameterised UniqueFinder
	 */
	public static UniqueFinder<AllTypes> findByPrimaryKey(Long idAllTypes) {
		return(new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_BUILTIN_FIND_BY_PRIMARY_KEY,
				resultSet -> { try { return list(resultSet); } catch (SQLException e) { return(null); }},
				idAllTypes
		));
	}

	/**
	 * <p>Create a MultiFinder that can find all AllTypes rows</p>
	 * <p>
	 * <p>This will return a UniqueFinder, to execute the finder, either call</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findAll()
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findAll()
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findAll()
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 */
	public static MultiFinder<AllTypes> findAll() {
		return(
				new MultiFinder<AllTypes>(
				LOGGER,
				SQL_SELECT_START,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {}
		));
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined finders which are generated
	 * through either the "finders" JSON key, or the "fieldFinders" JSON
	 * key.
	 * 
	 * There are 15 defined finders on the all_types table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * - findByIdAllTypes - Generated from the 'fieldFinders' JSON key
	 * - findByTestBigint - Generated from the 'fieldFinders' JSON key
	 * - findByTestBoolean - Generated from the 'fieldFinders' JSON key
	 * - findByTestDate - Generated from the 'fieldFinders' JSON key
	 * - findByTestDatetime - Generated from the 'fieldFinders' JSON key
	 * - findByTestDouble - Generated from the 'fieldFinders' JSON key
	 * - findByTestFloat - Generated from the 'fieldFinders' JSON key
	 * - findByTestInt - Generated from the 'fieldFinders' JSON key
	 * - findByTestInteger - Generated from the 'fieldFinders' JSON key
	 * - findByTestMediumint - Generated from the 'fieldFinders' JSON key
	 * - findByTestNumeric - Generated from the 'fieldFinders' JSON key
	 * - findByTestSmallint - Generated from the 'fieldFinders' JSON key
	 * - findByTestText - Generated from the 'fieldFinders' JSON key
	 * - findByTestTinyint - Generated from the 'fieldFinders' JSON key
	 * - findByTestVarchar - Generated from the 'fieldFinders' JSON key
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * This is the <code>findByIdAllTypes</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByIdAllTypes(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByIdAllTypes(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByIdAllTypes(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param idAllTypes - maps to the id_all_types field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByIdAllTypes(Long idAllTypes) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_ID_ALL_TYPES,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {idAllTypes}
		));
	}
	/**
	 * This is the <code>findByTestBigint</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestBigint(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestBigint(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestBigint(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testBigint - maps to the test_bigint field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestBigint(Long testBigint) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_BIGINT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testBigint}
		));
	}
	/**
	 * This is the <code>findByTestBoolean</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestBoolean(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestBoolean(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestBoolean(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testBoolean - maps to the test_boolean field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestBoolean(Boolean testBoolean) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_BOOLEAN,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testBoolean}
		));
	}
	/**
	 * This is the <code>findByTestDate</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestDate(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestDate(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestDate(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testDate - maps to the test_date field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestDate(Date testDate) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_DATE,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testDate}
		));
	}
	/**
	 * This is the <code>findByTestDatetime</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestDatetime(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestDatetime(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestDatetime(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testDatetime - maps to the test_datetime field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestDatetime(Timestamp testDatetime) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_DATETIME,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testDatetime}
		));
	}
	/**
	 * This is the <code>findByTestDouble</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestDouble(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestDouble(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestDouble(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testDouble - maps to the test_double field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestDouble(Double testDouble) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_DOUBLE,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testDouble}
		));
	}
	/**
	 * This is the <code>findByTestFloat</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestFloat(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestFloat(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestFloat(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testFloat - maps to the test_float field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestFloat(Float testFloat) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_FLOAT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testFloat}
		));
	}
	/**
	 * This is the <code>findByTestInt</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestInt(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestInt(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestInt(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testInt - maps to the test_int field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestInt(Integer testInt) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_INT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testInt}
		));
	}
	/**
	 * This is the <code>findByTestInteger</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestInteger(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestInteger(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestInteger(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testInteger - maps to the test_integer field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestInteger(Integer testInteger) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_INTEGER,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testInteger}
		));
	}
	/**
	 * This is the <code>findByTestMediumint</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestMediumint(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestMediumint(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestMediumint(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testMediumint - maps to the test_mediumint field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestMediumint(Integer testMediumint) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_MEDIUMINT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testMediumint}
		));
	}
	/**
	 * This is the <code>findByTestNumeric</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestNumeric(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestNumeric(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestNumeric(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testNumeric - maps to the test_numeric field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestNumeric(BigDecimal testNumeric) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_NUMERIC,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testNumeric}
		));
	}
	/**
	 * This is the <code>findByTestSmallint</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestSmallint(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestSmallint(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestSmallint(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testSmallint - maps to the test_smallint field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestSmallint(Short testSmallint) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_SMALLINT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testSmallint}
		));
	}
	/**
	 * This is the <code>findByTestText</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestText(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestText(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestText(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testText - maps to the test_text field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestText(String testText) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_TEXT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testText}
		));
	}
	/**
	 * This is the <code>findByTestTinyint</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestTinyint(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestTinyint(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestTinyint(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testTinyint - maps to the test_tinyint field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestTinyint(Boolean testTinyint) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_TINYINT,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testTinyint}
		));
	}
	/**
	 * This is the <code>findByTestVarchar</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<AllTypes> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestVarchar(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>AllTypes.findByTestVarchar(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>AllTypes.findByTestVarchar(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param testVarchar - maps to the test_varchar field
	 * 
	 * @return the parameterised UniqueFinder()
	 * 
	 */
	public static UniqueFinder<AllTypes> findByTestVarchar(String testVarchar) {
		return(
				new UniqueFinder<AllTypes>(
				LOGGER,
				SQL_FIND_BY_TEST_VARCHAR,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {testVarchar}
		));
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
	 * There are 15 defined finders on the all_types table, of those finders
	 * the following are the select clause finders:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}