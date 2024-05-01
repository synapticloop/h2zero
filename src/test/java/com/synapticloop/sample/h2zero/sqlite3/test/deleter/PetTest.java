package com.synapticloop.sample.h2zero.sqlite3.test.deleter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/test/java-deleter-test.templar)


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.synapticloop.sample.h2zero.sqlite3.ConnectionManagerInitialiserOverride;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.sample.h2zero.sqlite3.test.DatabaseSetupTest;


import java.math.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.synapticloop.sample.h2zero.sqlite3.deleter.PetDeleter;

/**
 * WARNING NOTE: these tests are designed to test the generated SQL statements
 * __NOT__ whether the database actually returns sane results.
 */

public class PetTest extends DatabaseSetupTest {

	@Test
	public void testPetDeleteByPrimaryKey() throws SQLException {
		Assert.assertEquals(0, PetDeleter.deleteByPrimaryKey(1L).execute());
	}

	@Test
	public void testPetDeleteByPrimaryKeySilent() throws SQLException {
		Assert.assertEquals(0, PetDeleter.deleteByPrimaryKey(1L).executeSilent());
	}

	@Test
	public void testPetDeleteByPrimaryKeyWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetDeleter.deleteByPrimaryKey(1L).withConnection(connection).execute());
		}
	}

	@Test
	public void testPetDeleteByPrimaryKeyWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetDeleter.deleteByPrimaryKey(1L).withConnection(connection).executeSilent());
		}
	}

	@Test
	public void testPetDeleteAll() throws SQLException {
		Assert.assertEquals(0, PetDeleter.deleteAll().execute());
	}

	@Test
	public void testPetDeleteAllSilent() throws SQLException {
		Assert.assertEquals(0, PetDeleter.deleteAll().executeSilent());
	}

	@Test
	public void testPetDeleteAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetDeleter.deleteAll().withConnection(connection).execute());
		}
	}

	@Test
	public void testPetDeleteAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetDeleter.deleteAll().withConnection(connection).executeSilent());
		}
	}

}
