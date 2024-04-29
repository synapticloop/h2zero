package com.synapticloop.sample.h2zero.cockroach.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.sql.cockroach.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;

import com.synapticloop.sample.h2zero.cockroach.model.UserType;

import com.synapticloop.h2zero.base.manager.cockroach.finder.MultiFinder;import com.synapticloop.h2zero.base.manager.cockroach.finder.UniqueFinder;
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


	private UserTypeFinder() {}

	/**
	 * <p>Create a UniqueFinder that can find a UserType by its primary key</p>
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
	 * <pre>UserType.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserType.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * @param idUserType the primary key
	 * 
	 * @return the parameterised UniqueFinder
	 */
	public static UniqueFinder<UserType> findByPrimaryKey(Long idUserType) {
		return(new UniqueFinder<UserType>(
				LOGGER,
				SQL_BUILTIN_FIND_BY_PRIMARY_KEY,
				resultSet -> { try { return list(resultSet); } catch (SQLException e) { return(null); }},
				idUserType
		));
	}

	/**
	 * <p>Create a MultiFinder that can find all UserType rows</p>
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
	 * <pre>UserType.findAll()
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserType.findAll()
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>UserType.findAll()
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 */
	public static MultiFinder<UserType> findAll() {
		return(
				new MultiFinder<UserType>(
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
	 * There are 0 defined finders on the user_type table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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