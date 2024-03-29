package synapticloop.sample.h2zero.sqlite3.impex;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                 (/impex/impex-importer.templar)

import synapticloop.h2zero.exception.H2ZeroParseException;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.sample.h2zero.sqlite3.model.Pet;

import java.math.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PetImporter {
	// The primary key cache is keyed on the id from the imported line -> value 
	// is the actual inserted value - which may be the same 
	public static final Map<Long, Long> PRIMARY_KEY_CACHE = new HashMap<>();

	private static final String SQL_SELECT_UNIQUE = "";
	public static void importLine(String line) throws H2ZeroParseException {
		String[] splits = line.split("\t");
		if(splits.length != 5) {
			// we don't have enough tokens to import - we always import every field
			throw new H2ZeroParseException(String.format("Insufficient fields, found '%d', required '%d'",5 , splits.length));
		}

		// we are good to go
		// for each field - we parse the value
		Long idPet = ImpexConverter.convertLong(splits[0], false);
		String nmPet = ImpexConverter.convertString(splits[1], false);
		Integer numAge = ImpexConverter.convertInteger(splits[2], false);
		Float fltWeight = ImpexConverter.convertFloat(splits[3], true);
		Date dtBirthday = ImpexConverter.convertDate(splits[4], true);

		if(confirmExisting(idPet, nmPet, numAge, fltWeight, dtBirthday)) {
			return;
		}
	}


	// IMPORT 'find-existing.templar' START

	/**
	 * Confirm whether there is an existing table with all of the fields matching 
	 * (apart from the primary key)
	 * 
	 * @param idPet The id_pet which is used as the lookup
	 * @param nmPet The nm_pet which is used as the lookup
	 * @param numAge The num_age which is used as the lookup
	 * @param fltWeight The flt_weight which is used as the lookup
	 * @param dtBirthday The dt_birthday which is used as the lookup
	 * 
	 * @return whether we were able to find the exact duplicat of this table row
	 * 
	 * @throws H2ZeroParseException - if there was a SQLException, or this is a 
	 * constant table and we couldn't look up the constant value
	 */
	private static boolean confirmExisting(Long idPet, String nmPet, Integer numAge, Float fltWeight, Date dtBirthday) throws H2ZeroParseException {
		StringBuilder SQL_FIND_EXACT = new StringBuilder("select * from pet where ");
		if (null == nmPet) {
			SQL_FIND_EXACT.append(" nm_pet is null");
		} else {
			SQL_FIND_EXACT.append(" nm_pet = ?");
		}
		SQL_FIND_EXACT.append(" and ");
		if (null == numAge) {
			SQL_FIND_EXACT.append(" num_age is null");
		} else {
			SQL_FIND_EXACT.append(" num_age = ?");
		}
		SQL_FIND_EXACT.append(" and ");
		if (null == fltWeight) {
			SQL_FIND_EXACT.append(" flt_weight is null");
		} else {
			SQL_FIND_EXACT.append(" flt_weight = ?");
		}
		SQL_FIND_EXACT.append(" and ");
		if (null == dtBirthday) {
			SQL_FIND_EXACT.append(" dt_birthday is null");
		} else {
			SQL_FIND_EXACT.append(" dt_birthday = ?");
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