package synapticloop.h2zero.revenge.model;

import java.sql.Connection;
import java.sql.SQLException;

public class View extends Table {

	public View(Connection connection, String database, String name) throws SQLException {
		super(connection, database, name);
	}
}
