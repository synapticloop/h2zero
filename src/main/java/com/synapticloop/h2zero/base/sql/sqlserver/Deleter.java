package com.synapticloop.h2zero.base.sql.sqlserver;

import com.synapticloop.h2zero.base.sql.base.OffsetFetchDeleter;
import com.synapticloop.h2zero.base.sql.mysql.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class Deleter extends OffsetFetchDeleter {
	public Deleter(Logger logger, String sqlStatement, Function<ResultSet, Integer> resultsFunction, Object... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}