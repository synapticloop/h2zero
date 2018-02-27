package synapticloop.h2zero.base.model.sqlite3;

import java.sql.Connection;
import java.sql.SQLException;

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;

public abstract class ModelBase extends synapticloop.h2zero.base.model.ModelBase {

	@Override
	protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
	
}
