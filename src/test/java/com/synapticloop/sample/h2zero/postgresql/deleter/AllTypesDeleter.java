package com.synapticloop.sample.h2zero.postgresql.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-deleter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.synapticloop.h2zero.util.LruCache;

import com.synapticloop.h2zero.base.sql.cockroach.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

public class AllTypesDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesDeleter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "truncate table all_types";
	private static final String SQL_DELETE_START = "delete from all_types ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_all_types = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);

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
	 * @param connection The connection to use - the caller must close this connection
	 * @param idAllTypes the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Connection connection, Long idAllTypes) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_BY_PRIMARY_KEY)) {
			preparedStatement.setLong(1, idAllTypes);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Delete a row in the ALL_TYPES table by its primary key
	 * 
	 * @param idAllTypes the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Long idAllTypes) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKey(connection, idAllTypes));
		}
	}

	/**
	 * Delete a row in the ALL_TYPES table by its primary key silently
	 * (i.e. don't throw an exception if it couldn't be deleted).
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * @param idAllTypes the primary key to delete
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByPrimaryKeySilent(Connection connection, Long idAllTypes) {
		try {
			return(deleteByPrimaryKey(connection, idAllTypes));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete a row in the ALL_TYPES table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idAllTypes the primary key to delete
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByPrimaryKeySilent(Long idAllTypes) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKeySilent(connection, idAllTypes));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete all of the rows in the table 'all_types'.
	 * 
	 * This table has no foreign key relationships and consequently can be truncated.
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
	 * Delete all the rows in the ALL_TYPES table
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
	 * Delete all the rows in the ALL_TYPES table silently - i.e
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
	 * Delete all the rows in the ALL_TYPES table silently - i.e
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
	 *     USER DEFINED DELETERS FOR THE TABLE: all_types
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 0 defined Deleters on the all_types table:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}