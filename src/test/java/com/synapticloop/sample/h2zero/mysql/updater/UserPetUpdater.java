package com.synapticloop.sample.h2zero.mysql.updater;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-updater.templar)

import com.synapticloop.sample.h2zero.mysql.model.util.Constants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserPetUpdater {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPetUpdater.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_UPDATE_START = "update user_pet ";

	// static fields generated from the user input

	private UserPetUpdater() {}

}