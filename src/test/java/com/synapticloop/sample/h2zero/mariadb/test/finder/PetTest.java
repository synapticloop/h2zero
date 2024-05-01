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
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		// this shouldn't find anything - and we don't care - only about SQLExceptions
		PetFinder.findByPrimaryKey(1L)
				.execute();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindByPrimaryKeySilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		Assert.assertNull(PetFinder.findByPrimaryKey(-831486134981L)
				.executeSilent());

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindByPrimaryKeyWithConnection() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findByPrimaryKey(1L)
				.withConnection(connection)
				.execute();

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindByPrimaryKeyWithConnectionSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertNull(PetFinder.findByPrimaryKey(-831486134981L)
				.withConnection(connection)
				.executeSilent());

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAll() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		Assert.assertEquals(0, PetFinder.findAll()
				.execute()
				.size());

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllLimitOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		Assert.assertEquals(0, PetFinder.findAll()
			.withLimit(1)
			.withOffset(0)
			.execute()
			.size());
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		Assert.assertEquals(0, PetFinder.findAll()
				.executeSilent()
				.size());

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllLimitOffsetSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		Assert.assertEquals(0, PetFinder.findAll()
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnection() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetFinder.findAll()
				.withConnection(connection)
				.execute()
				.size());

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnectionSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetFinder.findAll()
				.withConnection(connection)
				.executeSilent()
				.size());

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnectionLimit() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll().withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnectionLimitSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			Assert.assertEquals(0, PetFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.executeSilent()
				.size());
		}

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnectionOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(1)
				.execute();
			Assert.fail("A SQL Exception should have been thrown as there was an offset without a limit set.");		} catch(SQLException ignored) {
		}		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnectionOffsetSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll()
				.withConnection(connection)
				.withLimit(null)
				.withOffset(0)
				.executeSilent();

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithConnectionLimitOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findAll()
				.withConnection(connection)
				.withLimit(1)
				.withOffset(0)
				.execute();
		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetFindAllWithNullConnectionLimitOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findAll()
				.withConnection(null)
				.withLimit(1)
				.withOffset(0)
				.execute();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAge() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1).execute();
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1).executeSilent();
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeLimitOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withLimit(0)
				.withOffset(0)
				.execute();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeLimitOffsetSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withLimit(0)
				.withOffset(0)
				.executeSilent();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithConnection() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(connection)
				.execute();

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithConnectionSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		try (Connection connection = ConnectionManager.getConnection()) {
			PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(connection)
				.executeSilent();

		}
		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnection() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
			.withConnection(null)
			.execute();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(null)
				.executeSilent();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionLimitOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
			.withConnection(null)
			.withLimit(0)
			.withOffset(0)
			.execute();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionLimitOffsetSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(null)
				.withLimit(0)
				.withOffset(0)
				.executeSilent();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionNullLimitNullOffset() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
			.withConnection(null)
			.withLimit(null)
			.withOffset(null)
			.execute();

		Assert.assertEquals(numConnections, ConnectionManager.getComboPooledDataSource().getNumConnections());
		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

	@Test
	public void testPetfindByNmPetNumAgeWithNullConnectionNullLimitNullOffsetSilent() throws SQLException {
		int numConnections = ConnectionManager.getComboPooledDataSource().getNumConnections();
		int numBusyConnections = ConnectionManager.getComboPooledDataSource().getNumBusyConnections();
		PetFinder.findByNmPetNumAge("varchar", 1)
				.withConnection(null)
				.withLimit(null)
				.withOffset(null)
				.executeSilent();

		// need to sleep to ensure that the connection has time to close - hacky... :(
		try { Thread.sleep(1); } catch (InterruptedException e) { /* do nothing */ }
		Assert.assertEquals(numBusyConnections, ConnectionManager.getComboPooledDataSource().getNumBusyConnections());
	}

}
