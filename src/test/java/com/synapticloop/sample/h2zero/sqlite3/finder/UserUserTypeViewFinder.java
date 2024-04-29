package com.synapticloop.sample.h2zero.sqlite3.finder;

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

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import com.synapticloop.sample.h2zero.sqlite3.view.UserUserType;

import com.synapticloop.h2zero.base.sql.sqlite3.MultiFinder;
import com.synapticloop.h2zero.base.sql.sqlite3.UniqueFinder;


public class UserUserTypeViewFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserUserTypeViewFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				nm_user, 
				nm_user_type
			from 
				user_user_type
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_pet = ?";

	private static final String SQL_FIND_BY_NM_USER = SQL_SELECT_START + 
		"""
			where nm_user = ?
		""";
	// now for the statement limit cache(s)
	private static final LruCache<String, String> findAll_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> findByNmUser_limit_statement_cache = new LruCache<>(1024);

	private UserUserTypeViewFinder() {}

	/**
	 * <p>Create a UniqueFinder that can find a UserUserType by its primary key</p>
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
	 * <pre>UserUserType.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserUserType.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * @param idUserPet the primary key
	 * 
	 * @return the parameterised UniqueFinder
	 */
	public static UniqueFinder<UserUserType> findByPrimaryKey(Long idUserPet) {
		return(new UniqueFinder<UserUserType>(
				LOGGER,
				SQL_BUILTIN_FIND_BY_PRIMARY_KEY,
				resultSet -> { try { return list(resultSet); } catch (SQLException e) { return(null); }},
				idUserPet
		));
	}

	/**
	 * <p>Create a MultiFinder that can find all UserUserType rows</p>
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
	 * <pre>UserUserType.findAll()
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserUserType.findAll()
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>UserUserType.findAll()
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 */
	public static MultiFinder<UserUserType> findAll() {
		return(
				new MultiFinder<UserUserType>(
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
	 * There are 1 defined finders on the user_user_type table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * - findByNmUser - Generated from the 'finders' JSON key
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * This is the <code>findByNmUser</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>finders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<UserUserType> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>UserUserType.findByNmUser(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserUserType.findByNmUser(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>UserUserType.findByNmUser(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param nmUser - maps to the nm_user field
	 * 
	 * @return the MultiFinder<UserUserType>()
	 * 
	 */
	public static MultiFinder<UserUserType> findByNmUser(String nmUser) {
		return(
				new MultiFinder<UserUserType>(
				LOGGER,
				SQL_FIND_BY_NM_USER,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {nmUser}
		));
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
	 * There are 1 defined finders on the user_user_type table, of those finders
	 * the following are the select clause finders:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}