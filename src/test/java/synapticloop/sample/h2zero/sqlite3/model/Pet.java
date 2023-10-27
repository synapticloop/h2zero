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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import synapticloop.sample.h2zero.sqlite3.finder.PetFinder;


/**
 * This is the model for the Pet which maps to the pet database table
 * and contains the default CRUD methods.
 */
 public class Pet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_pet";  // the primary key - a convenience field

	private static final String SQL_INSERT = "insert into pet (nm_pet, num_age, flt_weight, dt_birthday) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update pet set nm_pet = ?, num_age = ?, flt_weight = ?, dt_birthday = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from pet where nm_pet = ? and num_age = ? and flt_weight = ? and dt_birthday = ?";


// Static lookups for fields in the hit counter.
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_PET = 1;
	public static final int HIT_NM_PET = 2;
	public static final int HIT_NUM_AGE = 3;
	public static final int HIT_FLT_WEIGHT = 4;
	public static final int HIT_DT_BIRTHDAY = 5;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_pet", "nm_pet", "num_age", "flt_weight", "dt_birthday" };
	// the number of read-hits for a particular field
	private static int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0 };


	private Long idPet = null; // maps to the id_pet field
	private String nmPet = null; // maps to the nm_pet field
	private Integer numAge = null; // maps to the num_age field
	private Float fltWeight = null; // maps to the flt_weight field
	private Date dtBirthday = null; // maps to the dt_birthday field

	public Pet(Long idPet, String nmPet, Integer numAge, Float fltWeight, Date dtBirthday) {
		this.idPet = idPet;
		this.nmPet = nmPet;
		this.numAge = numAge;
		this.fltWeight = fltWeight;
		this.dtBirthday = dtBirthday;
	}

	public Pet(Long idPet, String nmPet, Integer numAge) {
		this.idPet = idPet;
		this.nmPet = nmPet;
		this.numAge = numAge;
		this.fltWeight = null;
		this.dtBirthday = null;
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idPet);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert pet model when primary key is not null.");
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// create this bean 
			preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
			ConnectionManager.setInt(preparedStatement, 2, numAge);
			ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
			ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idPet = resultSet.getLong(1);
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
			ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
			ConnectionManager.setInt(preparedStatement, 2, numAge);
			ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
			ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idPet = resultSet.getLong(1);
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
				ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
				ConnectionManager.setInt(preparedStatement, 2, numAge);
				ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
				ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
				// now set the primary key
				preparedStatement.setLong(5, idPet);
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
			preparedStatement.setLong(1, idPet);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'Pet' when primary key is null.");
		}

		Pet pet = PetFinder.findByPrimaryKeySilent(connection, this.idPet);
		if(null == pet) {
			throw new H2ZeroFinderException("Could not find the model 'Pet' with primaryKey of " + getPrimaryKey());
		}
		this.idPet = pet.getIdPet();
		this.nmPet = pet.getNmPet();
		this.numAge = pet.getNumAge();
		this.fltWeight = pet.getFltWeight();
		this.dtBirthday = pet.getDtBirthday();
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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idPet); }
	public void setPrimaryKey(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; }}
	public Long getIdPet() { updateHitCount(1); return(this.idPet); }
	public void setIdPet(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; }}
	public String getNmPet() { updateHitCount(2); return(this.nmPet); }
	public void setNmPet(String nmPet) { if(isDifferent(this.nmPet, nmPet)) { this.nmPet = nmPet;this.isDirty = true; }}
	public Integer getNumAge() { updateHitCount(3); return(this.numAge); }
	public void setNumAge(Integer numAge) { if(isDifferent(this.numAge, numAge)) { this.numAge = numAge;this.isDirty = true; }}
	public Float getFltWeight() { updateHitCount(4); return(this.fltWeight); }
	public void setFltWeight(Float fltWeight) { if(isDifferent(this.fltWeight, fltWeight)) { this.fltWeight = fltWeight;this.isDirty = true; }}
	public Date getDtBirthday() { updateHitCount(5); return(this.dtBirthday); }
	public void setDtBirthday(Date dtBirthday) { if(isDifferent(this.dtBirthday, dtBirthday)) { this.dtBirthday = dtBirthday;this.isDirty = true; }}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		validationBean.addValidationFieldBean(new VarcharValidator("nm_pet", nmPet.toString(), 0, 64, false).validate());
		validationBean.addValidationFieldBean(new IntValidator("num_age", numAge.toString(), 0, 0, false).validate());
		validationBean.addValidationFieldBean(new FloatValidator("flt_weight", fltWeight.toString(), 0, 6, true).validate());
		validationBean.addValidationFieldBean(new DateValidator("dt_birthday", dtBirthday.toString(), 0, 0, true).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Model: 'Pet'\n")
			.append("  Field: 'idPet:").append(this.idPet).append("'\n")
			.append("  Field: 'nmPet:").append(this.nmPet).append("'\n")
			.append("  Field: 'numAge:").append(this.numAge).append("'\n")
			.append("  Field: 'fltWeight:").append(this.fltWeight).append("'\n")
			.append("  Field: 'dtBirthday:").append(this.dtBirthday).append("'\n")
			;
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "Pet");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idPet", this.getIdPet());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmPet", this.getNmPet());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numAge", this.getNumAge());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "fltWeight", this.getFltWeight());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "dtBirthday", this.getDtBirthday());

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
		jsonObject.put("type", "Pet");
		jsonObject.put("total", HIT_COUNTS[0]);
		jsonObject.put("idPet", HIT_COUNTS[1]);
		jsonObject.put("nmPet", HIT_COUNTS[2]);
		jsonObject.put("numAge", HIT_COUNTS[3]);
		jsonObject.put("fltWeight", HIT_COUNTS[4]);
		jsonObject.put("dtBirthday", HIT_COUNTS[5]);
		return(jsonObject.toString());
	}

}