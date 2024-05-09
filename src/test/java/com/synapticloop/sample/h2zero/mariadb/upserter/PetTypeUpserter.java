package com.synapticloop.sample.h2zero.mariadb.upserter;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//               (/java/upserter/java-create-upserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;

import com.synapticloop.sample.h2zero.mariadb.model.PetType;

public class PetTypeUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeUpserter.class);
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


	private PetTypeUpserter() {}

	public static boolean upsertByNmPetType(List<Integer> numAgeList) {
		return(false);
	}

}