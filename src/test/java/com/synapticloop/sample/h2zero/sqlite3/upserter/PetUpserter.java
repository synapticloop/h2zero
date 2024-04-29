package com.synapticloop.sample.h2zero.sqlite3.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

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
				dt_birthday
			from 
				pet
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_pet = ?";

	private static final String SQL_FIND_BY_NM_PET_NUM_AGE = SQL_SELECT_START + 
		"""
			where nm_pet = ? and num_age = ?
		""";

	private PetUpserter() {}

}