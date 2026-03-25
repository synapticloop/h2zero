package com.synapticloop.h2zero.generator.validator.database;

/*
 * Copyright (c) 2013-2026 synapticloop.
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

import com.synapticloop.h2zero.generator.annotation.H2ZeroValidator;
import com.synapticloop.h2zero.generator.model.Database;
import com.synapticloop.h2zero.generator.model.Options;
import com.synapticloop.h2zero.generator.util.SimpleLogger;
import com.synapticloop.h2zero.generator.validator.BaseValidator;

import java.util.*;

@H2ZeroValidator
public class DatabaseSchemaNameValidator extends BaseValidator {
	private static final Set<String> SYSTEM_SCHEMA_NAMES = new HashSet<>();
	private static final Map<String, String> SYSTEM_SCHEMA_NAME_PREFIXES = new HashMap<>();

	static {
		// mysql/mariadb (and sometimes some others)
		SYSTEM_SCHEMA_NAMES.add("mysql");
		SYSTEM_SCHEMA_NAMES.add("information_schema");
		SYSTEM_SCHEMA_NAMES.add("performance_schema");
		SYSTEM_SCHEMA_NAMES.add("sys");
		SYSTEM_SCHEMA_NAMES.add("ndbinfo");

		// not yet added, but possible prudent
		SYSTEM_SCHEMA_NAMES.add("mariadb");
		SYSTEM_SCHEMA_NAMES.add("maria");

		// postgresql and some cockroachdb
		SYSTEM_SCHEMA_NAMES.add("pg_catalog");
		SYSTEM_SCHEMA_NAMES.add("pg_toast");
		SYSTEM_SCHEMA_NAMES.add("public");
		SYSTEM_SCHEMA_NAMES.add("pg_temp");
		SYSTEM_SCHEMA_NAMES.add("pglogical");
		SYSTEM_SCHEMA_NAMES.add("pg_statistic_ext_data");
		SYSTEM_SCHEMA_NAMES.add("pg_internal");
		SYSTEM_SCHEMA_NAMES.add("pg_snapshot");

		// cockroachdb
		SYSTEM_SCHEMA_NAMES.add("crdb_internal");

		// microsoft sqlserver
		SYSTEM_SCHEMA_NAMES.add("dbo");
		SYSTEM_SCHEMA_NAMES.add("guest");
		SYSTEM_SCHEMA_NAMES.add("db_owner");
		SYSTEM_SCHEMA_NAMES.add("db_accessadmin");
		SYSTEM_SCHEMA_NAMES.add("db_securityadmin");
		SYSTEM_SCHEMA_NAMES.add("db_ddladmin");
		SYSTEM_SCHEMA_NAMES.add("db_backupoperator");
		SYSTEM_SCHEMA_NAMES.add("db_datareader");
		SYSTEM_SCHEMA_NAMES.add("db_datawriter");
		SYSTEM_SCHEMA_NAMES.add("db_denydatareader");
		SYSTEM_SCHEMA_NAMES.add("db_denydatawriter");
		SYSTEM_SCHEMA_NAMES.add("queue");
		SYSTEM_SCHEMA_NAMES.add("filestream");
		SYSTEM_SCHEMA_NAMES.add("cdc");
		SYSTEM_SCHEMA_NAMES.add("hierarchyid");

		// sqlite3
		SYSTEM_SCHEMA_NAMES.add("sqlite_master");
		SYSTEM_SCHEMA_NAMES.add("sqlite_sequence");
		SYSTEM_SCHEMA_NAMES.add("temp");
		SYSTEM_SCHEMA_NAMES.add("sqlite_temp_master");

		// oracle
		SYSTEM_SCHEMA_NAMES.add("system");
		SYSTEM_SCHEMA_NAMES.add("outln");
		SYSTEM_SCHEMA_NAMES.add("dbsnmp");
		SYSTEM_SCHEMA_NAMES.add("sysman");
		SYSTEM_SCHEMA_NAMES.add("ctxsys");
		SYSTEM_SCHEMA_NAMES.add("wmsys");
		SYSTEM_SCHEMA_NAMES.add("xdb");
		SYSTEM_SCHEMA_NAMES.add("orddata");
		SYSTEM_SCHEMA_NAMES.add("ordplugins");
		SYSTEM_SCHEMA_NAMES.add("ordsys");
		SYSTEM_SCHEMA_NAMES.add("mdsys");
		SYSTEM_SCHEMA_NAMES.add("exfsys");
		SYSTEM_SCHEMA_NAMES.add("lbacsys");
		SYSTEM_SCHEMA_NAMES.add("olapsys");
		SYSTEM_SCHEMA_NAMES.add("si_informtn_schema");
		SYSTEM_SCHEMA_NAMES.add("flow_files");

		SYSTEM_SCHEMA_NAME_PREFIXES.put("pg_", "PostgreSQL and CockroachDB");
		SYSTEM_SCHEMA_NAME_PREFIXES.put("crdb_", "CockroachDB");
		SYSTEM_SCHEMA_NAME_PREFIXES.put("_", "SQLServer");
		SYSTEM_SCHEMA_NAME_PREFIXES.put("sqlite_", "SQLite3");
		SYSTEM_SCHEMA_NAME_PREFIXES.put("sqlite_stat", "SQLite3");
		SYSTEM_SCHEMA_NAME_PREFIXES.put("apex_", "Oracle");
	}

	@Override
	public void validate(Database database, Options options) {
		String schema = database.getSchema();
		if(SYSTEM_SCHEMA_NAMES.contains(schema.toLowerCase())) {
			addFatalMessage("The database schema named '" +
					database.getSchema() +
					"' may conflict with internal system database schemas for various databases, and, as such are disallowed.");
		} else {
			// check for prefixes
			SYSTEM_SCHEMA_NAME_PREFIXES.forEach((schemaName, dbNames) -> {
				if(schema.toLowerCase().startsWith(schemaName)) {
					addWarnMessage("The database schema named '" +
							schema +
							"' has a prefix of " +
							schemaName +
							"' which may conflict with internal system database schemas for " +
							dbNames +
							" and should be avoided.");
				}
			});
		}
		numChecked++;
	}

	@Override
	public String getShortDescription() {
		return "Check database schema names and prefixes.";
	}

	@Override
	public List<String> getMessageTypes() {
		return(List.of(SimpleLogger.WARN, SimpleLogger.FATAL));
	}

}
