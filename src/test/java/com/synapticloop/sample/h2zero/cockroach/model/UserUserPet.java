package com.synapticloop.sample.h2zero.cockroach.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import com.synapticloop.h2zero.base.sql.cockroach.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import com.synapticloop.sample.h2zero.cockroach.question.UserUserQuestion;
import com.synapticloop.sample.h2zero.cockroach.question.PetQuestion;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.cockroach.ModelBase;
import com.synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.cockroach.model.util.Constants;

import com.synapticloop.sample.h2zero.cockroach.finder.UserUserPetFinder;
import com.synapticloop.sample.h2zero.cockroach.finder.UserUserFinder;
import com.synapticloop.sample.h2zero.cockroach.finder.PetFinder;


/**
 * <p>This is the model for the <code>UserUserPet</code> which maps to the <code>user_user_pet</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
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
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_USER_USER_PET = 1;
	public static final int HIT_ID_USER_USER = 2;
	public static final int HIT_ID_PET = 3;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user_user_pet", "id_user_user", "id_pet" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0 };

	private UserUser UserUser = null; // maps to the id_user_user field
	private Pet Pet = null; // maps to the id_pet field

	private Long idUserUserPet = null; // maps to the id_user_user_pet field
	private Long idUserUser = null; // maps to the id_user_user field
	private Long idPet = null; // maps to the id_pet field

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

		UserUserPet userUserPet = UserUserPetFinder.findByPrimaryKeySilent(connection, this.idUserUserPet);
		if(null == userUserPet) {
			throw new H2ZeroFinderException("Could not find the model 'UserUserPet' with primaryKey of " + getPrimaryKey());
		}
		this.idUserUserPet = userUserPet.getIdUserUserPet();
		this.idUserUser = userUserPet.getIdUserUser();
		this.idPet = userUserPet.getIdPet();
		this.UserUser = null;
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
	 * <p>{@link #HIT_ID_USER_USER_PET Use <code>UserUserPet.HIT_ID_USER_USER_PET</code> to retrieve the hit count for the <code>id_user_user_pet</code> field}</p>
	 * <p>{@link #HIT_ID_USER_USER Use <code>UserUserPet.HIT_ID_USER_USER</code> to retrieve the hit count for the <code>id_user_user</code> field}</p>
	 * <p>{@link #HIT_ID_PET Use <code>UserUserPet.HIT_ID_PET</code> to retrieve the hit count for the <code>id_pet</code> field}</p>

	 */
	public static int getHitCountForField(int hitCountField) { return(HIT_COUNTS[hitCountField]); }

	public UserUser getUserUser() {
		if(null == this.UserUser) {
			this.UserUser = UserUserFinder.findByPrimaryKeySilent(this.idUserUser);
		}
		return(this.UserUser);
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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idUserUserPet); }
	public void setPrimaryKey(Long idUserUserPet) { if(isDifferent(this.idUserUserPet, idUserUserPet)) { this.idUserUserPet = idUserUserPet;this.isDirty = true; }}
	public Long getIdUserUserPet() { updateHitCount(1); return(this.idUserUserPet); }
	public void setIdUserUserPet(Long idUserUserPet) { if(isDifferent(this.idUserUserPet, idUserUserPet)) { this.idUserUserPet = idUserUserPet;this.isDirty = true; }}
	public Long getIdUserUser() { updateHitCount(2); return(this.idUserUser); }
	public void setIdUserUser(Long idUserUser) { if(isDifferent(this.idUserUser, idUserUser)) { this.idUserUser = idUserUser;this.isDirty = true; this.UserUser = null;}}
	public Long getIdPet() { updateHitCount(3); return(this.idPet); }
	public void setIdPet(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; this.Pet = null;}}

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

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserUserPet", this.getIdUserUserPet());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserUser", this.getIdUserUser());
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
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
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