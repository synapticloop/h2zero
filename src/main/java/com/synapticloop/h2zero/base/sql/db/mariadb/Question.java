package com.synapticloop.h2zero.base.sql.db.mariadb;

/*
 * Copyright (c) 2024 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.base.sql.base.question.BaseQuestionExecutor;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Question extends BaseQuestionExecutor {
	public Question(Logger logger, String sqlStatement,Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected String getLimitedResultsStatement() throws SQLException {
		return("");
	}

	@Override protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}

	public Question withConnection(Connection connection) {
		this.connection = connection;
		return(this);
	}

	public Boolean execute() throws SQLException {
		return(executeInternal());
	}

	public Boolean executeSilent() {
		return(executeSilentInternal());
	}

}
