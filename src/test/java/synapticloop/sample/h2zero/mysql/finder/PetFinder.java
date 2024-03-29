package synapticloop.sample.h2zero.mysql.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Blob;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;

import synapticloop.sample.h2zero.mysql.model.Pet;

public class PetFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_pet, 
				nm_pet, 
				num_age, 
				flt_weight, 
				dt_birthday, 
				img_photo
			from 
				pet
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_pet = ?";

	private static final String SQL_FIND_BY_NM_PET_NUM_AGE = SQL_SELECT_START + 
		"""
			where nm_pet = ? and num_age = ?
		""";
	// now for the statement limit cache(s)
	private static final LruCache<String, String> findAll_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> findByNmPetNumAge_limit_statement_cache = new LruCache<>(1024);

	private PetFinder() {}

	/**
	 * Find a Pet by its primary key
	 * 
	 * @param connection the connection item
	 * @param idPet the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static Pet findByPrimaryKey(Connection connection, Long idPet) throws H2ZeroFinderException {
		Pet pet = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if(null == idPet) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idPet] was null.");
		}

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_FIND_BY_PRIMARY_KEY);
			preparedStatement.setLong(1, idPet);
			resultSet = preparedStatement.executeQuery();
			pet = uniqueResult(resultSet);
		} catch (H2ZeroFinderException | SQLException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idPet:" + idPet + "].");
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		if(null == pet) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idPet:" + idPet + "].");
		}
		return(pet);
	}

	/**
	 * Find a Pet by its primary key
	 * 
	 * @param idPet the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static Pet findByPrimaryKey(Long idPet) throws H2ZeroFinderException {

		if(null == idPet) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idPet] was null.");
		}

		Pet pet = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			pet = findByPrimaryKey(connection, idPet);
		} catch (SQLException | H2ZeroFinderException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idPet:" + idPet + "].");
		}

		if(null == pet) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idPet:" + idPet + "].");
		}
		return(pet);
	}

	/**
	 * Find a Pet by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param connection the connection item
	 * @param idPet the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static Pet findByPrimaryKeySilent(Connection connection, Long idPet) {
		try {
			return(findByPrimaryKey(connection, idPet));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idPet + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find a Pet by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param idPet the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static Pet findByPrimaryKeySilent(Long idPet) {
		try {
			return(findByPrimaryKey(idPet));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idPet + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find all UserTitle objects with the passed in connection, with limited
	 * results starting at a particular offset.
	 * <p>
	 * If the limit parameter is null, there will be no limit applied.
	 * <p>
	 * If the offset is null, then this will be set to 0
	 * <p>
	 * If both limit and offset are null, then no limit and no offset will be applied
	 * to the statement.
	 * <p>
	 * The passed in connection object is usable for transactional SQL statements,
	 * where the connection has already had a transaction started on it.
	 * <p>
	 * If the connection object is null an new connection object will be created 
	 * and closed at the end of the method.
	 * <p>
	 * If the connection object is not null, then it will not be closed.
	 * 
	 * @param connection - the connection object to use (or null if not part of a transaction)
	 * @param limit - the limit for the result set
	 * @param offset - the offset for the start of the results.
	 * 
	 * @return a list of all the Pet objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<Pet> findAll(Connection connection, Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
		boolean hasConnection = (null != connection);
		String statement = null;
		// first find the statement that we want

		String cacheKey = limit + ":" + offset;
		if(!findAll_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_SELECT_START);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
				if(null != offset) {
					stringBuilder.append(" offset ");
					stringBuilder.append(offset);
				}
			}


			statement = stringBuilder.toString();
			findAll_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = findAll_limit_statement_cache.get(cacheKey);
		}

		// now set up the statement
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if(connection == null) {
			connection = ConnectionManager.getConnection();
		}

		List<Pet> results = new ArrayList<Pet>();

		try {
			preparedStatement = connection.prepareStatement(statement);
			resultSet = preparedStatement.executeQuery();
			results = list(resultSet);
		} catch(SQLException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findAll(): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			throw ex;
		} finally {
			if(hasConnection) {
				ConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				ConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}

		if(results.size() == 0) {
			throw new H2ZeroFinderException("Could not find any results for findAll");
		}
		return(results);
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters.
	 * 
	 * @return The list of Pet model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<Pet> findAll() throws SQLException, H2ZeroFinderException {
		return(findAll(null, null, null));
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset
	 * parameters.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of Pet model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<Pet> findAll(Connection connection) throws SQLException, H2ZeroFinderException {
		return(findAll(connection, null, null));
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null connection parameter
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of Pet model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<Pet> findAll(Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
		return(findAll(null, limit, offset));
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * 
	 * @param connection - the connection to be used
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of Pet model objects, or an empty List on error
	 */
	public static List<Pet> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException | H2ZeroFinderException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("Exception findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			return(new ArrayList<Pet>());
		}
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of Pet model objects, or an empty List on error
	 */
	public static List<Pet> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of Pet model objects, or an empty List on error
	 */
	public static List<Pet> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	/**
	 * Find all the Pet objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @return The list of Pet model objects, or an empty List on error
	 */
	public static List<Pet> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined finders which are generated
	 * through either the "finders" JSON key, or the "fieldFinders" JSON
	 * key.
	 * 
	 * There are 1 defined finders on the pet table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * - findByNmPetNumAge - Generated from the 'fieldFinders' JSON key
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * findByNmPetNumAge 
	 * <p>
	 * (This finder was generated through the 'fieldFinders' JSON key)
	 * <p>
	 * Note that if a limit and offset are passed through, then the generated statement 
	 * will be cached for further use
	 * 
	 * @param connection - the connection to the database
	 * @param nmPet - maps to the nm_pet field
	 * @param numAge - maps to the num_age field
	 * @param limit - The maximum number of rows to return
	 * @param offset - The row offset to start with
	 * 
	 * @return the list of Pet results found
	 * 
	 * @throws H2ZeroFinderException if no results could be found
	 * @throws SQLException if there was an error in the SQL statement
	 */
	public static List<Pet> findByNmPetNumAge(Connection connection, String nmPet, Integer numAge, Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		boolean hasConnection = (null != connection);
		String statement = null;

		// first find the statement that we want - or cache it if it doesn't exist

		String cacheKey = limit + ":" + offset;
		if(!findByNmPetNumAge_limit_statement_cache.containsKey(cacheKey)) {
			// place the cacheKey in the cache for later use

			StringBuilder stringBuilder = new StringBuilder(SQL_FIND_BY_NM_PET_NUM_AGE);

			if(null != limit) {
				stringBuilder.append(" limit ");
				stringBuilder.append(limit);
				if(null != offset) {
					stringBuilder.append(" offset ");
					stringBuilder.append(offset);
				}
			}

			statement = stringBuilder.toString();
			findByNmPetNumAge_limit_statement_cache.put(cacheKey, statement);
		} else {
			statement = findByNmPetNumAge_limit_statement_cache.get(cacheKey);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Pet> results = null;
		try {
			if(!hasConnection) {
				connection = ConnectionManager.getConnection();
			}
			preparedStatement = connection.prepareStatement(statement);
			ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
			ConnectionManager.setInt(preparedStatement, 2, numAge);

			resultSet = preparedStatement.executeQuery();
			results = list(resultSet);
		} catch (SQLException ex) {
			throw new SQLException("SQL exception in statement: " + statement, ex);
		} finally {
			if(hasConnection) {
				ConnectionManager.closeAll(resultSet, preparedStatement, null);
			} else {
				ConnectionManager.closeAll(resultSet, preparedStatement, connection);
			}
		}


		if(results.size() == 0) {
			throw new H2ZeroFinderException("Could not find result.");
		}
		return(results);
	}

	public static List<Pet> findByNmPetNumAge(Connection connection, String nmPet, Integer numAge) throws H2ZeroFinderException, SQLException {
		return(findByNmPetNumAge(connection, nmPet, numAge, null, null));
	}

	public static List<Pet> findByNmPetNumAge(String nmPet, Integer numAge, Integer limit, Integer offset) throws H2ZeroFinderException, SQLException {
		return(findByNmPetNumAge(null, nmPet, numAge, limit, offset));
	}

	public static List<Pet> findByNmPetNumAge(String nmPet, Integer numAge) throws H2ZeroFinderException, SQLException {
		return(findByNmPetNumAge(null, nmPet, numAge, null, null));
	}

	// silent connection, params..., limit, offset
	public static List<Pet> findByNmPetNumAgeSilent(Connection connection, String nmPet, Integer numAge, Integer limit, Integer offset) {
		try {
			return(findByNmPetNumAge(connection, nmPet, numAge, limit, offset));
		} catch(H2ZeroFinderException h2zfex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByNmPetNumAgeSilent(connection: " + connection + ", " + nmPet + ", " + numAge + ", limit: " + limit + ", offset: " + offset + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(new ArrayList<Pet>());
		} catch(SQLException sqlex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("SQLException findByNmPetNumAgeSilent(connection: " + connection + ", " + nmPet + ", " + numAge + ", limit: " + limit + ", offset: " + offset + "): " + sqlex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					sqlex.printStackTrace();
				}
			}
			return(new ArrayList<Pet>());
		}
	}

	// silent connection, params...
	public static List<Pet> findByNmPetNumAgeSilent(Connection connection, String nmPet, Integer numAge) {
		return(findByNmPetNumAgeSilent(connection, nmPet, numAge, null, null));
	}

	// silent params..., limit, offset
	public static List<Pet> findByNmPetNumAgeSilent(String nmPet, Integer numAge, Integer limit, Integer offset) {
		return(findByNmPetNumAgeSilent(null, nmPet, numAge, limit, offset));
	}

	public static List<Pet> findByNmPetNumAgeSilent(String nmPet, Integer numAge) {
		return(findByNmPetNumAgeSilent(null, nmPet, numAge, null, null));
	}

	/**
	 * Return a unique result for the query - in effect just the first result of
	 * query.  If there is a second result (i.e. the query did not return the 
	 * expected unique result), then an exception will be thrown.
	 * 
	 * @param resultSet The result set of the query
	 * 
	 * @return The Pet that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found or more than one result was found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static Pet uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			Long idPet = ConnectionManager.getNullableResultLong(resultSet, 1);
			String nmPet = ConnectionManager.getNullableResultString(resultSet, 2);
			Integer numAge = ConnectionManager.getNullableResultInt(resultSet, 3);
			Float fltWeight = ConnectionManager.getNullableResultFloat(resultSet, 4);
			Date dtBirthday = ConnectionManager.getNullableResultDate(resultSet, 5);
			Blob imgPhoto = ConnectionManager.getNullableResultBlob(resultSet, 6);

			Pet pet = new Pet(idPet, nmPet, numAge, fltWeight, dtBirthday, imgPhoto);

			if(resultSet.next()) {
				throw new H2ZeroFinderException("More than one result in resultset for unique finder.");
			} else {
				return(pet);
			}
		} else {
			// could not get a result
			return(null);
		}
	}

	/**
	 * Return the results as a list of Pet, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of Pet
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<Pet> list(ResultSet resultSet) throws SQLException {
		List<Pet> arrayList = new ArrayList<Pet>();
		while(resultSet.next()) {
			arrayList.add(new Pet(
					ConnectionManager.getNullableResultLong(resultSet, 1),
					ConnectionManager.getNullableResultString(resultSet, 2),
					ConnectionManager.getNullableResultInt(resultSet, 3),
					ConnectionManager.getNullableResultFloat(resultSet, 4),
					ConnectionManager.getNullableResultDate(resultSet, 5),
					ConnectionManager.getNullableResultBlob(resultSet, 6)));
		}
		return(arrayList);
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined select clause finders which are 
	 * generated through the "finders" JSON key, with a 'selectClause' 
	 * key on the finder.
	 * 
	 * All selectClause finders return a subset of the data from a row of the 
	 * database table (or tables if there is a join statement) as a generated
	 * bean
	 * 
	 * There are 1 defined finders on the pet table, of those finders
	 * the following are the select clause finders:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}