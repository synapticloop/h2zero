package synapticloop.sample.h2zero.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-deleter.templar)

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.sample.h2zero.model.util.Constants;

public class UserPetDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from user_pet";
	private static final String SQL_DELETE_START = "delete from user_pet ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_user_pet = ?";


	private UserPetDeleter() {}

	/**
	 * Delete a row in the USER_PET table by its primary key
	 * 
	 * @param connection The connection
	 * @param idUserPet the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Connection connection, Long idUserPet) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_BY_PRIMARY_KEY);
		preparedStatement.setLong(1, idUserPet);
		int numResults = preparedStatement.executeUpdate();
		ConnectionManager.closeAll(preparedStatement);
		return(numResults);
	}

	/**
	 * Delete a row in the USER_PET table by its primary key
	 * 
	 * @param idUserPet the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Long idUserPet) throws SQLException {
		Connection connection = ConnectionManager.getConnection();
		int numResults = deleteByPrimaryKey(connection, idUserPet);
		ConnectionManager.closeAll(connection);
		return(numResults);
	}

	/**
	 * Delete a row in the USER_PET table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idUserPet the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKeySilent(Connection connection, Long idUserPet) {
		int numResults = 0;
		try {
			numResults = deleteByPrimaryKey(connection, idUserPet);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			return(-1);
		}
		return(numResults);
	}

	/**
	 * Delete a row in the USER_PET table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idUserPet the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKeySilent(Long idUserPet) {
		int numResults = 0;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = deleteByPrimaryKeySilent(connection, idUserPet);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			return(-1);
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

	/**
	 * Delete all of the rows in the table 'user_pet'.
	 * This table has a foreign key relationship on it, consequently the truncate
	 * method would have been faster, but would fail, hence the 'DELETE FROM' SQL
	 * statement is used
	 * 
	 * @return The number of rows affected by this statement
	 */
	public static int deleteAll(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		int numResults = -1;
		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_ALL);
			numResults = preparedStatement.executeUpdate();
		} catch (SQLException sqlex) {
			ConnectionManager.closeAll(preparedStatement);
		}
		return(numResults);
	}

	public static int deleteAll() throws SQLException {
		Connection connection = null;
		int numResults = -1;
		try {
			connection = ConnectionManager.getConnection();
			numResults = deleteAll(connection);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

	public static int deleteAllSilent(Connection connection) {
		int numResults = -1;
		try {
			numResults = deleteAll(connection);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return(numResults);
	}

	public static int deleteAllSilent() {
		Connection connection = null;
		int numResults = -1;
		try {
			connection = ConnectionManager.getConnection();
			numResults = deleteAll(connection);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

}