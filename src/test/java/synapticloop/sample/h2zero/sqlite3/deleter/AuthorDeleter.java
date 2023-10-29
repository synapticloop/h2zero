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

public class AuthorDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.AUTHOR_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDeleter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from author";
	private static final String SQL_DELETE_START = "delete from author ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_author = ?";

	// static fields generated from the user input
	private static final String SQL_DELETE_IN_NUMBER = SQL_DELETE_START + "  where fl_is_updating in (...)";
	// This is the cache for 'in Deleter' which have an ellipses (...) in the statement
	private static final LruCache<String, String> deleteInNumber_limit_statement_cache = new LruCache<>(1024);
	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private AuthorDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the AUTHOR table by its primary key
	 * 
	 * @param connection The connection to use - the caller must close this connection
	 * @param idAuthor the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Connection connection, Long idAuthor) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_BY_PRIMARY_KEY)) {
		preparedStatement.setLong(1, idAuthor);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Delete a row in the AUTHOR table by its primary key
	 * 
	 * @param idAuthor the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Long idAuthor) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKey(connection, idAuthor));
		}
	}

	/**
	 * Delete a row in the AUTHOR table by its primary key silently
	 * (i.e. don't throw an exception if it couldn't be deleted).
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * @param idAuthor the primary key to delete
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByPrimaryKeySilent(Connection connection, Long idAuthor) {
		try {
			return(deleteByPrimaryKey(connection, idAuthor));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete a row in the AUTHOR table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idAuthor the primary key to delete
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByPrimaryKeySilent(Long idAuthor) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKeySilent(connection, idAuthor));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete all of the rows in the table 'author'.
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
	 * Delete all the rows in the AUTHOR table
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
	 * Delete all the rows in the AUTHOR table silently - i.e
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
	 * Delete all the rows in the AUTHOR table silently - i.e
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
	 * This is the start of the user defined deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 1 defined deleters on the author table:
	 * 
	 * - deleteInNumber - from 'deleters' JSON key 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * deleteInNumber - from 'deleters' JSON key
	 *
	 * @param connection - the connection - the caller must close this connection
	 * @param flIsUpdatingList - maps to the fl_is_updating field
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteInNumber(Connection connection, List<Boolean> flIsUpdatingList) throws SQLException {
		String cacheKey = flIsUpdatingList.size() + ":" ;
		boolean hasConnection = (null != connection);
		String statement = null;
		if(!deleteInNumber_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			String preparedStatementTemp = SQL_DELETE_IN_NUMBER;
			StringBuilder whereFieldStringBuilder = null;
			whereFieldStringBuilder = new StringBuilder();
			for(int i = 0; i < flIsUpdatingList.size(); i++) {
				if(i > 0) {
					whereFieldStringBuilder.append(", ");
				}
				whereFieldStringBuilder.append("?");
			}
			preparedStatementTemp = SQL_DELETE_IN_NUMBER.replaceFirst("\\.\\.\\.", whereFieldStringBuilder.toString());
			StringBuilder stringBuilder = new StringBuilder(preparedStatementTemp);
			statement = stringBuilder.toString();
			deleteInNumber_statement_cache.put(cacheKey, statement);
		} else {
			statement = deleteInNumber_statement_cache.get(cacheKey);
		}

		if(!hasConnection) {
			connection = ConnectionManager.getConnection();
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
			int i = 1;
			for (Boolean flIsUpdatingIn : flIsUpdatingList) {
				ConnectionManager.setBoolean(preparedStatement, i, flIsUpdatingIn);
				i++;
			}

			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * deleteInNumber - from 'deleters' JSON key
	 *
	 * @param flIsUpdatingList - maps to the fl_is_updating field
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the deletion
	 */
	public static int deleteInNumber(List<Boolean> flIsUpdatingList) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteInNumber(connection, flIsUpdatingList));
		}
	}

	/**
	 * deleteInNumber - from 'deleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param flIsUpdatingList - maps to the fl_is_updating field
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */

	public static int deleteInNumberSilent(List<Boolean> flIsUpdatingList) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteInNumber(connection, flIsUpdatingList));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteInNumber, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * deleteInNumber - from 'deleters' JSON key.
	 * This will silently swallow any exceptions.
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * @param flIsUpdatingList - maps to the fl_is_updating field
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteInNumberSilent(Connection connection,List<Boolean> flIsUpdatingList) {
		try {
			return(deleteInNumber(connection, flIsUpdatingList));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteInNumber, a SQL Exception occurred.", ex);
			return(-1);
		}
	}
}