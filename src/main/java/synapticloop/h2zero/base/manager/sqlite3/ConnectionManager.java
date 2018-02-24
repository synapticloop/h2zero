package synapticloop.h2zero.base.manager.sqlite3;

/*
 * Copyright (c) 2015-2018 synapticloop.
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import synapticloop.h2zero.base.manager.BaseConnectionManager;
import synapticloop.h2zero.base.sqlite3.SqliteBlob;

public class ConnectionManager extends BaseConnectionManager {

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
		return((Long)returnPossibleNullObject(resultSet, resultSet.getLong(index)));
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
		return((Integer)returnPossibleNullObject(resultSet, resultSet.getInt(index)));
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
		return((Boolean)returnPossibleNullObject(resultSet, resultSet.getBoolean(index)));
	}


	public static String clobReader(String fileName, Writer writerArg) {
		String clobData = null;

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(fileName));
			String nextLine = "";
			StringBuilder sb = new StringBuilder();
			while ((nextLine = br.readLine()) != null) {
				writerArg.write(nextLine);
				sb.append(nextLine);
			}

			// Convert the content into to a string
			clobData = sb.toString();
		} catch (FileNotFoundException fnfex) {
			// do nothing - return null
		} catch (IOException ioex) {
			// do nothing return null
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				} finally {
					br = null;
				}
			}
		}

		// Return the data.
		return clobData;
	}

	private static Object returnPossibleNullObject(ResultSet resultSet, Object object) throws SQLException {
		if(resultSet.wasNull()) {
			return(null);
		}
		
		return(object);
	}

	public static Clob getNullableResultClob(ResultSet resultSet, int index) throws SQLException { 
		return((Clob)returnPossibleNullObject(resultSet, resultSet.getClob(index)));
	}

	public static String getNullableResultString(ResultSet resultSet, int index) throws SQLException {
		return((String)returnPossibleNullObject(resultSet, resultSet.getString(index)));
	}

	public static Blob getNullableResultBlob(ResultSet resultSet, int index) throws SQLException {
		return((Blob)returnPossibleNullObject(resultSet, new SqliteBlob(resultSet.getBytes(index))));
	}

	public static Timestamp getNullableResultTimestamp(ResultSet resultSet, int index) throws SQLException {
		return((Timestamp)returnPossibleNullObject(resultSet, resultSet.getTimestamp(index)));
	}

	public static Date getNullableResultDate(ResultSet resultSet, int index) throws SQLException {
		return((Date)returnPossibleNullObject(resultSet, resultSet.getDate(index)));
	}

	public static Float getNullableResultFloat(ResultSet resultSet, int index) throws SQLException { 
		return((Float)returnPossibleNullObject(resultSet, resultSet.getFloat(index)));
	}

	public static ComboPooledDataSource getComboPooledDataSource() { return comboPooledDataSource; }

}
