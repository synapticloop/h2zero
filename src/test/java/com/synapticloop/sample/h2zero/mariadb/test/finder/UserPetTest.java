package com.synapticloop.sample.h2zero.mariadb.test.finder;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//            (java/test/java-finder-test.templar)


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.synapticloop.sample.h2zero.mariadb.ConnectionManagerInitialiserOverride;
import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.sample.h2zero.mariadb.test.DatabaseSetupTest;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;


import com.synapticloop.sample.h2zero.mariadb.finder.UserPetFinder;

/**
 * WARNING NOTE: these tests are designed to test the generated SQL statements
 * __NOT__ whether the database actually returns sane results.
 * 
 * These will also test to ensure that connections are setting closed properly
 * so that there are no resource leakages.
 */

public class UserPetTest extends DatabaseSetupTest {

	@Test
	public void testUserPetFindByPrimaryKey() throws SQLException {
		UserPetFinder.findByPrimaryKey(1L)
				.execute();

	}

	@Test
	public void testUserPetFindByPrimaryKeySilent() throws SQLException {
		Assert.assertNull(UserPetFinder.findByPrimaryKey(-831486134981L)
				.executeSilent());

	}

	@Test
	public void testUserPetFindByPrimaryKeyWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetFinder.findByPrimaryKey(1L)
				.withConnection(connection)
				.execute();
		}
	}

	@Test
	public void testUserPetFindByPrimaryKeyWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertNull(UserPetFinder.findByPrimaryKey(-831486134981L)
				.withConnection(connection)
				.executeSilent());
		}
	}

	@Test
	public void testUserPetFindAll() throws SQLException {
		Assert.assertEquals(0, UserPetFinder.findAll()
				.execute()
				.size());

	}

	@Test
	public void testUserPetFindAllLimitOffset() throws SQLException {
		Assert.assertEquals(0, UserPetFinder.findAll()
			.withLimit(1)
			.withOffset(0)
			.execute()
			.size());
	}

	@Test
	public void testUserPetFindAllSilent() throws SQLException {
		Assert.assertEquals(0, UserPetFinder.findAll()
				.executeSilent()
				.size());

	}

	@Test
	public void testUserPetFindAllLimitOffsetSilent() throws SQLException {
		Assert.assertEquals(0, UserPetFinder.findAll()
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
	}

	@Test
	public void testUserPetFindAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, UserPetFinder.findAll()
				.withConnection(connection)
				.execute()
				.size());

		}
	}

	@Test
	public void testUserPetFindAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, UserPetFinder.findAll()
				.withConnection(connection)
				.executeSilent()
				.size());
		}
	}

	@Test
	public void testUserPetFindAllWithConnectionLimit() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetFinder.findAll().withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}

	}

	@Test
	public void testUserPetFindAllWithConnectionLimitSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, UserPetFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
		}
	}

	@Test
	public void testUserPetFindAllWithConnectionOffset() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(1)
				.execute();
			Assert.fail("A SQL Exception should have been thrown as there was an offset without a limit set.");		} catch(SQLException ignored) {
		}
	}

	@Test
	public void testUserPetFindAllWithConnectionOffsetSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(0)
				.executeSilent();
		}
	}

	@Test
	public void testUserPetFindAllWithConnectionLimitOffset() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			UserPetFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}
	}

	@Test
	public void testUserPetFindAllWithNullConnectionLimitOffset() throws SQLException {
		UserPetFinder.findAll()
				.withConnection(null)
				.withLimit(1)
				.withOffset(0)
				.execute();

	}

}
