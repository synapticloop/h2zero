package com.synapticloop.sample.h2zero.mariadb.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import com.synapticloop.h2zero.base.manager.mariadb.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import com.synapticloop.sample.h2zero.mariadb.question.UserTypeQuestion;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.mariadb.ModelBase;
import com.synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;

import com.synapticloop.sample.h2zero.mariadb.finder.UserFinder;


/**
 * <p>This is the model for the <code>User</code> which maps to the <code>user</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class User extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;


	public static final String PRIMARY_KEY_FIELD = "id_user";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			user (
				id_user_type,
				fl_is_alive,
				num_age,
				nm_username,
				txt_address_email,
				txt_password,
				dtm_signup
			) values (
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
				user
			set
				id_user_type = ?,
				fl_is_alive = ?,
				num_age = ?,
				nm_username = ?,
				txt_address_email = ?,
				txt_password = ?,
				dtm_signup = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from user where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from user where id_user_type = ? and fl_is_alive = ? and num_age = ? and nm_username = ? and txt_address_email = ? and txt_password = ? and dtm_signup = ?";

	private static final String SQL_SELECT_HYDRATE = "select num_age, dtm_signup from user where " + PRIMARY_KEY_FIELD + " = ?";

	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_USER = 1;
	public static final int HIT_ID_USER_TYPE = 2;
	public static final int HIT_FL_IS_ALIVE = 3;
	public static final int HIT_NUM_AGE = 4;
	public static final int HIT_NM_USERNAME = 5;
	public static final int HIT_TXT_ADDRESS_EMAIL = 6;
	public static final int HIT_TXT_PASSWORD = 7;
	public static final int HIT_DTM_SIGNUP = 8;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user", "id_user_type", "fl_is_alive", "num_age", "nm_username", "txt_address_email", "txt_password", "dtm_signup" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private boolean isHydrated = false;


	private Long idUser = null; // maps to the id_user field
	private Long idUserType = null; // maps to the id_user_type field
	private Boolean flIsAlive = null; // maps to the fl_is_alive field
	private Integer numAge = null; // maps to the num_age field
	private String nmUsername = null; // maps to the nm_username field
	private String txtAddressEmail = null; // maps to the txt_address_email field
	private String txtPassword = null; // maps to the txt_password field
	private Timestamp dtmSignup = null; // maps to the dtm_signup field

	public User(Long idUser, Long idUserType, Boolean flIsAlive, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword, Timestamp dtmSignup) {
		this.idUser = idUser;
		this.idUserType = idUserType;
		this.flIsAlive = flIsAlive;
		this.numAge = numAge;
		this.nmUsername = nmUsername;
		this.txtAddressEmail = txtAddressEmail;
		this.txtPassword = txtPassword;
		this.dtmSignup = dtmSignup;
	}

	public User(Long idUser, Long idUserType, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword) {
		this.idUser = idUser;
		this.idUserType = idUserType;
		this.flIsAlive = null;
		this.numAge = numAge;
		this.nmUsername = nmUsername;
		this.txtAddressEmail = txtAddressEmail;
		this.txtPassword = txtPassword;
		this.dtmSignup = null;
	}

	/**
	 * <p>Get a new User model, or set the fields on an existing
	 * User model.</p>
	 * 
	 * <p>If the passed in user is null, then a new User
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param user the model to check
	 * @param idUserType - maps to the <code>id_user_type</code> field.
	 * @param flIsAlive - maps to the <code>fl_is_alive</code> field.
	 * @param numAge - maps to the <code>num_age</code> field.
	 * @param nmUsername - maps to the <code>nm_username</code> field.
	 * @param txtAddressEmail - maps to the <code>txt_address_email</code> field.
	 * @param txtPassword - maps to the <code>txt_password</code> field.
	 * @param dtmSignup - maps to the <code>dtm_signup</code> field.
	 * 
	 * @return Either the existing user with updated field values,
	 *   or a new User with the field values set.
	 */
	public static User getOrSet(User user,Long idUserType, Boolean flIsAlive, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword, Timestamp dtmSignup) {
		if(null == user) {
			return (new User(null, idUserType, flIsAlive, numAge, nmUsername, txtAddressEmail, txtPassword, dtmSignup));
		} else {
			user.setIdUserType(idUserType);
			user.setFlIsAlive(flIsAlive);
			user.setNumAge(numAge);
			user.setNmUsername(nmUsername);
			user.setTxtAddressEmail(txtAddressEmail);
			user.setTxtPassword(txtPassword);
			user.setDtmSignup(dtmSignup);

			return(user);
		}
	}

	/**
	 * Get a new User model, or set the fields on an existing
	 * User model.
	 * <p>
	 * If the passed in user is null, then a new User
	 * will be created.  If not null, the fields will be updated on the existing model.
	 * <p>
	 * <strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call.
	 * 
	 * @param user the model to check
	 * @param idUserType
	 * @param numAge
	 * @param nmUsername
	 * @param txtAddressEmail
	 * @param txtPassword
	 * 
	 * @return Either the existing user with updated field values,
	 *   or a new User with the field values set.
	 */
	public static User getOrSet(User user, Long idUserType, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword) {
		if(null == user) {
			return (new User(null , idUserType, numAge, nmUsername, txtAddressEmail, txtPassword));
		} else {
			user.setIdUserType(idUserType);
			user.setNumAge(numAge);
			user.setNmUsername(nmUsername);
			user.setTxtAddressEmail(txtAddressEmail);
			user.setTxtPassword(txtPassword);

			return(user);
		}
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idUser);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert user model when primary key is not null.");
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// create this bean 
			preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ConnectionManager.setBigint(preparedStatement, 1, idUserType);
			ConnectionManager.setBoolean(preparedStatement, 2, flIsAlive);
			ConnectionManager.setInt(preparedStatement, 3, numAge);
			ConnectionManager.setVarchar(preparedStatement, 4, nmUsername);
			ConnectionManager.setVarchar(preparedStatement, 5, txtAddressEmail);
			ConnectionManager.setVarchar(preparedStatement, 6, txtPassword);
			ConnectionManager.setDatetime(preparedStatement, 7, dtmSignup);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idUser = resultSet.getLong(1);
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
			ConnectionManager.setBigint(preparedStatement, 1, idUserType);
			ConnectionManager.setBoolean(preparedStatement, 2, flIsAlive);
			ConnectionManager.setInt(preparedStatement, 3, numAge);
			ConnectionManager.setVarchar(preparedStatement, 4, nmUsername);
			ConnectionManager.setVarchar(preparedStatement, 5, txtAddressEmail);
			ConnectionManager.setVarchar(preparedStatement, 6, txtPassword);
			ConnectionManager.setDatetime(preparedStatement, 7, dtmSignup);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idUser = resultSet.getLong(1);
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
				ConnectionManager.setBigint(preparedStatement, 1, idUserType);
				ConnectionManager.setBoolean(preparedStatement, 2, flIsAlive);
				ConnectionManager.setInt(preparedStatement, 3, numAge);
				ConnectionManager.setVarchar(preparedStatement, 4, nmUsername);
				ConnectionManager.setVarchar(preparedStatement, 5, txtAddressEmail);
				ConnectionManager.setVarchar(preparedStatement, 6, txtPassword);
				ConnectionManager.setDatetime(preparedStatement, 7, dtmSignup);
				// now set the primary key
				preparedStatement.setLong(8, idUser);
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
			preparedStatement.setLong(1, idUser);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'User' when primary key is null.");
		}

		User user = UserFinder.findByPrimaryKey(this.idUser)
				.withConnection(connection)
				.executeSilent();

		if(null == user) {
			throw new H2ZeroFinderException("Could not find the model 'User' with primaryKey of " + getPrimaryKey());
		}
		this.idUser = user.getIdUser();
		this.idUserType = user.getIdUserType();
		this.flIsAlive = user.getFlIsAlive();
		this.numAge = user.getNumAge();
		this.nmUsername = user.getNmUsername();
		this.txtAddressEmail = user.getTxtAddressEmail();
		this.txtPassword = user.getTxtPassword();
		this.dtmSignup = user.getDtmSignup();
	}

	@Override
	protected void hydrate(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot hydrate bean when primary key is null.");
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_SELECT_HYDRATE);
			preparedStatement.setLong(1, idUser);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) {
				throw new H2ZeroPrimaryKeyException("Could not find result with primary key of: " + getPrimaryKey());
			}
			this.numAge = ConnectionManager.getNullableResultInt(resultSet, 1);
			this.dtmSignup = ConnectionManager.getNullableResultTimestamp(resultSet, 2);
		} catch (SQLException sqlex) {
			throw sqlex;
		} finally {
		this.isHydrated = true;
			ConnectionManager.closeAll(resultSet, preparedStatement);
		}
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
	 * <p>{@link #HIT_ID_USER Use <code>User.HIT_ID_USER</code> to retrieve the hit count for the <code>id_user</code> field}</p>
	 * <p>{@link #HIT_ID_USER_TYPE Use <code>User.HIT_ID_USER_TYPE</code> to retrieve the hit count for the <code>id_user_type</code> field}</p>
	 * <p>{@link #HIT_FL_IS_ALIVE Use <code>User.HIT_FL_IS_ALIVE</code> to retrieve the hit count for the <code>fl_is_alive</code> field}</p>
	 * <p>{@link #HIT_NUM_AGE Use <code>User.HIT_NUM_AGE</code> to retrieve the hit count for the <code>num_age</code> field}</p>
	 * <p>{@link #HIT_NM_USERNAME Use <code>User.HIT_NM_USERNAME</code> to retrieve the hit count for the <code>nm_username</code> field}</p>
	 * <p>{@link #HIT_TXT_ADDRESS_EMAIL Use <code>User.HIT_TXT_ADDRESS_EMAIL</code> to retrieve the hit count for the <code>txt_address_email</code> field}</p>
	 * <p>{@link #HIT_TXT_PASSWORD Use <code>User.HIT_TXT_PASSWORD</code> to retrieve the hit count for the <code>txt_password</code> field}</p>
	 * <p>{@link #HIT_DTM_SIGNUP Use <code>User.HIT_DTM_SIGNUP</code> to retrieve the hit count for the <code>dtm_signup</code> field}</p>

	 */
	public static int getHitCountForField(int hitCountField) { return(HIT_COUNTS[hitCountField]); }

	public UserType getUserType() {
		return(UserType.ALL_LOOKUP.get(this.idUserType));
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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idUser); }
	public void setPrimaryKey(Long idUser) { if(isDifferent(this.idUser, idUser)) { this.idUser = idUser;this.isDirty = true; }}
	public Long getIdUser() { updateHitCount(1); return(this.idUser); }
	public void setIdUser(Long idUser) { if(isDifferent(this.idUser, idUser)) { this.idUser = idUser;this.isDirty = true; }}
	public Long getIdUserType() { updateHitCount(2); return(this.idUserType); }
	public void setIdUserType(Long idUserType) { if(isDifferent(this.idUserType, idUserType)) { this.idUserType = idUserType;this.isDirty = true; }}
	public Boolean getFlIsAlive() { updateHitCount(3); return(this.flIsAlive); }
	public void setFlIsAlive(Boolean flIsAlive) { if(isDifferent(this.flIsAlive, flIsAlive)) { this.flIsAlive = flIsAlive;this.isDirty = true; }}
	public Integer getNumAge() { updateHitCount(4); if(!isHydrated) { hydrateSilent(); this.isHydrated = true; } return(this.numAge); }
	public void setNumAge(Integer numAge) { if(isDifferent(this.numAge, numAge)) { this.numAge = numAge;this.isDirty = true; }}
	public String getNmUsername() { updateHitCount(5); return(this.nmUsername); }
	public void setNmUsername(String nmUsername) { if(isDifferent(this.nmUsername, nmUsername)) { this.nmUsername = nmUsername;this.isDirty = true; }}
	public String getTxtAddressEmail() { updateHitCount(6); return(this.txtAddressEmail); }
	public void setTxtAddressEmail(String txtAddressEmail) { if(isDifferent(this.txtAddressEmail, txtAddressEmail)) { this.txtAddressEmail = txtAddressEmail;this.isDirty = true; }}
	public String getTxtPassword() { updateHitCount(7); return(this.txtPassword); }
	public void setTxtPassword(String txtPassword) { if(isDifferent(this.txtPassword, txtPassword)) { this.txtPassword = txtPassword;this.isDirty = true; }}
	public Timestamp getDtmSignup() { updateHitCount(8); if(!isHydrated) { hydrateSilent(); this.isHydrated = true; } return(this.dtmSignup); }
	public void setDtmSignup(Timestamp dtmSignup) { if(isDifferent(this.dtmSignup, dtmSignup)) { this.dtmSignup = dtmSignup;this.isDirty = true; }}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		ValidationFieldBean idUserTypeValidationFieldBean = new BigintValidator("id_user_type", idUserType.toString(), 0, 0, false).validate();
		idUserTypeValidationFieldBean.setIsIncorrectForeignKey(!UserTypeQuestion.internalDoesPrimaryKeyExist(idUserType));
		validationBean.addValidationFieldBean(idUserTypeValidationFieldBean);

		validationBean.addValidationFieldBean(new BooleanValidator("fl_is_alive", flIsAlive.toString(), 0, 0, true).validate());
		validationBean.addValidationFieldBean(new IntValidator("num_age", numAge.toString(), 0, 0, false).validate());
		validationBean.addValidationFieldBean(new VarcharValidator("nm_username", nmUsername.toString(), 0, 64, false).validate());
		validationBean.addValidationFieldBean(new VarcharValidator("txt_address_email", txtAddressEmail.toString(), 6, 256, false).validate());
		validationBean.addValidationFieldBean(new VarcharValidator("txt_password", txtPassword.toString(), 8, 32, false).validate());
		validationBean.addValidationFieldBean(new DatetimeValidator("dtm_signup", dtmSignup.toString(), 0, 0, true).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		return(
			"{\"User\": {" +
			"\"idUser\":\"" + this.idUser + "\"" +
			"\"idUserType\":\"" + this.idUserType + "\"" +
			"\"flIsAlive\":\"" + this.flIsAlive + "\"" +
			"\"numAge\":\"" + this.numAge + "\"" +
			"\"nmUsername\":\"" + this.nmUsername + "\"" +
			"\"txtAddressEmail\":\"" + this.txtAddressEmail + "\"" +
			"\"txtPassword\": \"<**secure**>\"" +
			"\"dtmSignup\":\"" + this.dtmSignup + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "User");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addToJSONObject(fieldsObject, "idUser", this.getIdUser());
		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserType", this.getIdUserType());
		ModelBaseHelper.addToJSONObject(fieldsObject, "flIsAlive", this.getFlIsAlive());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numAge", this.getNumAge());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmUsername", this.getNmUsername());
		ModelBaseHelper.addToJSONObject(fieldsObject, "txtAddressEmail", this.getTxtAddressEmail());
		ModelBaseHelper.addToJSONObject(fieldsObject, "dtmSignup", this.getDtmSignup());

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
	 * <p>Return an XML representation of the <code>User</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user>" + 
			String.format("<id_user null=\"%b\">%s</id_user>", (this.getIdUser() == null), (this.getIdUser() != null ? this.getIdUser() : "")) + 
			String.format("<id_user_type null=\"%b\">%s</id_user_type>", (this.getIdUserType() == null), (this.getIdUserType() != null ? this.getIdUserType() : "")) + 
			String.format("<fl_is_alive null=\"%b\">%s</fl_is_alive>", (this.getFlIsAlive() == null), (this.getFlIsAlive() != null ? this.getFlIsAlive() : "")) + 
			String.format("<num_age null=\"%b\">%s</num_age>", (this.getNumAge() == null), (this.getNumAge() != null ? this.getNumAge() : "")) + 
			String.format("<nm_username null=\"%b\">%s</nm_username>", (this.getNmUsername() == null), (this.getNmUsername() != null ? XmlHelper.escapeXml(this.getNmUsername()) : "")) + 
			String.format("<txt_address_email null=\"%b\">%s</txt_address_email>", (this.getTxtAddressEmail() == null), (this.getTxtAddressEmail() != null ? XmlHelper.escapeXml(this.getTxtAddressEmail()) : "")) + 
			String.format("<dtm_signup null=\"%b\">%s</dtm_signup>", (this.getDtmSignup() == null), (this.getDtmSignup() != null ? this.getDtmSignup() : "")) + 
			"</user>");
	}


	/**
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "User");
		jsonObject.put("total", HIT_COUNTS[0]);
		jsonObject.put("idUser", HIT_COUNTS[1]);
		jsonObject.put("idUserType", HIT_COUNTS[2]);
		jsonObject.put("flIsAlive", HIT_COUNTS[3]);
		jsonObject.put("numAge", HIT_COUNTS[4]);
		jsonObject.put("nmUsername", HIT_COUNTS[5]);
		jsonObject.put("txtAddressEmail", HIT_COUNTS[6]);
		jsonObject.put("txtPassword", HIT_COUNTS[7]);
		jsonObject.put("dtmSignup", HIT_COUNTS[8]);
		return(jsonObject.toString());
	}

}