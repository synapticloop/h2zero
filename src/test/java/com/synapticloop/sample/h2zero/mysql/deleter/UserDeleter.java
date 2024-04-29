package com.synapticloop.sample.h2zero.mysql.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-deleter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.synapticloop.h2zero.util.LruCache;

import com.synapticloop.h2zero.base.sql.mysql.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

public class UserDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeleter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from user";
	private static final String SQL_DELETE_START = "delete from user ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_user = ?";

	// static fields generated from the user input
	private static final String SQL_DELETE_BY_NUM_AGE = SQL_DELETE_START + " where num_age = ?";
	private static final String SQL_DELETE_BY_FL_IS_ALIVE_ID_USER_TYPE = SQL_DELETE_START + " where fl_is_alive = ? and id_user_type = ?";
	private static final String SQL_DELETE_BY_NUM_AGE_TEST = SQL_DELETE_START + " where num_age = ?";
	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByNumAge_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByFlIsAliveIdUserType_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByNumAgeTest_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private UserDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the USER table by its primary key
	 * 
	 * @param connection The connection to use - the caller must close this connection
	 * @param idUser the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Connection connection, Long idUser) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_BY_PRIMARY_KEY)) {
			preparedStatement.setLong(1, idUser);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Delete a row in the USER table by its primary key
	 * 
	 * @param idUser the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Long idUser) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKey(connection, idUser));
		}
	}

	/**
	 * Delete a row in the USER table by its primary key silently
	 * (i.e. don't throw an exception if it couldn't be deleted).
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * @param idUser the primary key to delete
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByPrimaryKeySilent(Connection connection, Long idUser) {
		try {
			return(deleteByPrimaryKey(connection, idUser));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete a row in the USER table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idUser the primary key to delete
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByPrimaryKeySilent(Long idUser) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKeySilent(connection, idUser));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete all of the rows in the table 'user'.
	 * 
	 * This table has a foreign key relationship on it, consequently the truncate
	 * method would have been faster, but would fail, hence the 'DELETE FROM' SQL
	 * statement is used
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * 
	 * @return The number of rows affected by this statement
	 */
	public static int deleteAll(Connection connection) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_ALL)) {
			return(preparedStatement.executeUpdate());
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteAll, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete all the rows in the USER table
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteAll() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteAll(connection));
		}
	}

	/**
	 * Delete all the rows in the USER table silently - i.e
	 * swallow any SQL exceptions
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteAllSilent(Connection connection) {
		try {
			return(deleteAll(connection));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteAll, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete all the rows in the USER table silently - i.e
	 * swallow any SQL exceptions
	 * 
	 * @return the number of rows deleted, or -1 if there was an error
	 */
	public static int deleteAllSilent() {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteAll(connection));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteAll, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 *     USER DEFINED DELETERS FOR THE TABLE: user
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 3 defined Deleters on the user table:
	 * 
	 * - deleteByNumAge - 'fieldDeleters' JSON key 
	 * - deleteByFlIsAliveIdUserType - 'fieldDeleters' JSON key 
	 * - deleteByNumAgeTest - from 'deleter' JSON key 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param connection - the connection - the caller __MUST__ close this connection
	 *        if the caller created this connection. If the passed in connection is 
	 *        null, then a new connection will be created, utilised, and closed within
	 *        this method.
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteByNumAge(Connection connection, Integer numAge, Integer limit) throws SQLException {
		String cacheKey = limit  + "";
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteByNumAge_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_DELETE_BY_NUM_AGE);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
			}

			statement = stringBuilder.toString();
			deleteByNumAge_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = deleteByNumAge_limit_statement_cache.get(cacheKey);
		}

		if(!hasConnection) {
			connection = ConnectionManager.getConnection();
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
			ConnectionManager.setInt(preparedStatement, 1, numAge);

			return(preparedStatement.executeUpdate());
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT SILENT
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param connection - the connection - the caller __MUST__ close this connection
	 *        if the caller created this connection. If the passed in connection is 
	 *        null, then a new connection will be created, utilised, and closed within
	 *        this method.
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByNumAgeSilent(Connection connection, Integer numAge, Integer limit) {
		try {
			return(deleteByNumAge(null, numAge, limit));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAge, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER - CONNECTION, PARAMS, NULL - LOUD
	public static int deleteByNumAge(Connection connection, Integer numAge) throws SQLException {
			return(deleteByNumAge(connection, numAge, null));
	}

	// USER DEFINED DELETER - CONNECTION, PARAMS, NULL - SILENT
	public static int deleteByNumAgeSilent(Connection connection, Integer numAge) {
		try {
			return(deleteByNumAge(connection, numAge, null));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAge, a SQL Exception occurred.", ex);
			return(-1);
		}
	}
	// USER DEFINED DELETER - NULL, PARAMS, LIMIT
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByNumAge(Integer numAge, Integer limit) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(deleteByNumAge(null, numAge, limit));
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, LIMIT (SILENT)
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByNumAgeSilent(Integer numAge, Integer limit) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(deleteByNumAge(null, numAge, limit));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAge, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, NULL LOUD
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
  
	public static int deleteByNumAge(Integer numAge) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByNumAge(connection, numAge, null));
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, NULL SILENT
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
  
	public static int deleteByNumAgeSilent(Integer numAge) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByNumAge(connection, numAge, null));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAge, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param connection - the connection - the caller __MUST__ close this connection
	 *        if the caller created this connection. If the passed in connection is 
	 *        null, then a new connection will be created, utilised, and closed within
	 *        this method.
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteByFlIsAliveIdUserType(Connection connection, Boolean flIsAlive, Long idUserType, Integer limit) throws SQLException {
		String cacheKey = limit  + "";
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteByFlIsAliveIdUserType_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_DELETE_BY_FL_IS_ALIVE_ID_USER_TYPE);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
			}

			statement = stringBuilder.toString();
			deleteByFlIsAliveIdUserType_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = deleteByFlIsAliveIdUserType_limit_statement_cache.get(cacheKey);
		}

		if(!hasConnection) {
			connection = ConnectionManager.getConnection();
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
			ConnectionManager.setBoolean(preparedStatement, 1, flIsAlive);
			ConnectionManager.setBigint(preparedStatement, 2, idUserType);

			return(preparedStatement.executeUpdate());
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT SILENT
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param connection - the connection - the caller __MUST__ close this connection
	 *        if the caller created this connection. If the passed in connection is 
	 *        null, then a new connection will be created, utilised, and closed within
	 *        this method.
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByFlIsAliveIdUserTypeSilent(Connection connection, Boolean flIsAlive, Long idUserType, Integer limit) {
		try {
			return(deleteByFlIsAliveIdUserType(null, flIsAlive, idUserType, limit));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByFlIsAliveIdUserType, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER - CONNECTION, PARAMS, NULL - LOUD
	public static int deleteByFlIsAliveIdUserType(Connection connection, Boolean flIsAlive, Long idUserType) throws SQLException {
			return(deleteByFlIsAliveIdUserType(connection, flIsAlive, idUserType, null));
	}

	// USER DEFINED DELETER - CONNECTION, PARAMS, NULL - SILENT
	public static int deleteByFlIsAliveIdUserTypeSilent(Connection connection, Boolean flIsAlive, Long idUserType) {
		try {
			return(deleteByFlIsAliveIdUserType(connection, flIsAlive, idUserType, null));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByFlIsAliveIdUserType, a SQL Exception occurred.", ex);
			return(-1);
		}
	}
	// USER DEFINED DELETER - NULL, PARAMS, LIMIT
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key.
	 * 
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByFlIsAliveIdUserType(Boolean flIsAlive, Long idUserType, Integer limit) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(deleteByFlIsAliveIdUserType(null, flIsAlive, idUserType, limit));
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, LIMIT (SILENT)
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key.
	 * 
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByFlIsAliveIdUserTypeSilent(Boolean flIsAlive, Long idUserType, Integer limit) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(deleteByFlIsAliveIdUserType(null, flIsAlive, idUserType, limit));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByFlIsAliveIdUserType, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, NULL LOUD
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
  
	public static int deleteByFlIsAliveIdUserType(Boolean flIsAlive, Long idUserType) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByFlIsAliveIdUserType(connection, flIsAlive, idUserType, null));
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, NULL SILENT
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
  
	public static int deleteByFlIsAliveIdUserTypeSilent(Boolean flIsAlive, Long idUserType) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByFlIsAliveIdUserType(connection, flIsAlive, idUserType, null));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByFlIsAliveIdUserType, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param connection - the connection - the caller __MUST__ close this connection
	 *        if the caller created this connection. If the passed in connection is 
	 *        null, then a new connection will be created, utilised, and closed within
	 *        this method.
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteByNumAgeTest(Connection connection, Integer numAge, Integer limit) throws SQLException {
		String cacheKey = limit  + "";
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteByNumAgeTest_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_DELETE_BY_NUM_AGE_TEST);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
			}

			statement = stringBuilder.toString();
			deleteByNumAgeTest_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = deleteByNumAgeTest_limit_statement_cache.get(cacheKey);
		}

		if(!hasConnection) {
			connection = ConnectionManager.getConnection();
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
			ConnectionManager.setInt(preparedStatement, 1, numAge);

			return(preparedStatement.executeUpdate());
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT SILENT
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param connection - the connection - the caller __MUST__ close this connection
	 *        if the caller created this connection. If the passed in connection is 
	 *        null, then a new connection will be created, utilised, and closed within
	 *        this method.
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByNumAgeTestSilent(Connection connection, Integer numAge, Integer limit) {
		try {
			return(deleteByNumAgeTest(null, numAge, limit));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAgeTest, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER - CONNECTION, PARAMS, NULL - LOUD
	public static int deleteByNumAgeTest(Connection connection, Integer numAge) throws SQLException {
			return(deleteByNumAgeTest(connection, numAge, null));
	}

	// USER DEFINED DELETER - CONNECTION, PARAMS, NULL - SILENT
	public static int deleteByNumAgeTestSilent(Connection connection, Integer numAge) {
		try {
			return(deleteByNumAgeTest(connection, numAge, null));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAgeTest, a SQL Exception occurred.", ex);
			return(-1);
		}
	}
	// USER DEFINED DELETER - NULL, PARAMS, LIMIT
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByNumAgeTest(Integer numAge, Integer limit) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()){
			return(deleteByNumAgeTest(null, numAge, limit));
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, LIMIT (SILENT)
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * @param limit - The limit of the number of rows to affect
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByNumAgeTestSilent(Integer numAge, Integer limit) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(deleteByNumAgeTest(null, numAge, limit));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAgeTest, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, NULL LOUD
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
  
	public static int deleteByNumAgeTest(Integer numAge) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByNumAgeTest(connection, numAge, null));
		}
	}

	// USER DEFINED DELETER - NULL, PARAMS, NULL SILENT
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
  
	public static int deleteByNumAgeTestSilent(Integer numAge) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByNumAgeTest(connection, numAge, null));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAgeTest, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

}