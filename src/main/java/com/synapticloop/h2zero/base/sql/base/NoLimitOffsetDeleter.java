package com.synapticloop.h2zero.base.sql.base;

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

import com.synapticloop.h2zero.base.sql.BaseIntegerNoLimitOffsetExecutor;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This deleter is used for those databases which do not allow offset/limits
 * in the query. (SQLite3 is an example of this type - unless it has been a
 * pre-compiled binary with the <code>#define SQLITE_ENABLE_UPDATE_DELETE_LIMIT</code>
 * flag set).
 */
public abstract class NoLimitOffsetDeleter extends BaseIntegerNoLimitOffsetExecutor {
	public NoLimitOffsetDeleter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	@Override protected String getLimitedResultsStatement() {
		return(super.getLimitOffsetStatement());
	}

	@Override protected abstract Connection getConnection() throws SQLException;
}
