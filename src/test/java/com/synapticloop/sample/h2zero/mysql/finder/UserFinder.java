package com.synapticloop.sample.h2zero.mysql.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;
import com.synapticloop.sample.h2zero.mysql.bean.UserFindNmUserDtmSignupBean;
import com.synapticloop.sample.h2zero.mysql.bean.UserFindGroupNumAgeBean;

import com.synapticloop.sample.h2zero.mysql.model.User;

import com.synapticloop.h2zero.base.sql.limitoffset.MultiFinder;
import com.synapticloop.h2zero.base.sql.limitoffset.UniqueFinder;

public class UserFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_user, 
				id_user_type, 
				fl_is_alive, 
				nm_username, 
				txt_address_email, 
				txt_password
			from 
				user
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user = ?";

	private static final String SQL_FIND_BY_NUM_AGE = SQL_SELECT_START + 
		"""
			where num_age = ?
		""";
	private static final String SQL_FIND_BY_FL_IS_ALIVE_NUM_AGE = SQL_SELECT_START + 
		"""
			where fl_is_alive = ? and num_age = ?
		""";
	private static final String SQL_FIND_BY_NM_USERNAME = SQL_SELECT_START + 
		"""
			where nm_username = ?
		""";
	private static final String SQL_FIND_BY_TXT_ADDRESS_EMAIL = SQL_SELECT_START + 
		"""
			where txt_address_email = ?
		""";
	private static final String SQL_FIND_BY_TXT_ADDRESS_EMAIL_TXT_PASSWORD = SQL_SELECT_START + 
		"""
			where txt_address_email = ? and txt_password = ?
		""";
	private static final String SQL_FIND_NM_USER_DTM_SIGNUP =
		"""
			select nm_user, dtm_signup from user
		""";
	private static final String SQL_FIND_GROUP_NUM_AGE =
		"""
			select count(*) as num_count, num_age from user group by num_count
		""";
	private static final String SQL_FIND_BY_NUM_AGE_IN = SQL_SELECT_START + 
		"""
			where num_age in (...)
		""";
	private static final String SQL_FIND_BY_NUM_AGE_BETWEEN = SQL_SELECT_START + 
		"""
			where num_age > ? and num_age < ?
		""";

	private UserFinder() {}

	/**
	 * <p>Create a UniqueFinder that can find a User by its primary key</p>
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
	 * <pre>User.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * @param idUser the primary key
	 * 
	 * @return the parameterised UniqueFinder
	 */
	public static UniqueFinder<User> findByPrimaryKey(Long idUser) {
		return(new UniqueFinder<User>(
				LOGGER,
				SQL_BUILTIN_FIND_BY_PRIMARY_KEY,
				resultSet -> { try { return list(resultSet); } catch (SQLException e) { return(null); }},
				idUser
		));
	}

	/**
	 * <p>Create a MultiFinder that can find all User rows</p>
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
	 * <pre>User.findAll()
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findAll()
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findAll()
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 */
	public static MultiFinder<User> findAll() {
		return(
				new MultiFinder<User>(
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
	 * There are 9 defined finders on the user table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * - findByNumAge - Generated from the 'fieldFinders' JSON key
	 * - findByFlIsAliveNumAge - Generated from the 'fieldFinders' JSON key
	 * - findByNmUsername - Generated from the 'fieldFinders' JSON key
	 * - findByTxtAddressEmail - Generated from the 'fieldFinders' JSON key
	 * - findByTxtAddressEmailTxtPassword - Generated from the 'finders' JSON key
	 * - findByNumAgeIn - Generated from the 'finders' JSON key
	 * - findByNumAgeBetween - Generated from the 'finders' JSON key
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * This is the <code>findByNumAge</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByNumAge(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByNumAge(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByNumAge(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param numAge - maps to the num_age field
	 * 
	 * @return the MultiFinder<User>()
	 * 
	 */
	public static MultiFinder<User> findByNumAge(Integer numAge) {
		return(
				new MultiFinder<User>(
				LOGGER,
				SQL_FIND_BY_NUM_AGE,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {numAge}
		));
	}
	/**
	 * This is the <code>findByFlIsAliveNumAge</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByFlIsAliveNumAge(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByFlIsAliveNumAge(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByFlIsAliveNumAge(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param flIsAlive - maps to the fl_is_alive field
	 * @param numAge - maps to the num_age field
	 * 
	 * @return the MultiFinder<User>()
	 * 
	 */
	public static MultiFinder<User> findByFlIsAliveNumAge(Boolean flIsAlive, Integer numAge) {
		return(
				new MultiFinder<User>(
				LOGGER,
				SQL_FIND_BY_FL_IS_ALIVE_NUM_AGE,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {flIsAlive, numAge}
		));
	}
	/**
	 * This is the <code>findByNmUsername</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByNmUsername(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByNmUsername(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByNmUsername(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param nmUsername - maps to the nm_username field
	 * 
	 * @return the UniqueFinder<User>()
	 * 
	 */
	public static UniqueFinder<User> findByNmUsername(String nmUsername) {
		return(
				new UniqueFinder<User>(
				LOGGER,
				SQL_FIND_BY_NM_USERNAME,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {nmUsername}
		));
	}
	/**
	 * This is the <code>findByTxtAddressEmail</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByTxtAddressEmail(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByTxtAddressEmail(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByTxtAddressEmail(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param txtAddressEmail - maps to the txt_address_email field
	 * 
	 * @return the UniqueFinder<User>()
	 * 
	 */
	public static UniqueFinder<User> findByTxtAddressEmail(String txtAddressEmail) {
		return(
				new UniqueFinder<User>(
				LOGGER,
				SQL_FIND_BY_TXT_ADDRESS_EMAIL,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {txtAddressEmail}
		));
	}
	/**
	 * This is the <code>findByTxtAddressEmailTxtPassword</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>finders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a UniqueFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByTxtAddressEmailTxtPassword(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByTxtAddressEmailTxtPassword(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByTxtAddressEmailTxtPassword(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param txtAddressEmail - maps to the txt_address_email field
	 * @param txtPassword - maps to the txt_password field
	 * 
	 * @return the UniqueFinder<User>()
	 * 
	 */
	public static UniqueFinder<User> findByTxtAddressEmailTxtPassword(String txtAddressEmail, String txtPassword) {
		return(
				new UniqueFinder<User>(
				LOGGER,
				SQL_FIND_BY_TXT_ADDRESS_EMAIL_TXT_PASSWORD,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {txtAddressEmail, txtPassword}
		));
	}
	/**
	 * This is the <code>findByNumAgeIn</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>finders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByNumAgeIn(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByNumAgeIn(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByNumAgeIn(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param numAgeList - maps to the num_age field
	 * 
	 * @return the MultiFinder<User>()
	 * 
	 */
	public static MultiFinder<User> findByNumAgeIn(List<Integer> numAgeList) {
		return(
				new MultiFinder<User>(
				LOGGER,
				SQL_FIND_BY_NUM_AGE_IN,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {numAgeList}
		));
	}
	/**
	 * This is the <code>findByNumAgeBetween</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>finders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<User> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>User.findByNumAgeBetween(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>User.findByNumAgeBetween(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>User.findByNumAgeBetween(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 * @param numAgeMin - maps to the num_age field
	 * @param numAgeMax - maps to the num_age field
	 * 
	 * @return the MultiFinder<User>()
	 * 
	 */
	public static MultiFinder<User> findByNumAgeBetween(Integer numAgeMin, Integer numAgeMax) {
		return(
				new MultiFinder<User>(
				LOGGER,
				SQL_FIND_BY_NUM_AGE_BETWEEN,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {numAgeMin, numAgeMax}
		));
	}
	/**
	 * Return the results as a list of User, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of User
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<User> list(ResultSet resultSet) throws SQLException {
		List<User> arrayList = new ArrayList<User>();
		while(resultSet.next()) {
			Long idUser = ConnectionManager.getNullableResultLong(resultSet, 1);
			Long idUserType = ConnectionManager.getNullableResultLong(resultSet, 2);
			Boolean flIsAlive = ConnectionManager.getNullableResultBoolean(resultSet, 3);
			String nmUsername = ConnectionManager.getNullableResultString(resultSet, 4);
			String txtAddressEmail = ConnectionManager.getNullableResultString(resultSet, 5);
			String txtPassword = ConnectionManager.getNullableResultString(resultSet, 6);
			Integer numAge = null;
			Timestamp dtmSignup = null;
					arrayList.add(new User(
					idUser,
					idUserType,
					flIsAlive,
					numAge,
					nmUsername,
					txtAddressEmail,
					txtPassword,
					dtmSignup
					));
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
	 * There are 9 defined finders on the user table, of those finders
	 * the following are the select clause finders:
	 * 
	 * - findNmUserDtmSignup
	 * - findGroupNumAge
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public static MultiFinder<UserFindNmUserDtmSignupBean> findNmUserDtmSignup() {
		return(
				new MultiFinder<UserFindNmUserDtmSignupBean>(
				LOGGER,
				SQL_FIND_NM_USER_DTM_SIGNUP,
				resultSet -> { try {return listFindNmUserDtmSignupBean(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {}
		));
	}
	public static MultiFinder<UserFindGroupNumAgeBean> findGroupNumAge() {
		return(
				new MultiFinder<UserFindGroupNumAgeBean>(
				LOGGER,
				SQL_FIND_GROUP_NUM_AGE,
				resultSet -> { try {return listFindGroupNumAgeBean(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {}
		));
	}
	/**
	 * Return the results as a list of UserFindNmUserDtmSignupBeans, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of UserFindNmUserDtmSignupBean
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<UserFindNmUserDtmSignupBean> listFindNmUserDtmSignupBean(ResultSet resultSet) throws SQLException {
		List<UserFindNmUserDtmSignupBean> arrayList = new ArrayList<UserFindNmUserDtmSignupBean>();
		while(resultSet.next()) {
			arrayList.add(new UserFindNmUserDtmSignupBean(
					resultSet.getString(1),
					resultSet.getTimestamp(2)));
		}
		return(arrayList);
	}

	/**
	 * Return the results as a list of UserFindGroupNumAgeBeans, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of UserFindGroupNumAgeBean
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<UserFindGroupNumAgeBean> listFindGroupNumAgeBean(ResultSet resultSet) throws SQLException {
		List<UserFindGroupNumAgeBean> arrayList = new ArrayList<UserFindGroupNumAgeBean>();
		while(resultSet.next()) {
			arrayList.add(new UserFindGroupNumAgeBean(
					resultSet.getInt(1),
					resultSet.getInt(2)));
		}
		return(arrayList);
	}

}