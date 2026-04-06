package com.synapticloop.h2zero.base.sql.db.mariadb;

import com.synapticloop.h2zero.base.manager.mariadb.C3P0ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.deleter.LimitOffsetDeleter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Deleter extends LimitOffsetDeleter {
	public Deleter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(C3P0ConnectionManager.getConnection());
	}
}
