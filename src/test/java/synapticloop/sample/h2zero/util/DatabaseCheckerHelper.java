package synapticloop.sample.h2zero.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
public class DatabaseCheckerHelper {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseCheckerHelper.class);

	private Set<String> allTables = new HashSet<String>();
	private Map<String, Set<String>> allTableFields = new HashMap<String, Set<String>>();

	private static final String[] ALL_TABLE_NAMES = { "user_type", "user_title", "user", "pet", "user_pet" };
	private static final String[] ALL_TABLE_FIELD_NAMES = { "user_type.id_user_type", "user_type.nm_user_type", 
			"user_title.id_user_title", "user_title.nm_user_title", "user_title.num_order_by", 
			"user.id_user", "user.id_user_type", "user.fl_is_alive", "user.num_age", "user.nm_username", "user.txt_address_email", "user.txt_password", "user.dtm_signup", 
			"pet.id_pet", "pet.nm_pet", "pet.num_age", "pet.flt_weight", "pet.dt_birthday", 
			"user_pet.id_user_pet", "user_pet.id_user", "user_pet.id_pet"
			 };

	private void initialiseDataStructures() {
		for (int i = 0; i < ALL_TABLE_NAMES.length; i++) {
			String allTableName = ALL_TABLE_NAMES[i];
			allTables.add(allTableName);
		}

		for (int i = 0; i < ALL_TABLE_FIELD_NAMES.length; i++) {
			String allTableFieldName = ALL_TABLE_FIELD_NAMES[i];
			String[] split = allTableFieldName.split("\\.");
			String table = split[0];
			String field = split[1];
			if(allTableFields.containsKey(table)) {
				allTableFields.get(table).add(field);
			} else {
				Set<String> hashSet = new HashSet<String>();
				hashSet.add(field);
				allTableFields.put(table, hashSet);
			}
		}
	}

	private boolean areTablesCorrect() {
		boolean isDatabaseCorrect = true;
		Connection connection = null;
		ResultSet tablesResultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			tablesResultSet = databaseMetaData.getTables("macdaddy", null, "%", new String[] { "TABLE" });
			while(tablesResultSet.next()) {
				String tableName = tablesResultSet.getString("TABLE_NAME");
				if(allTables.contains(tableName)) {
					LOGGER.info("Table '" + tableName + "' exists in the h2zero file and in the database.");
				} else {
					LOGGER.fatal("Table '" + tableName + "' exists in the database but not in the h2zero file.");
					LOGGER.fatal("SQL COMMAND -> DROP TABLE " + tableName + ";");
				}
				allTables.remove(tableName);
			}

			// now go through the tableNames and see what is left...
			Iterator<String> allTablesIterator = allTables.iterator();
			while (allTablesIterator.hasNext()) {
				String tableName = allTablesIterator.next();
				LOGGER.fatal("Table '" + tableName + "' exists in the h2zero file, but not in the database.");
				LOGGER.fatal("SQL COMMAND -> CREATE TABLE " + tableName + ";");
				// now we need to remove this from the all table fields lookup as it won't be found and we will get spurious
				// errors
				allTableFields.remove(tableName);
				isDatabaseCorrect = false;
			}

		} catch (SQLException sqlex) {
			// do nothing
		} finally {
			ConnectionManager.closeAll(tablesResultSet, null, connection);
		}

		return(isDatabaseCorrect);
	}

	private boolean areTableFieldsCorrect() {
		boolean isDatabaseCorrect = true;
		// now it is time to check all of the fields for all of the tables
		Connection connection = null;
		ResultSet columns = null;
		try {
			connection = ConnectionManager.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			columns = databaseMetaData.getColumns("macdaddy", null, "%", "%");
			while(columns.next()) {
				String tableName = columns.getString("TABLE_NAME");
				String columnName = columns.getString("COLUMN_NAME");

				// this will be picked up by the previous check
				if(allTableFields.containsKey(tableName)) {
					if(allTableFields.get(tableName).contains(columnName)) {
						LOGGER.info("table.field '" + tableName + "." + columnName + "' exists in the h2zero file and in the database.");
					} else {
						LOGGER.fatal("table.field '" + tableName + "." + columnName + "' exists in the h2zero file, but not in the database.");
						LOGGER.fatal("SQL COMMAND -> ALTER TABLE " + tableName + " ADD COLUMN " + columnName + ";");
						isDatabaseCorrect = false;
					}
					allTableFields.get(tableName).remove(columnName);
				} else {
					LOGGER.warn("Refusing to check '" + tableName + "." + columnName + "' as the table does not exist in the database.");
				}
			}

			// now go through everything that is left
			Iterator<String> allTableFieldsIterator = allTableFields.keySet().iterator();
			while (allTableFieldsIterator.hasNext()) {
				String tableName = allTableFieldsIterator.next();
				Set<String> hashSet = allTableFields.get(tableName);
				Iterator<String> iterator = hashSet.iterator();
				while (iterator.hasNext()) {
					String columnName = iterator.next();
					LOGGER.fatal("SQL COMMAND -> ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";");
					isDatabaseCorrect = false;

				}
			}
		} catch (SQLException sqlex) {
			// do nothing
		} finally {
			ConnectionManager.closeAll(columns, null, connection);
		}

		return(isDatabaseCorrect);
	}

	public boolean isDatabaseCorrect() {
		initialiseDataStructures();
		return(areTablesCorrect() && areTableFieldsCorrect());
	}

}