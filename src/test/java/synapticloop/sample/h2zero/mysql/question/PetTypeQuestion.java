package synapticloop.sample.h2zero.mysql.question;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-question.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;

import java.util.List;

public class PetTypeQuestion {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeQuestion.class);


	// this is an internal SQL question select statement used by the validator
	private static final String SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST = "SELECT (COUNT(*) = 1) WHERE id_pet_type = ?";



	/** Private to deter instantiation */
	private PetTypeQuestion() {}

	/**
	 * An internal method to check whether a specific primary key exists, 
	 * generated as part of the validation methods
	 * 
	 * @param idPetType The primary key for this model
	 * 
	 * @return whether the primary key exists
	 */
	public static boolean internalDoesPrimaryKeyExist(Long idPetType) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean answer = false;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST);
			ConnectionManager.setBigint(preparedStatement, 1, idPetType);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				answer = resultSet.getBoolean(1);
			}
		} catch (SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException internalDoesPrimaryKeyExist(): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement, connection);
		}
		return(answer);
	}

}