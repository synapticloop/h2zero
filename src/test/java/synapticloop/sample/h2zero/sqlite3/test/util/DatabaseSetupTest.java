package synapticloop.sample.h2zero.sqlite3.test.util;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//          (tests/java-sqlite3-database-test-base.templar)


import static org.junit.Assert.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.base.manager.BaseConnectionManager;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetupTest extends BaseConnectionManager {
	private static final String DB_DRIVER_CLASS = "org.sqlite.JDBC";

	@BeforeClass
	public static void setup() {
		try {
			BaseConnectionManager.comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass(DB_DRIVER_CLASS);
		} catch (PropertyVetoException e) { // runtime exception
			throw new RuntimeException(e);
		}

		comboPooledDataSource.setJdbcUrl("jdbc:sqlite:/C:\Users\strata\IdeaProjects\h2zero\./src/test/resources//test.db");
		createDatabase();
	}

	@AfterClass
	public static void teardown() {
		if(null != comboPooledDataSource) {
			comboPooledDataSource.close();
		}
		File dbFile = new File("./src/test/resources//test.db");
		dbFile.delete();
	}

	private static void createDatabase() {
		PreparedStatement preparedStatement = null;

		try (
				Connection connection = ConnectionManager.getConnection();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(
								DatabaseSetupTest.class.getResourceAsStream("/create-database-sqlite3.sql")));
		) {
			String line = null;
			StringBuilder query = new StringBuilder();

			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("--") && !line.trim().isEmpty()) {
					query.append(line);
				} else {
					continue;
				}

				if (line.trim().endsWith(";")) {
					if (line.trim().isEmpty()) {
						continue;
					}
					preparedStatement = connection.prepareStatement(query.toString());
					preparedStatement.execute();
					preparedStatement.close();
					query.setLength(0);
				}
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeAll(preparedStatement);
		}
	}}
