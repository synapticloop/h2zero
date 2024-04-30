package com.synapticloop.h2zero.base.sql.sqlserver;

import com.synapticloop.h2zero.base.manager.sqlserver.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.question.OffsetFetchQuestion;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Question extends OffsetFetchQuestion {
	public Question(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
