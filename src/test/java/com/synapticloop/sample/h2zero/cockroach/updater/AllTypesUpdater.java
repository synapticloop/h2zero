package com.synapticloop.sample.h2zero.cockroach.updater;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-updater.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;

import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import com.synapticloop.h2zero.base.sql.limitoffset.Updater;
import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AllTypesUpdater {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(AllTypesUpdater.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_UPDATE_START = "update all_types ";

	// static fields generated from the user input

	private AllTypesUpdater() {}

}