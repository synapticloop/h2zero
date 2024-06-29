package com.synapticloop.sample.h2zero.mariadb.inserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/java-create-inserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;

/**
 * This model maps to the pet type table in the database
 * 
 * This class contains the methods to insert new rows into the: 
 *   <code>sample.pet_type</code>
 * table.  The fields that are available are as follows:
 * 
 * <ul>
 *  <li><code>id_pet_type</code> (bigint)  <strong>NOT</strong> nullable  (PRIMARY)</li>
 *  <li><code>nm_pet_type</code> (varchar)  <strong>NOT</strong> nullable </li>
 *  <li><code>txt_desc_pet_type</code> (varchar)  <strong>NOT</strong> nullable </li>
 * </ul>
 * 
 * @author synapticloop h2zero
 */

public class PetTypeInserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

		private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeInserter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_INSERT_ALL = "insert into pet_type(id_pet_type, nm_pet_type, txt_desc_pet_type)";
	private static final String SQL_BUILTIN_INSERT_VALUES = SQL_BUILTIN_INSERT_ALL + " values (?, ?, ?)";
	// static inserter SQL generated from the user input

	private PetTypeInserter() {}

	/**
	 * Insert a new PetType into the database utilising the passed in connection.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idPetType  maps to id_pet_type
	 * @param nmPetType  maps to nm_pet_type
	 * @param txtDescPetType  maps to txt_desc_pet_type
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Connection connection, Long idPetType, String nmPetType, String txtDescPetType) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUILTIN_INSERT_VALUES)) {
			ConnectionManager.setBigint(preparedStatement, 1, idPetType);
			ConnectionManager.setVarchar(preparedStatement, 2, nmPetType);
			ConnectionManager.setVarchar(preparedStatement, 3, txtDescPetType);
			return(preparedStatement.executeUpdate());
		}
	}

	/**
	 * Insert a new PetType into the database a new connection will be retrieved 
	 * from the pool, used and then closed.
	 * 
	 * @param idPetType  maps to id_pet_type
	 * @param nmPetType  maps to nm_pet_type
	 * @param txtDescPetType  maps to txt_desc_pet_type
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 * 
	 * @throws SQLException if there was an error in the SQL insert statement
	 */
	public static int insert(Long idPetType, String nmPetType, String txtDescPetType) throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			return(insert(connection, idPetType, nmPetType, txtDescPetType));
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new PetType into the 
	 * database utilising the passed in connection. If an exception is thrown by the
	 * method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param connection the connection to use for the database, this __MUST__ be 
	 *   closed by the calling function.
	 * @param idPetType  maps to id_pet_type
	 * @param nmPetType  maps to nm_pet_type
	 * @param txtDescPetType  maps to txt_desc_pet_type
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Connection connection, Long idPetType, String nmPetType, String txtDescPetType) {
		try {
			return(insert(connection, idPetType, nmPetType, txtDescPetType));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

	/**
	 * Silently (i.e. swallow any exceptions) Insert a new PetType into the 
	 * database, creating and closing a connection in the process. If an exception is thrown 
	 * by the method, the exception message will be logged as an 'error', if 'trace' logging
	 * is enabled, the stack trace will be printed to the output stream.
	 * 
	 * @param idPetType  maps to id_pet_type
	 * @param nmPetType  maps to nm_pet_type
	 * @param txtDescPetType  maps to txt_desc_pet_type
	 * 
	 * @return the number of rows that were inserted, or -1 if an error occurred
	 */
	public static int insertSilent(Long idPetType, String nmPetType, String txtDescPetType) {
		try (Connection connection = ConnectionManager.getConnection()){
			return(insert(connection, idPetType, nmPetType, txtDescPetType));
		} catch (SQLException sqlex) {
			LOGGER.error("SQLException caught, message was: {}", sqlex.getMessage());
			if(LOGGER.isTraceEnabled()){
				sqlex.printStackTrace();
			}
			return(-1);
		}
	}

}