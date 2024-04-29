package com.synapticloop.h2zero.base.sql.mysql;

import com.synapticloop.h2zero.base.sql.base.LimitOffsetQuestion;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class Question extends LimitOffsetQuestion {
	public Question(Logger logger, String sqlStatement, Function<ResultSet, Boolean> resultsFunction, Object... parameters) {
		super(logger, sqlStatement, resultsFunction, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
