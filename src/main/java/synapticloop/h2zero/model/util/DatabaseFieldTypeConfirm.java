package synapticloop.h2zero.model.util;

/*
 * Copyright (c) 2017-2020 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DatabaseFieldTypeConfirm {
	public static final Map<String, Set<String>> FIELD_VALIDATION_LOOKUP = new HashMap<String, Set<String>>();
	static {
		Set<String> sqliteSet = new HashSet<String>();
		sqliteSet.add("int");
		sqliteSet.add("integer");
		sqliteSet.add("tinyint");
		sqliteSet.add("smallint");
		sqliteSet.add("mediumint");
		sqliteSet.add("bigint");
		sqliteSet.add("character");
		sqliteSet.add("varchar");
		sqliteSet.add("nchar");
		sqliteSet.add("nvarchar");
		sqliteSet.add("text");
		sqliteSet.add("clob");
		sqliteSet.add("blob");
		sqliteSet.add("real");
		sqliteSet.add("double");
		sqliteSet.add("float");
		sqliteSet.add("numeric");
		sqliteSet.add("decimal");
		sqliteSet.add("boolean");
		sqliteSet.add("date");
		sqliteSet.add("datetime");

		FIELD_VALIDATION_LOOKUP.put("sqlite3", sqliteSet);

		Set<String> mysqlSet = new HashSet<String>();
		mysqlSet.add("bigint");
		mysqlSet.add("binary");
		mysqlSet.add("blob");
		mysqlSet.add("bool");
		mysqlSet.add("boolean");
		mysqlSet.add("char");
		mysqlSet.add("date");
		mysqlSet.add("datetime");
		mysqlSet.add("dec");
		mysqlSet.add("decimal");
		mysqlSet.add("double");
		mysqlSet.add("float");
		mysqlSet.add("int");
		mysqlSet.add("integer");
		mysqlSet.add("longtext");
		mysqlSet.add("mediumblob");
		mysqlSet.add("mediumint");
		mysqlSet.add("mediumtext");
		mysqlSet.add("numeric");
		mysqlSet.add("smallint");
		mysqlSet.add("text");
		mysqlSet.add("time");
		mysqlSet.add("timestamp");
		mysqlSet.add("tinyint");
		mysqlSet.add("tinytext");
		mysqlSet.add("varbinary");
		mysqlSet.add("varchar");
		mysqlSet.add("year");

		// mysqlSet.add("bit");
		// mysqlSet.add("enum");
		// mysqlSet.add("set");
		// add in spatial sets
		FIELD_VALIDATION_LOOKUP.put("mysql", mysqlSet);

		Set<String> cockroachSet = new HashSet<String>();
		cockroachSet.add("serial");
		cockroachSet.add("bigserial");
		cockroachSet.add("smallserial");
		cockroachSet.add("smallint");
		cockroachSet.add("bigint");
		cockroachSet.add("blob");
		cockroachSet.add("bool");
		cockroachSet.add("boolean");
		cockroachSet.add("date");
		cockroachSet.add("time");
		cockroachSet.add("dec");
		cockroachSet.add("decimal");
		cockroachSet.add("double");
		cockroachSet.add("real");
		cockroachSet.add("numeric");
		cockroachSet.add("int");
		cockroachSet.add("integer");
		cockroachSet.add("longtext");
		cockroachSet.add("mediumblob");
		cockroachSet.add("mediumtext");
		cockroachSet.add("timestamp");
		cockroachSet.add("tinyint");
		cockroachSet.add("varchar");

		FIELD_VALIDATION_LOOKUP.put("cockroach", cockroachSet);
		
		Set<String> postgresqlSet = new HashSet<String>();
		postgresqlSet.addAll(cockroachSet);
		postgresqlSet.add("money");
		FIELD_VALIDATION_LOOKUP.put("postgresql", postgresqlSet);

		
	}

	public static boolean getIsValidFieldTypeForDatabase(String database, String fieldType) {
		if(null == FIELD_VALIDATION_LOOKUP.get(database)) {
			return(false);
		}
		return(FIELD_VALIDATION_LOOKUP.get(database).contains(fieldType));
	}
}
