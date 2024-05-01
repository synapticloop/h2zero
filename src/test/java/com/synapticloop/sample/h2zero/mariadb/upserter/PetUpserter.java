package com.synapticloop.sample.h2zero.mariadb.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)

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
import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;

import com.synapticloop.sample.h2zero.mariadb.model.Pet;

public class PetUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetUpserter.class);
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
	private static final String SQL_FIND_BY_NUM_AGE = SQL_SELECT_START + 
		"""
			where num_age = ?
		""";
	private static final String SQL_FIND_AGE_BETWEEN = SQL_SELECT_START + 
		"""
			where num_age >= ? and num_age <= ?
		""";
	private static final String SQL_FIND_BIRTHDAYS_BETWEEN = SQL_SELECT_START + 
		"""
			where dt_birthday >= ? and dt_birthday <= ?
		""";

	private PetUpserter() {}

}