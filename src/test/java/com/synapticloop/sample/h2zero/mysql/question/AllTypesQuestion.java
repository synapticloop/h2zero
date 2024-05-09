package com.synapticloop.sample.h2zero.mysql.question;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                (/java/question/java-create-question.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Blob;
import java.sql.Timestamp;
import java.sql.Time;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

import com.synapticloop.h2zero.base.sql.nolimitoffset.Question;
import java.util.List;

/**
 * <p>This class contains all of the questions that are defined in the h2zero
 * file.</p>
 * 
 * <p>A question may only return a simple true/false response from a query.</p>
 * 
 * <p>Table name: <code>all_types</code></p>
 * 
 * 
* <p>To execute any question SQL query, you may pass in a connection, and execute it (silently if desired)</p>
 *
 * <p><strong><em>Executing a question without a passed in connection.</em></strong></p>
 *
 * <pre>AllTypesQuestion.questionName(parameter1, parameter2, ...)
 *          .execute();
 * </pre>
 *
 * <p><strong><em>Executing a question WITH a passed in connection.</em></strong></p>
 *
 * <pre>AllTypesQuestion.questionName(parameter1, parameter2, ...)
 *          .withConnection(connection)
 *          .execute();
 *  </pre>
 *
 * <p><strong><em>Executing a question WITHOUT a passed in connection SILENTLY
 * (i.e. All Exceptions are silently swallowed and logged as an error through
 * the logger.)</em></strong></p>
 *
 * <pre>AllTypesQuestion.questionName(parameter1, parameter2, ...)
 *          .executeSilent();
 * </pre>
 *
 * <p><strong><em>Executing a question WITH a passed in connection SILENTLY
 * (i.e. All Exceptions are silently swallowed and logged as an error through
 * the logger.)</em></strong></p>
 *
 * <pre>AllTypesQuestion.questionName(parameter1, parameter2, ...)
 *          .withConnection(connection)
 *          .executeSilent();
 * </pre>
 *
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class AllTypesQuestion {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesQuestion.class);


	// this is an internal SQL question select statement used by the validator
	private static final String SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST = "SELECT (COUNT(*) = 1) WHERE id_all_types = ?";



	/**
	 * <p>An internal method to check whether a specific primary key exists, 
	 * generated as part of the validation methods.</p>
	 * 
	 * @param idAllTypes The primary key for this model.
	 * 
	 * @return whether the primary key exists
	 */
	public static boolean internalDoesPrimaryKeyExist(Long idAllTypes) {
		ResultSet resultSet = null;

		boolean answer = false;

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST);
			ConnectionManager.setBigint(preparedStatement, 1, idAllTypes);

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
			ConnectionManager.closeAll(resultSet);
		}
		return(answer);
	}

}