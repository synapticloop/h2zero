package synapticloop.h2zero.base.manager;

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

import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager {
	private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

	public static Connection getConnection() throws SQLException {
		return(comboPooledDataSource.getConnection());
	}

	public static void closeAll(Connection connection) {
		closeAll(null, null, connection);
	}

	public static void closeAll(Statement statement, Connection connection) {
		closeAll(null, statement, connection);
	}

	public static void closeAll(ResultSet resultSet, Statement statement) {
		closeAll(resultSet, statement, null);
	}

	public static void closeAll(Statement statement) {
		closeAll(null, statement, null);
	}

	public static void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
		if(null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException jssqlex) {
			} finally {
				resultSet = null;
			}
		}
		
		if(null != statement) {
			try {
				statement.close();
			} catch (SQLException jssqlex) {
			} finally {
				statement = null;
			}
		}

		if(null != connection) {
			try {
				connection.close();
			} catch (SQLException jssqlex) {
			} finally {
				connection = null;
			}
		}
	}

	/**
	 * Set a BIGINT datatype to a prepared statement with the value of the passed
	 * in Long, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setBigint(PreparedStatement preparedStatement, int parameterIndex, Long value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.BIGINT);
		} else {
			preparedStatement.setLong(parameterIndex, value);
		}
	}

	/**
	 * Set a VARCHAR datatype to a prepared statement with the value of the passed
	 * in String, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
 	public static void setVarchar(PreparedStatement preparedStatement, int parameterIndex, String value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.VARCHAR);
		} else {
			preparedStatement.setString(parameterIndex, value);
		}
	}

	/**
	 * Set a VARCHAR datatype to a prepared statement with the value of the passed
	 * in String, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
 	public static void setClob(PreparedStatement preparedStatement, int parameterIndex, String value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.CLOB);
		} else {
			preparedStatement.setClob(parameterIndex, new StringReader(value));
		}
	}

	/**
	 * Set a INT datatype to a prepared statement with the value of the passed
	 * in Integer, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setInt(PreparedStatement preparedStatement, int parameterIndex, Integer value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.INTEGER);
		} else {
			preparedStatement.setInt(parameterIndex, value);
		}
	}

	/**
	 * Set a DATETIME datatype to a prepared statement with the value of the passed
	 * in timestamp or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setDatetime(PreparedStatement preparedStatement, int parameterIndex, Timestamp value) throws SQLException {
		setTimestamp(preparedStatement, parameterIndex, value);
	}

	public static void setDate(PreparedStatement preparedStatement, int parameterIndex, Date value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.DATE);
		} else {
			preparedStatement.setDate(parameterIndex, value);
		}
	}

	/**
	 * Set a TIMESTAMP datatype to a prepared statement with the value of the passed
	 * in timestamp, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setTimestamp(PreparedStatement preparedStatement, int parameterIndex, Timestamp value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.TIMESTAMP);
		} else {
			preparedStatement.setTimestamp(parameterIndex, value);
		}
	}

	/**
	 * Set a MEDIUMTEXT datatype to a prepared statement with the value of the 
	 * passed in mediumtext, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setMediumtext(PreparedStatement preparedStatement, int parameterIndex, String value) throws SQLException {
		setVarchar(preparedStatement, parameterIndex, value);
	}

	/**
	 * Set a LONGTEXT datatype to a prepared statement with the value of the 
	 * passed in longtext, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setLongtext(PreparedStatement preparedStatement, int parameterIndex, String value) throws SQLException {
		setVarchar(preparedStatement, parameterIndex, value);
	}

	/**
	 * Set a FLOAT datatype to a prepared statement with the value of the passed
	 * in float, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setFloat(PreparedStatement preparedStatement, int parameterIndex, Float value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.FLOAT);
		} else {
			preparedStatement.setFloat(parameterIndex, value);
		}
	}

	/**
	 * Set a TINYINT datatype to a prepared statement with the value of the passed
	 * in boolean, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setTinyint(PreparedStatement preparedStatement, int parameterIndex, Boolean value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.TINYINT);
		} else {
			preparedStatement.setBoolean(parameterIndex, value);
		}
	}

	/**
	 * Set a BOOLEAN (or in sthis case conversion to a TINYINT) datatype to a 
	 * prepared statement with the value of the passed in boolean, or the correct 
	 * SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setBoolean(PreparedStatement preparedStatement, int parameterIndex, Boolean value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.TINYINT);
		} else {
			preparedStatement.setBoolean(parameterIndex, value);
		}
	}

	/**
	 * Set a DOUBLE datatype to a prepared statement with the value of the passed 
	 * in double, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setDouble(PreparedStatement preparedStatement, int parameterIndex, Double value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.DOUBLE);
		} else {
			preparedStatement.setDouble(parameterIndex, value);
		}
	}

	/**
	 * Set a CLOB datatype to a prepared statement with the value of the passed
	 * in clob, or the correct SQL null type if null
	 * 
	 * @param preparedStatement The prepared statement
	 * @param parameterIndex the index of the parameter
	 * @param value the value to be set
	 * 
	 * @throws SQLException if something went horribly wrong
	 */
	public static void setClob(PreparedStatement preparedStatement, int parameterIndex, Clob value) throws SQLException {
		if(null == value) {
			preparedStatement.setNull(parameterIndex, Types.CLOB);
		} else {
			preparedStatement.setClob(parameterIndex, value);
		}
	}


	/**
	 * Get an Long result from the resultSet as a value or null.  In the case where the resulting value is null, this will 
	 * be set to 0 (zero) by the jdbc driver.  Consequently the resultSet is checked to see whether it was null.  If so, 
	 * null is returned, else the actual value
	 * 
	 * @param resultSet The resultSet to get the value from
	 * @param index The index of the result
	 * @return the value, or null
	 * 
	 * @throws SQLException if something went wrong
	 */
	public static Long getNullableResultLong(ResultSet resultSet, int index) throws SQLException {
		Long temp = resultSet.getLong(index);
		if(resultSet.wasNull()) {
			return(null);
		} else {
			return(temp);
		}
	}

	/**
	 * Get an Int result from the resultSet as a value or null.  In the case where the resulting value is null, this will 
	 * be set to 0 (zero) by the jdbc driver.  Consequently the resultSet is checked to see whether it was null.  If so, 
	 * null is returned, else the actual value
	 * 
	 * @param resultSet The resultSet to get the value from
	 * @param index The index of the result
	 * @return the value, or null
	 * 
	 * @throws SQLException if something went wrong
	 */
	public static Integer getNullableResultInt(ResultSet resultSet, int index) throws SQLException {
		Integer temp = resultSet.getInt(index);
		if(resultSet.wasNull()) {
			return(null);
		} else {
			return(temp);
		}
	}

	/**
	 * Get a Boolean result from the resultSet as a value or null.  In the case where the resulting value is null, this will 
	 * be set to 0 (zero) by the jdbc driver.  Consequently the resultSet is checked to see whether it was null.  If so, 
	 * null is returned, else the actual value
	 * 
	 * @param resultSet The resultSet to get the value from
	 * @param index The index of the result
	 * @return the value, or null
	 * 
	 * @throws SQLException if something went wrong
	 */
	public static Boolean getNullableResultBoolean(ResultSet resultSet, int index) throws SQLException {
		Boolean temp = resultSet.getBoolean(index);
		if(resultSet.wasNull()) {
			return(null);
		} else {
			return(temp);
		}
	}

	public static String getNullableResultString(ResultSet resultSet, int index) throws SQLException { return(resultSet.getString(index)); }
	public static Timestamp getNullableResultTimestamp(ResultSet resultSet, int index) throws SQLException { return(resultSet.getTimestamp(index)); }

	public static ComboPooledDataSource getComboPooledDataSource() {
		return comboPooledDataSource;
	}

}
