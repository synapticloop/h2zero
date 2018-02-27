package synapticloop.h2zero.base.model.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;

public abstract class ModelBase extends synapticloop.h2zero.base.model.ModelBase {

	@Override
	protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}