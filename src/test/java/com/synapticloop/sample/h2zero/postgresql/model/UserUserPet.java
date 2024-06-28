package com.synapticloop.sample.h2zero.postgresql.model;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                  (/java/model/java-create-model.templar)

import com.synapticloop.h2zero.base.manager.postgresql.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import com.synapticloop.sample.h2zero.postgresql.question.UserUserQuestion;
import com.synapticloop.sample.h2zero.postgresql.question.PetQuestion;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.postgresql.ModelBase;
import com.synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

import com.synapticloop.sample.h2zero.postgresql.finder.UserUserPetFinder;
import com.synapticloop.sample.h2zero.postgresql.finder.UserUserFinder;
import com.synapticloop.sample.h2zero.postgresql.finder.PetFinder;


/**
 * <p>This is the model for the <code>UserUserPet</code> which maps to the <code>user_user_pet</code> database table.</p>
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
 *       <td><code>id_user_user_pet</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td><code>primary</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>id_user_user</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td> <code>foreign -> user_user.id_user_user</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>id_pet</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td> <code>foreign -> pet.id_pet</code></td>
 *       <td> -- </td>
 *     </tr>
 *   </tbody>
 * </table>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserUserPet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_PET_BINDER;


	public static final String PRIMARY_KEY_FIELD = "id_user_user_pet";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			user_user_pet (
				id_user_user,
				id_pet
			) values (
				?,
				?
			)
		""";
	private static final String SQL_UPDATE = 
		"""
			update
				user_user_pet
			set
				id_user_user = ?,
				id_pet = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from user_user_pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from user_user_pet where id_user_user = ? and id_pet = ?";


	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computed, external classes can use these static 
	// fields to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;  // The total number of hits on all fields of this model
	public static final int HIT_ID_USER_USER_PET = 1; // The number of hits on the id_user_user_pet property
	public static final int HIT_ID_USER_USER = 2; // The number of hits on the id_user_user property
	public static final int HIT_ID_PET = 3; // The number of hits on the id_pet property


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user_user_pet", "id_user_user", "id_pet" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0 };

	private UserUser UserUser = null; // maps to the id_user_user field
	private Pet Pet = null; // maps to the id_pet field

	private Long idUserUserPet = null; // maps to the id_user_user_pet field
	private Long idUserUser = null; // maps to the id_user_user field
	private Long idPet = null; // maps to the id_pet field

	/**
	 * <p>Create a new <code>UserUserPet</code> object with all fields
	 * some of which can be null.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object.</p>
	 * 
	 * <p>Creating a new UserUserPet:</p>
	 * 
	 * <pre>new UserUserPet(
	 *     Long idUserUserPet,  // id_user_user_pet 
	 *     Long idUserUser,  // id_user_user 
	 *     Long idPet // id_pet 
	 * );</pre>
	 * 
	 */
	public UserUserPet(Long idUserUserPet, Long idUserUser, Long idPet) {
		this.idUserUserPet = idUserUserPet;
		this.idUserUser = idUserUser;
		this.idPet = idPet;
	}


	/**
	 * <p>Get a new UserUserPet model, or set the fields on an existing
	 * UserUserPet model.</p>
	 * 
	 * <p>If the passed in userUserPet is null, then a new UserUserPet
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param userUserPet the model to check
	 * @param idUserUser - maps to the <code>id_user_user</code> field.
	 * @param idPet - maps to the <code>id_pet</code> field.
	 * 
	 * @return Either the existing userUserPet with updated field values,
	 *   or a new UserUserPet with the field values set.
	 */
	public static UserUserPet getOrSet(UserUserPet userUserPet,Long idUserUser, Long idPet) {
		if(null == userUserPet) {
			return (new UserUserPet(null, idUserUser, idPet));
		} else {
			userUserPet.setIdUserUser(idUserUser);
			userUserPet.setIdPet(idPet);

			return(userUserPet);
		}
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idUserUserPet);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert user_user_pet model when primary key is not null.");
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// create this bean 
			preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ConnectionManager.setBigint(preparedStatement, 1, idUserUser);
			ConnectionManager.setBigint(preparedStatement, 2, idPet);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idUserUserPet = resultSet.getLong(1);
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
			ConnectionManager.setBigint(preparedStatement, 1, idUserUser);
			ConnectionManager.setBigint(preparedStatement, 2, idPet);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idUserUserPet = resultSet.getLong(1);
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
				ConnectionManager.setBigint(preparedStatement, 1, idUserUser);
				ConnectionManager.setBigint(preparedStatement, 2, idPet);
				// now set the primary key
				preparedStatement.setLong(3, idUserUserPet);
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
			preparedStatement.setLong(1, idUserUserPet);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'UserUserPet' when primary key is null.");
		}

		UserUserPet userUserPet = UserUserPetFinder.findByPrimaryKey(this.idUserUserPet)
				.withConnection(connection)
				.executeSilent();

		if(null == userUserPet) {
			throw new H2ZeroFinderException("Could not find the model 'UserUserPet' with primaryKey of " + getPrimaryKey());
		}
		this.idUserUserPet = userUserPet.getIdUserUserPet();
		this.idUserUser = userUserPet.getIdUserUser();
		this.idPet = userUserPet.getIdPet();
		this.UserUser = null;
		this.Pet = null;
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
	 * <p>{@link #HIT_ID_USER_USER_PET Use <code>UserUserPet.HIT_ID_USER_USER_PET</code> to retrieve the hit count for the <code>id_user_user_pet</code> field}</p>
	 * <p>{@link #HIT_ID_USER_USER Use <code>UserUserPet.HIT_ID_USER_USER</code> to retrieve the hit count for the <code>id_user_user</code> field}</p>
	 * <p>{@link #HIT_ID_PET Use <code>UserUserPet.HIT_ID_PET</code> to retrieve the hit count for the <code>id_pet</code> field}</p>
	 */
	public static int getHitCountForField(int hitCountField) { return(HIT_COUNTS[hitCountField]); }

	/**
	 * <p>The <code>id_user_user</code> is a foreign key, consequently this is a convenience
	 * method to get the UserUser Model object, rather than returning the
	 * primary key field.</p>
	 * 
	 * <p>To find the referenced primary key field - use the <code>getIdUserUser()</code>
	 * method.</p>
	 * 
	 * <p>In effect, this will look up the model from the <code>user_user</code> database - with
	 * the appropriate SQL statement <code>.findByPrimaryKey(idUserUser)</code></p>
	 * 
	 * 
	 * @return The model for the foreign key reference. 
	 */
	public UserUser getUserUser() {
		if(null == this.UserUser) {
			this.UserUser = UserUserFinder.findByPrimaryKey(this.idUserUser).executeSilent();
		}
		return(this.UserUser);
	}

	/**
	 * <p>The <code>id_pet</code> is a foreign key, consequently this is a convenience
	 * method to get the Pet Model object, rather than returning the
	 * primary key field.</p>
	 * 
	 * <p>To find the referenced primary key field - use the <code>getIdPet()</code>
	 * method.</p>
	 * 
	 * <p>In effect, this will look up the model from the <code>pet</code> database - with
	 * the appropriate SQL statement <code>.findByPrimaryKey(idPet)</code></p>
	 * 
	 * 
	 * @return The model for the foreign key reference. 
	 */
	public Pet getPet() {
		if(null == this.Pet) {
			this.Pet = PetFinder.findByPrimaryKey(this.idPet).executeSilent();
		}
		return(this.Pet);
	}

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
	 * <p>Convenience method for returning the primary key field (which is the id_user_user_pet field).</p>
	 * 
	 * <p><strong>NOTE:</strong> This will update the hit count.</p>
	 * 
	 * @return The value of the primary key
	 */
	public Long getPrimaryKey() {updateHitCount(1); return(this.idUserUserPet); }

	/**
	 * <p>Set the primary key field (which is the id_user_user_pet field).</p>
	 * 
	 * <p>If the primary key value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 * 
	 * <p>If the primary key value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 * 
	 * @param idUser The primary key field to update
	 */
	public void setPrimaryKey(Long idUserUserPet) {if(isDifferent(this.idUserUserPet, idUserUserPet)) { this.idUserUserPet = idUserUserPet; this.isDirty = true;}}

	/**
	 * <p>Return the value of the id_user_user_pet, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user_user_pet which may NOT be null.
	 */
	public Long getIdUserUserPet() {updateHitCount(1); return(this.idUserUserPet); }

	/**
	 * <p>Set the <code>id_user_user_pet</code> value setting the 'isDirty' flag
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
	 * @param idUserUserPet the id_user_user_pet value to be set
	 */
	public void setIdUserUserPet(Long idUserUserPet) {if(isDifferent(this.idUserUserPet, idUserUserPet)) { this.idUserUserPet = idUserUserPet; this.isDirty = true;}}

	/**
	 * <p>Return the value of the id_user_user, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user_user which may NOT be null.
	 */
	public Long getIdUserUser() {updateHitCount(2); return(this.idUserUser); }

	/**
	 * <p>Set the <code>id_user_user</code> value setting the 'isDirty' flag
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
	 * @param idUserUser the id_user_user value to be set
	 */
	public void setIdUserUser(Long idUserUser) {if(isDifferent(this.idUserUser, idUserUser)) { this.idUserUser = idUserUser; this.isDirty = true;this.UserUser = null;}}

	/**
	 * <p>Return the value of the id_pet, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_pet which may NOT be null.
	 */
	public Long getIdPet() {updateHitCount(3); return(this.idPet); }

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
	public void setIdPet(Long idPet) {if(isDifferent(this.idPet, idPet)) { this.idPet = idPet; this.isDirty = true;this.Pet = null;}}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		ValidationFieldBean idUserUserValidationFieldBean = new BigintValidator("id_user_user", idUserUser.toString(), 0, 0, false).validate();
		idUserUserValidationFieldBean.setIsIncorrectForeignKey(!UserUserQuestion.internalDoesPrimaryKeyExist(idUserUser));
		validationBean.addValidationFieldBean(idUserUserValidationFieldBean);

		ValidationFieldBean idPetValidationFieldBean = new BigintValidator("id_pet", idPet.toString(), 0, 0, false).validate();
		idPetValidationFieldBean.setIsIncorrectForeignKey(!PetQuestion.internalDoesPrimaryKeyExist(idPet));
		validationBean.addValidationFieldBean(idPetValidationFieldBean);

		return(validationBean);
	}


	@Override
	public String toString() {
		return(
			"{\"UserUserPet\": {" +
			"\"idUserUserPet\":\"" + this.idUserUserPet + "\"" +
			"\"idUserUser\":\"" + this.idUserUser + "\"" +
			"\"idPet\":\"" + this.idPet + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserUserPet");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserUserPet", this.getIdUserUserPet());
		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserUser", this.getIdUserUser());
		ModelBaseHelper.addToJSONObject(fieldsObject, "idPet", this.getIdPet());

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
	 * <p>Return an XML representation of the <code>UserUserPet</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user_user_pet /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user_user_pet>" + 
			String.format("<id_user_user_pet null=\"%b\">%s</id_user_user_pet>", (this.getIdUserUserPet() == null), (this.getIdUserUserPet() != null ? this.getIdUserUserPet() : "")) + 
			String.format("<id_user_user null=\"%b\">%s</id_user_user>", (this.getIdUserUser() == null), (this.getIdUserUser() != null ? this.getIdUserUser() : "")) + 
			String.format("<id_pet null=\"%b\">%s</id_pet>", (this.getIdPet() == null), (this.getIdPet() != null ? this.getIdPet() : "")) + 
			"</user_user_pet>");
	}


	/**
	 * <p>Get the hit count statistics as a JSON encoded object as a <code>String</code></p>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "UserUserPet");
		jsonObject.put("total", HIT_COUNTS[0]);
		jsonObject.put("idUserUserPet", HIT_COUNTS[1]);
		jsonObject.put("idUserUser", HIT_COUNTS[2]);
		jsonObject.put("idPet", HIT_COUNTS[3]);
		return(jsonObject.toString());
	}

}