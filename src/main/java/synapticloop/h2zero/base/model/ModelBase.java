package synapticloop.h2zero.base.model;

/*
 * Copyright (c) 2012-2023 synapticloop.
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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import synapticloop.h2zero.base.validator.bean.ValidationBean;

/**
 * This is the base class for all h2zero generated models and defines the required functionality for a working model.
 * It contains methods to insert, update and delete itself.
 *
 */
public abstract class ModelBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelBase.class);

	protected boolean isDirty = false; // whether the model has changes to any of its fields or values

	/**
	 * Whether the primary key is set for this bean
	 *
	 * @return whether the primary key is set
	 */
	public abstract boolean primaryKeySet();

	/**
	 * Whether the bean is valid for insertion
	 *
	 * @return a validation bean
	 */
	public abstract ValidationBean validate();

	/**
	 * Retrieve a connection from the appropriate connection manager, which is
	 * delegated to the sub-classes.
	 *
	 * @return The connection to the database
	 *
	 * @throws SQLException If there was an error with retrieving the connection
	 */
	protected abstract Connection getConnection() throws SQLException;

	/**
	 * Persist the model object to the database
	 *
	 * @param connection the SQL connection to be used - this connection __MUST__
	 *                   be closed by the caller
	 *
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
		try (Connection connection = getConnection()) {
			insert(connection);
		}
	}

	/**
	 * Persist the model object to the database silently, i.e. swallow any SQL or H2Zero exceptions
	 */
	public void insertSilent() {
		try (Connection connection = getConnection()){
			insert(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error("Could not insert", ex);
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
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error("Could not insert", ex);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 * All of the update methods
	 *
	 */

	/**
	 * Update the model utilising the passed in connection, the caller must
	 * close the connection.
	 *
	 * @param connection The connection to the database
	 */
	public abstract void update(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	/**
	 * Update the model utilising the passed in connection, the caller must
	 * close the connection.  If there are any exceptions thrown from subsequent
	 * calls, these will be caught and swallowed.
	 *
	 * @param connection The connection to the database
	 */
	public void updateSilent(Connection connection) {
		try {
			update(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	/**
	 * Update the model, this will automatically get a connection and then close it
	 * once completed.
	 *
	 * @throws SQLException If there was an error with the updating of the model
	 * @throws H2ZeroPrimaryKeyException If the primary key is not set
	 */
	public void update() throws SQLException, H2ZeroPrimaryKeyException {
		try (Connection connection = getConnection()) {
			update(connection);
		}
	}

	/**
	 * Update the model silently, i.e. swallow any exceptions
	 *
	 */
	public void updateSilent() {
		try (Connection connection = getConnection()) {
			update(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
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
		try (Connection connection = getConnection()){
			upsert(connection);
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
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
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
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 * All the delete methods
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete this been with its primary key
	 *
	 * @param connection The connection to use - note that this connection __MUST__
	 *                   be closed by the caller
	 * @throws SQLException If there was an error with the SQL statement
	 * @throws H2ZeroPrimaryKeyException If the primary key is null
	 */
	public abstract void delete(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	/**
	 * Delete this been with its primary key - this will swallow any exceptions
	 *
	 * @param connection The connection to use - note that this connection __MUST__
	 *                   be closed by the caller
	 */
	public void deleteSilent(Connection connection) {
		try {
			delete(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	public void delete() throws SQLException, H2ZeroPrimaryKeyException {
		try (Connection connection = getConnection()) {
			delete(connection);
		}
	}

	/**
	 * Delete the model silently - i.e. swallow any exceptions
	 */
	public void deleteSilent() {
		try (Connection connection = getConnection()) {
			delete(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}


	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 * All the ensure methods
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public abstract void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	public void ensureSilent(Connection connection) {
		try {
			ensure(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	public void ensure() throws SQLException, H2ZeroPrimaryKeyException {
		try (Connection connection = getConnection()) {
			ensure(connection);
		}
	}

	public void ensureSilent() {
		try (Connection connection = getConnection()) {
			ensure(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	/**
	 * Hydrate any fields that are not automatically populated.  By default, this does
	 * nothing unless over-written in the child classes
	 *
	 * @param connection The connection to use
	 *
	 * @throws SQLException IUf there was an error in the SQL statement
	 * @throws H2ZeroPrimaryKeyException If the primary key is null
	 */
	protected void hydrate(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		// do nothing
	}

	protected void hydrateSilent(Connection connection) {
		try {
			hydrate(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	protected void hydrate() throws SQLException, H2ZeroPrimaryKeyException {
		try (Connection connection = getConnection()) {
			hydrate(connection);
		}
	}

	protected void hydrateSilent() {
		try (Connection connection = getConnection()) {
			hydrate(connection);
		} catch(H2ZeroPrimaryKeyException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 * All the refresh methods
	 *
   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Refresh the bean with the passed in connection by loading all the
	 * information again using the primary key to look it up.  The passed in
	 * connection can be used for transactional purposes.
	 *
	 * @param connection The connection to use
	 *
	 * @throws SQLException If there was an error in the SQL statement
	 * @throws H2ZeroPrimaryKeyException if the primary key is null
	 */
	public abstract void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException;

	/**
	 * Refresh the bean by loading all information again using the primary key
	 * to look it up
	 *
	 * @throws SQLException if there was an error in the SQL statement
	 * @throws H2ZeroPrimaryKeyException if the primary key is null
	 */
	public void refresh() throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		try (Connection connection = getConnection()) {
			refresh(connection);
		}
	}

	public void refreshSilent() {
		try {
			refresh();
		} catch(H2ZeroPrimaryKeyException | H2ZeroFinderException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	public void refreshSilent(Connection connection) {
		try {
			refresh(connection);
		} catch(H2ZeroPrimaryKeyException | H2ZeroFinderException | SQLException ex) {
			LOGGER.error(ex.getMessage(), ex);
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

	protected void addtoJSONObject(JSONObject jsonObject, String key, Object object) {
		jsonObject.put(key, object);
	}

	public JSONObject getToJSON() {
		return(toJSON());
	}

	public abstract JSONObject toJSON();

	public abstract String toJsonString();

	public abstract String getJsonString();
}
