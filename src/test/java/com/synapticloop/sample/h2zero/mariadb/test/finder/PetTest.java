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


import com.synapticloop.sample.h2zero.mariadb.finder.PetFinder;

/**
 * WARNING NOTE: these tests are designed to test the generated SQL statements
 * __NOT__ whether the database actually returns sane results.
 * 
 * These will also test to ensure that connections are setting closed properly
 * so that there are no resource leakages.
 */

public class PetTest extends DatabaseSetupTest {

	@Test
	public void testPetFindByPrimaryKey() throws SQLException {
		PetFinder.findByPrimaryKey(1L)
				.execute();

	}

	@Test
	public void testPetFindByPrimaryKeySilent() throws SQLException {
		Assert.assertNull(PetFinder.findByPrimaryKey(-831486134981L)
				.executeSilent());

	}

	@Test
	public void testPetFindByPrimaryKeyWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findByPrimaryKey(1L)
				.withConnection(connection)
				.execute();
		}
	}

	@Test
	public void testPetFindByPrimaryKeyWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertNull(PetFinder.findByPrimaryKey(-831486134981L)
				.withConnection(connection)
				.executeSilent());
		}
	}

	@Test
	public void testPetFindAll() throws SQLException {
		Assert.assertEquals(0, PetFinder.findAll()
				.execute()
				.size());

	}

	@Test
	public void testPetFindAllLimitOffset() throws SQLException {
		Assert.assertEquals(0, PetFinder.findAll()
			.withLimit(1)
			.withOffset(0)
			.execute()
			.size());
	}

	@Test
	public void testPetFindAllSilent() throws SQLException {
		Assert.assertEquals(0, PetFinder.findAll()
				.executeSilent()
				.size());

	}

	@Test
	public void testPetFindAllLimitOffsetSilent() throws SQLException {
		Assert.assertEquals(0, PetFinder.findAll()
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
	}

	@Test
	public void testPetFindAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetFinder.findAll()
				.withConnection(connection)
				.execute()
				.size());

		}
	}

	@Test
	public void testPetFindAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetFinder.findAll()
				.withConnection(connection)
				.executeSilent()
				.size());
		}
	}

	@Test
	public void testPetFindAllWithConnectionLimit() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll().withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}

	}

	@Test
	public void testPetFindAllWithConnectionLimitSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
		}
	}

	@Test
	public void testPetFindAllWithConnectionOffset() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(1)
				.execute();
			Assert.fail("A SQL Exception should have been thrown as there was an offset without a limit set.");		} catch(SQLException ignored) {
		}
	}

	@Test
	public void testPetFindAllWithConnectionOffsetSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(0)
				.executeSilent();
		}
	}

	@Test
	public void testPetFindAllWithConnectionLimitOffset() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}
	}

	@Test
	public void testPetFindAllWithNullConnectionLimitOffset() throws SQLException {
		PetFinder.findAll()
				.withConnection(null)
				.withLimit(1)
				.withOffset(0)
				.execute();

	}

	@Test
	public void testPetfindByNmPetNumAge() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1).execute();
	}

	@Test
	public void testPetfindByNmPetNumAgeSilent() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1).executeSilent();
	}

	@Test
	public void testPetfindByNmPetNumAgeLimitOffset() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withLimit(0)
				.withOffset(0)
				.execute();
	}

	@Test
	public void testPetfindByNmPetNumAgeLimitOffsetSilent() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withLimit(0)
				.withOffset(0)
				.executeSilent();

	}

	@Test
	public void testPetfindByNmPetNumAgeWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(connection)
				.execute();
		}
	}

	@Test
	public void testPetfindByNmPetNumAgeWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(connection)
				.executeSilent();
		}
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnection() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
			.withConnection(null)
			.execute();
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionSilent() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(null)
				.executeSilent();
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionLimitOffset() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
			.withConnection(null)
			.withLimit(0)
			.withOffset(0)
			.execute();
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionLimitOffsetSilent() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(null)
				.withLimit(0)
				.withOffset(0)
				.executeSilent();
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionNullLimitNullOffset() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
			.withConnection(null)
			.withLimit(null)
			.withOffset(null)
			.execute();
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionNullLimitNullOffsetSilent() throws SQLException {
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(null)
				.withLimit(null)
				.withOffset(null)
				.executeSilent();
	}

}
