package com.synapticloop.h2zero.base.sql.sqlite3;

import com.synapticloop.h2zero.base.sql.base.updater.LimitOffsetUpdater;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Updater extends LimitOffsetUpdater {
	public Updater(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
