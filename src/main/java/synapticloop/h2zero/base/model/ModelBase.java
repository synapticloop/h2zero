package synapticloop.h2zero.base.model;

/*
 * Copyright (c) 2012-2015 synapticloop.
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

/**
 * This is the base class for all h2zero generated models and defines the required functionality for a working model.  
 * It contains methods to insert, update and delete itself.
 *
 */
public abstract class ModelBase {
	protected boolean isDirty = false; // whether the model has changes to any of its fields or values

	/**
	 * Persist the model object to the database
	 * 
	 * @param connection the SQL connection to be used
	 * @throws SQLException if there was an error in the SQL expression
	 * @throws H2ZeroPrimaryKeyException if the model already has a primary key
	 */
	protected abstract void insert() throws SQLException, H2ZeroPrimaryKeyException;

	/**
	 * Persist the model object to the database
	 * 
	 * @param connection the SQL connection to be used
	 * @throws SQLException if there was an error in the SQL expression
	 * @throws H2ZeroPrimaryKeyException if the model already has a primary key
	 */
	protected abstract void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;

	/**
	 * Persist the model object to the database silently, i.e. swallow any SQL or H2Zero exceptions
	 */
	protected abstract void insertSilent();

	/**
	 * Persist the model object to the database silently, i.e. swallow any SQL or H2Zero exceptions
	 * 
	 * @param connection the SQL connection to be used
	 */
	protected abstract void insertSilent(Connection connection);

	protected abstract void update() throws SQLException, H2ZeroPrimaryKeyException;
	protected abstract void update(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;
	protected abstract void updateSilent();
	protected abstract void updateSilent(Connection connection);

	protected abstract void delete() throws SQLException, H2ZeroPrimaryKeyException;
	protected abstract void delete(Connection connection) throws SQLException, H2ZeroPrimaryKeyException;
	protected abstract void deleteSilent();
	protected abstract void deleteSilen(Connection connection);

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
