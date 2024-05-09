package com.synapticloop.sample.h2zero.cockroach.model;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                  (/java/model/java-create-model.templar)

import com.synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.cockroach.ModelBase;
import com.synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;

import com.synapticloop.sample.h2zero.cockroach.finder.AllTypesFinder;


/**
 * <p>This is the model for the <code>AllTypes</code> which maps to the <code>all_types</code> database table.</p>
 * 
 * <p>This model maps all of the fields from the database as defined in the
 * <code>.h2zero</code> file.  The parsed definition of the table and fields are:</p>
 * 
  * <p>This class contains all the base CRUD (Create, Read, Update, and Delete)
 * methods for a model.</p>
  * 
 * <table>
 *   <thead>
 *     <tr>
 *       <th>Field name</th>
 *       <th>SQL type</th>
 *       <th>Field length<br />(min:max)</th>
 *       <th>Nullable?</th>
 *       <th>Keys</th>
 *       <th>Comments</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td><code>id_all_types</code></td>
 *       <td>bigserial</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td><code>primary</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_smallint</code></td>
 *       <td>smallint</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_integer</code></td>
 *       <td>integer</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_bigint</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_decimal</code></td>
 *       <td>decimal</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_numeric</code></td>
 *       <td>numeric</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>flt_real</code></td>
 *       <td>real</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>dbl_real</code></td>
 *       <td>double</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_serial</code></td>
 *       <td>serial</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_smallserial</code></td>
 *       <td>smallserial</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_bigserial</code></td>
 *       <td>bigserial</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *   </tbody>
 * </table>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class AllTypes extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;


	public static final String PRIMARY_KEY_FIELD = "id_all_types";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			all_types (
				num_smallint,
				num_integer,
				num_bigint,
				num_decimal,
				num_numeric,
				flt_real,
				dbl_real,
				num_serial,
				num_smallserial,
				num_bigserial
			) values (
				?,
				?,
				?,
				?,
				?,
				?,
				?,
				?,
				?,
				?
			)
		""";
	private static final String SQL_UPDATE = 
		"""
			update
				all_types
			set
				num_smallint = ?,
				num_integer = ?,
				num_bigint = ?,
				num_decimal = ?,
				num_numeric = ?,
				flt_real = ?,
				dbl_real = ?,
				num_serial = ?,
				num_smallserial = ?,
				num_bigserial = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from all_types where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from all_types where num_smallint = ? and num_integer = ? and num_bigint = ? and num_decimal = ? and num_numeric = ? and flt_real = ? and dbl_real = ? and num_serial = ? and num_smallserial = ? and num_bigserial = ?";


	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computed, external classes can use these static 
	// fields to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;  // The total number of hits on all fields of this model
	public static final int HIT_ID_ALL_TYPES = 1; // The number of hits on the id_all_types property
	public static final int HIT_NUM_SMALLINT = 2; // The number of hits on the num_smallint property
	public static final int HIT_NUM_INTEGER = 3; // The number of hits on the num_integer property
	public static final int HIT_NUM_BIGINT = 4; // The number of hits on the num_bigint property
	public static final int HIT_NUM_DECIMAL = 5; // The number of hits on the num_decimal property
	public static final int HIT_NUM_NUMERIC = 6; // The number of hits on the num_numeric property
	public static final int HIT_FLT_REAL = 7; // The number of hits on the flt_real property
	public static final int HIT_DBL_REAL = 8; // The number of hits on the dbl_real property
	public static final int HIT_NUM_SERIAL = 9; // The number of hits on the num_serial property
	public static final int HIT_NUM_SMALLSERIAL = 10; // The number of hits on the num_smallserial property
	public static final int HIT_NUM_BIGSERIAL = 11; // The number of hits on the num_bigserial property


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_all_types", "num_smallint", "num_integer", "num_bigint", "num_decimal", "num_numeric", "flt_real", "dbl_real", "num_serial", "num_smallserial", "num_bigserial" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


	private Long idAllTypes = null; // maps to the id_all_types field
	private Short numSmallint = null; // maps to the num_smallint field
	private Integer numInteger = null; // maps to the num_integer field
	private Long numBigint = null; // maps to the num_bigint field
	private BigDecimal numDecimal = null; // maps to the num_decimal field
	private BigDecimal numNumeric = null; // maps to the num_numeric field
	private Double fltReal = null; // maps to the flt_real field
	private Double dblReal = null; // maps to the dbl_real field
	private Integer numSerial = null; // maps to the num_serial field
	private Short numSmallserial = null; // maps to the num_smallserial field
	private Long numBigserial = null; // maps to the num_bigserial field

	/**
	 * <p>Create a new <code>AllTypes</code> object with all fields
	 * some of which can be null.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object.</p>
	 * 
	 * <p>Creating a new AllTypes:</p>
	 * 
	 * <pre>new AllTypes(
	 *     Long idAllTypes,  // id_all_types 
	 *     Short numSmallint,  // num_smallint  [NULLABLE]
	 *     Integer numInteger,  // num_integer  [NULLABLE]
	 *     Long numBigint,  // num_bigint  [NULLABLE]
	 *     BigDecimal numDecimal,  // num_decimal  [NULLABLE]
	 *     BigDecimal numNumeric,  // num_numeric  [NULLABLE]
	 *     Double fltReal,  // flt_real  [NULLABLE]
	 *     Double dblReal,  // dbl_real  [NULLABLE]
	 *     Integer numSerial,  // num_serial 
	 *     Short numSmallserial,  // num_smallserial 
	 *     Long numBigserial // num_bigserial 
	 * );</pre>
	 * 
	 */
	public AllTypes(Long idAllTypes, Short numSmallint, Integer numInteger, Long numBigint, BigDecimal numDecimal, BigDecimal numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) {
		this.idAllTypes = idAllTypes;
		this.numSmallint = numSmallint;
		this.numInteger = numInteger;
		this.numBigint = numBigint;
		this.numDecimal = numDecimal;
		this.numNumeric = numNumeric;
		this.fltReal = fltReal;
		this.dblReal = dblReal;
		this.numSerial = numSerial;
		this.numSmallserial = numSmallserial;
		this.numBigserial = numBigserial;
	}

	/**
	 * <p>Create a new <code>AllTypes</code> object with only the
	 * fields that are non-nullable.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object</p>
	 * 
	 * <p>Creating a new AllTypes:</p>
	 * 
	 * <pre>new AllTypes(
	 *     Long idAllTypes,  // id_all_types
	 *     Integer numSerial,  // num_serial
	 *     Short numSmallserial,  // num_smallserial
	 *     Long numBigserial // num_bigserial
	 * );</pre>
	 * 
	 */
	public AllTypes(Long idAllTypes, Integer numSerial, Short numSmallserial, Long numBigserial) {
		this.idAllTypes = idAllTypes;
		this.numSmallint = null;
		this.numInteger = null;
		this.numBigint = null;
		this.numDecimal = null;
		this.numNumeric = null;
		this.fltReal = null;
		this.dblReal = null;
		this.numSerial = numSerial;
		this.numSmallserial = numSmallserial;
		this.numBigserial = numBigserial;
	}

	/**
	 * <p>Get a new AllTypes model, or set the fields on an existing
	 * AllTypes model.</p>
	 * 
	 * <p>If the passed in allTypes is null, then a new AllTypes
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param allTypes the model to check
	 * @param numSmallint - maps to the <code>num_smallint</code> field.
	 * @param numInteger - maps to the <code>num_integer</code> field.
	 * @param numBigint - maps to the <code>num_bigint</code> field.
	 * @param numDecimal - maps to the <code>num_decimal</code> field.
	 * @param numNumeric - maps to the <code>num_numeric</code> field.
	 * @param fltReal - maps to the <code>flt_real</code> field.
	 * @param dblReal - maps to the <code>dbl_real</code> field.
	 * @param numSerial - maps to the <code>num_serial</code> field.
	 * @param numSmallserial - maps to the <code>num_smallserial</code> field.
	 * @param numBigserial - maps to the <code>num_bigserial</code> field.
	 * 
	 * @return Either the existing allTypes with updated field values,
	 *   or a new AllTypes with the field values set.
	 */
	public static AllTypes getOrSet(AllTypes allTypes,Short numSmallint, Integer numInteger, Long numBigint, BigDecimal numDecimal, BigDecimal numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) {
		if(null == allTypes) {
			return (new AllTypes(null, numSmallint, numInteger, numBigint, numDecimal, numNumeric, fltReal, dblReal, numSerial, numSmallserial, numBigserial));
		} else {
			allTypes.setNumSmallint(numSmallint);
			allTypes.setNumInteger(numInteger);
			allTypes.setNumBigint(numBigint);
			allTypes.setNumDecimal(numDecimal);
			allTypes.setNumNumeric(numNumeric);
			allTypes.setFltReal(fltReal);
			allTypes.setDblReal(dblReal);
			allTypes.setNumSerial(numSerial);
			allTypes.setNumSmallserial(numSmallserial);
			allTypes.setNumBigserial(numBigserial);

			return(allTypes);
		}
	}

	/**
	 * Get a new AllTypes model, or set the fields on an existing
	 * AllTypes model.
	 * <p>
	 * If the passed in allTypes is null, then a new AllTypes
	 * will be created.  If not null, the fields will be updated on the existing model.
	 * <p>
	 * <strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call.
	 * 
	 * @param allTypes the model to check
	 * @param numSerial
	 * @param numSmallserial
	 * @param numBigserial
	 * 
	 * @return Either the existing allTypes with updated field values,
	 *   or a new AllTypes with the field values set.
	 */
	public static AllTypes getOrSet(AllTypes allTypes, Integer numSerial, Short numSmallserial, Long numBigserial) {
		if(null == allTypes) {
			return (new AllTypes(null , numSerial, numSmallserial, numBigserial));
		} else {
			allTypes.setNumSerial(numSerial);
			allTypes.setNumSmallserial(numSmallserial);
			allTypes.setNumBigserial(numBigserial);

			return(allTypes);
		}
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idAllTypes);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert all_types model when primary key is not null.");
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// create this bean 
			preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ConnectionManager.setSmallint(preparedStatement, 1, numSmallint);
			ConnectionManager.setInteger(preparedStatement, 2, numInteger);
			ConnectionManager.setBigint(preparedStatement, 3, numBigint);
			ConnectionManager.setDecimal(preparedStatement, 4, numDecimal);
			ConnectionManager.setNumeric(preparedStatement, 5, numNumeric);
			ConnectionManager.setReal(preparedStatement, 6, fltReal);
			ConnectionManager.setDouble(preparedStatement, 7, dblReal);
			ConnectionManager.setSerial(preparedStatement, 8, numSerial);
			ConnectionManager.setSmallserial(preparedStatement, 9, numSmallserial);
			ConnectionManager.setBigserial(preparedStatement, 10, numBigserial);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idAllTypes = resultSet.getLong(1);
			} else {
				throw new H2ZeroPrimaryKeyException("Could not get return value for primary key!");
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
	}

	@Override
	public void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_ENSURE);
			ConnectionManager.setSmallint(preparedStatement, 1, numSmallint);
			ConnectionManager.setInteger(preparedStatement, 2, numInteger);
			ConnectionManager.setBigint(preparedStatement, 3, numBigint);
			ConnectionManager.setDecimal(preparedStatement, 4, numDecimal);
			ConnectionManager.setNumeric(preparedStatement, 5, numNumeric);
			ConnectionManager.setReal(preparedStatement, 6, fltReal);
			ConnectionManager.setDouble(preparedStatement, 7, dblReal);
			ConnectionManager.setSerial(preparedStatement, 8, numSerial);
			ConnectionManager.setSmallserial(preparedStatement, 9, numSmallserial);
			ConnectionManager.setBigserial(preparedStatement, 10, numBigserial);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idAllTypes = resultSet.getLong(1);
			} else {
				// could not find the value - need to insert it - null is the primary key
				insert(connection);
			}
		} finally {
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
	}

	@Override
	public void update(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot update bean when primary key is null.");
		}

		if(isDirty) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
				// update this bean, but only if dirty
				ConnectionManager.setSmallint(preparedStatement, 1, numSmallint);
				ConnectionManager.setInteger(preparedStatement, 2, numInteger);
				ConnectionManager.setBigint(preparedStatement, 3, numBigint);
				ConnectionManager.setDecimal(preparedStatement, 4, numDecimal);
				ConnectionManager.setNumeric(preparedStatement, 5, numNumeric);
				ConnectionManager.setReal(preparedStatement, 6, fltReal);
				ConnectionManager.setDouble(preparedStatement, 7, dblReal);
				ConnectionManager.setSerial(preparedStatement, 8, numSerial);
				ConnectionManager.setSmallserial(preparedStatement, 9, numSmallserial);
				ConnectionManager.setBigserial(preparedStatement, 10, numBigserial);
				// now set the primary key
				preparedStatement.setLong(11, idAllTypes);
				preparedStatement.executeUpdate();
			} finally {
				isDirty = false;
			}
		}
	}

	@Override
	public void delete(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot delete bean when primary key is null.");
		}
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
			preparedStatement.setLong(1, idAllTypes);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'AllTypes' when primary key is null.");
		}

		AllTypes allTypes = AllTypesFinder.findByPrimaryKey(this.idAllTypes)
				.withConnection(connection)
				.executeSilent();

		if(null == allTypes) {
			throw new H2ZeroFinderException("Could not find the model 'AllTypes' with primaryKey of " + getPrimaryKey());
		}
		this.idAllTypes = allTypes.getIdAllTypes();
		this.numSmallint = allTypes.getNumSmallint();
		this.numInteger = allTypes.getNumInteger();
		this.numBigint = allTypes.getNumBigint();
		this.numDecimal = allTypes.getNumDecimal();
		this.numNumeric = allTypes.getNumNumeric();
		this.fltReal = allTypes.getFltReal();
		this.dblReal = allTypes.getDblReal();
		this.numSerial = allTypes.getNumSerial();
		this.numSmallserial = allTypes.getNumSmallserial();
		this.numBigserial = allTypes.getNumBigserial();
	}

	/**
	 * <p>Return the names of the hit fields as an array.  The first entry of the array
	 * is the static name <code>TOTAL</code></p>
	 *
	 * @return The array of the hit fields name
	 */
	public static String[] getHitFields() { return(HIT_FIELDS); }

	/**
	 * <p>Return the hit counts as an array, the first element being the total number of 
	 * hits for all fields</p>
	 * 
	 * @return The field hit counts as an array, the first entry in the array is the 
	 *         total number of hits for all fields
	 */	public static int[] getHitCounts() { return(HIT_COUNTS); }

	/**
	 * Get the hit count for a specific field - look at the <code>public static HIT_*</code>
	 * fields to retrieve a specific field.
	 *
	 * @param hitCountField the hit count field number to retrieve the hit count from
	 *
	 * @return the hit count for the field
	 * 
	 * <p>{@link #HIT_ID_ALL_TYPES Use <code>AllTypes.HIT_ID_ALL_TYPES</code> to retrieve the hit count for the <code>id_all_types</code> field}</p>
	 * <p>{@link #HIT_NUM_SMALLINT Use <code>AllTypes.HIT_NUM_SMALLINT</code> to retrieve the hit count for the <code>num_smallint</code> field}</p>
	 * <p>{@link #HIT_NUM_INTEGER Use <code>AllTypes.HIT_NUM_INTEGER</code> to retrieve the hit count for the <code>num_integer</code> field}</p>
	 * <p>{@link #HIT_NUM_BIGINT Use <code>AllTypes.HIT_NUM_BIGINT</code> to retrieve the hit count for the <code>num_bigint</code> field}</p>
	 * <p>{@link #HIT_NUM_DECIMAL Use <code>AllTypes.HIT_NUM_DECIMAL</code> to retrieve the hit count for the <code>num_decimal</code> field}</p>
	 * <p>{@link #HIT_NUM_NUMERIC Use <code>AllTypes.HIT_NUM_NUMERIC</code> to retrieve the hit count for the <code>num_numeric</code> field}</p>
	 * <p>{@link #HIT_FLT_REAL Use <code>AllTypes.HIT_FLT_REAL</code> to retrieve the hit count for the <code>flt_real</code> field}</p>
	 * <p>{@link #HIT_DBL_REAL Use <code>AllTypes.HIT_DBL_REAL</code> to retrieve the hit count for the <code>dbl_real</code> field}</p>
	 * <p>{@link #HIT_NUM_SERIAL Use <code>AllTypes.HIT_NUM_SERIAL</code> to retrieve the hit count for the <code>num_serial</code> field}</p>
	 * <p>{@link #HIT_NUM_SMALLSERIAL Use <code>AllTypes.HIT_NUM_SMALLSERIAL</code> to retrieve the hit count for the <code>num_smallserial</code> field}</p>
	 * <p>{@link #HIT_NUM_BIGSERIAL Use <code>AllTypes.HIT_NUM_BIGSERIAL</code> to retrieve the hit count for the <code>num_bigserial</code> field}</p>
	 */
	public static int getHitCountForField(int hitCountField) { return(HIT_COUNTS[hitCountField]); }

	/**
	 * <p>Update the field hit count.  Use the static variables <code>HIT_&lt;FIELD_NAME&gt;</code>
	 * variables for the offset.</p>
	 * 
	 * @param offset The offset of the <code>HIT_COUNTS</code> array to update
	 */
	private static void updateHitCount(int offset) {
		HIT_COUNTS[0]++;
		HIT_COUNTS[offset]++;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Boring ol' getters and setters 
	 * 
	 * Getters will update the hit count upon access.
	 * 
	 * Setters, if the passed in parameter's value differs will set the
	 * 'isDirty' flag
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * <p>Convenience method for returning the primary key field (which is the id_all_types field).</p>
	 * 
	 * <p><strong>NOTE:</strong> This will update the hit count.</p>
	 * 
	 * @return The value of the primary key
	 */
	public Long getPrimaryKey() {updateHitCount(1); return(this.idAllTypes); }

	/**
	 * <p>Set the primary key field (which is the id_all_types field).</p>
	 * 
	 * <p>If the primary key value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 * 
	 * <p>If the primary key value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 * 
	 * @param idUser The primary key field to update
	 */
	public void setPrimaryKey(Long idAllTypes) {if(isDifferent(this.idAllTypes, idAllTypes)) { this.idAllTypes = idAllTypes; this.isDirty = true;}}

	/**
	 * <p>Return the value of the id_all_types, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_all_types which may NOT be null.
	 */
	public Long getIdAllTypes() {updateHitCount(1); return(this.idAllTypes); }

	/**
	 * <p>Set the id_all_types value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param idAllTypes the id_all_types value to be set
	 */
	public void setIdAllTypes(Long idAllTypes) {if(isDifferent(this.idAllTypes, idAllTypes)) { this.idAllTypes = idAllTypes; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_smallint, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_smallint which may be null.
	 */
	public Short getNumSmallint() {updateHitCount(2); return(this.numSmallint); }

	/**
	 * <p>Set the num_smallint value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numSmallint the num_smallint value to be set
	 */
	public void setNumSmallint(Short numSmallint) {if(isDifferent(this.numSmallint, numSmallint)) { this.numSmallint = numSmallint; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_integer, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_integer which may be null.
	 */
	public Integer getNumInteger() {updateHitCount(3); return(this.numInteger); }

	/**
	 * <p>Set the num_integer value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numInteger the num_integer value to be set
	 */
	public void setNumInteger(Integer numInteger) {if(isDifferent(this.numInteger, numInteger)) { this.numInteger = numInteger; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_bigint, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_bigint which may be null.
	 */
	public Long getNumBigint() {updateHitCount(4); return(this.numBigint); }

	/**
	 * <p>Set the num_bigint value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numBigint the num_bigint value to be set
	 */
	public void setNumBigint(Long numBigint) {if(isDifferent(this.numBigint, numBigint)) { this.numBigint = numBigint; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_decimal, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_decimal which may be null.
	 */
	public BigDecimal getNumDecimal() {updateHitCount(5); return(this.numDecimal); }

	/**
	 * <p>Set the num_decimal value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numDecimal the num_decimal value to be set
	 */
	public void setNumDecimal(BigDecimal numDecimal) {if(isDifferent(this.numDecimal, numDecimal)) { this.numDecimal = numDecimal; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_numeric, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_numeric which may be null.
	 */
	public BigDecimal getNumNumeric() {updateHitCount(6); return(this.numNumeric); }

	/**
	 * <p>Set the num_numeric value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numNumeric the num_numeric value to be set
	 */
	public void setNumNumeric(BigDecimal numNumeric) {if(isDifferent(this.numNumeric, numNumeric)) { this.numNumeric = numNumeric; this.isDirty = true;}}

	/**
	 * <p>Return the value of the flt_real, updating the hit count for this field.</p>
	 * 
	 * @return the value of the flt_real which may be null.
	 */
	public Double getFltReal() {updateHitCount(7); return(this.fltReal); }

	/**
	 * <p>Set the flt_real value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param fltReal the flt_real value to be set
	 */
	public void setFltReal(Double fltReal) {if(isDifferent(this.fltReal, fltReal)) { this.fltReal = fltReal; this.isDirty = true;}}

	/**
	 * <p>Return the value of the dbl_real, updating the hit count for this field.</p>
	 * 
	 * @return the value of the dbl_real which may be null.
	 */
	public Double getDblReal() {updateHitCount(8); return(this.dblReal); }

	/**
	 * <p>Set the dbl_real value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param dblReal the dbl_real value to be set
	 */
	public void setDblReal(Double dblReal) {if(isDifferent(this.dblReal, dblReal)) { this.dblReal = dblReal; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_serial, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_serial which may NOT be null.
	 */
	public Integer getNumSerial() {updateHitCount(9); return(this.numSerial); }

	/**
	 * <p>Set the num_serial value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numSerial the num_serial value to be set
	 */
	public void setNumSerial(Integer numSerial) {if(isDifferent(this.numSerial, numSerial)) { this.numSerial = numSerial; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_smallserial, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_smallserial which may NOT be null.
	 */
	public Short getNumSmallserial() {updateHitCount(10); return(this.numSmallserial); }

	/**
	 * <p>Set the num_smallserial value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numSmallserial the num_smallserial value to be set
	 */
	public void setNumSmallserial(Short numSmallserial) {if(isDifferent(this.numSmallserial, numSmallserial)) { this.numSmallserial = numSmallserial; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_bigserial, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_bigserial which may NOT be null.
	 */
	public Long getNumBigserial() {updateHitCount(11); return(this.numBigserial); }

	/**
	 * <p>Set the num_bigserial value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numBigserial the num_bigserial value to be set
	 */
	public void setNumBigserial(Long numBigserial) {if(isDifferent(this.numBigserial, numBigserial)) { this.numBigserial = numBigserial; this.isDirty = true;}}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		validationBean.addValidationFieldBean(new SmallintValidator("num_smallint", numSmallint.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new IntegerValidator("num_integer", numInteger.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new BigintValidator("num_bigint", numBigint.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new DecimalValidator("num_decimal", numDecimal.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new NumericValidator("num_numeric", numNumeric.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new RealValidator("flt_real", fltReal.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new DoubleValidator("dbl_real", dblReal.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new SerialValidator("num_serial", numSerial.toString(), 0, 0, false).validate());
		validationBean.addValidationFieldBean(new SmallserialValidator("num_smallserial", numSmallserial.toString(), 0, 0, false).validate());
		validationBean.addValidationFieldBean(new BigserialValidator("num_bigserial", numBigserial.toString(), 0, 0, false).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		return(
			"{\"AllTypes\": {" +
			"\"idAllTypes\":\"" + this.idAllTypes + "\"" +
			"\"numSmallint\":\"" + this.numSmallint + "\"" +
			"\"numInteger\":\"" + this.numInteger + "\"" +
			"\"numBigint\":\"" + this.numBigint + "\"" +
			"\"numDecimal\":\"" + this.numDecimal + "\"" +
			"\"numNumeric\":\"" + this.numNumeric + "\"" +
			"\"fltReal\":\"" + this.fltReal + "\"" +
			"\"dblReal\":\"" + this.dblReal + "\"" +
			"\"numSerial\":\"" + this.numSerial + "\"" +
			"\"numSmallserial\":\"" + this.numSmallserial + "\"" +
			"\"numBigserial\":\"" + this.numBigserial + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "AllTypes");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addToJSONObject(fieldsObject, "idAllTypes", this.getIdAllTypes());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numSmallint", this.getNumSmallint());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numInteger", this.getNumInteger());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numBigint", this.getNumBigint());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numDecimal", this.getNumDecimal());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numNumeric", this.getNumNumeric());
		ModelBaseHelper.addToJSONObject(fieldsObject, "fltReal", this.getFltReal());
		ModelBaseHelper.addToJSONObject(fieldsObject, "dblReal", this.getDblReal());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numSerial", this.getNumSerial());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numSmallserial", this.getNumSmallserial());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numBigserial", this.getNumBigserial());

		jsonObject.put("fields", fieldsObject);

		return(jsonObject);
	}


	public String toJsonString() {
		return(toJSON().toString());
	}

	public String getJsonString() {
		return(toJsonString());
	}

	/**
	 * <p>Return an XML representation of the <code>AllTypes</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;all_types /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<all_types>" + 
			String.format("<id_all_types null=\"%b\">%s</id_all_types>", (this.getIdAllTypes() == null), (this.getIdAllTypes() != null ? this.getIdAllTypes() : "")) + 
			String.format("<num_smallint null=\"%b\">%s</num_smallint>", (this.getNumSmallint() == null), (this.getNumSmallint() != null ? this.getNumSmallint() : "")) + 
			String.format("<num_integer null=\"%b\">%s</num_integer>", (this.getNumInteger() == null), (this.getNumInteger() != null ? this.getNumInteger() : "")) + 
			String.format("<num_bigint null=\"%b\">%s</num_bigint>", (this.getNumBigint() == null), (this.getNumBigint() != null ? this.getNumBigint() : "")) + 
			String.format("<num_decimal null=\"%b\">%s</num_decimal>", (this.getNumDecimal() == null), (this.getNumDecimal() != null ? this.getNumDecimal() : "")) + 
			String.format("<num_numeric null=\"%b\">%s</num_numeric>", (this.getNumNumeric() == null), (this.getNumNumeric() != null ? this.getNumNumeric() : "")) + 
			String.format("<flt_real null=\"%b\">%s</flt_real>", (this.getFltReal() == null), (this.getFltReal() != null ? this.getFltReal() : "")) + 
			String.format("<dbl_real null=\"%b\">%s</dbl_real>", (this.getDblReal() == null), (this.getDblReal() != null ? this.getDblReal() : "")) + 
			String.format("<num_serial null=\"%b\">%s</num_serial>", (this.getNumSerial() == null), (this.getNumSerial() != null ? this.getNumSerial() : "")) + 
			String.format("<num_smallserial null=\"%b\">%s</num_smallserial>", (this.getNumSmallserial() == null), (this.getNumSmallserial() != null ? this.getNumSmallserial() : "")) + 
			String.format("<num_bigserial null=\"%b\">%s</num_bigserial>", (this.getNumBigserial() == null), (this.getNumBigserial() != null ? this.getNumBigserial() : "")) + 
			"</all_types>");
	}


	/**
	 * <p>Get the hit count statistics as a JSON encoded object as a <code>String</code></p>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "AllTypes");
		jsonObject.put("total", HIT_COUNTS[0]);
		jsonObject.put("idAllTypes", HIT_COUNTS[1]);
		jsonObject.put("numSmallint", HIT_COUNTS[2]);
		jsonObject.put("numInteger", HIT_COUNTS[3]);
		jsonObject.put("numBigint", HIT_COUNTS[4]);
		jsonObject.put("numDecimal", HIT_COUNTS[5]);
		jsonObject.put("numNumeric", HIT_COUNTS[6]);
		jsonObject.put("fltReal", HIT_COUNTS[7]);
		jsonObject.put("dblReal", HIT_COUNTS[8]);
		jsonObject.put("numSerial", HIT_COUNTS[9]);
		jsonObject.put("numSmallserial", HIT_COUNTS[10]);
		jsonObject.put("numBigserial", HIT_COUNTS[11]);
		return(jsonObject.toString());
	}

}