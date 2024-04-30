package com.synapticloop.h2zero.base.sql.mysql;

import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.counter.LimitOffsetCounter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Counter extends LimitOffsetCounter {
	public Counter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
