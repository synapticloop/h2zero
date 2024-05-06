package com.synapticloop.sample.h2zero.sqlite3.counter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//         (/java/counter/java-create-counter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.sql.*;

import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import com.synapticloop.h2zero.base.sql.nolimitoffset.Counter;

/**
 * <p>This class contains all of the counters that are defined in the h2zero
 * file.</p>
 * 
 * <p>A counter may only return a simple integer (i.e. the count) response
 * from a query.</p>
 * 
 * <p>Table name: <code>user</code></p>
 * 
 * <p>Counters defined:
 * <ul>
 * <li><code>countNumberOfUsers</code> - SQL query run {@link #SQL_COUNT_NUMBER_OF_USERS}</li>
 * <li><code>countNumberOfUsersOverAge</code> - SQL query run {@link #SQL_COUNT_NUMBER_OF_USERS_OVER_AGE}</li>
 * <li><code>countNumberOfUsersBetweenAge</code> - SQL query run {@link #SQL_COUNT_NUMBER_OF_USERS_BETWEEN_AGE}</li>
 * <li><code>countUsersInAges</code> - SQL query run {@link #SQL_COUNT_USERS_IN_AGES}</li>
 * </ul>
 * 
* <p>To execute any counter SQL query, you may optionally pass in a connection, and execute it (silently if desired)</p>
 *
 * <p><strong><em>Executing a counter without a passed in connection.</em></strong></p>
 *
 * <pre>UserCounter.counterName(parameter1, parameter2, ...)
 *          .execute();
 * </pre>
 *
 * <p><strong><em>Executing a counter WITH a passed in connection.</em></strong></p>
 *
 * <pre>UserCounter.counterName(parameter1, parameter2, ...)
 *          .withConnection(connection)
 *          .execute();
 *  </pre>
 *
 * <p><strong><em>Executing a counter WITHOUT a passed in connection SILENTLY
 * (i.e. All Exceptions are silently swallowed and logged as an error through
 * the logger.)</em></strong></p>
 *
 * <pre>UserCounter.counterName(parameter1, parameter2, ...)
 *          .executeSilent();
 * </pre>
 *
 * <p><strong><em>Executing a counter WITH a passed in connection SILENTLY
 * (i.e. All Exceptions are silently swallowed and logged as an error through
 * the logger.)</em></strong></p>
 *
 * <pre>UserCounter.counterName(parameter1, parameter2, ...)
 *          .withConnection(connection)
 *          .executeSilent();
 * </pre>
 *
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserCounter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCounter.class);


	private static final String SQL_BUILTIN_COUNT_ALL = "select count(*) from user";

	private static final String SQL_COUNT_NUMBER_OF_USERS = "select count(*) from user";
	private static final String SQL_COUNT_NUMBER_OF_USERS_OVER_AGE = "select count(*) from user" + " where num_age > ?";
	private static final String SQL_COUNT_NUMBER_OF_USERS_BETWEEN_AGE = "select count(*) from user" + " where num_age > ? and num_age < ?";
	private static final String SQL_COUNT_USERS_IN_AGES = "select count(*) from user" + " where num_age in (...)";

	private static Map<String, String> countUsersInAges_statement_cache = new HashMap<String, String>();

	private UserCounter() {}

	/**
	 * Find the count of all User objects
	 * 
	 * @return the Counter ready to be executed 
	 */
	public static Counter countAll() {
	return(
		new Counter(
				LOGGER,
				SQL_BUILTIN_COUNT_ALL));
	}

	/**
	 * <p>Return the counter object (optionally setting a connection) for this query,
	 * ready for execution.</p>
	 *
	 * <p>A <code>Connection</code> object may optionally be set for this query.
	 * Note that if no connection is set, one will automatically be retrieved from
	 * the connection pool.</p>
	 *
	 * <p><code>.withConnection(connection)</code></p>
	 *
	 * <p>And then can be executed with the following call:</p>
	 *
	 * <p><code>.execute()</code></p>
	 *
	 * <p>Alternatively the call can be executed silently (i.e. any exceptions that
	 * may have been thrown will be silently swallowed and the error logged.)</p>
	 *
	 * <p><code>.executeSilent()</code></p>
	 *
	 * <p>This counter executes the following sql statement:</p>
	 *
	 * <pre>select count(*) from user
	 * </pre>
	 *
	 *
	 * @return The counter object (optionally setting a connection) ready for 
	 *    and execution.
	 */
	public static Counter countNumberOfUsers() {
		return(
				new Counter(
						LOGGER, 
						SQL_COUNT_NUMBER_OF_USERS, 
						new Object[] {  } ));
	}

	/**
	 * <p>Return the counter object (optionally setting a connection) for this query,
	 * ready for execution.</p>
	 *
	 * <p>A <code>Connection</code> object may optionally be set for this query.
	 * Note that if no connection is set, one will automatically be retrieved from
	 * the connection pool.</p>
	 *
	 * <p><code>.withConnection(connection)</code></p>
	 *
	 * <p>And then can be executed with the following call:</p>
	 *
	 * <p><code>.execute()</code></p>
	 *
	 * <p>Alternatively the call can be executed silently (i.e. any exceptions that
	 * may have been thrown will be silently swallowed and the error logged.)</p>
	 *
	 * <p><code>.executeSilent()</code></p>
	 *
	 * <p>This counter executes the following sql statement:</p>
	 *
	 * <pre>select count(*) from user
	 * where num_age > ?
	 * </pre>
	 *
	 * @param numAge maps to the <code>num_age<code> field
	 *
	 * @return The counter object (optionally setting a connection) ready for 
	 *    and execution.
	 */
	public static Counter countNumberOfUsersOverAge(Integer numAge) {
		return(
				new Counter(
						LOGGER, 
						SQL_COUNT_NUMBER_OF_USERS_OVER_AGE, 
						new Object[] { numAge } ));
	}

	/**
	 * <p>Return the counter object (optionally setting a connection) for this query,
	 * ready for execution.</p>
	 *
	 * <p>A <code>Connection</code> object may optionally be set for this query.
	 * Note that if no connection is set, one will automatically be retrieved from
	 * the connection pool.</p>
	 *
	 * <p><code>.withConnection(connection)</code></p>
	 *
	 * <p>And then can be executed with the following call:</p>
	 *
	 * <p><code>.execute()</code></p>
	 *
	 * <p>Alternatively the call can be executed silently (i.e. any exceptions that
	 * may have been thrown will be silently swallowed and the error logged.)</p>
	 *
	 * <p><code>.executeSilent()</code></p>
	 *
	 * <p>This counter executes the following sql statement:</p>
	 *
	 * <pre>select count(*) from user
	 * where num_age > ? and num_age < ?
	 * </pre>
	 *
	 * @param numAgeFrom maps to the <code>num_age<code> field
	 * @param numAgeTo maps to the <code>num_age<code> field
	 *
	 * @return The counter object (optionally setting a connection) ready for 
	 *    and execution.
	 */
	public static Counter countNumberOfUsersBetweenAge(Integer numAgeFrom, Integer numAgeTo) {
		return(
				new Counter(
						LOGGER, 
						SQL_COUNT_NUMBER_OF_USERS_BETWEEN_AGE, 
						new Object[] { numAgeFrom, numAgeTo } ));
	}

	/**
	 * <p>Return the counter object (optionally setting a connection) for this query,
	 * ready for execution.</p>
	 *
	 * <p>A <code>Connection</code> object may optionally be set for this query.
	 * Note that if no connection is set, one will automatically be retrieved from
	 * the connection pool.</p>
	 *
	 * <p><code>.withConnection(connection)</code></p>
	 *
	 * <p>And then can be executed with the following call:</p>
	 *
	 * <p><code>.execute()</code></p>
	 *
	 * <p>Alternatively the call can be executed silently (i.e. any exceptions that
	 * may have been thrown will be silently swallowed and the error logged.)</p>
	 *
	 * <p><code>.executeSilent()</code></p>
	 *
	 * <p>This counter executes the following sql statement:</p>
	 *
	 * <pre>select count(*) from user
	 * where num_age in (...)
	 * </pre>
	 *
	 * @param numAgeList The list of <code>num_age<code> fields to be 
	 *        set on the prepared SQL statement which is part of an 'in' clause.
	 *
	 * @return The counter object (optionally setting a connection) ready for 
	 *    and execution.
	 */
	public static Counter countUsersInAges(List<Integer> numAgeList) {
		return(
				new Counter(
						LOGGER, 
						SQL_COUNT_USERS_IN_AGES, 
						new Object[] { numAgeList } ));
	}

}