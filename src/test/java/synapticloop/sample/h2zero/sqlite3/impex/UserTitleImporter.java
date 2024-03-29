package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/impex-importer.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.sample.h2zero.sqlite3.model.UserTitle;

import java.math.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserTitleImporter {
	// The primary key cache is keyed on the id from the imported line -> value 
	// is the actual inserted value - which may be the same 
	public static final Map<Long, Long> PRIMARY_KEY_CACHE = new HashMap<>();

	public static void importLine(String line) throws H2ZeroParseException {
		String[] splits = line.split("\t");
		if(splits.length != 3) {
			// we don't have enough tokens to import - we always import every field
			throw new H2ZeroParseException(String.format("Insufficient fields, found '%d', required '%d'",3 , splits.length));
		}

		// we are good to go
		// for each field - we parse the value
		Long idUserTitle = ImpexConverter.convertLong(splits[0], false);
		String nmUserTitle = ImpexConverter.convertString(splits[1], false);
		Integer numOrderBy = ImpexConverter.convertInteger(splits[2], false);

		if(confirmExisting(idUserTitle, nmUserTitle, numOrderBy)) {
			return;
		}
	}


	// IMPORT 'find-existing.templar' START

	/**
	 * Confirm whether there is an existing table with all of the fields matching 
	 * (apart from the primary key)
	 * 
	 * @param idUserTitle The id_user_title which is used as the lookup
	 * @param nmUserTitle The nm_user_title which is used as the lookup
	 * @param numOrderBy The num_order_by which is used as the lookup
	 * 
	 * @return whether we were able to find the exact duplicat of this table row
	 * 
	 * @throws H2ZeroParseException - if there was a SQLException, or this is a 
	 * constant table and we couldn't look up the constant value
	 */
	private static boolean confirmExisting(Long idUserTitle, String nmUserTitle, Integer numOrderBy) throws H2ZeroParseException {
		StringBuilder SQL_FIND_EXACT = new StringBuilder("select * from user_title where ");
		if (null == nmUserTitle) {
			SQL_FIND_EXACT.append(" nm_user_title is null");
		} else {
			SQL_FIND_EXACT.append(" nm_user_title = ?");
		}
		SQL_FIND_EXACT.append(" and ");
		if (null == numOrderBy) {
			SQL_FIND_EXACT.append(" num_order_by is null");
		} else {
			SQL_FIND_EXACT.append(" num_order_by = ?");
		}
		ResultSet resultSetExact = null;
		// now set all of the parameters
		try (Connection connection = ConnectionManager.getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_EXACT.toString())) {
			resultSetExact = preparedStatement.getResultSet();
			if(resultSetExact.next()) {
				// we have found one, this means that we do not need to import it
				// just update the primary key cache
				PRIMARY_KEY_CACHE.put(idAuthorStatus, resultSetExact.getLong(1));
				return(true);
			} else {
				// fall through and see if we can find it by some other means...
			}
		} catch (SQLException e) {
			throw new H2ZeroParseException(String.format("Could not execute the SQL statement.  Exception, message was: '%s'", e.getMessage()), e);
		} finally {
			if(null != resultSetExact) {
				try { resultSetExact.close(); } catch (SQLException e) { /* do nothing */ }
			}
		}

		// this is a constant table - if it cannot be looked up then a code change will need to be made
		throw new H2ZeroParseException("Could not find a constant table with values [" + idUserTitle + ", " + nmUserTitle + ", " + numOrderBy + "] a code change will be required.");
	}
	// IMPORT 'find-existing.templar' END





	/**
	 * Clear the primary key cache, ready for data importing 
	 */
	public static void clearPrimaryKeyCache() {
		PRIMARY_KEY_CACHE.clear();
	}
}