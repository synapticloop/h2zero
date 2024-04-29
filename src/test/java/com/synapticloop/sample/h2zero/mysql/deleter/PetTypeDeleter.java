package com.synapticloop.sample.h2zero.mysql.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-deleter.templar)

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.synapticloop.h2zero.util.LruCache;

import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.base.sql.mysql.Deleter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

public class PetTypeDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeDeleter.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_BUILTIN_DELETE_ALL = "truncate table pet_type";
	private static final String SQL_DELETE_START = "delete from pet_type ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_pet_type = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private PetTypeDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the PET_TYPE table by its primary key
	 * 
	 * @param idPetType the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 */
	public static Deleter deleteByPrimaryKey(Long idPetType) {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_BY_PRIMARY_KEY,
				new Object[] { idPetType }
		));
	}
	/**
	 * Delete all of the rows in the table 'pet_type'.
	 * 
	 * This table has no foreign key relationships and consequently can be truncated.
	 * 
	 * 
	 * @return The number of rows affected by this statement
	 */
	public static Deleter deleteAll() {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_ALL,
				new Object[] { }
		));
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 *     USER DEFINED DELETERS FOR THE TABLE: pet_type
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 0 defined Deleters on the pet_type table:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}