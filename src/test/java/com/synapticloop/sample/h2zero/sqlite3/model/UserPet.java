package com.synapticloop.sample.h2zero.sqlite3.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import com.synapticloop.h2zero.base.sql.sqlite3.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import com.synapticloop.sample.h2zero.sqlite3.question.UserQuestion;
import com.synapticloop.sample.h2zero.sqlite3.question.PetQuestion;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.sqlite3.ModelBase;
import com.synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import com.synapticloop.sample.h2zero.sqlite3.finder.UserPetFinder;
import com.synapticloop.sample.h2zero.sqlite3.finder.UserFinder;
import com.synapticloop.sample.h2zero.sqlite3.finder.PetFinder;


/**
 * <p>This is the model for the <code>UserPet</code> which maps to the <code>user_pet</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserPet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;


	public static final String PRIMARY_KEY_FIELD = "id_user_pet";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			user_pet (
				id_user,
				id_pet
			) values (
				?,
				?
			)
		""";
	private static final String SQL_UPDATE = 
		"""
			update
				user_pet
			set
				id_user = ?,
				id_pet = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from user_pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from user_pet where id_user = ? and id_pet = ?";


	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_USER_PET = 1;
	public static final int HIT_ID_USER = 2;
	public static final int HIT_ID_PET = 3;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user_pet", "id_user", "id_pet" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0 };

	private User User = null; // maps to the id_user field
	private Pet Pet = null; // maps to the id_pet field

	private Long idUserPet = null; // maps to the id_user_pet field
	private Long idUser = null; // maps to the id_user field
	private Long idPet = null; // maps to the id_pet field

	public UserPet(Long idUserPet, Long idUser, Long idPet) {
		this.idUserPet = idUserPet;
		this.idUser = idUser;
		this.idPet = idPet;
	}

	/**
	 * <p>Get a new UserPet model, or set the fields on an existing
	 * UserPet model.</p>
	 * 
	 * <p>If the passed in userPet is null, then a new UserPet
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param userPet the model to check
	 * @param idUser - maps to the <code>id_user</code> field.
	 * @param idPet - maps to the <code>id_pet</code> field.
	 * 
	 * @return Either the existing userPet with updated field values,
	 *   or a new UserPet with the field values set.
	 */
	public static UserPet getOrSet(UserPet userPet,Long idUser, Long idPet) {
		if(null == userPet) {
			return (new UserPet(null, idUser, idPet));
		} else {
			userPet.setIdUser(idUser);
			userPet.setIdPet(idPet);

			return(userPet);
		}
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idUserPet);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert user_pet model when primary key is not null.");
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// create this bean 
			preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ConnectionManager.setBigint(preparedStatement, 1, idUser);
			ConnectionManager.setBigint(preparedStatement, 2, idPet);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idUserPet = resultSet.getLong(1);
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
			ConnectionManager.setBigint(preparedStatement, 1, idUser);
			ConnectionManager.setBigint(preparedStatement, 2, idPet);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idUserPet = resultSet.getLong(1);
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
				ConnectionManager.setBigint(preparedStatement, 1, idUser);
				ConnectionManager.setBigint(preparedStatement, 2, idPet);
				// now set the primary key
				preparedStatement.setLong(3, idUserPet);
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
			preparedStatement.setLong(1, idUserPet);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'UserPet' when primary key is null.");
		}

		UserPet userPet = UserPetFinder.findByPrimaryKeySilent(connection, this.idUserPet);
		if(null == userPet) {
			throw new H2ZeroFinderException("Could not find the model 'UserPet' with primaryKey of " + getPrimaryKey());
		}
		this.idUserPet = userPet.getIdUserPet();
		this.idUser = userPet.getIdUser();
		this.idPet = userPet.getIdPet();
		this.User = null;
		this.Pet = null;
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
	 * <p>{@link #HIT_ID_USER_PET Use <code>UserPet.HIT_ID_USER_PET</code> to retrieve the hit count for the <code>id_user_pet</code> field}</p>
	 * <p>{@link #HIT_ID_USER Use <code>UserPet.HIT_ID_USER</code> to retrieve the hit count for the <code>id_user</code> field}</p>
	 * <p>{@link #HIT_ID_PET Use <code>UserPet.HIT_ID_PET</code> to retrieve the hit count for the <code>id_pet</code> field}</p>

	 */
	public static int getHitCountForField(int hitCountField) { return(HIT_COUNTS[hitCountField]); }

	public User getUser() {
		if(null == this.User) {
			this.User = UserFinder.findByPrimaryKeySilent(this.idUser);
		}
		return(this.User);
	}

	public Pet getPet() {
		if(null == this.Pet) {
			this.Pet = PetFinder.findByPrimaryKeySilent(this.idPet);
		}
		return(this.Pet);
	}

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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idUserPet); }
	public void setPrimaryKey(Long idUserPet) { if(isDifferent(this.idUserPet, idUserPet)) { this.idUserPet = idUserPet;this.isDirty = true; }}
	public Long getIdUserPet() { updateHitCount(1); return(this.idUserPet); }
	public void setIdUserPet(Long idUserPet) { if(isDifferent(this.idUserPet, idUserPet)) { this.idUserPet = idUserPet;this.isDirty = true; }}
	public Long getIdUser() { updateHitCount(2); return(this.idUser); }
	public void setIdUser(Long idUser) { if(isDifferent(this.idUser, idUser)) { this.idUser = idUser;this.isDirty = true; this.User = null;}}
	public Long getIdPet() { updateHitCount(3); return(this.idPet); }
	public void setIdPet(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; this.Pet = null;}}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		ValidationFieldBean idUserValidationFieldBean = new BigintValidator("id_user", idUser.toString(), 0, 0, false).validate();
		idUserValidationFieldBean.setIsIncorrectForeignKey(!UserQuestion.internalDoesPrimaryKeyExist(idUser));
		validationBean.addValidationFieldBean(idUserValidationFieldBean);

		ValidationFieldBean idPetValidationFieldBean = new BigintValidator("id_pet", idPet.toString(), 0, 0, false).validate();
		idPetValidationFieldBean.setIsIncorrectForeignKey(!PetQuestion.internalDoesPrimaryKeyExist(idPet));
		validationBean.addValidationFieldBean(idPetValidationFieldBean);

		return(validationBean);
	}


	@Override
	public String toString() {
		return(
			"{\"UserPet\": {" +
			"\"idUserPet\":\"" + this.idUserPet + "\"" +
			"\"idUser\":\"" + this.idUser + "\"" +
			"\"idPet\":\"" + this.idPet + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserPet");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserPet", this.getIdUserPet());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUser", this.getIdUser());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "idPet", this.getIdPet());

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
	 * <p>Return an XML representation of the <code>UserPet</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user_pet /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user_pet>" + 
			String.format("<id_user_pet null=\"%b\">%s</id_user_pet>", (this.getIdUserPet() == null), (this.getIdUserPet() != null ? this.getIdUserPet() : "")) + 
			String.format("<id_user null=\"%b\">%s</id_user>", (this.getIdUser() == null), (this.getIdUser() != null ? this.getIdUser() : "")) + 
			String.format("<id_pet null=\"%b\">%s</id_pet>", (this.getIdPet() == null), (this.getIdPet() != null ? this.getIdPet() : "")) + 
			"</user_pet>");
	}


	/**
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "UserPet");
		jsonObject.put("total", HIT_COUNTS[0]);
		jsonObject.put("idUserPet", HIT_COUNTS[1]);
		jsonObject.put("idUser", HIT_COUNTS[2]);
		jsonObject.put("idPet", HIT_COUNTS[3]);
		return(jsonObject.toString());
	}

}