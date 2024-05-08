package com.synapticloop.sample.h2zero.mariadb.updater;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//          (/java/updater/java-create-updater.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;

import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.base.sql.limitoffset.Updater;
import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PetTypeUpdater {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(PetTypeUpdater.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_UPDATE_START = "update pet_type ";

	// static fields generated from the user input

	private PetTypeUpdater() {}

}