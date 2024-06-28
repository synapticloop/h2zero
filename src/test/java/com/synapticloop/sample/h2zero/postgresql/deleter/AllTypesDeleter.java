package com.synapticloop.sample.h2zero.postgresql.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//         (/java/deleter/java-create-deleter.templar)

import com.synapticloop.h2zero.generator.util.LruCache;

import com.synapticloop.h2zero.base.sql.limitoffset.Deleter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

public class AllTypesDeleter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesDeleter.class);

	// static fields generated by synapticloop h2zero
	//private static final String SQL_BUILTIN_DELETE_ALL = "truncate table all_types";
	private static final String SQL_BUILTIN_DELETE_ALL = "delete from all_types";
	private static final String SQL_DELETE_START = "delete from all_types ";
	private static final String SQL_BUILTIN_DELETE_BY_PRIMARY_KEY = SQL_DELETE_START + "where id_all_types = ?";

	// now for the statement limit cache(s)
	private static final LruCache<String, String> deleteAll_limit_statement_cache = new LruCache<>(1024);

	// We don't allow instantiation
	private AllTypesDeleter() {}

 	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 	 * 
 	 * The following deleters are built in by h2zero and are always generated 
 	 * 
 	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a row in the ALL_TYPES table by its primary key
	 * 
	 * @param idAllTypes the primary key to delete
	 * 
	 * @return the number of rows deleted
	 * 
	 */
	public static Deleter deleteByPrimaryKey(Long idAllTypes) {
		return(
				new Deleter(
				LOGGER,
				SQL_BUILTIN_DELETE_BY_PRIMARY_KEY,
				new Object[] { idAllTypes }
		));
	}
	/**
	 * Delete all of the rows in the table 'all_types'.
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
	 *     USER DEFINED DELETERS FOR THE TABLE: all_types
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * This is the start of the user defined Deleters which are generated
	 * through either the "deleters" JSON key, or the "fieldDeleters" JSON
	 * key.
	 * 
	 * There are 0 defined Deleters on the all_types table:
	 * 
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}