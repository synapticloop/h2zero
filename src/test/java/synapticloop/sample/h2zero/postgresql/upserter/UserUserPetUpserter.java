package synapticloop.sample.h2zero.postgresql.upserter;

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

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.postgresql.model.util.Constants;

import synapticloop.sample.h2zero.postgresql.model.UserUserPet;

public class UserUserPetUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserUserPetUpserter.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_user_user_pet, 
				id_user_user, 
				id_pet
			from 
				user_user_pet
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_user_user_pet = ?";


	private UserUserPetUpserter() {}

}