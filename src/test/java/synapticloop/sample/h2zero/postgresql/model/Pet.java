package synapticloop.sample.h2zero.postgresql.model;

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
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.postgresql.model.util.Constants;

import synapticloop.sample.h2zero.postgresql.finder.PetFinder;


public class Pet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_pet";

	private static final String SQL_INSERT = "insert into pet (nm_pet, num_age, flt_weight, dt_birthday, img_photo) values (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update pet set nm_pet = ?, num_age = ?, flt_weight = ?, dt_birthday = ?, img_photo = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from pet where nm_pet = ? and num_age = ? and flt_weight = ? and dt_birthday = ? and img_photo = ?";


// Static lookups for fields in the hit counter.
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_PET = 1;
	public static final int HIT_NM_PET = 2;
	public static final int HIT_NUM_AGE = 3;
	public static final int HIT_FLT_WEIGHT = 4;
	public static final int HIT_DT_BIRTHDAY = 5;
	public static final int HIT_IMG_PHOTO = 6;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_pet", "nm_pet", "num_age", "flt_weight", "dt_birthday", "img_photo" };
	// the number of read-hits for a particular field
	private static int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0 };


	private Long idPet = null;
	private String nmPet = null;
	private Integer numAge = null;
	private BigDecimal fltWeight = null;
	private Date dtBirthday = null;
	private Blob imgPhoto = null;

	public Pet(Long idPet, String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, Blob imgPhoto) {
		this.idPet = idPet;
		this.nmPet = nmPet;
		this.numAge = numAge;
		this.fltWeight = fltWeight;
		this.dtBirthday = dtBirthday;
		this.imgPhoto = imgPhoto;
	}

	public Pet(Long idPet, String nmPet, Integer numAge) {
		this.idPet = idPet;
		this.nmPet = nmPet;
		this.numAge = numAge;
		this.fltWeight = null;
		this.dtBirthday = null;
		this.imgPhoto = null;
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
		// create this bean 
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
		ConnectionManager.setInt(preparedStatement, 2, numAge);
		ConnectionManager.setNumeric(preparedStatement, 3, fltWeight);
		ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
		ConnectionManager.setBlob(preparedStatement, 5, imgPhoto);
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		if(resultSet.next()) {
			this.idPet = resultSet.getLong(1);
		} else {
			throw new H2ZeroPrimaryKeyException("Could not get return value for primary key!");
		}
		ConnectionManager.closeAll(resultSet, preparedStatement);
	}

	@Override
	public void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_ENSURE);
		ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
		ConnectionManager.setInt(preparedStatement, 2, numAge);
		ConnectionManager.setNumeric(preparedStatement, 3, fltWeight);
		ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
		ConnectionManager.setBlob(preparedStatement, 5, imgPhoto);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			this.idPet = resultSet.getLong(1);
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
			ConnectionManager.setVarchar(preparedStatement, 1, nmPet);
			ConnectionManager.setInt(preparedStatement, 2, numAge);
			ConnectionManager.setNumeric(preparedStatement, 3, fltWeight);
			ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
			ConnectionManager.setBlob(preparedStatement, 5, imgPhoto);
			// now set the primary key
			preparedStatement.setLong(6, idPet);
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
		preparedStatement.setLong(1, idPet);
		preparedStatement.executeUpdate();
		ConnectionManager.closeAll(preparedStatement);
	}

	@Override
	public void refresh(Connection connection) throws H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh bean when primary key is null.");
		}
		Pet pet = PetFinder.findByPrimaryKeySilent(connection, this.idPet);
		this.idPet = pet.getIdPet();
		this.nmPet = pet.getNmPet();
		this.numAge = pet.getNumAge();
		this.fltWeight = pet.getFltWeight();
		this.dtBirthday = pet.getDtBirthday();
		this.imgPhoto = pet.getImgPhoto();
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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idPet); }
	public void setPrimaryKey(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; }}
	public Long getIdPet() { updateHitCount(1); return(this.idPet); }
	public void setIdPet(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; }}
	public String getNmPet() { updateHitCount(2); return(this.nmPet); }
	public void setNmPet(String nmPet) { if(isDifferent(this.nmPet, nmPet)) { this.nmPet = nmPet;this.isDirty = true; }}
	public Integer getNumAge() { updateHitCount(3); return(this.numAge); }
	public void setNumAge(Integer numAge) { if(isDifferent(this.numAge, numAge)) { this.numAge = numAge;this.isDirty = true; }}
	public BigDecimal getFltWeight() { updateHitCount(4); return(this.fltWeight); }
	public void setFltWeight(BigDecimal fltWeight) { if(isDifferent(this.fltWeight, fltWeight)) { this.fltWeight = fltWeight;this.isDirty = true; }}
	public Date getDtBirthday() { updateHitCount(5); return(this.dtBirthday); }
	public void setDtBirthday(Date dtBirthday) { if(isDifferent(this.dtBirthday, dtBirthday)) { this.dtBirthday = dtBirthday;this.isDirty = true; }}
	public Blob getImgPhoto() { updateHitCount(6); return(this.imgPhoto); }
	public void setImgPhoto(Blob imgPhoto) { if(isDifferent(this.imgPhoto, imgPhoto)) { this.imgPhoto = imgPhoto;this.isDirty = true; }}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		validationBean.addValidationFieldBean(new VarcharValidator("nm_pet", nmPet.toString(), 0, 64, false).validate());
		validationBean.addValidationFieldBean(new IntValidator("num_age", numAge.toString(), 0, 0, false).validate());
		validationBean.addValidationFieldBean(new NumericValidator("flt_weight", fltWeight.toString(), 0, 6, true).validate());
		validationBean.addValidationFieldBean(new DateValidator("dt_birthday", dtBirthday.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new BlobValidator("img_photo", imgPhoto.toString(), 0, 0, true).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model[Pet]\n");
		stringBuilder.append("  Field[idPet:" + this.idPet + "]\n");
		stringBuilder.append("  Field[nmPet:" + this.nmPet + "]\n");
		stringBuilder.append("  Field[numAge:" + this.numAge + "]\n");
		stringBuilder.append("  Field[fltWeight:" + this.fltWeight + "]\n");
		stringBuilder.append("  Field[dtBirthday:" + this.dtBirthday + "]\n");
		stringBuilder.append("  Field[imgPhoto:" + this.imgPhoto + "]\n");
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "Pet");

		ModelBaseHelper.addtoJSONObject(jsonObject, "idPet", this.getIdPet());
		ModelBaseHelper.addtoJSONObject(jsonObject, "nmPet", this.getNmPet());
		ModelBaseHelper.addtoJSONObject(jsonObject, "numAge", this.getNumAge());
		ModelBaseHelper.addtoJSONObject(jsonObject, "fltWeight", this.getFltWeight());
		ModelBaseHelper.addtoJSONObject(jsonObject, "dtBirthday", this.getDtBirthday());
		ModelBaseHelper.addtoJSONObject(jsonObject, "imgPhoto", this.getImgPhoto());
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
		jsonObject.put("imgPhoto", HIT_COUNTS[6]);
		return(jsonObject.toString());
	}

}