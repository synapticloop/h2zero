package com.synapticloop.sample.h2zero.mariadb.test.counter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/test/java-counter-test.templar)


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.synapticloop.sample.h2zero.mariadb.ConnectionManagerInitialiserOverride;
import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.sample.h2zero.mariadb.test.DatabaseSetupTest;


import java.math.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;


import com.synapticloop.sample.h2zero.mariadb.counter.PetCounter;

/**
 * <p><strong>WARNING NOTE:</strong> these tests are designed to test the generated SQL statements
 * <strong>__NOT__<strong> whether the database actually returns sane results.<p>
 */

public class PetTest extends DatabaseSetupTest {

	@Test
	public void testPetcountAll() throws SQLException {
		PetCounter.countAll()
				.execute();
	}

	@Test
	public void testPetcountAllSilent() {
		PetCounter.countAll()
				.executeSilent();
	}

	@Test
	public void testPetcountAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetCounter.countAll()
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testPetcountAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetCounter.countAll()
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testPetcountAllWithNullConnection() throws SQLException {
		PetCounter.countAll()
				.withConnection(null)
				.execute();
	}

	@Test
	public void testPetcountAllWithNullConnectionSilent() {
		PetCounter.countAll()
				.withConnection(null)
				.executeSilent();
	}

}
