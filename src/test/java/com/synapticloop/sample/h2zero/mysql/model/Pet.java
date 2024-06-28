package com.synapticloop.sample.h2zero.mysql.model;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                  (/java/model/java-create-model.templar)

import com.synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.mysql.ModelBase;
import com.synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import com.synapticloop.h2zero.generator.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.mysql.model.util.Constants;

import com.synapticloop.sample.h2zero.mysql.finder.PetFinder;


/**
 * <p>This is the model for the <code>Pet</code> which maps to the <code>pet</code> database table.</p>
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
 *       <td><code>id_pet</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td><code>primary</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>nm_pet</code></td>
 *       <td>varchar</td>
 *       <td>(0:64)</td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_age</code></td>
 *       <td>int</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>flt_weight</code></td>
 *       <td>float</td>
 *       <td>(0:6)</td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>dt_birthday</code></td>
 *       <td>date</td>
 *       <td> -- </td>
 *       <td>true</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>img_photo</code></td>
 *       <td>blob</td>
 *       <td> -- </td>
 *       <td>true</td>
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
	// automatically computed, external classes can use these static 
	// fields to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;  // The total number of hits on all fields of this model
	public static final int HIT_ID_PET = 1; // The number of hits on the id_pet property
	public static final int HIT_NM_PET = 2; // The number of hits on the nm_pet property
	public static final int HIT_NUM_AGE = 3; // The number of hits on the num_age property
	public static final int HIT_FLT_WEIGHT = 4; // The number of hits on the flt_weight property
	public static final int HIT_DT_BIRTHDAY = 5; // The number of hits on the dt_birthday property
	public static final int HIT_IMG_PHOTO = 6; // The number of hits on the img_photo property


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_pet", "nm_pet", "num_age", "flt_weight", "dt_birthday", "img_photo" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0 };


	private Long idPet = null; // maps to the id_pet field
	private String nmPet = null; // maps to the nm_pet field
	private Integer numAge = null; // maps to the num_age field
	private Float fltWeight = null; // maps to the flt_weight field
	private Date dtBirthday = null; // maps to the dt_birthday field
	private Blob imgPhoto = null; // maps to the img_photo field

	/**
	 * <p>Create a new <code>Pet</code> object with all fields
	 * some of which can be null.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object.</p>
	 * 
	 * <p>Creating a new Pet:</p>
	 * 
	 * <pre>new Pet(
	 *     Long idPet,  // id_pet 
	 *     String nmPet,  // nm_pet 
	 *     Integer numAge,  // num_age 
	 *     Float fltWeight,  // flt_weight  [NULLABLE]
	 *     Date dtBirthday,  // dt_birthday  [NULLABLE]
	 *     Blob imgPhoto // img_photo  [NULLABLE]
	 * );</pre>
	 * 
	 */
	public Pet(Long idPet, String nmPet, Integer numAge, Float fltWeight, Date dtBirthday, Blob imgPhoto) {
		this.idPet = idPet;
		this.nmPet = nmPet;
		this.numAge = numAge;
		this.fltWeight = fltWeight;
		this.dtBirthday = dtBirthday;
		this.imgPhoto = imgPhoto;
	}

	/**
	 * <p>Create a new <code>Pet</code> object with only the
	 * fields that are non-nullable.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object</p>
	 * 
	 * <p>Creating a new Pet:</p>
	 * 
	 * <pre>new Pet(
	 *     Long idPet,  // id_pet
	 *     String nmPet,  // nm_pet
	 *     Integer numAge // num_age
	 * );</pre>
	 * 
	 */
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
	public static Pet getOrSet(Pet pet,String nmPet, Integer numAge, Float fltWeight, Date dtBirthday, Blob imgPhoto) {
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
			ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
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
			ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
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
				ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
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

		Pet pet = PetFinder.findByPrimaryKey(this.idPet)
				.withConnection(connection)
				.executeSilent();

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
	 * <p>{@link #HIT_ID_PET Use <code>Pet.HIT_ID_PET</code> to retrieve the hit count for the <code>id_pet</code> field}</p>
	 * <p>{@link #HIT_NM_PET Use <code>Pet.HIT_NM_PET</code> to retrieve the hit count for the <code>nm_pet</code> field}</p>
	 * <p>{@link #HIT_NUM_AGE Use <code>Pet.HIT_NUM_AGE</code> to retrieve the hit count for the <code>num_age</code> field}</p>
	 * <p>{@link #HIT_FLT_WEIGHT Use <code>Pet.HIT_FLT_WEIGHT</code> to retrieve the hit count for the <code>flt_weight</code> field}</p>
	 * <p>{@link #HIT_DT_BIRTHDAY Use <code>Pet.HIT_DT_BIRTHDAY</code> to retrieve the hit count for the <code>dt_birthday</code> field}</p>
	 * <p>{@link #HIT_IMG_PHOTO Use <code>Pet.HIT_IMG_PHOTO</code> to retrieve the hit count for the <code>img_photo</code> field}</p>
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
	 * <p>Convenience method for returning the primary key field (which is the id_pet field).</p>
	 * 
	 * <p><strong>NOTE:</strong> This will update the hit count.</p>
	 * 
	 * @return The value of the primary key
	 */
	public Long getPrimaryKey() {updateHitCount(1); return(this.idPet); }

	/**
	 * <p>Set the primary key field (which is the id_pet field).</p>
	 * 
	 * <p>If the primary key value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 * 
	 * <p>If the primary key value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 * 
	 * @param idUser The primary key field to update
	 */
	public void setPrimaryKey(Long idPet) {if(isDifferent(this.idPet, idPet)) { this.idPet = idPet; this.isDirty = true;}}

	/**
	 * <p>Return the value of the id_pet, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_pet which may NOT be null.
	 */
	public Long getIdPet() {updateHitCount(1); return(this.idPet); }

	/**
	 * <p>Set the <code>id_pet</code> value setting the 'isDirty' flag
	 * if the value has changed.  The 'isDirty' flag is used when the model is
	 * updated - and the update call will be ignored if the 'isDirty' flag is
	 * not set.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param idPet the id_pet value to be set
	 */
	public void setIdPet(Long idPet) {if(isDifferent(this.idPet, idPet)) { this.idPet = idPet; this.isDirty = true;}}

	/**
	 * <p>Return the value of the nm_pet, updating the hit count for this field.</p>
	 * 
	 * @return the value of the nm_pet which may NOT be null.
	 */
	public String getNmPet() {updateHitCount(2); return(this.nmPet); }

	/**
	 * <p>Set the <code>nm_pet</code> value setting the 'isDirty' flag
	 * if the value has changed.  The 'isDirty' flag is used when the model is
	 * updated - and the update call will be ignored if the 'isDirty' flag is
	 * not set.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param nmPet the nm_pet value to be set
	 */
	public void setNmPet(String nmPet) {if(isDifferent(this.nmPet, nmPet)) { this.nmPet = nmPet; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_age, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_age which may NOT be null.
	 */
	public Integer getNumAge() {updateHitCount(3); return(this.numAge); }

	/**
	 * <p>Set the <code>num_age</code> value setting the 'isDirty' flag
	 * if the value has changed.  The 'isDirty' flag is used when the model is
	 * updated - and the update call will be ignored if the 'isDirty' flag is
	 * not set.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param numAge the num_age value to be set
	 */
	public void setNumAge(Integer numAge) {if(isDifferent(this.numAge, numAge)) { this.numAge = numAge; this.isDirty = true;}}

	/**
	 * <p>Return the value of the flt_weight, updating the hit count for this field.</p>
	 * 
	 * @return the value of the flt_weight which may be null.
	 */
	public Float getFltWeight() {updateHitCount(4); return(this.fltWeight); }

	/**
	 * <p>Set the <code>flt_weight</code> value setting the 'isDirty' flag
	 * if the value has changed.  The 'isDirty' flag is used when the model is
	 * updated - and the update call will be ignored if the 'isDirty' flag is
	 * not set.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param fltWeight the flt_weight value to be set
	 */
	public void setFltWeight(Float fltWeight) {if(isDifferent(this.fltWeight, fltWeight)) { this.fltWeight = fltWeight; this.isDirty = true;}}

	/**
	 * <p>Return the value of the dt_birthday, updating the hit count for this field.</p>
	 * 
	 * @return the value of the dt_birthday which may be null.
	 */
	public Date getDtBirthday() {updateHitCount(5); return(this.dtBirthday); }

	/**
	 * <p>Set the <code>dt_birthday</code> value setting the 'isDirty' flag
	 * if the value has changed.  The 'isDirty' flag is used when the model is
	 * updated - and the update call will be ignored if the 'isDirty' flag is
	 * not set.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param dtBirthday the dt_birthday value to be set
	 */
	public void setDtBirthday(Date dtBirthday) {if(isDifferent(this.dtBirthday, dtBirthday)) { this.dtBirthday = dtBirthday; this.isDirty = true;}}

	/**
	 * <p>Return the value of the img_photo, updating the hit count for this field.</p>
	 * 
	 * @return the value of the img_photo which may be null.
	 */
	public Blob getImgPhoto() {updateHitCount(6); return(this.imgPhoto); }

	/**
	 * <p>Set the <code>img_photo</code> value setting the 'isDirty' flag
	 * if the value has changed.  The 'isDirty' flag is used when the model is
	 * updated - and the update call will be ignored if the 'isDirty' flag is
	 * not set.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param imgPhoto the img_photo value to be set
	 */
	public void setImgPhoto(Blob imgPhoto) {if(isDifferent(this.imgPhoto, imgPhoto)) { this.imgPhoto = imgPhoto; this.isDirty = true;}}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		validationBean.addValidationFieldBean(new VarcharValidator("nm_pet", nmPet.toString(), 0, 64, false).validate());
		validationBean.addValidationFieldBean(new IntValidator("num_age", numAge.toString(), 0, 0, false).validate());
		validationBean.addValidationFieldBean(new FloatValidator("flt_weight", fltWeight.toString(), 0, 6, true).validate());
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

		ModelBaseHelper.addToJSONObject(fieldsObject, "idPet", this.getIdPet());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmPet", this.getNmPet());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numAge", this.getNumAge());
		ModelBaseHelper.addToJSONObject(fieldsObject, "fltWeight", this.getFltWeight());
		ModelBaseHelper.addToJSONObject(fieldsObject, "dtBirthday", this.getDtBirthday());
		ModelBaseHelper.addToJSONObject(fieldsObject, "imgPhoto", this.getImgPhoto());

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
	 * <p>Get the hit count statistics as a JSON encoded object as a <code>String</code></p>.
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