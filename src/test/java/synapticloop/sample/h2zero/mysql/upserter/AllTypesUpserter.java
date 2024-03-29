package synapticloop.sample.h2zero.mysql.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Blob;
import java.sql.Timestamp;
import java.sql.Time;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;

import synapticloop.sample.h2zero.mysql.model.AllTypes;

public class AllTypesUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesUpserter.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_all_types, 
				test_bigint, 
				test_blob, 
				test_bool, 
				test_char, 
				test_boolean, 
				test_binary, 
				test_varbinary, 
				test_date, 
				test_datetime, 
				test_dec, 
				test_decimal, 
				test_double, 
				test_float, 
				test_int, 
				test_integer, 
				test_longtext, 
				test_mediumblob, 
				test_mediumint, 
				test_mediumtext, 
				test_numeric, 
				test_smallint, 
				test_time, 
				test_text, 
				test_timestamp, 
				test_tinyint, 
				test_tinytext, 
				test_varchar, 
				test_year
			from 
				all_types
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_all_types = ?";


	private AllTypesUpserter() {}

}