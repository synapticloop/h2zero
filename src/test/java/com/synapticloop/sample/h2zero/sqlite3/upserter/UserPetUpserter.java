package com.synapticloop.sample.h2zero.sqlite3.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import com.synapticloop.sample.h2zero.sqlite3.model.UserPet;

public class UserPetUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPetUpserter.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_user_pet, 
				id_user, 
				id_pet
			from 
				user_pet
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_pet = ?";


	private UserPetUpserter() {}

}