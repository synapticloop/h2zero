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

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

public class UserPetDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPetDeleter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from user_pet";
	private static final String SQL_DELETE_START = "delete from user_pet ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_user_pet = ?";


	// We don't allow instantiation
	private UserPetDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the USER_PET table by its primary key
	 * 
	 * @param connection The connection to use - the caller must close this connection
	 * @param idUserPet the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Connection connection, Long idUserPet) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_BY_PRIMARY_KEY)) {
		preparedStatement.setLong(1, idUserPet);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Delete a row in the USER_PET table by its primary key
	 * 
	 * @param idUserPet the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Long idUserPet) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKey(connection, idUserPet));
		}
	}

	/**
	 * Delete a row in the USER_PET table by its primary key silently
	 * (i.e. don't throw an exception if it couldn't be deleted).
	 * 
	 * @param connection - the connection to use - the caller must close this connection
	 * @param idUserPet the primary key to delete
	 * 
	 * @return the number of rows deleted
	 */
	public static int deleteByPrimaryKeySilent(Connection connection, Long idUserPet) {
		try {
			return(deleteByPrimaryKey(connection, idUserPet));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete a row in the USER_PET table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idUserPet the primary key to delete
	 * 
	 * @return the number of rows deleted or -1 if there was an error
	 */
	public static int deleteByPrimaryKeySilent(Long idUserPet) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(deleteByPrimaryKeySilent(connection, idUserPet));
		} catch (SQLException ex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occurred.", ex);
			return(-1);
		}
	}

	/**
	 * Delete all of the rows in the table 'user_pet'.
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
	 * Delete all the rows in the USER_PET table
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
	 * Delete all the rows in the USER_PET table silently - i.e
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
	 * Delete all the rows in the USER_PET table silently - i.e
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
	 * There are 0 defined deleters on the user_pet table:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}