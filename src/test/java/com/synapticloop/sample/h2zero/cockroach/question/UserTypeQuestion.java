package com.synapticloop.sample.h2zero.cockroach.question;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-question.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;

import java.util.List;

/**
 * <p>This class contains all of the questions that are defined in the h2zero
 * file</p>
 * <p>A question returns a simple true/false response from a query.</p>
 * 
 * <p>Table name: <code>user_type</code></p>
 * 
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserTypeQuestion {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeQuestion.class);


	// this is an internal SQL question select statement used by the validator
	private static final String SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST = "SELECT (COUNT(*) = 1) WHERE id_user_type = ?";



	/** Private to deter instantiation */
	private UserTypeQuestion() {}

	/**
	 * An internal method to check whether a specific primary key exists, 
	 * generated as part of the validation methods
	 * 
	 * @param idUserType The primary key for this model
	 * 
	 * @return whether the primary key exists
	 */
	public static boolean internalDoesPrimaryKeyExist(Long idUserType) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean answer = false;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST);
			ConnectionManager.setBigint(preparedStatement, 1, idUserType);

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