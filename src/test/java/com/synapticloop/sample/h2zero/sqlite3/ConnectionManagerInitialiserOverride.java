package com.synapticloop.sample.h2zero.sqlite3;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//  (java-create-connection-manager-initialise-override.templar)


import com.synapticloop.sample.h2zero.sqlite3.ConnectionManagerInitialiser;

/**
 * <p>
 * This file is used to override the default initialisation of the connection
 * pool, so that you can initialise your connection by your own methods.
 * </p>
 *
 * <p>
 * Example, commented-out code is included within the method.
 * </p>
 *
 * <p>
 * To initialise the combo pool for use, call <br />
 * <code>ConnectionManagerInitialiserOverride.initialise()</code><br />
 * __ONCE__ upon initialisation of your application.
 * </p>
 *
 *
 * <p>
 * With multiple connection pools to multiple databases (rarely used, but
 * necessary in some instances) use the protected static field
 * <code>CONNECTION_POOL_NAME</code> inherited from the parent class.
 * </p>
 *
 * <p>
 * {@link  com.synapticloop.sample.h2zero.sqlite3.ConnectionManagerInitialiser#CONNECTION_POOL_NAME  com.synapticloop.sample.h2zero.sqlite3.ConnectionManagerInitialiser#CONNECTION_POOL_NAME}
 * </p>
 *
 * <pre>
 *
 * NOTE: ONCE GENERATED - THIS FILE WILL __NEVER__ BE OVER-WRITTEN BY AN h2zero
 *       RE-GENERATION.
 *
 *       THIS __WILL__ BE A PROBLEM IF h2zero UPDATES THE TEMPLATE IN FUTURE
 *       VERSIONS...
 *
 *       JUST SAYING...
 * </pre>
 */
public class ConnectionManagerInitialiserOverride extends ConnectionManagerInitialiser {

	public static void initialise() {
//		// create a new combo pool
//		ComboPooledDataSource myComboPooledDataSource = new ComboPooledDataSource();
//		// configure the combopool
//		try {
//			myComboPooledDataSource.setDriverClass("db.driver.class");
//		} catch (PropertyVetoException e) { // runtime exception
//			throw new RuntimeException(e);
//		}
//
//		try {
//			myComboPooledDataSource.setLoginTimeout(1);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//
//		myComboPooledDataSource.setAcquireIncrement(1);
//
//		myComboPooledDataSource.setJdbcUrl("jdbc://");
//		myComboPooledDataSource.setUser("username");
//		myComboPooledDataSource.setPassword("password");
//
//		addComboPool(CONNECTION_POOL_NAME, myComboPooledDataSource);
	}
}
