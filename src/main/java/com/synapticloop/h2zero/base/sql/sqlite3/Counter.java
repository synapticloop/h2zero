package com.synapticloop.h2zero.base.sql.sqlite3;

import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
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
