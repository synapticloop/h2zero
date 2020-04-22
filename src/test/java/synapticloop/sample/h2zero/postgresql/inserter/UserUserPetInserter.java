package synapticloop.sample.h2zero.postgresql.inserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-inserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import synapticloop.sample.h2zero.postgresql.model.util.Constants;

/**
 * This class contains the methods to insert new rows into the: 
 *   <code>sample.user_user_pet</code>
 * table.  The fields that are available are as follows:
 * 
 * <ul>
 *  <li><code>id_user_user_pet</code> (bigint)  <strong>NOT</strong> nullable  (PRIMARY)</li>
 *  <li><code>id_user_user</code> (bigint)  <strong>NOT</strong> nullable </li>
 *  <li><code>id_pet</code> (bigint)  <strong>NOT</strong> nullable </li>
 * </ul>
 * 
 * @author synapticloop h2zero
 */

public class UserUserPetInserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_PET_BINDER;

		private static final Logger LOGGER = LoggerFactory.getLogger(UserUserPetInserter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_INSERT_ALL = "insert into user_user_pet(id_user_user_pet, id_user_user, id_pet)";
	private static final String SQL_BUILTIN_INSERT_VALUES = SQL_BUILTIN_INSERT_ALL + " values (?, ?, ?)";
	// static inserter SQL generated from the user input

	private UserUserPetInserter() {}

	/**
	 * Insert a new UserUserPet into the database utilising the passed in connection.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idUserUserPet  maps to id_user_user_pet
	 * @param idUserUser  maps to id_user_user
	 * @param idPet  maps to id_pet
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idUserUserPet, Long idUserUser, Long idPet) throws SQLException {
		int numResults = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES);
			ConnectionManager.setBigint(preparedStatement, 1, idUserUserPet);
			ConnectionManager.setBigint(preparedStatement, 2, idUserUser);
			ConnectionManager.setBigint(preparedStatement, 3, idPet);
			numResults = preparedStatement.executeUpdate();
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(preparedStatement);
		}
		return(numResults);
	}

	/**
	 * Insert a new UserUserPet into the database a new connection will be retrieved 
	 * from the pool, used and then closed.
	 * 
	 * @param idUserUserPet  maps to id_user_user_pet
	 * @param idUserUser  maps to id_user_user
	 * @param idPet  maps to id_pet
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idUserUserPet, Long idUserUser, Long idPet) throws SQLException {
		int numResults = -1;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = insert(connection, idUserUserPet, idUserUser, idPet);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new UserUserPet into the 
	 * database utilising the passed in connection. If an exception is thrown by the
	 * method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idUserUserPet  maps to id_user_user_pet
	 * @param idUserUser  maps to id_user_user
	 * @param idPet  maps to id_pet
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idUserUserPet, Long idUserUser, Long idPet) {
		int numResults = -1;
		try {
			numResults = insert(connection, idUserUserPet, idUserUser, idPet);
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
		}
		return(numResults);
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new UserUserPet into the 
	 * database, creating and closing a connection in the process. If an exception is thrown 
	 * by the method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param idUserUserPet  maps to id_user_user_pet
	 * @param idUserUser  maps to id_user_user
	 * @param idPet  maps to id_pet
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idUserUserPet, Long idUserUser, Long idPet) {
		int numResults = -1;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = insert(connection, idUserUserPet, idUserUser, idPet);
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

}