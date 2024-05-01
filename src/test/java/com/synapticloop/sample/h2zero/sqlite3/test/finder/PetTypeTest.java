package com.synapticloop.sample.h2zero.sqlite3.test.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/test/java-finder-test.templar)


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


import com.synapticloop.sample.h2zero.sqlite3.finder.PetTypeFinder;

/**
 * WARNING NOTE: these tests are designed to test the generated SQL statements
 * __NOT__ whether the database actually returns sane results.
 * 
 * These will also test to ensure that connections are setting closed properly
 * so that there are no resource leakages.
 */

public class PetTypeTest extends DatabaseSetupTest {

	@Test
	public void testPetTypeFindByPrimaryKey() throws SQLException {
		PetTypeFinder.findByPrimaryKey(1L)
				.execute();

	}

	@Test
	public void testPetTypeFindByPrimaryKeySilent() throws SQLException {
		Assert.assertNull(PetTypeFinder.findByPrimaryKey(-831486134981L)
				.executeSilent());

	}

	@Test
	public void testPetTypeFindByPrimaryKeyWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeFinder.findByPrimaryKey(1L)
				.withConnection(connection)
				.execute();
		}
	}

	@Test
	public void testPetTypeFindByPrimaryKeyWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertNull(PetTypeFinder.findByPrimaryKey(-831486134981L)
				.withConnection(connection)
				.executeSilent());
		}
	}

	@Test
	public void testPetTypeFindAll() throws SQLException {
		Assert.assertEquals(0, PetTypeFinder.findAll()
				.execute()
				.size());

	}

	@Test
	public void testPetTypeFindAllLimitOffset() throws SQLException {
		Assert.assertEquals(0, PetTypeFinder.findAll()
			.withLimit(1)
			.withOffset(0)
			.execute()
			.size());
	}

	@Test
	public void testPetTypeFindAllSilent() throws SQLException {
		Assert.assertEquals(0, PetTypeFinder.findAll()
				.executeSilent()
				.size());

	}

	@Test
	public void testPetTypeFindAllLimitOffsetSilent() throws SQLException {
		Assert.assertEquals(0, PetTypeFinder.findAll()
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
	}

	@Test
	public void testPetTypeFindAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetTypeFinder.findAll()
				.withConnection(connection)
				.execute()
				.size());

		}
	}

	@Test
	public void testPetTypeFindAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetTypeFinder.findAll()
				.withConnection(connection)
				.executeSilent()
				.size());
		}
	}

	@Test
	public void testPetTypeFindAllWithConnectionLimit() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeFinder.findAll().withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}

	}

	@Test
	public void testPetTypeFindAllWithConnectionLimitSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetTypeFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
		}
	}

	@Test
	public void testPetTypeFindAllWithConnectionOffset() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(1)
				.execute();
			Assert.fail("A SQL Exception should have been thrown as there was an offset without a limit set.");		} catch(SQLException ignored) {
		}
	}

	@Test
	public void testPetTypeFindAllWithConnectionOffsetSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(0)
				.executeSilent();
		}
	}

	@Test
	public void testPetTypeFindAllWithConnectionLimitOffset() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetTypeFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}
	}

	@Test
	public void testPetTypeFindAllWithNullConnectionLimitOffset() throws SQLException {
		PetTypeFinder.findAll()
				.withConnection(null)
				.withLimit(1)
				.withOffset(0)
				.execute();

	}

}
