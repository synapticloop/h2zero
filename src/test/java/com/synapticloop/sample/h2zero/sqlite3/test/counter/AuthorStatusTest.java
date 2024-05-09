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


import com.synapticloop.sample.h2zero.sqlite3.counter.AuthorStatusCounter;

/**
 * <p><strong>WARNING NOTE:</strong> these tests are designed to test the generated SQL statements
 * <strong>__NOT__<strong> whether the database actually returns sane results.<p>
 */

public class AuthorStatusTest extends DatabaseSetupTest {

	@Test
	public void testAuthorStatuscountAll() throws SQLException {
		AuthorStatusCounter.countAll()
				.execute();
	}

	@Test
	public void testAuthorStatuscountAllSilent() {
		AuthorStatusCounter.countAll()
				.executeSilent();
	}

	@Test
	public void testAuthorStatuscountAllWithConnection() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorStatusCounter.countAll()
					.withConnection(connection)
					.execute();
		}
	}

	@Test
	public void testAuthorStatuscountAllWithConnectionSilent() throws SQLException {
		try (Connection connection = ConnectionManager.getConnection()) {
			AuthorStatusCounter.countAll()
					.withConnection(connection)
					.executeSilent();
		}
	}

	@Test
	public void testAuthorStatuscountAllWithNullConnection() throws SQLException {
		AuthorStatusCounter.countAll()
				.withConnection(null)
				.execute();
	}

	@Test
	public void testAuthorStatuscountAllWithNullConnectionSilent() {
		AuthorStatusCounter.countAll()
				.withConnection(null)
				.executeSilent();
	}

}
