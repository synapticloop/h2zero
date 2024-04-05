package com.synapticloop.sample.h2zero.cockroach.model;

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
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;

import com.synapticloop.sample.h2zero.cockroach.finder.PetFinder;


/**
 * <p>This is the model for the <code>Pet</code> which maps to the <code>pet</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class Pet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;


	public static final String PRIMARY_KEY_FIELD = "id_pet";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			pet (
				nm_pet,
				num_age,
				flt_weight,
				dt_birthday,
				img_photo
			) values (
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
				pet
			set
				nm_pet = ?,
				num_age = ?,
				flt_weight = ?,
				dt_birthday = ?,
				img_photo = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from pet where nm_pet = ? and num_age = ? and flt_weight = ? and dt_birthday = ? and img_photo = ?";


	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
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
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0 };


	private Long idPet = null; // maps to the id_pet field
	private String nmPet = null; // maps to the nm_pet field
	private Integer numAge = null; // maps to the num_age field
	private BigDecimal fltWeight = null; // maps to the flt_weight field
	private Date dtBirthday = null; // maps to the dt_birthday field
	private Blob imgPhoto = null; // maps to the img_photo field

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

	/**
	 * <p>Get a new Pet model, or set the fields on an existing
	 * Pet model.</p>
	 * 
	 * <p>If the passed in pet is null, then a new Pet
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param pet the model to check
	 * @param nmPet - maps to the <code>nm_pet</code> field.
	 * @param numAge - maps to the <code>num_age</code> field.
	 * @param fltWeight - maps to the <code>flt_weight</code> field.
	 * @param dtBirthday - maps to the <code>dt_birthday</code> field.
	 * @param imgPhoto - maps to the <code>img_photo</code> field.
	 * 
	 * @return Either the existing pet with updated field values,
	 *   or a new Pet with the field values set.
	 */
	public static Pet getOrSet(Pet pet,String nmPet, Integer numAge, BigDecimal fltWeight, Date dtBirthday, Blob imgPhoto) {
		if(null == pet) {
			return (new Pet(null, nmPet, numAge, fltWeight, dtBirthday, imgPhoto));
		} else {
			pet.setNmPet(nmPet);
			pet.setNumAge(numAge);
			pet.setFltWeight(fltWeight);
			pet.setDtBirthday(dtBirthday);
			pet.setImgPhoto(imgPhoto);

			return(pet);
		}
	}

	/**
	 * Get a new Pet model, or set the fields on an existing
	 * Pet model.
	 * <p>
	 * If the passed in pet is null, then a new Pet
	 * will be created.  If not null, the fields will be updated on the existing model.
	 * <p>
	 * <strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call.
	 * 
	 * @param pet the model to check
	 * @param nmPet
	 * @param numAge
	 * 
	 * @return Either the existing pet with updated field values,
	 *   or a new Pet with the field values set.
	 */
	public static Pet getOrSet(Pet pet, String nmPet, Integer numAge) {
		if(null == pet) {
			return (new Pet(null , nmPet, numAge));
		} else {
			pet.setNmPet(nmPet);
			pet.setNumAge(numAge);

			return(pet);
		}
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
			ConnectionManager.setNumeric(preparedStatement, 3, fltWeight);
			ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
			ConnectionManager.setBlob(preparedStatement, 5, imgPhoto);
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
			ConnectionManager.setNumeric(preparedStatement, 3, fltWeight);
			ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
			ConnectionManager.setBlob(preparedStatement, 5, imgPhoto);
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
				ConnectionManager.setNumeric(preparedStatement, 3, fltWeight);
				ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
				ConnectionManager.setBlob(preparedStatement, 5, imgPhoto);
				// now set the primary key
				preparedStatement.setLong(6, idPet);
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
		this.imgPhoto = pet.getImgPhoto();
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
	 * <p>{@link #HIT_ID_PET Use <code>Pet.HIT_ID_PET</code> to retrieve the hit count for the <code>id_pet</code> field}</p>
	 * <p>{@link #HIT_NM_PET Use <code>Pet.HIT_NM_PET</code> to retrieve the hit count for the <code>nm_pet</code> field}</p>
	 * <p>{@link #HIT_NUM_AGE Use <code>Pet.HIT_NUM_AGE</code> to retrieve the hit count for the <code>num_age</code> field}</p>
	 * <p>{@link #HIT_FLT_WEIGHT Use <code>Pet.HIT_FLT_WEIGHT</code> to retrieve the hit count for the <code>flt_weight</code> field}</p>
	 * <p>{@link #HIT_DT_BIRTHDAY Use <code>Pet.HIT_DT_BIRTHDAY</code> to retrieve the hit count for the <code>dt_birthday</code> field}</p>
	 * <p>{@link #HIT_IMG_PHOTO Use <code>Pet.HIT_IMG_PHOTO</code> to retrieve the hit count for the <code>img_photo</code> field}</p>

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
		return(
			"{\"Pet\": {" +
			"\"idPet\":\"" + this.idPet + "\"" +
			"\"nmPet\":\"" + this.nmPet + "\"" +
			"\"numAge\":\"" + this.numAge + "\"" +
			"\"fltWeight\":\"" + this.fltWeight + "\"" +
			"\"dtBirthday\":\"" + this.dtBirthday + "\"" +
			"\"imgPhoto\":\"" + this.imgPhoto + "\"" +
			"}");
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
		ModelBaseHelper.addtoJSONObject(fieldsObject, "imgPhoto", this.getImgPhoto());

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
	 * <p>Return an XML representation of the <code>Pet</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;pet /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<pet>" + 
			String.format("<id_pet null=\"%b\">%s</id_pet>", (this.getIdPet() == null), (this.getIdPet() != null ? this.getIdPet() : "")) + 
			String.format("<nm_pet null=\"%b\">%s</nm_pet>", (this.getNmPet() == null), (this.getNmPet() != null ? XmlHelper.escapeXml(this.getNmPet()) : "")) + 
			String.format("<num_age null=\"%b\">%s</num_age>", (this.getNumAge() == null), (this.getNumAge() != null ? this.getNumAge() : "")) + 
			String.format("<flt_weight null=\"%b\">%s</flt_weight>", (this.getFltWeight() == null), (this.getFltWeight() != null ? this.getFltWeight() : "")) + 
			String.format("<dt_birthday null=\"%b\">%s</dt_birthday>", (this.getDtBirthday() == null), (this.getDtBirthday() != null ? this.getDtBirthday() : "")) + 
			String.format("<img_photo null=\"%b\">%s</img_photo>", (this.getImgPhoto() == null), (this.getImgPhoto() != null ? this.getImgPhoto() : "")) + 
			"</pet>");
	}


	/**
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
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