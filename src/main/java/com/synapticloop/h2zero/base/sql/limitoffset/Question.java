package com.synapticloop.h2zero.base.sql.limitoffset;

import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.question.LimitOffsetQuestion;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Question extends LimitOffsetQuestion {
	public Question(Logger logger, String sqlStatement,Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
}
