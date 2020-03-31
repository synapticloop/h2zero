package synapticloop.h2zero.base.model.sqlite3;

/*
 * Copyright (c) 2018 - 2020 synapticloop.
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

import java.sql.Connection;
import java.sql.SQLException;

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;

public abstract class ModelBase extends synapticloop.h2zero.base.model.ModelBase {

	@Override
	protected Connection getConnection() throws SQLException {
		return(ConnectionManager.getConnection());
	}
	
}
