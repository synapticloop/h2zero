package synapticloop.h2zero.base.model;

/*
 * Copyright (c) 2012-2017 synapticloop.
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

import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import synapticloop.h2zero.base.manager.mysql.ConnectionManager;

/**
 * This is the base class for all h2zero generated models and defines the required functionality for a working model.  
 * It contains methods to insert, update and delete itself.
 *
 */
public abstract class ModelBase {
	protected boolean isDirty = false; // whether the model has changes to any of its fields or values

	public abstract boolean primaryKeySet();

	/**
	 * Persist the model object to the database
	 * 
	 * @param connection the SQL connection to be used
	 * @throws SQLException if there was an error in the SQL expression
	 * @throws H2ZeroPrimaryKeyException if the model already has a primary key
	 */
	public abstract void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	/**
	 * Persist the model object to the database
	 * 
	 * @throws SQLException if there was an error in the SQL expression
	 * @throws H2ZeroPrimaryKeyException if the model already has a primary key
	 */
	public void insert() throws SQLException, H2ZeroPrimaryKeyException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			insert(connection);
			connection.close();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	/**
	 * Persist the model object to the database silently, i.e. swallow any SQL or H2Zero exceptions
	 */
	public void insertSilent() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			insert(connection);
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	/**
	 * Persist the model object to the database silently, i.e. swallow any SQL or H2Zero exceptions
	 * 
	 * @param connection the SQL connection to be used
	 */
	public void insertSilent(Connection connection) {
		try {
			insert(connection);
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * All of the update methods
	 * 
	 */
	public abstract void update(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	public void updateSilent(Connection connection) {
		try {
			update(connection);
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	public void update() throws SQLException, H2ZeroPrimaryKeyException {
		Connection connection = ConnectionManager.getConnection();
		update(connection);
		connection.close();
	}

	public void updateSilent() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			update(connection);
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	/*
	 * All of the insert or update methods
	 */
	
	/**
	 * @see #upsert(Connection connection)
	 * 
	 * @param connection the connection to use (if a transaction has been started)
	 * 
	 * @throws H2ZeroPrimaryKeyException if there was an error retrieving the primary key 
	 * @throws SQLException if there was an error with the SQL statement
	 */
	@Deprecated
	public void insertOrUpdate(Connection connection) throws H2ZeroPrimaryKeyException, SQLException {
		if(!primaryKeySet()) {
			insert(connection);
		} else {
			update(connection);
		}
	}

	/**
	 * @see #upsert()
	 * 
	 * @throws H2ZeroPrimaryKeyException if there was an error retrieving the primary key 
	 * @throws SQLException if there was an error with the SQL statement
	 */
	@Deprecated
	public void insertOrUpdate() throws H2ZeroPrimaryKeyException, SQLException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			insertOrUpdate(connection);
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			throw(h2zpkex);
		} catch(SQLException sqlex) {
			throw (sqlex);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	/**
	 * @see #upsertSilent(Connection connection)
	 * 
	 * @param connection the connection to use (if a transaction has been started)
	 */
	@Deprecated
	public void insertOrUpdateSilent(Connection connection) {
		try {
			if(!primaryKeySet()) {
				insert(connection);
			} else {
				update(connection);
			}
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	/**
	 * @see #upsertSilent()
	 */
	@Deprecated
	public void insertOrUpdateSilent() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			if(!primaryKeySet()) {
				insert(connection);
			} else {
				update(connection);
			}
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	/**
	 * Update or insert the model object - updates are performed where the 
	 * primary key is set, inserts where the primary key is null
	 * 
	 * @param connection The connection to use for transactional purposes 
	 * @throws H2ZeroPrimaryKeyException if the return value for the primary key 
	 *     could not be determined
	 * @throws SQLException if there was an error with the SQL statement
	 */
	public void upsert(Connection connection) throws H2ZeroPrimaryKeyException, SQLException {
		if(!primaryKeySet()) {
			insert(connection);
		} else {
			update(connection);
		}
	}

	/**
	 * Update or insert the model object - updates are performed where the 
	 * primary key is set, inserts where the primary key is null.  This will
	 * grab a connection from the pool and close it at the end of the method.
	 * 
	 * @throws H2ZeroPrimaryKeyException if the return value for the primary key 
	 *     could not be determined
	 * @throws SQLException if there was an error with the SQL statement
	 */
	public void upsert() throws H2ZeroPrimaryKeyException, SQLException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			upsert(connection);
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			throw(h2zpkex);
		} catch(SQLException sqlex) {
			throw (sqlex);
		} finally {
			ConnectionManager.closeAll(connection);
		}
	}

	/**
	 * Update or insert the model object silently (that is catch all exceptions)
	 * updates are performed where the primary key is set, inserts where the 
	 * primary key is null.
	 * 
	 * @param connection The connection to use for transactional purposes 
	 */
	public void upsertSilent(Connection connection) {
		try {
			upsert(connection);
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	/**
	 * Update or insert the model object silently (that is catch all exceptions)
	 * updates are performed where the primary key is set, inserts where the 
	 * primary key is null.   This will grab a connection from the pool and 
	 * close it at the end of the method.
	 */
	public void upsertSilent() {
		try {
			upsert();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * All of the delete methods
	 * 
	 */

	public abstract void delete(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	public void deleteSilent(Connection connection) {
		try {
			delete(connection);
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	public void delete() throws SQLException, H2ZeroPrimaryKeyException {
		Connection connection = ConnectionManager.getConnection();
		delete(connection);
		connection.close();
	}

	public void deleteSilent() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			delete(connection);
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * All of the ensure methods
	 * 
	 */

	public abstract void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	public void ensureSilent(Connection connection) {
		try {
			ensure(connection);
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	public void ensure() throws SQLException, H2ZeroPrimaryKeyException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			ensure(connection);
			connection.close();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	public void ensureSilent() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			ensure(connection);
			connection.close();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * All of the refresh methods
	 * 
	 */

	/**
	 * Refresh the bean with the passed in connection by loading all of the information again using the primary 
	 * key to look it up.  The passed in connection can be used for transactional purposes.
	 * 
	 * @param Connection The connection to use
	 * @throws SQLException If there was an error in the SQL statement
	 * @throws H2ZeroPrimaryKeyException if the primary key is null
	 */
	public abstract void refresh(Connection Connection) throws SQLException, H2ZeroPrimaryKeyException;

	/**
	 * Refresh the bean by loading all of the information again using the primary key to look it up
	 * 
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroPrimaryKeyException if the primary key is null
	 */
	public void refresh() throws SQLException, H2ZeroPrimaryKeyException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			refresh(connection);
			connection.close();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// do nothing
				} finally {
					connection = null;
				}
			}
		}
	}

	public void refreshSilent() {
		try {
			refresh();
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch (SQLException sqlex) {
			// TODO Auto-generated catch block
			sqlex.printStackTrace();
		}
	}

	public void refreshSilent(Connection connection) {
		try {
			refresh(connection);
		} catch(H2ZeroPrimaryKeyException h2zpkex) {
			h2zpkex.printStackTrace();
		} catch (SQLException sqlex) {
			// TODO Auto-generated catch block
			sqlex.printStackTrace();
		}
	}

	/**
	 * Determine whether the two objects differ
	 * 
	 * @param from the first object to be checked against
	 * @param to the second object to be checked
	 * 
	 * @return whether the two objects differ
	 */
	protected boolean isDifferent(Object from, Object to) {
		if(null == from) {
			return(to != null);
		} else {
			if(null == to) {
				return(true);
			} else {
				return(!from.equals(to));
			}
		}
	}
}
