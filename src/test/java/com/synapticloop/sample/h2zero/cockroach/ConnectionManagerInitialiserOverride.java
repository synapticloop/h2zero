package com.synapticloop.sample.h2zero.cockroach;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//   (/java/util/java-create-connection-manager-initialise-override.templar)


import com.synapticloop.sample.h2zero.cockroach.ConnectionManagerInitialiser;
import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


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
 * {@link  com.synapticloop.sample.h2zero.cockroach.ConnectionManagerInitialiser#CONNECTION_POOL_NAME  com.synapticloop.sample.h2zero.cockroach.ConnectionManagerInitialiser#CONNECTION_POOL_NAME}
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
	private static boolean hasCreatedDatabase = false;
	private static Properties properties;

	private static void initialisePropertiesFile() throws SQLException {
		properties = new Properties();
		try {
			// !!! NOTE !!!
			// If you are loading the properties file from the file system - you will need
			// to ensure that this file exists
			properties.load(ConnectionManagerInitialiserOverride.class.getResourceAsStream("/application.cockroach.sample.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load the properties file '/application.cockroach.sample.properties' there shall be no SQL for you.", e);
		}
	}

	public static void initialiseFromProperties() throws SQLException {

		if(null == properties) {
			initialisePropertiesFile();
		}

		ComboPooledDataSource myComboPooledDataSource = new ComboPooledDataSource();
		try {
			myComboPooledDataSource.setDriverClass("org.postgresql.Driver");
		} catch (PropertyVetoException e) { // runtime exception
			throw new RuntimeException(e);
		}

		myComboPooledDataSource.setJdbcUrl(properties.getProperty("c3p0.jdbcUrl"));
		myComboPooledDataSource.setUser(properties.getProperty("c3p0.user"));
		myComboPooledDataSource.setPassword(properties.getProperty("c3p0.password"));

		myComboPooledDataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("c3p0.maxPoolSize")));
		myComboPooledDataSource.setMaxStatements(Integer.parseInt(properties.getProperty("c3p0.maxStatements")));
		myComboPooledDataSource.setMaxStatementsPerConnection(Integer.parseInt(properties.getProperty("c3p0.maxStatementsPerConnection")));

		myComboPooledDataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("c3p0.minPoolSize")));

		myComboPooledDataSource.setAcquireIncrement(Integer.parseInt(properties.getProperty("c3p0.acquireIncrement")));
		myComboPooledDataSource.setInitialPoolSize(Integer.parseInt(properties.getProperty("c3p0.initialPoolSize")));
		myComboPooledDataSource.setAcquireRetryAttempts(Integer.parseInt(properties.getProperty("c3p0.acquireRetryAttempts")));
		myComboPooledDataSource.setDebugUnreturnedConnectionStackTraces(Boolean.parseBoolean(properties.getProperty("c3p0.debugUnreturnedConnectionStackTraces")));

		addComboPool(CONNECTION_POOL_NAME, myComboPooledDataSource);
	}

	public static void initialise() throws SQLException {
//		// create a new combo pool
//		ComboPooledDataSource myComboPooledDataSource = new ComboPooledDataSource();
//		// configure the combopool
//		try {
//			myComboPooledDataSource.setDriverClass("org.postgresql.Driver");
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


	/**
	 * Creating the database requires a separate JDBC URL if the database has
	 * never been setup before
	 *
	 * @throws SQLException If there was an error connecting to the database
	 *   or executing the SQL creation statements.
	 */
	public static void createDatabase() throws SQLException {
		if(null == properties) {
			initialiseFromProperties();
		}

		if(hasCreatedDatabase) {
			return;
		}

		PreparedStatement preparedStatement = null;

		try (
				Connection connection = DriverManager.getConnection(
						properties.getProperty("db.initial.jdbcUrl"),
						properties.getProperty("db.initial.user"),
						properties.getProperty("db.initial.password"));

				InputStreamReader inputStreamReader = new InputStreamReader(ConnectionManagerInitialiser.class.getResourceAsStream("/create-database-cockroach-sample.sql"));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		) {
			String line = null;
			StringBuilder query = new StringBuilder();

			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("--") && !line.trim().isEmpty()) {
					query.append(line);
					query.append(" ");
				} else {
					continue;
				}

				if (line.trim().endsWith(";")) {
					preparedStatement = connection.prepareStatement(query.toString());
					preparedStatement.execute();
					preparedStatement.close();
					query.setLength(0);
				}
			}

		} catch (IOException e) {
			throw new SQLException("Could not load the /create-database-cockroach-sample.sql file, original message was: " + e.getMessage(), e);
		} catch (SQLException e) {
			throw(e);
		} finally {
			ConnectionManager.closeAll(preparedStatement);
		}
		hasCreatedDatabase = true;
	}
}
