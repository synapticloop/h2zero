package com.synapticloop.sample.h2zero.postgresql.inserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-inserter.templar)

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Blob;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

/**
 * This model maps to the pet table in the database
 * 
 * This class contains the methods to insert new rows into the: 
 *   <code>sample.pet</code>
 * table.  The fields that are available are as follows:
 * 
 * <ul>
 *  <li><code>id_pet</code> (bigint)  <strong>NOT</strong> nullable  (PRIMARY)</li>
 *  <li><code>nm_pet</code> (varchar)  <strong>NOT</strong> nullable </li>
 *  <li><code>num_age</code> (int)  <strong>NOT</strong> nullable </li>
 *  <li><code>flt_weight</code> (numeric)  nullable </li>
 *  <li><code>dt_birthday</code> (date)  nullable </li>
 *  <li><code>img_photo</code> (blob)  nullable </li>
 * </ul>
 * 
 * @author synapticloop h2zero
 */

public class PetInserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;

		private static final Logger LOGGER = LoggerFactory.getLogger(PetInserter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_INSERT_ALL = "insert into pet(id_pet, nm_pet, num_age, flt_weight, dt_birthday, img_photo)";
	private static final String SQL_BUILTIN_INSERT_VALUES = SQL_BUILTIN_INSERT_ALL + " values (?, ?, ?, ?, ?, ?)";
	// static inserter SQL generated from the user input

	private PetInserter() {}

	/**
	 * Insert a new Pet into the database utilising the passed in connection.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * @param fltWeight  maps to flt_weight
	 * @param dtBirthday  maps to dt_birthday
	 * @param imgPhoto  maps to img_photo
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, Blob imgPhoto) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigint(preparedStatement, 1, idPet);
			ConnectionManager.setVarchar(preparedStatement, 2, nmPet);
			ConnectionManager.setInt(preparedStatement, 3, numAge);
			ConnectionManager.setNumeric(preparedStatement, 4, fltWeight);
			ConnectionManager.setDate(preparedStatement, 5, dtBirthday);
			ConnectionManager.setBlob(preparedStatement, 6, imgPhoto);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Insert a new Pet into the database utilising the passed in connection 
	 * with only the fields that are allowed to be not null.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idPet, String nmPet, Integer numAge) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigint(preparedStatement, 1, idPet);
			ConnectionManager.setVarchar(preparedStatement, 2, nmPet);
			ConnectionManager.setInt(preparedStatement, 3, numAge);
			ConnectionManager.setNumeric(preparedStatement, 4, null);
			ConnectionManager.setDate(preparedStatement, 5, null);
			Blob imgPhotoBlob = null;
			ConnectionManager.setBlob(preparedStatement, 6, imgPhotoBlob);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Insert a new Pet into the database a new connection will be retrieved 
	 * from the pool, used and then closed.
	 * 
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * @param fltWeight  maps to flt_weight
	 * @param dtBirthday  maps to dt_birthday
	 * @param imgPhoto  maps to img_photo
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, Blob imgPhoto) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto));
		}
	}

	/**
	 * Insert a new Pet into the database a new connection will be retrieved 
	 * from the pool, used and then closed. This is for fields which have a nullable allowed default
	 * 
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idPet, String nmPet, Integer numAge) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idPet, nmPet, numAge));
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new Pet into the 
	 * database utilising the passed in connection. If an exception is thrown by the
	 * method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * @param fltWeight  maps to flt_weight
	 * @param dtBirthday  maps to dt_birthday
	 * @param imgPhoto  maps to img_photo
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, Blob imgPhoto) {
		try {
			return(insert(connection, idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new Pet into the 
	 * database utilising the passed in connection. If an exception is thrown by the
	 * method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * This is only for Non-Nullable fields
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idPet, String nmPet, Integer numAge) {
		try {
			return(insert(connection, idPet, nmPet, numAge));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new Pet into the 
	 * database, creating and closing a connection in the process. If an exception is thrown 
	 * by the method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * @param fltWeight  maps to flt_weight
	 * @param dtBirthday  maps to dt_birthday
	 * @param imgPhoto  maps to img_photo
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, Blob imgPhoto) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(insert(connection, idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new Pet into the 
	 * database, creating and closing a connection in the process. If an exception is thrown 
	 * by the method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * This is for non-nullable fields only
	 * 
	 * @param idPet  maps to id_pet
	 * @param nmPet  maps to nm_pet
	 * @param numAge  maps to num_age
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idPet, String nmPet, Integer numAge) {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idPet, nmPet, numAge));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	public static int insert(Connection connection, Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, InputStream imgPhoto) throws SQLException {
		int numResults = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES);
			ConnectionManager.setBigint(preparedStatement, 1, idPet);
			ConnectionManager.setVarchar(preparedStatement, 2, nmPet);
			ConnectionManager.setInt(preparedStatement, 3, numAge);
			ConnectionManager.setNumeric(preparedStatement, 4, fltWeight);
			ConnectionManager.setDate(preparedStatement, 5, dtBirthday);
			ConnectionManager.setBlobInputStream(preparedStatement, 6, imgPhoto);
			numResults = preparedStatement.executeUpdate();
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(preparedStatement);
		}
		return(numResults);
	}

	public static int insert(Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, InputStream imgPhoto) throws SQLException {
		int numResults = -1;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = insert(connection, idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
			ConnectionManager.closeAll(connection);
		}
		return(numResults);
	}

	public static int insertSilent(Connection connection, Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, InputStream imgPhoto) {
		int numResults = -1;
		try {
			numResults = insert(connection, idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto);
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
		}
		return(numResults);
	}

	public static int insertSilent(Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, InputStream imgPhoto) {
		int numResults = 0;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			numResults = insert(connection, idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto);
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