package synapticloop.sample.h2zero.mysql.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-deleter.templar)

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;

public class PetTypeDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeDeleter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "truncate table pet_type";
	private static final String SQL_DELETE_START = "delete from pet_type ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_pet_type = ?";


	private PetTypeDeleter() {}

	/**
	 * Delete a row in the PET_TYPE table by its primary key
	 * 
	 * @param connection The connection
	 * @param idPetType the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Connection connection, Long idPetType) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_DELETE_BY_PRIMARY_KEY);
		preparedStatement.setLong(1, idPetType);
		int numResults = preparedStatement.executeUpdate();
		ConnectionManager.closeAll(preparedStatement);
		return(numResults);
	}

	/**
	 * Delete a row in the PET_TYPE table by its primary key
	 * 
	 * @param idPetType the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKey(Long idPetType) throws SQLException {
		Connection connection = ConnectionManager.getConnection();
		int numResults = deleteByPrimaryKey(connection, idPetType);
		ConnectionManager.closeAll(connection);
		return(numResults);
	}

	/**
	 * Delete a row in the PET_TYPE table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idPetType the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKeySilent(Connection connection, Long idPetType) {
		int numResults = 0;
		try {
			numResults = deleteByPrimaryKey(connection, idPetType);
		} catch (SQLException sqlex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occured.", sqlex);
			return(-1);
		}
		return(numResults);
	}

	/**
	 * Delete a row in the PET_TYPE table by its primary key silently
	 * (i.e. don't throw an exception if it coudn't be deleted).
	 * 
	 * @param idPetType the primary key to delete
	 * @return the number of rows deleted
	 * 
	 * @throws SQLException if there was an error in the delete
	 */
	public static int deleteByPrimaryKeySilent(Long idPetType) {
		int numResults = 0;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = deleteByPrimaryKeySilent(connection, idPetType);
		} catch (SQLException sqlex) {
			LOGGER.error("Could not deleteByPrimaryKey, a SQL Exception occured.", sqlex);
			return(-1);
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

	/**
	 * Delete all of the rows in the table 'pet_type'.
	 * 
	 * This table has no foreign key relationships and consequently can be truncated.
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
			LOGGER.error("Could not deleteAll, a SQL Exception occured.", sqlex);
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
			LOGGER.error("Could not deleteAll, a SQL Exception occured.", sqlex);
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
			LOGGER.error("Could not deleteAll, a SQL Exception occured.", sqlex);
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

}