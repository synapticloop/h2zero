package synapticloop.sample.h2zero.mysql.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;

import synapticloop.sample.h2zero.mysql.model.PetType;

public class PetTypeFinder {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeFinder.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_pet_type, 
				nm_pet_type, 
				txt_desc_pet_type
			from 
				pet_type
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_pet_type = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> findAll_limit_statement_cache = new LruCache<>(1024);

	private PetTypeFinder() {}

	/**
	 * Find a PetType by its primary key
	 * 
	 * @param connection the connection item
	 * @param idPetType the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static PetType findByPrimaryKey(Connection connection, Long idPetType) throws H2ZeroFinderException {
		PetType petType = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if(null == idPetType) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idPetType] was null.");
		}

		try {
			preparedStatement = connection.prepareStatement(SQL_BUILTIN_FIND_BY_PRIMARY_KEY);
			preparedStatement.setLong(1, idPetType);
			resultSet = preparedStatement.executeQuery();
			petType = uniqueResult(resultSet);
		} catch (H2ZeroFinderException | SQLException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idPetType:" + idPetType + "].");
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}

		if(null == petType) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idPetType:" + idPetType + "].");
		}
		return(petType);
	}

	/**
	 * Find a PetType by its primary key
	 * 
	 * @param idPetType the primary key
	 * 
	 * @return the unique result or throw an exception if one couldn't be found.
	 * 
	 * @throws H2ZeroFinderException if one couldn't be found
	 */
	public static PetType findByPrimaryKey(Long idPetType) throws H2ZeroFinderException {

		if(null == idPetType) {
			throw new H2ZeroFinderException("Could not find result as the primary key field [idPetType] was null.");
		}

		PetType petType = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			petType = findByPrimaryKey(connection, idPetType);
		} catch (SQLException | H2ZeroFinderException ex) {
			throw new H2ZeroFinderException(ex.getMessage() + "  Additionally, the parameters were [idPetType:" + idPetType + "].");
		}

		if(null == petType) {
			throw new H2ZeroFinderException("Could not find result the parameters were [idPetType:" + idPetType + "].");
		}
		return(petType);
	}

	/**
	 * Find a PetType by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param connection the connection item
	 * @param idPetType the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static PetType findByPrimaryKeySilent(Connection connection, Long idPetType) {
		try {
			return(findByPrimaryKey(connection, idPetType));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idPetType + "): " + h2zfex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					h2zfex.printStackTrace();
				}
			}
			return(null);
		}
	}

	/**
	 * Find a PetType by its primary key and silently fail.
	 * I.e. Do not throw an exception on error.
	 * 
	 * @param idPetType the primary key
	 * 
	 * @return the unique result or null if it couldn't be found
	 * 
	 */
	public static PetType findByPrimaryKeySilent(Long idPetType) {
		try {
			return(findByPrimaryKey(idPetType));
		} catch(H2ZeroFinderException h2zfex){
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("H2ZeroFinderException findByPrimaryKeySilent(" + idPetType + "): " + h2zfex.getMessage());
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
	 * @return a list of all the PetType objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<PetType> findAll(Connection connection, Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
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

		List<PetType> results = new ArrayList<PetType>();

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
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters.
	 * 
	 * @return The list of PetType model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<PetType> findAll() throws SQLException, H2ZeroFinderException {
		return(findAll(null, null, null));
	}

	/**
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset
	 * parameters.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of PetType model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<PetType> findAll(Connection connection) throws SQLException, H2ZeroFinderException {
		return(findAll(connection, null, null));
	}

	/**
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null connection parameter
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of PetType model objects
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroFinderException if no results were found
	 */
	public static List<PetType> findAll(Integer limit, Integer offset) throws SQLException, H2ZeroFinderException {
		return(findAll(null, limit, offset));
	}

	/**
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * 
	 * @param connection - the connection to be used
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of PetType model objects, or an empty List on error
	 */
	public static List<PetType> findAllSilent(Connection connection, Integer limit, Integer offset) {
		try {
			return(findAll(connection, limit, offset));
		} catch(SQLException | H2ZeroFinderException ex) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn("Exception findAllSilent(connection: " + connection + ", limit: " +  limit + ", offset: " + offset + "): " + ex.getMessage());
				if(LOGGER.isDebugEnabled()) {
					ex.printStackTrace();
				}
			}
			return(new ArrayList<PetType>());
		}
	}

	/**
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param connection - the connection to be used
	 * 
	 * @return The list of PetType model objects, or an empty List on error
	 */
	public static List<PetType> findAllSilent(Connection connection) {
		return(findAllSilent(connection, null, null));
	}

	/**
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null limit and offset parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @param limit - the limit for the number of results to return
	 * @param offset - the offset from the start of the results
	 * 
	 * @return The list of PetType model objects, or an empty List on error
	 */
	public static List<PetType> findAllSilent(Integer limit, Integer offset) {
		return(findAllSilent(null, limit, offset));
	}

	/**
	 * Find all the PetType objects - in effect this chains 
	 * to the findAll(connection, limit, offset) with null parameters,
	 * however this method swallows any exceptions and will return an empty list.
	 * 
	 * @return The list of PetType model objects, or an empty List on error
	 */
	public static List<PetType> findAllSilent() {
		return(findAllSilent(null, null, null));
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined finders which are generated
	 * through either the "finders" JSON key, or the "fieldFinders" JSON
	 * key.
	 * 
	 * There are 0 defined finders on the pet_type table, of those finders
	 * the following are the regular finders, either defined through the
	 * 'finders' or 'fieldFinders' JSON key
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Return a unique result for the query - in effect just the first result of
	 * query.  If there is a second result (i.e. the query did not return the 
	 * expected unique result), then an exception will be thrown.
	 * 
	 * @param resultSet The result set of the query
	 * 
	 * @return The PetType that represents this result
	 * 
	 * @throws H2ZeroFinderException if no results were found or more than one result was found
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static PetType uniqueResult(ResultSet resultSet) throws H2ZeroFinderException, SQLException {
		if(resultSet.next()) {
			// we have a result
			Long idPetType = ConnectionManager.getNullableResultLong(resultSet, 1);
			String nmPetType = ConnectionManager.getNullableResultString(resultSet, 2);
			String txtDescPetType = ConnectionManager.getNullableResultString(resultSet, 3);

			PetType petType = new PetType(idPetType, nmPetType, txtDescPetType);

			if(resultSet.next()) {
				throw new H2ZeroFinderException("More than one result in resultset for unique finder.");
			} else {
				return(petType);
			}
		} else {
			// could not get a result
			return(null);
		}
	}

	/**
	 * Return the results as a list of PetType, this will be empty if
	 * none are found.
	 * 
	 * @param resultSet the results as a list of PetType
	 * 
	 * @return the list of results
	 * 
	 * @throws SQLException if there was a problem retrieving the results
	 */
	private static List<PetType> list(ResultSet resultSet) throws SQLException {
		List<PetType> arrayList = new ArrayList<PetType>();
		while(resultSet.next()) {
			arrayList.add(new PetType(
					ConnectionManager.getNullableResultLong(resultSet, 1),
					ConnectionManager.getNullableResultString(resultSet, 2),
					ConnectionManager.getNullableResultString(resultSet, 3)));
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
	 * There are 0 defined finders on the pet_type table, of those finders
	 * the following are the select clause finders:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}