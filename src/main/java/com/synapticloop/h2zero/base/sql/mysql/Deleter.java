package com.synapticloop.h2zero.base.sql.mysql;

import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.LimitOffsetDeleter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Deleter extends LimitOffsetDeleter {
	public Deleter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
