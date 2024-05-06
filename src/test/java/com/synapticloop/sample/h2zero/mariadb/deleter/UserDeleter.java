package com.synapticloop.sample.h2zero.mariadb.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//         (/java/deleter/java-create-deleter.templar)

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.synapticloop.h2zero.util.LruCache;

import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.base.sql.limitoffset.Deleter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;

public class UserDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeleter.class);

	// static fields generated by synapticloop h2zero
	// private static final String SQL_BUILTIN_DELETE_ALL = "delete from user";
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from user";
	private static final String SQL_DELETE_START = "delete from user ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_user = ?";

	// static fields generated from the user input
	private static final String SQL_DELETE_BY_NUM_AGE = SQL_DELETE_START + " where num_age = ?";
	private static final String SQL_DELETE_BY_FL_IS_ALIVE_ID_USER_TYPE = SQL_DELETE_START + " where fl_is_alive = ? and id_user_type = ?";
	private static final String SQL_DELETE_BY_NUM_AGE_TEST = SQL_DELETE_START + " where num_age = ?";
	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByNumAge_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByFlIsAliveIdUserType_limit_statement_cache = new LruCache<>(1024);
	private static final LruCache<String, String> deleteByNumAgeTest_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private UserDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the USER table by its primary key
	 * 
	 * @param idUser the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 */
	public static Deleter deleteByPrimaryKey(Long idUser) {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_BY_PRIMARY_KEY,
				new Object[] { idUser }
		));
	}
	/**
	 * Delete all of the rows in the table 'user'.
	 * 
	 * This table has a foreign key relationship on it, consequently the truncate
	 * method would have been faster, but would fail, hence the 'DELETE FROM' SQL
	 * statement is used
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
	 *     USER DEFINED DELETERS FOR THE TABLE: user
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 3 defined Deleters on the user table:
	 * 
	 * - deleteByNumAge - 'fieldDeleters' JSON key 
	 * - deleteByFlIsAliveIdUserType - 'fieldDeleters' JSON key 
	 * - deleteByNumAgeTest - from 'deleter' JSON key 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByNumAge - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByNumAge(Integer numAge ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_NUM_AGE,
				new Object[] {numAge }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByFlIsAliveIdUserType - from 'fieldDeleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param flIsAlive - maps to the fl_is_alive field (from the where clause)
	 * @param idUserType - maps to the id_user_type field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByFlIsAliveIdUserType(Boolean flIsAlive, Long idUserType ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_FL_IS_ALIVE_ID_USER_TYPE,
				new Object[] {flIsAlive, idUserType }
		));
	}
	// USER DEFINED DELETER CONNECTION, PARAMS, LIMIT
	/**
	 * deleteByNumAgeTest - from 'deleters' JSON key
	 *
	 * This is the main method for all other deleter methods with the same prefix,
	 * including the (silent method signatures).  All methods chain to this one.
	 * 
	 * @param numAge - maps to the num_age field (from the where clause)
	 * 
	 * @return the Deleter
	 * 
	 */
	public static Deleter deleteByNumAgeTest(Integer numAge ) {
		return(
				new Deleter(
				LOGGER,
				SQL_DELETE_BY_NUM_AGE_TEST,
				new Object[] {numAge }
		));
	}
}