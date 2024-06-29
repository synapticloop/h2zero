package com.synapticloop.sample.h2zero.sqlite3.test.counter;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                     (java/test/java-counter-test.templar)


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


import com.synapticloop.sample.h2zero.sqlite3.counter.AuthorCounter;

/**
 * <p><strong>WARNING NOTE:</strong> these tests are designed to test the generated SQL statements
 * <strong>__NOT__<strong> whether the database actually returns sane results.<p>
 */

public class AuthorTest extends DatabaseSetupTest {

	@Test
	public void testAuthorcountAll() throws SQLException {
		AuthorCounter.countAll()
				.execute();
	}

	@Test
	public void testAuthorcountAllSilent() {
		AuthorCounter.countAll()
				.executeSilent();
	}

	@Test
	public void testAuthorcountAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countAll()
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testAuthorcountAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countAll()
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testAuthorcountAllWithNullConnection() throws SQLException {
		AuthorCounter.countAll()
				.withConnection(null)
				.execute();
	}

	@Test
	public void testAuthorcountAllWithNullConnectionSilent() {
		AuthorCounter.countAll()
				.withConnection(null)
				.executeSilent();
	}

	@Test
	public void testAuthorcountAllByFlIsUpdatingNumFollowers() throws SQLException {
		AuthorCounter.countAllByFlIsUpdatingNumFollowers(true, 1L)
				.execute();
	}

	@Test
	public void testAuthorcountAllByFlIsUpdatingNumFollowersSilent() {
		AuthorCounter.countAllByFlIsUpdatingNumFollowers(true, 1L)
				.executeSilent();
	}

	@Test
	public void testAuthorcountAllByFlIsUpdatingNumFollowersWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countAllByFlIsUpdatingNumFollowers(true, 1L)
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testAuthorcountAllByFlIsUpdatingNumFollowersWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countAllByFlIsUpdatingNumFollowers(true, 1L)
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testAuthorcountAllByFlIsUpdatingNumFollowersWithNullConnection() throws SQLException {
			AuthorCounter.countAllByFlIsUpdatingNumFollowers(true, 1L)
				.withConnection(null)
				.execute();
	}

	@Test
	public void testAuthorcountAllByFlIsUpdatingNumFollowersWithNullConnectionSilent() {
		AuthorCounter.countAllByFlIsUpdatingNumFollowers(true, 1L)
				.withConnection(null)
				.executeSilent();
	}

	@Test
	public void testAuthorcountAllToBeEvaluated() throws SQLException {
		AuthorCounter.countAllToBeEvaluated(new java.sql.Timestamp(System.currentTimeMillis()))
				.execute();
	}

	@Test
	public void testAuthorcountAllToBeEvaluatedSilent() {
		AuthorCounter.countAllToBeEvaluated(new java.sql.Timestamp(System.currentTimeMillis()))
				.executeSilent();
	}

	@Test
	public void testAuthorcountAllToBeEvaluatedWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countAllToBeEvaluated(new java.sql.Timestamp(System.currentTimeMillis()))
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testAuthorcountAllToBeEvaluatedWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countAllToBeEvaluated(new java.sql.Timestamp(System.currentTimeMillis()))
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testAuthorcountAllToBeEvaluatedWithNullConnection() throws SQLException {
			AuthorCounter.countAllToBeEvaluated(new java.sql.Timestamp(System.currentTimeMillis()))
				.withConnection(null)
				.execute();
	}

	@Test
	public void testAuthorcountAllToBeEvaluatedWithNullConnectionSilent() {
		AuthorCounter.countAllToBeEvaluated(new java.sql.Timestamp(System.currentTimeMillis()))
				.withConnection(null)
				.executeSilent();
	}

	@Test
	public void testAuthorcountByStatus() throws SQLException {
		AuthorCounter.countByStatus(1L)
				.execute();
	}

	@Test
	public void testAuthorcountByStatusSilent() {
		AuthorCounter.countByStatus(1L)
				.executeSilent();
	}

	@Test
	public void testAuthorcountByStatusWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countByStatus(1L)
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testAuthorcountByStatusWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorCounter.countByStatus(1L)
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testAuthorcountByStatusWithNullConnection() throws SQLException {
			AuthorCounter.countByStatus(1L)
				.withConnection(null)
				.execute();
	}

	@Test
	public void testAuthorcountByStatusWithNullConnectionSilent() {
		AuthorCounter.countByStatus(1L)
				.withConnection(null)
				.executeSilent();
	}

}