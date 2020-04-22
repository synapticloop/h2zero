package synapticloop.sample.h2zero.cockroach.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import synapticloop.h2zero.base.validator.bean.ValidationBean;
import synapticloop.h2zero.base.validator.*;
import synapticloop.h2zero.base.model.cockroach.ModelBase;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.cockroach.model.util.Constants;

import synapticloop.sample.h2zero.cockroach.finder.AllTypesFinder;


public class AllTypes extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_all_types";

	private static final String SQL_INSERT = "insert into all_types (num_smallint, num_integer, num_bigint, num_decimal, num_numeric, flt_real, dbl_real, num_serial, num_smallserial, num_bigserial) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update all_types set num_smallint = ?, num_integer = ?, num_bigint = ?, num_decimal = ?, num_numeric = ?, flt_real = ?, dbl_real = ?, num_serial = ?, num_smallserial = ?, num_bigserial = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from all_types where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from all_types where num_smallint = ? and num_integer = ? and num_bigint = ? and num_decimal = ? and num_numeric = ? and flt_real = ? and dbl_real = ? and num_serial = ? and num_smallserial = ? and num_bigserial = ?";


// Static lookups for fields in the hit counter.
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
	private static int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


	private Long idAllTypes = null;
	private Short numSmallint = null;
	private Integer numInteger = null;
	private Long numBigint = null;
	private Double numDecimal = null;
	private Float numNumeric = null;
	private Double fltReal = null;
	private Double dblReal = null;
	private Integer numSerial = null;
	private Short numSmallserial = null;
	private Long numBigserial = null;

	public AllTypes(Long idAllTypes, Short numSmallint, Integer numInteger, Long numBigint, Double numDecimal, Float numNumeric, Double fltReal, Double dblReal, Integer numSerial, Short numSmallserial, Long numBigserial) {
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

	@Override
	public boolean primaryKeySet() {
		return(null != idAllTypes);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert all_types model when primary key is not null.");
		}
		// create this bean 
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
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
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		if(resultSet.next()) {
			this.idAllTypes = resultSet.getLong(1);
		} else {
			throw new H2ZeroPrimaryKeyException("Could not get return value for primary key!");
		}
		ConnectionManager.closeAll(resultSet, preparedStatement);
	}

	@Override
	public void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_ENSURE);
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
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			this.idAllTypes = resultSet.getLong(1);
		} else {
			// could not find the value - need to insert it - null is the primary key
			insert(connection);
		}
		ConnectionManager.closeAll(resultSet, preparedStatement);
	}

	@Override
	public void update(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot update bean when primary key is null.");
		}
		if(isDirty) {
			// update this bean, but only if dirty
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
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
			ConnectionManager.closeAll(preparedStatement);
			isDirty = false;
		}
	}

	@Override
	public void delete(Connection connection) throws SQLException, H2ZeroPrimaryKeyException{
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot delete bean when primary key is null.");
		}
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
		preparedStatement.setLong(1, idAllTypes);
		preparedStatement.executeUpdate();
		ConnectionManager.closeAll(preparedStatement);
	}

	@Override
	public void refresh(Connection connection) throws H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh bean when primary key is null.");
		}
		AllTypes allTypes = AllTypesFinder.findByPrimaryKeySilent(connection, this.idAllTypes);
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

	public static void updateHitCount(int offset) {
		HIT_COUNTS[0]++;
		HIT_COUNTS[offset]++;
	}

	/*
	 * Boring ol' getters and setters 
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
	public Double getNumDecimal() { updateHitCount(5); return(this.numDecimal); }
	public void setNumDecimal(Double numDecimal) { if(isDifferent(this.numDecimal, numDecimal)) { this.numDecimal = numDecimal;this.isDirty = true; }}
	public Float getNumNumeric() { updateHitCount(6); return(this.numNumeric); }
	public void setNumNumeric(Float numNumeric) { if(isDifferent(this.numNumeric, numNumeric)) { this.numNumeric = numNumeric;this.isDirty = true; }}
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model[AllTypes]\n");
		stringBuilder.append("  Field[idAllTypes:" + this.idAllTypes + "]\n");
		stringBuilder.append("  Field[numSmallint:" + this.numSmallint + "]\n");
		stringBuilder.append("  Field[numInteger:" + this.numInteger + "]\n");
		stringBuilder.append("  Field[numBigint:" + this.numBigint + "]\n");
		stringBuilder.append("  Field[numDecimal:" + this.numDecimal + "]\n");
		stringBuilder.append("  Field[numNumeric:" + this.numNumeric + "]\n");
		stringBuilder.append("  Field[fltReal:" + this.fltReal + "]\n");
		stringBuilder.append("  Field[dblReal:" + this.dblReal + "]\n");
		stringBuilder.append("  Field[numSerial:" + this.numSerial + "]\n");
		stringBuilder.append("  Field[numSmallserial:" + this.numSmallserial + "]\n");
		stringBuilder.append("  Field[numBigserial:" + this.numBigserial + "]\n");
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "AllTypes");

		ModelBaseHelper.addtoJSONObject(jsonObject, "idAllTypes", this.getIdAllTypes());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numSmallint", this.getNumSmallint());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numInteger", this.getNumInteger());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numBigint", this.getNumBigint());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numDecimal", this.getNumDecimal());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numNumeric", this.getNumNumeric());
		ModelBaseHelper.addtoJSONObject(jsonObject, "fltReal", this.getFltReal());
		ModelBaseHelper.addtoJSONObject(jsonObject, "dblReal", this.getDblReal());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numSerial", this.getNumSerial());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numSmallserial", this.getNumSmallserial());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numBigserial", this.getNumBigserial());
		return(jsonObject);
	}


	public String toJsonString() {
		return(toJSON().toString());
	}

	public String getJsonString() {
		return(toJsonString());
	}

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