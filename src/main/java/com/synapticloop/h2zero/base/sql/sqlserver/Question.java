package com.synapticloop.h2zero.base.sql.sqlserver;

import com.synapticloop.h2zero.base.sql.base.OffsetFetchQuestion;
import com.synapticloop.h2zero.base.manager.sqlserver.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class Question extends OffsetFetchQuestion {
	public Question(Logger logger, String sqlStatement, Function<ResultSet, Boolean> resultsFunction, Object... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
