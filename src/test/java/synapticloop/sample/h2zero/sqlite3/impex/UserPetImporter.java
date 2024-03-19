package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/impex-importer.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.sample.h2zero.sqlite3.model.UserPet;
import synapticloop.sample.h2zero.sqlite3.finder.UserFinder;
import synapticloop.sample.h2zero.sqlite3.model.User;
import synapticloop.sample.h2zero.sqlite3.finder.PetFinder;
import synapticloop.sample.h2zero.sqlite3.model.Pet;

import java.math.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserPetImporter {
	// The primary key cache is keyed on the id from the imported line -> value 
	// is the actual inserted value - which may be the same 
	public static final Map<Long, Long> PRIMARY_KEY_CACHE = new HashMap<>();

	private static final String SQL_SELECT_UNIQUE = "";
	public static void importLine(String line) throws H2ZeroParseException {
		String[] splits = line.split("\t");
		if(splits.length != 3) {
			// we don't have enough tokens to import - we always import every field
			throw new H2ZeroParseException(String.format("Insufficient fields, found '%d', required '%d'",3 , splits.length));
		}

		// we are good to go
		// for each field - we parse the value
		Long idUserPet = ImpexConverter.convertLong(splits[0], false);
		Long idUser = ImpexConverter.convertLong(splits[1], false);
		Long idPet = ImpexConverter.convertLong(splits[2], false);

		if(confirmExisting(idUserPet, idUser, idPet)) {
			return;
		}
	}


	// IMPORT 'find-existing.templar' START

	/**
	 * Confirm whether there is an existing table with all of the fields matching 
	 * (apart from the primary key)
	 * 
	 * @param idUserPet The id_user_pet which is used as the lookup
	 * @param idUser The id_user which is used as the lookup
	 * @param idPet The id_pet which is used as the lookup
	 * 
	 * @return whether we were able to find the exact duplicat of this table row
	 * 
	 * @throws H2ZeroParseException - if there was a SQLException, or this is a 
	 * constant table and we couldn't look up the constant value
	 */
	private static boolean confirmExisting(Long idUserPet, Long idUser, Long idPet) throws H2ZeroParseException {
		StringBuilder SQL_FIND_EXACT = new StringBuilder("select * from user_pet where ");
		if (null == idUser) {
			SQL_FIND_EXACT.append(" id_user is null");
		} else {
			SQL_FIND_EXACT.append(" id_user = ?");
		}
		SQL_FIND_EXACT.append(" and ");
		if (null == idPet) {
			SQL_FIND_EXACT.append(" id_pet is null");
		} else {
			SQL_FIND_EXACT.append(" id_pet = ?");
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

		return(false);
	}
	// IMPORT 'find-existing.templar' END





		// IMPORT 'find-unique.templar' START

// we have no unique fields

		// IMPORT 'find-unique.templar' END





	/**
	 * Clear the primary key cache, ready for data importing 
	 */
	public static void clearPrimaryKeyCache() {
		PRIMARY_KEY_CACHE.clear();
	}
}