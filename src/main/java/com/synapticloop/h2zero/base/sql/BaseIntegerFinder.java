package com.synapticloop.h2zero.base.sql;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseIntegerFinder extends BaseSQLExecutor {
	public BaseIntegerFinder(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * <p>Execute the SQL statement.</p>
	 *
	 * <p>This will evaluate the (String) SQL statement and perform the following
	 * operations on the statement to set it up before it gets executed</p>
	 *
	 * <ol>
	 *   <li>Look at any inField (i.e. <code>where id_some_number in (...)</code>
	 *   and replace them with the correct number of parameterised variables.</li>
	 *   <li>Add a limit statement with or without and offset statement.</li>
	 *   <li>Safely add all of the parameters to the prepared statement.</li>
	 *   <li>Finally, execute the statement and return the results.</li>
	 * </ol>
	 *
	 * @return the object, or null if one wasn't found
	 *
	 * @throws SQLException If there was an error executing the SQL statement
	 * @throws H2ZeroFinderException If no results could be found
	 */
	protected Integer executeInternal() throws SQLException, H2ZeroFinderException {
		if(null == connection) {
			connection = getConnection();
		}

		// we are going to add the limit/offset or offset/fetch statement first,
		// which may be a blank string
		String preparedStatementTemp = sqlStatement + getLimitedResultsStatement();

		// look for any parameters which are a list - this means that they are in fields
		List<Integer> inFieldOffsets = new ArrayList<>();

		int i = 0;
		for(Object object: parameters) {
			if(object instanceof List<?>) {
				inFieldOffsets.add(i);
			}
			i++;
		}

		if(inFieldOffsets.size() != 0) {
			for(Integer offset: inFieldOffsets) {
				StringBuilder whereFieldStringBuilder = null;
				whereFieldStringBuilder = new StringBuilder();
				int size = ((List<?>) parameters[offset]).size();
				for(int j = 0; j < size; j++) {
					if(j > 0) {
						whereFieldStringBuilder.append(", ");
					}
					whereFieldStringBuilder.append("?");
				}

				preparedStatementTemp = preparedStatementTemp.replaceFirst("\\.\\.\\.", whereFieldStringBuilder.toString());
			}
		}

		// now we prepare the statement
		PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementTemp);

		// now we set all of the parameters
		int k = 1;
		for(Object object: parameters) {
			preparedStatement.setObject(k, object);
			k++;

			if(object instanceof List<?> listObjects) {
				for(Object listObject: listObjects) {
					preparedStatement.setObject(i, listObject);
					k++;
				}
			}
		}

		// finally execute the statement
		return(preparedStatement.executeUpdate());
	}

	/**
	 * <p>Execute the statement, swallowing any exceptions - <strong>NOTE</strong>
	 * that if a SQL exception is caught - then it will be logged as an error.</p>
	 *
	 * <p>This is just a chain of the call to the <code>executeInternal</code>
	 * catching any exceptions, and logging them where necessary.</p>
	 *
	 * @return List the list of object, or an empty list if none were found.
	 */
	protected Integer executeSilentInternal() {
		try {
			return(executeInternal());
		} catch (SQLException e) {
			logger.error("SQLException executing statement '{}'", sqlStatement);
		} catch (H2ZeroFinderException ignored) {
		}

		return(null);
	}

	protected abstract String getLimitedResultsStatement();

	protected abstract Connection getConnection() throws SQLException;
}
