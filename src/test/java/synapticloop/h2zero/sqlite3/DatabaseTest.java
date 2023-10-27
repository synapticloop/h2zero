package synapticloop.h2zero.sqlite3;

import static org.junit.Assert.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.base.manager.BaseConnectionManager;
import synapticloop.sample.h2zero.sqlite3.deleter.AuthorDeleter;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTest extends BaseConnectionManager {
	private static final String DB_DRIVER_CLASS = "org.sqlite.JDBC";
	private static final ComboPooledDataSource comboPooledDataSource = BaseConnectionManager.comboPooledDataSource;

	@Before
	public void setup() {
// setup the database
		try {
			comboPooledDataSource.setDriverClass(DB_DRIVER_CLASS);
		} catch (PropertyVetoException e) { // runtime exception
			throw new RuntimeException(e);
		}
// now we need to check for backups and whatnot

		comboPooledDataSource.setJdbcUrl("jdbc:sqlite:/test.db");
	}

	@After
	public void teardown() {
		AuthorDeleter.deleteAllSilent();
	}

	@Test
	public void createDatabase() {
		try {
			Connection connection = ConnectionManager.getConnection();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							DatabaseTest.class.getResourceAsStream("/create-database-sqlite3.sql")));
			String line = null;
			StringBuilder query = new StringBuilder();

			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("--") && !line.trim().isEmpty()) {
					query.append(line);
				} else {
					continue;
				}

				if (line.trim().endsWith(";")) {
// execute the query and
					if (line.trim().isEmpty()) {
// we don't want to run an empty query
						continue;
					}
					PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
					preparedStatement.execute();
					preparedStatement.close();
					query.setLength(0);
				}
			}

		} catch (IOException | SQLException e) {
// TODO - this is going to be a problem
			e.printStackTrace();
		}
	}
}
