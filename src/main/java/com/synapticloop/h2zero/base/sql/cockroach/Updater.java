package com.synapticloop.h2zero.base.sql.cockroach;

import com.synapticloop.h2zero.base.sql.base.LimitOffsetUpdater;
import com.synapticloop.h2zero.base.sql.mysql.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class Updater extends LimitOffsetUpdater {
	public Updater(Logger logger, String sqlStatement, Function<ResultSet, Integer> resultsFunction, Object... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
