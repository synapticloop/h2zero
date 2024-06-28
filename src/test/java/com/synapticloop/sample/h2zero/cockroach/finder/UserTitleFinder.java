package com.synapticloop.sample.h2zero.cockroach.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (/java/finder/java-create-finder.templar)

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;
import com.synapticloop.sample.h2zero.cockroach.finder.bean.UserTitleFindIdUserTitleNmUserTitleOrderedBean;

import com.synapticloop.sample.h2zero.cockroach.model.UserTitle;

import com.synapticloop.h2zero.base.sql.limitoffset.MultiFinder;
import com.synapticloop.h2zero.base.sql.limitoffset.UniqueFinder;

public class UserTitleFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TITLE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTitleFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_user_title, 
				nm_user_title, 
				num_order_by
			from 
				user_title
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_title = ?";

	private static final String SQL_FIND_ID_USER_TITLE_NM_USER_TITLE_ORDERED =
		"""
			select id_user_title, nm_user_title from user_title order by num_order_by
		""";
	private static final String SQL_FIND_ALL_ORDERED = SQL_SELECT_START + " order by num_order_by";

	private UserTitleFinder() {}

	/**
	 * <p>Create a UniqueFinder that can find a UserTitle by its primary key</p>
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
	 * <pre>UserTitle.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserTitle.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * @param idUserTitle the primary key
	 * 
	 * @return the parameterised UniqueFinder
	 */
	public static UniqueFinder<UserTitle> findByPrimaryKey(Long idUserTitle) {
		return(new UniqueFinder<UserTitle>(
				LOGGER,
				SQL_BUILTIN_FIND_BY_PRIMARY_KEY,
				resultSet -> { try { return list(resultSet); } catch (SQLException e) { return(null); }},
				idUserTitle
		));
	}

	/**
	 * <p>Create a MultiFinder that can find all UserTitle rows</p>
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
	 * <pre>UserTitle.findAll()
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserTitle.findAll()
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>UserTitle.findAll()
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 */
	public static MultiFinder<UserTitle> findAll() {
		return(
				new MultiFinder<UserTitle>(
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
	 * There are 2 defined finders on the user_title table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * - findAllOrdered - Generated from the 'finders' JSON key
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * This is the <code>findAllOrdered</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>finders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<UserTitle> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>UserTitle.findAllOrdered(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>UserTitle.findAllOrdered(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>UserTitle.findAllOrdered(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * 
	 * @return the parameterised MultiFinder()
	 * 
	 */
	public static MultiFinder<UserTitle> findAllOrdered() {
		return(
				new MultiFinder<UserTitle>(
				LOGGER,
				SQL_FIND_ALL_ORDERED,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {}
		));
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

	public static MultiFinder<UserTitleFindIdUserTitleNmUserTitleOrderedBean> findIdUserTitleNmUserTitleOrdered() {
		return(
				new MultiFinder<UserTitleFindIdUserTitleNmUserTitleOrderedBean>(
				LOGGER,
				SQL_FIND_ID_USER_TITLE_NM_USER_TITLE_ORDERED,
				resultSet -> { try {return listFindIdUserTitleNmUserTitleOrderedBean(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {}
		));
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