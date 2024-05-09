package com.synapticloop.sample.h2zero.mariadb.test.counter;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                     (java/test/java-counter-test.templar)


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


import com.synapticloop.sample.h2zero.mariadb.counter.PetTypeCounter;

/**
 * <p><strong>WARNING NOTE:</strong> these tests are designed to test the generated SQL statements
 * <strong>__NOT__<strong> whether the database actually returns sane results.<p>
 */

public class PetTypeTest extends DatabaseSetupTest {

	@Test
	public void testPetTypecountAll() throws SQLException {
		PetTypeCounter.countAll()
				.execute();
	}

	@Test
	public void testPetTypecountAllSilent() {
		PetTypeCounter.countAll()
				.executeSilent();
	}

	@Test
	public void testPetTypecountAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeCounter.countAll()
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testPetTypecountAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeCounter.countAll()
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testPetTypecountAllWithNullConnection() throws SQLException {
		PetTypeCounter.countAll()
				.withConnection(null)
				.execute();
	}

	@Test
	public void testPetTypecountAllWithNullConnectionSilent() {
		PetTypeCounter.countAll()
				.withConnection(null)
				.executeSilent();
	}

}
