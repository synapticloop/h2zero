package com.synapticloop.sample.h2zero.postgresql.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

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
import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

import com.synapticloop.sample.h2zero.postgresql.finder.AllTypesFinder;


/**
 * <p>This is the model for the <code>AllTypes</code> which maps to the <code>all_types</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
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
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_ALL_TYPES = 1;
	public static final int HIT_NUM_SMALLINT = 2;
	public static final int HIT_NUM_INTEGER = 3;
	public static final int HIT_NUM_BIGINT = 4;
	public static final int HIT_NUM_DECIMAL = 5;
	public static final int HIT_NUM_NUMERIC = 6;
	public static final int HIT_FLT_REAL = 7;
	public static final int HIT_DBL_REAL = 8;
	public static final int HIT_NUM_SERIAL = 9;
	public static final int HIT_NUM_SMALLSERIAL = 10;
	public static final int HIT_NUM_BIGSERIAL = 11;


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

		AllTypes allTypes = AllTypesFinder.findByPrimaryKeySilent(connection, this.idAllTypes);
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

	public static String[] getHitFields() { return(HIT_FIELDS); }
	public static int[] getHitCounts() { return(HIT_COUNTS); }

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

	public static void updateHitCount(int offset) {
		HIT_COUNTS[0]++;
		HIT_COUNTS[offset]++;
	}

	/*
	 * Boring ol' getters and setters 
	 * 
	 * On setting any of these fields - the 'isDirty' flag will be set
	 * 
	 */

	public Long getPrimaryKey() { updateHitCount(1); return(this.idAllTypes); }
	public void setPrimaryKey(Long idAllTypes) { if(isDifferent(this.idAllTypes, idAllTypes)) { this.idAllTypes = idAllTypes;this.isDirty = true; }}
	public Long getIdAllTypes() { updateHitCount(1); return(this.idAllTypes); }
	public void setIdAllTypes(Long idAllTypes) { if(isDifferent(this.idAllTypes, idAllTypes)) { this.idAllTypes = idAllTypes;this.isDirty = true; }}
	public Short getNumSmallint() { updateHitCount(2); return(this.numSmallint); }
	public void setNumSmallint(Short numSmallint) { if(isDifferent(this.numSmallint, numSmallint)) { this.numSmallint = numSmallint;this.isDirty = true; }}
	public Integer getNumInteger() { updateHitCount(3); return(this.numInteger); }
	public void setNumInteger(Integer numInteger) { if(isDifferent(this.numInteger, numInteger)) { this.numInteger = numInteger;this.isDirty = true; }}
	public Long getNumBigint() { updateHitCount(4); return(this.numBigint); }
	public void setNumBigint(Long numBigint) { if(isDifferent(this.numBigint, numBigint)) { this.numBigint = numBigint;this.isDirty = true; }}
	public BigDecimal getNumDecimal() { updateHitCount(5); return(this.numDecimal); }
	public void setNumDecimal(BigDecimal numDecimal) { if(isDifferent(this.numDecimal, numDecimal)) { this.numDecimal = numDecimal;this.isDirty = true; }}
	public BigDecimal getNumNumeric() { updateHitCount(6); return(this.numNumeric); }
	public void setNumNumeric(BigDecimal numNumeric) { if(isDifferent(this.numNumeric, numNumeric)) { this.numNumeric = numNumeric;this.isDirty = true; }}
	public Double getFltReal() { updateHitCount(7); return(this.fltReal); }
	public void setFltReal(Double fltReal) { if(isDifferent(this.fltReal, fltReal)) { this.fltReal = fltReal;this.isDirty = true; }}
	public Double getDblReal() { updateHitCount(8); return(this.dblReal); }
	public void setDblReal(Double dblReal) { if(isDifferent(this.dblReal, dblReal)) { this.dblReal = dblReal;this.isDirty = true; }}
	public Integer getNumSerial() { updateHitCount(9); return(this.numSerial); }
	public void setNumSerial(Integer numSerial) { if(isDifferent(this.numSerial, numSerial)) { this.numSerial = numSerial;this.isDirty = true; }}
	public Short getNumSmallserial() { updateHitCount(10); return(this.numSmallserial); }
	public void setNumSmallserial(Short numSmallserial) { if(isDifferent(this.numSmallserial, numSmallserial)) { this.numSmallserial = numSmallserial;this.isDirty = true; }}
	public Long getNumBigserial() { updateHitCount(11); return(this.numBigserial); }
	public void setNumBigserial(Long numBigserial) { if(isDifferent(this.numBigserial, numBigserial)) { this.numBigserial = numBigserial;this.isDirty = true; }}

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

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idAllTypes", this.getIdAllTypes());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numSmallint", this.getNumSmallint());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numInteger", this.getNumInteger());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numBigint", this.getNumBigint());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numDecimal", this.getNumDecimal());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numNumeric", this.getNumNumeric());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "fltReal", this.getFltReal());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "dblReal", this.getDblReal());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numSerial", this.getNumSerial());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numSmallserial", this.getNumSmallserial());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numBigserial", this.getNumBigserial());

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
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
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