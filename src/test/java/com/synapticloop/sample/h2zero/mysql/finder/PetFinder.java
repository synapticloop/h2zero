package com.synapticloop.sample.h2zero.mysql.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (/java/finder/java-create-finder.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Blob;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

import com.synapticloop.sample.h2zero.mysql.model.Pet;

import com.synapticloop.h2zero.base.sql.limitoffset.MultiFinder;
import com.synapticloop.h2zero.base.sql.limitoffset.UniqueFinder;

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

	private PetFinder() {}

	/**
	 * <p>Create a UniqueFinder that can find a Pet by its primary key</p>
	 * 
	 * <p>This will return a UniqueFinder, to execute the finder, either call</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>Pet.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>Pet.findByPrimaryKey(primaryKey)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * @param idPet the primary key
	 * 
	 * @return the parameterised UniqueFinder
	 */
	public static UniqueFinder<Pet> findByPrimaryKey(Long idPet) {
		return(new UniqueFinder<Pet>(
				LOGGER,
				SQL_BUILTIN_FIND_BY_PRIMARY_KEY,
				resultSet -> { try { return list(resultSet); } catch (SQLException e) { return(null); }},
				idPet
		));
	}

	/**
	 * <p>Create a MultiFinder that can find all Pet rows</p>
	 * <p>
	 * <p>This will return a UniqueFinder, to execute the finder, either call</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>Pet.findAll()
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>Pet.findAll()
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>Pet.findAll()
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @return the parameterised MultiFinder
	 */
	public static MultiFinder<Pet> findAll() {
		return(
				new MultiFinder<Pet>(
				LOGGER,
				SQL_SELECT_START,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {}
		));
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
	 * This is the <code>findByNmPetNumAge</code> finder.
	 * 
	 * <p><em>(This finder was generated through the '<code>fieldFinders</code>' JSON key)</em></p>
	 * 
	 * <p>Create a MultiFinder<Pet> Finder
	 * that can be invoked through:</p>
	 * 
	 * <ul>
	 *   <li><code>finder.execute();</code> to execute the finder with exceptions thrown</li>
	 *   <li><code>finder.executeSilent();</code> to execute the finder no exceptions (i.e. they are caught, swallowed and logged)</li>
	 * </ul>
	 * 
	 * <p>You may also want to pass in a connection, in which case use the following:</p>
	 * 
	 * <pre>Pet.findByNmPetNumAge(...)
	 *     .withConnection(connection)
	 *     .execute();</pre>
	 * 
	 * <p>You may also want to pass in a connection without exceptions being thrown,
	 * in which case use the following:</p>
	 * 
	 * <pre>Pet.findByNmPetNumAge(...)
	 *     .withConnection(connection)
	 *     .executeSilent();</pre>
	 * 
	 * <p>Additionally, you can limit and offset this query (with or without a connection)</p>
	 * 
	 * <pre>Pet.findByNmPetNumAge(...)
	 *     .withLimit(limit)
	 *     .withOffset(offset)
	 *     .executeSilent();</pre>
	 * 
	 * @param nmPet - maps to the nm_pet field
	 * @param numAge - maps to the num_age field
	 * 
	 * @return the parameterised MultiFinder()
	 * 
	 */
	public static MultiFinder<Pet> findByNmPetNumAge(String nmPet, Integer numAge) {
		return(
				new MultiFinder<Pet>(
				LOGGER,
				SQL_FIND_BY_NM_PET_NUM_AGE,
				resultSet -> { try {return list(resultSet);} catch (SQLException e) { return(null); }},
				new Object[] {nmPet, numAge}
		));
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