package com.synapticloop.sample.h2zero.sqlite3.test.counter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/test/java-counter-test.templar)


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.synapticloop.sample.h2zero.sqlite3.ConnectionManagerInitialiserOverride;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.sample.h2zero.sqlite3.test.DatabaseSetupTest;


import java.math.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;


import com.synapticloop.sample.h2zero.sqlite3.counter.UserPetCounter;

/**
 * <p><strong>WARNING NOTE:</strong> these tests are designed to test the generated SQL statements
 * <strong>__NOT__<strong> whether the database actually returns sane results.<p>
 */

public class UserPetTest extends DatabaseSetupTest {

	@Test
	public void testUserPetcountAll() throws SQLException {
		UserPetCounter.countAll()
				.execute();
	}

	@Test
	public void testUserPetcountAllSilent() {
		UserPetCounter.countAll()
				.executeSilent();
	}

	@Test
	public void testUserPetcountAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetCounter.countAll()
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testUserPetcountAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetCounter.countAll()
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testUserPetcountAllWithNullConnection() throws SQLException {
		UserPetCounter.countAll()
				.withConnection(null)
				.execute();
	}

	@Test
	public void testUserPetcountAllWithNullConnectionSilent() {
		UserPetCounter.countAll()
				.withConnection(null)
				.executeSilent();
	}

}
