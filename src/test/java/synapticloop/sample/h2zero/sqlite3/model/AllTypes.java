package synapticloop.sample.h2zero.sqlite3.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.base.validator.bean.ValidationBean;
import synapticloop.h2zero.base.validator.*;
import synapticloop.h2zero.base.model.sqlite3.ModelBase;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import synapticloop.sample.h2zero.sqlite3.finder.AllTypesFinder;


/**
 * This is the model for the AllTypes which maps to the all_types database table
 * and contains the default CRUD methods.
 */
 public class AllTypes extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.ALL_TYPES_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_all_types";  // the primary key - a convenience field

	private static final String SQL_INSERT = "insert into all_types (test_bigint, test_boolean, test_date, test_datetime, test_double, test_float, test_int, test_integer, test_mediumint, test_numeric, test_smallint, test_text, test_tinyint, test_varchar) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update all_types set test_bigint = ?, test_boolean = ?, test_date = ?, test_datetime = ?, test_double = ?, test_float = ?, test_int = ?, test_integer = ?, test_mediumint = ?, test_numeric = ?, test_smallint = ?, test_text = ?, test_tinyint = ?, test_varchar = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from all_types where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from all_types where test_bigint = ? and test_boolean = ? and test_date = ? and test_datetime = ? and test_double = ? and test_float = ? and test_int = ? and test_integer = ? and test_mediumint = ? and test_numeric = ? and test_smallint = ? and test_text = ? and test_tinyint = ? and test_varchar = ?";


// Static lookups for fields in the hit counter.
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_ALL_TYPES = 1;
	public static final int HIT_TEST_BIGINT = 2;
	public static final int HIT_TEST_BOOLEAN = 3;
	public static final int HIT_TEST_DATE = 4;
	public static final int HIT_TEST_DATETIME = 5;
	public static final int HIT_TEST_DOUBLE = 6;
	public static final int HIT_TEST_FLOAT = 7;
	public static final int HIT_TEST_INT = 8;
	public static final int HIT_TEST_INTEGER = 9;
	public static final int HIT_TEST_MEDIUMINT = 10;
	public static final int HIT_TEST_NUMERIC = 11;
	public static final int HIT_TEST_SMALLINT = 12;
	public static final int HIT_TEST_TEXT = 13;
	public static final int HIT_TEST_TINYINT = 14;
	public static final int HIT_TEST_VARCHAR = 15;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_all_types", "test_bigint", "test_boolean", "test_date", "test_datetime", "test_double", "test_float", "test_int", "test_integer", "test_mediumint", "test_numeric", "test_smallint", "test_text", "test_tinyint", "test_varchar" };
	// the number of read-hits for a particular field
	private static int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


	private Long idAllTypes = null; // maps to the id_all_types field
	private Long testBigint = null; // maps to the test_bigint field
	private Boolean testBoolean = null; // maps to the test_boolean field
	private Date testDate = null; // maps to the test_date field
	private Timestamp testDatetime = null; // maps to the test_datetime field
	private Double testDouble = null; // maps to the test_double field
	private Float testFloat = null; // maps to the test_float field
	private Integer testInt = null; // maps to the test_int field
	private Integer testInteger = null; // maps to the test_integer field
	private Integer testMediumint = null; // maps to the test_mediumint field
	private BigDecimal testNumeric = null; // maps to the test_numeric field
	private Short testSmallint = null; // maps to the test_smallint field
	private String testText = null; // maps to the test_text field
	private Boolean testTinyint = null; // maps to the test_tinyint field
	private String testVarchar = null; // maps to the test_varchar field

	public AllTypes(Long idAllTypes, Long testBigint, Boolean testBoolean, Date testDate, Timestamp testDatetime, Double testDouble, Float testFloat, Integer testInt, Integer testInteger, Integer testMediumint, BigDecimal testNumeric, Short testSmallint, String testText, Boolean testTinyint, String testVarchar) {
		this.idAllTypes = idAllTypes;
		this.testBigint = testBigint;
		this.testBoolean = testBoolean;
		this.testDate = testDate;
		this.testDatetime = testDatetime;
		this.testDouble = testDouble;
		this.testFloat = testFloat;
		this.testInt = testInt;
		this.testInteger = testInteger;
		this.testMediumint = testMediumint;
		this.testNumeric = testNumeric;
		this.testSmallint = testSmallint;
		this.testText = testText;
		this.testTinyint = testTinyint;
		this.testVarchar = testVarchar;
	}

	public AllTypes(Long idAllTypes) {
		this.idAllTypes = idAllTypes;
		this.testBigint = null;
		this.testBoolean = null;
		this.testDate = null;
		this.testDatetime = null;
		this.testDouble = null;
		this.testFloat = null;
		this.testInt = null;
		this.testInteger = null;
		this.testMediumint = null;
		this.testNumeric = null;
		this.testSmallint = null;
		this.testText = null;
		this.testTinyint = null;
		this.testVarchar = null;
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
			ConnectionManager.setBigint(preparedStatement, 1, testBigint);
			ConnectionManager.setBoolean(preparedStatement, 2, testBoolean);
			ConnectionManager.setDate(preparedStatement, 3, testDate);
			ConnectionManager.setDatetime(preparedStatement, 4, testDatetime);
			ConnectionManager.setDouble(preparedStatement, 5, testDouble);
			ConnectionManager.setFloat(preparedStatement, 6, testFloat);
			ConnectionManager.setInt(preparedStatement, 7, testInt);
			ConnectionManager.setInteger(preparedStatement, 8, testInteger);
			ConnectionManager.setMediumint(preparedStatement, 9, testMediumint);
			ConnectionManager.setNumeric(preparedStatement, 10, testNumeric);
			ConnectionManager.setSmallint(preparedStatement, 11, testSmallint);
			ConnectionManager.setText(preparedStatement, 12, testText);
			ConnectionManager.setTinyint(preparedStatement, 13, testTinyint);
			ConnectionManager.setVarchar(preparedStatement, 14, testVarchar);
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
			ConnectionManager.setBigint(preparedStatement, 1, testBigint);
			ConnectionManager.setBoolean(preparedStatement, 2, testBoolean);
			ConnectionManager.setDate(preparedStatement, 3, testDate);
			ConnectionManager.setDatetime(preparedStatement, 4, testDatetime);
			ConnectionManager.setDouble(preparedStatement, 5, testDouble);
			ConnectionManager.setFloat(preparedStatement, 6, testFloat);
			ConnectionManager.setInt(preparedStatement, 7, testInt);
			ConnectionManager.setInteger(preparedStatement, 8, testInteger);
			ConnectionManager.setMediumint(preparedStatement, 9, testMediumint);
			ConnectionManager.setNumeric(preparedStatement, 10, testNumeric);
			ConnectionManager.setSmallint(preparedStatement, 11, testSmallint);
			ConnectionManager.setText(preparedStatement, 12, testText);
			ConnectionManager.setTinyint(preparedStatement, 13, testTinyint);
			ConnectionManager.setVarchar(preparedStatement, 14, testVarchar);
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
				ConnectionManager.setBigint(preparedStatement, 1, testBigint);
				ConnectionManager.setBoolean(preparedStatement, 2, testBoolean);
				ConnectionManager.setDate(preparedStatement, 3, testDate);
				ConnectionManager.setDatetime(preparedStatement, 4, testDatetime);
				ConnectionManager.setDouble(preparedStatement, 5, testDouble);
				ConnectionManager.setFloat(preparedStatement, 6, testFloat);
				ConnectionManager.setInt(preparedStatement, 7, testInt);
				ConnectionManager.setInteger(preparedStatement, 8, testInteger);
				ConnectionManager.setMediumint(preparedStatement, 9, testMediumint);
				ConnectionManager.setNumeric(preparedStatement, 10, testNumeric);
				ConnectionManager.setSmallint(preparedStatement, 11, testSmallint);
				ConnectionManager.setText(preparedStatement, 12, testText);
				ConnectionManager.setTinyint(preparedStatement, 13, testTinyint);
				ConnectionManager.setVarchar(preparedStatement, 14, testVarchar);
				// now set the primary key
				preparedStatement.setLong(15, idAllTypes);
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
		this.testBigint = allTypes.getTestBigint();
		this.testBoolean = allTypes.getTestBoolean();
		this.testDate = allTypes.getTestDate();
		this.testDatetime = allTypes.getTestDatetime();
		this.testDouble = allTypes.getTestDouble();
		this.testFloat = allTypes.getTestFloat();
		this.testInt = allTypes.getTestInt();
		this.testInteger = allTypes.getTestInteger();
		this.testMediumint = allTypes.getTestMediumint();
		this.testNumeric = allTypes.getTestNumeric();
		this.testSmallint = allTypes.getTestSmallint();
		this.testText = allTypes.getTestText();
		this.testTinyint = allTypes.getTestTinyint();
		this.testVarchar = allTypes.getTestVarchar();
	}

	public static String[] getHitFields() { return(HIT_FIELDS); }
	public static int[] getHitCounts() { return(HIT_COUNTS); }

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
	public Long getTestBigint() { updateHitCount(2); return(this.testBigint); }
	public void setTestBigint(Long testBigint) { if(isDifferent(this.testBigint, testBigint)) { this.testBigint = testBigint;this.isDirty = true; }}
	public Boolean getTestBoolean() { updateHitCount(3); return(this.testBoolean); }
	public void setTestBoolean(Boolean testBoolean) { if(isDifferent(this.testBoolean, testBoolean)) { this.testBoolean = testBoolean;this.isDirty = true; }}
	public Date getTestDate() { updateHitCount(4); return(this.testDate); }
	public void setTestDate(Date testDate) { if(isDifferent(this.testDate, testDate)) { this.testDate = testDate;this.isDirty = true; }}
	public Timestamp getTestDatetime() { updateHitCount(5); return(this.testDatetime); }
	public void setTestDatetime(Timestamp testDatetime) { if(isDifferent(this.testDatetime, testDatetime)) { this.testDatetime = testDatetime;this.isDirty = true; }}
	public Double getTestDouble() { updateHitCount(6); return(this.testDouble); }
	public void setTestDouble(Double testDouble) { if(isDifferent(this.testDouble, testDouble)) { this.testDouble = testDouble;this.isDirty = true; }}
	public Float getTestFloat() { updateHitCount(7); return(this.testFloat); }
	public void setTestFloat(Float testFloat) { if(isDifferent(this.testFloat, testFloat)) { this.testFloat = testFloat;this.isDirty = true; }}
	public Integer getTestInt() { updateHitCount(8); return(this.testInt); }
	public void setTestInt(Integer testInt) { if(isDifferent(this.testInt, testInt)) { this.testInt = testInt;this.isDirty = true; }}
	public Integer getTestInteger() { updateHitCount(9); return(this.testInteger); }
	public void setTestInteger(Integer testInteger) { if(isDifferent(this.testInteger, testInteger)) { this.testInteger = testInteger;this.isDirty = true; }}
	public Integer getTestMediumint() { updateHitCount(10); return(this.testMediumint); }
	public void setTestMediumint(Integer testMediumint) { if(isDifferent(this.testMediumint, testMediumint)) { this.testMediumint = testMediumint;this.isDirty = true; }}
	public BigDecimal getTestNumeric() { updateHitCount(11); return(this.testNumeric); }
	public void setTestNumeric(BigDecimal testNumeric) { if(isDifferent(this.testNumeric, testNumeric)) { this.testNumeric = testNumeric;this.isDirty = true; }}
	public Short getTestSmallint() { updateHitCount(12); return(this.testSmallint); }
	public void setTestSmallint(Short testSmallint) { if(isDifferent(this.testSmallint, testSmallint)) { this.testSmallint = testSmallint;this.isDirty = true; }}
	public String getTestText() { updateHitCount(13); return(this.testText); }
	public void setTestText(String testText) { if(isDifferent(this.testText, testText)) { this.testText = testText;this.isDirty = true; }}
	public Boolean getTestTinyint() { updateHitCount(14); return(this.testTinyint); }
	public void setTestTinyint(Boolean testTinyint) { if(isDifferent(this.testTinyint, testTinyint)) { this.testTinyint = testTinyint;this.isDirty = true; }}
	public String getTestVarchar() { updateHitCount(15); return(this.testVarchar); }
	public void setTestVarchar(String testVarchar) { if(isDifferent(this.testVarchar, testVarchar)) { this.testVarchar = testVarchar;this.isDirty = true; }}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		validationBean.addValidationFieldBean(new BigintValidator("test_bigint", testBigint.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new BooleanValidator("test_boolean", testBoolean.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new DateValidator("test_date", testDate.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new DatetimeValidator("test_datetime", testDatetime.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new DoubleValidator("test_double", testDouble.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new FloatValidator("test_float", testFloat.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new IntValidator("test_int", testInt.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new IntegerValidator("test_integer", testInteger.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new MediumintValidator("test_mediumint", testMediumint.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new NumericValidator("test_numeric", testNumeric.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new SmallintValidator("test_smallint", testSmallint.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new TextValidator("test_text", testText.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new TinyintValidator("test_tinyint", testTinyint.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new VarcharValidator("test_varchar", testVarchar.toString(), 0, 0, true).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Model: 'AllTypes'\n")
			.append("  Field: 'idAllTypes:").append(this.idAllTypes).append("'\n")
			.append("  Field: 'testBigint:").append(this.testBigint).append("'\n")
			.append("  Field: 'testBoolean:").append(this.testBoolean).append("'\n")
			.append("  Field: 'testDate:").append(this.testDate).append("'\n")
			.append("  Field: 'testDatetime:").append(this.testDatetime).append("'\n")
			.append("  Field: 'testDouble:").append(this.testDouble).append("'\n")
			.append("  Field: 'testFloat:").append(this.testFloat).append("'\n")
			.append("  Field: 'testInt:").append(this.testInt).append("'\n")
			.append("  Field: 'testInteger:").append(this.testInteger).append("'\n")
			.append("  Field: 'testMediumint:").append(this.testMediumint).append("'\n")
			.append("  Field: 'testNumeric:").append(this.testNumeric).append("'\n")
			.append("  Field: 'testSmallint:").append(this.testSmallint).append("'\n")
			.append("  Field: 'testText:").append(this.testText).append("'\n")
			.append("  Field: 'testTinyint:").append(this.testTinyint).append("'\n")
			.append("  Field: 'testVarchar:").append(this.testVarchar).append("'\n")
			;
		return(stringBuilder.toString());
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
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testBigint", this.getTestBigint());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testBoolean", this.getTestBoolean());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testDate", this.getTestDate());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testDatetime", this.getTestDatetime());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testDouble", this.getTestDouble());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testFloat", this.getTestFloat());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testInt", this.getTestInt());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testInteger", this.getTestInteger());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testMediumint", this.getTestMediumint());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testNumeric", this.getTestNumeric());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testSmallint", this.getTestSmallint());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testText", this.getTestText());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testTinyint", this.getTestTinyint());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "testVarchar", this.getTestVarchar());

		jsonObject.put("fields", fieldsObject);

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
		jsonObject.put("testBigint", HIT_COUNTS[2]);
		jsonObject.put("testBoolean", HIT_COUNTS[3]);
		jsonObject.put("testDate", HIT_COUNTS[4]);
		jsonObject.put("testDatetime", HIT_COUNTS[5]);
		jsonObject.put("testDouble", HIT_COUNTS[6]);
		jsonObject.put("testFloat", HIT_COUNTS[7]);
		jsonObject.put("testInt", HIT_COUNTS[8]);
		jsonObject.put("testInteger", HIT_COUNTS[9]);
		jsonObject.put("testMediumint", HIT_COUNTS[10]);
		jsonObject.put("testNumeric", HIT_COUNTS[11]);
		jsonObject.put("testSmallint", HIT_COUNTS[12]);
		jsonObject.put("testText", HIT_COUNTS[13]);
		jsonObject.put("testTinyint", HIT_COUNTS[14]);
		jsonObject.put("testVarchar", HIT_COUNTS[15]);
		return(jsonObject.toString());
	}

}