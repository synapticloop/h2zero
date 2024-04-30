package com.synapticloop.h2zero.base.sql.offsetfetch;

import com.synapticloop.h2zero.base.manager.sqlserver.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.deleter.OffsetFetchDeleter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Deleter extends OffsetFetchDeleter {
	public Deleter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
