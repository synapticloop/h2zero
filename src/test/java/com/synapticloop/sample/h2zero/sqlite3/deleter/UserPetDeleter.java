package com.synapticloop.sample.h2zero.sqlite3.deleter;

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

import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.h2zero.base.sql.limitoffset.Deleter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

public class UserPetDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPetDeleter.class);

	// static fields generated by synapticloop h2zero
	// private static final String SQL_BUILTIN_DELETE_ALL = "delete from user_pet";
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from user_pet";
	private static final String SQL_DELETE_START = "delete from user_pet ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_user_pet = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private UserPetDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the USER_PET table by its primary key
	 * 
	 * @param idUserPet the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 */
	public static Deleter deleteByPrimaryKey(Long idUserPet) {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_BY_PRIMARY_KEY,
				new Object[] { idUserPet }
		));
	}
	/**
	 * Delete all of the rows in the table 'user_pet'.
	 * 
	 * This database does not have the 'truncate' keyword hence the 'DELETE FROM' SQL
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
	 *     USER DEFINED DELETERS FOR THE TABLE: user_pet
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 0 defined Deleters on the user_pet table:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}