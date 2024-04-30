package com.synapticloop.h2zero.base.sql.base.deleter;

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

import com.synapticloop.h2zero.base.sql.base.BaseDeleterUpdaterExecutor;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class LimitOffsetDeleter extends BaseDeleterUpdaterExecutor {
	public LimitOffsetDeleter(Logger logger, String sqlStatement, Object... parameters) {
		super(logger, sqlStatement, parameters);
	}

	/**
	 * Set the offset of the results.
	 *
	 * @param offset the offset to start with the results
	 *
	 * @return the MultiFinder with the offset set
	 */
	public BaseDeleterUpdaterExecutor withOffset(Integer offset) {
		this.offset = offset;
		return(this);
	}

	/**
	 * Set the limit of the results
	 *
	 * @param limit the limit of the results
	 *
	 * @return the MultiFinder with the limit set
	 */
	public BaseDeleterUpdaterExecutor withLimit(Integer limit) {
		this.limit = limit;
		return(this);
	}

	@Override protected String getLimitedResultsStatement() throws SQLException {
		return(super.getLimitOffsetStatement());
	}

	@Override protected abstract Connection getConnection() throws SQLException;
}
