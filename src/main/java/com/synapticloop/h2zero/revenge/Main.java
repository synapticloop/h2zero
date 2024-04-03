package com.synapticloop.h2zero.revenge;

/*
 * Copyright (c) 2013-2024 synapticloop.
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

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		if(args.length != 4) {
			usage();
		}
		String host = args[0];
		String database = args[1];
		String user = args[2];
		String password = args[3];

		try {
			ModelBuilder modelBuilder = new ModelBuilder(host, database, user, password);
			System.out.println(modelBuilder.generate());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void usage() {
		System.out.println("Usage:");
		System.out.println("\tmain <mysql_host> <mysql_database> <mysql_user> <mysql_password>");
		System.exit(-1);
	}

}
