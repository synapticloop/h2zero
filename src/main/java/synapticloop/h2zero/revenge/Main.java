package synapticloop.h2zero.revenge;

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
