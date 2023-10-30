package synapticloop.sample.h2zero.sqlite3.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-deleter.templar)

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import synapticloop.h2zero.util.LruCache;

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

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
	 * This database does not have the 'truncate' keyword hence the 'DELETE FROM' SQL
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

	// USER DEFINED DELETER CONNECTION, PARAMS - NO LIMIT - SQLITE3 STATEMENT
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
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteByNumAge(Connection connection, Integer numAge) throws SQLException {
		String cacheKey = "cacheKey"  + "";
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteByNumAge_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_DELETE_BY_NUM_AGE);

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

  	// USER DEFINED DELETER CONNECTION, PARAMS,
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
  	 * 
  	 * @return the number of rows deleted
  	 */
  	public static int deleteByNumAgeSilent(Connection connection, Integer numAge) {
  		try {
  			return(deleteByNumAge(connection,   numAge    ));
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
			return(deleteByNumAge(connection, numAge));
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
			return(deleteByNumAge(connection, numAge));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAge, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS - NO LIMIT - SQLITE3 STATEMENT
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
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteByFlIsAliveIdUserType(Connection connection, Boolean flIsAlive, Long idUserType) throws SQLException {
		String cacheKey = "cacheKey"  + "";
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteByFlIsAliveIdUserType_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_DELETE_BY_FL_IS_ALIVE_ID_USER_TYPE);

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

  	// USER DEFINED DELETER CONNECTION, PARAMS,
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
  	 * 
  	 * @return the number of rows deleted
  	 */
  	public static int deleteByFlIsAliveIdUserTypeSilent(Connection connection, Boolean flIsAlive, Long idUserType) {
  		try {
  			return(deleteByFlIsAliveIdUserType(connection,   flIsAlive  ,     idUserType    ));
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
			return(deleteByFlIsAliveIdUserType(connection, flIsAlive, idUserType));
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
			return(deleteByFlIsAliveIdUserType(connection, flIsAlive, idUserType));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByFlIsAliveIdUserType, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	// USER DEFINED DELETER CONNECTION, PARAMS - NO LIMIT - SQLITE3 STATEMENT
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
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteByNumAgeTest(Connection connection, Integer numAge) throws SQLException {
		String cacheKey = "cacheKey"  + "";
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteByNumAgeTest_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_DELETE_BY_NUM_AGE_TEST);

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

  	// USER DEFINED DELETER CONNECTION, PARAMS,
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
  	 * 
  	 * @return the number of rows deleted
  	 */
  	public static int deleteByNumAgeTestSilent(Connection connection, Integer numAge) {
  		try {
  			return(deleteByNumAgeTest(connection,   numAge    ));
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
			return(deleteByNumAgeTest(connection, numAge));
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
			return(deleteByNumAgeTest(connection, numAge));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByNumAgeTest, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

}