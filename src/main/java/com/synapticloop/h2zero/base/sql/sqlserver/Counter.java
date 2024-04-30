package com.synapticloop.h2zero.base.sql.sqlserver;

import com.synapticloop.h2zero.base.manager.sqlserver.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.counter.OffsetFetchCounter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Counter extends OffsetFetchCounter {
	public Counter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
