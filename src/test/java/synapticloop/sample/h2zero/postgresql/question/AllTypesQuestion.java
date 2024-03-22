package synapticloop.sample.h2zero.postgresql.question;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-question.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.cockroach.ConnectionManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.postgresql.model.util.Constants;

import java.util.List;

/**
 * <p>This class contains all of the questions that are defined in the h2zero
 * file</p>
 * <p>A question returns a simple true/false response from a query.</p>
 * 
 * <p>Table name: <code>all_types</code></p>
 * 
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



	/** Private to deter instantiation */
	private AllTypesQuestion() {}

	/**
	 * An internal method to check whether a specific primary key exists, 
	 * generated as part of the validation methods
	 * 
	 * @param idAllTypes The primary key for this model
	 * 
	 * @return whether the primary key exists
	 */
	public static boolean internalDoesPrimaryKeyExist(Long idAllTypes) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean answer = false;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INTERNAL_DOES_PRIMARY_KEY_EXIST);
			ConnectionManager.setBigserial(preparedStatement, 1, idAllTypes);

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