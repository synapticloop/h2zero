package com.synapticloop.h2zero.base.sql;

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

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseSQLNoLimitOrOffsetExecutor extends BaseSQLExecutor {

	public BaseSQLNoLimitOrOffsetExecutor(Logger logger, String sqlStatement, Object ... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * Get a connection from the connection manager.
	 *
	 * @return The connection from the correct connection manager
	 */
	protected abstract Connection getConnection() throws SQLException;
}
