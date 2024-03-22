package synapticloop.sample.h2zero.cockroach.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.cockroach.ConnectionManager;
import synapticloop.h2zero.base.validator.bean.ValidationBean;
import synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import synapticloop.sample.h2zero.cockroach.question.UserTypeQuestion;
import synapticloop.h2zero.base.validator.*;
import synapticloop.h2zero.base.model.cockroach.ModelBase;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import synapticloop.h2zero.util.XmlHelper;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.cockroach.model.util.Constants;

import synapticloop.sample.h2zero.cockroach.finder.UserUserFinder;


/**
 * <p>This is the model for the <code>UserUser</code> which maps to the <code>user_user</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserUser extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_user_user";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			user_user (
				id_user_type,
				fl_is_alive,
				num_age,
				nm_username,
				txt_address_email,
				txt_password,
				ts_signup
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
				user_user
			set
				id_user_type = ?,
				fl_is_alive = ?,
				num_age = ?,
				nm_username = ?,
				txt_address_email = ?,
				txt_password = ?,
				ts_signup = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from user_user where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from user_user where id_user_type = ? and fl_is_alive = ? and num_age = ? and nm_username = ? and txt_address_email = ? and txt_password = ? and ts_signup = ?";

	private static final String SQL_SELECT_HYDRATE = "select num_age, ts_signup from user_user where " + PRIMARY_KEY_FIELD + " = ?";

	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_USER_USER = 1;
	public static final int HIT_ID_USER_TYPE = 2;
	public static final int HIT_FL_IS_ALIVE = 3;
	public static final int HIT_NUM_AGE = 4;
	public static final int HIT_NM_USERNAME = 5;
	public static final int HIT_TXT_ADDRESS_EMAIL = 6;
	public static final int HIT_TXT_PASSWORD = 7;
	public static final int HIT_TS_SIGNUP = 8;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user_user", "id_user_type", "fl_is_alive", "num_age", "nm_username", "txt_address_email", "txt_password", "ts_signup" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private boolean isHydrated = false;


	private Long idUserUser = null; // maps to the id_user_user field
	private Long idUserType = null; // maps to the id_user_type field
	private Boolean flIsAlive = null; // maps to the fl_is_alive field
	private Integer numAge = null; // maps to the num_age field
	private String nmUsername = null; // maps to the nm_username field
	private String txtAddressEmail = null; // maps to the txt_address_email field
	private String txtPassword = null; // maps to the txt_password field
	private Timestamp tsSignup = null; // maps to the ts_signup field

	/**
	 * Instantiate the UserUser object with all the fields within the table.
	 * 
	 * <p>You have a primary key field of <code>synapticloop.h2zero.model.field.BigintField@68752231</code>
	 * Note, that if the primary key on this table is an <code>auto_increment</code> field
	 * then, passing in <code>null</code> will automatically generate this field value
	 * and will set the value.</p>
	 * 
	 * @param idUserUser - maps to the <code>id_user_user</code>
	 * @param idUserType - maps to the <code>id_user_type</code>
	 * @param flIsAlive - maps to the <code>fl_is_alive</code>
	 * @param numAge - maps to the <code>num_age</code>
	 * @param nmUsername - maps to the <code>nm_username</code>
	 * @param txtAddressEmail - maps to the <code>txt_address_email</code>
	 * @param txtPassword - maps to the <code>txt_password</code>
	 * @param tsSignup - maps to the <code>ts_signup</code>
	 */
	public UserUser(Long idUserUser, Long idUserType, Boolean flIsAlive, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword, Timestamp tsSignup) {
		this.idUserUser = idUserUser;
		this.idUserType = idUserType;
		this.flIsAlive = flIsAlive;
		this.numAge = numAge;
		this.nmUsername = nmUsername;
		this.txtAddressEmail = txtAddressEmail;
		this.txtPassword = txtPassword;
		this.tsSignup = tsSignup;
	}

	/**
	 * Instantiate the UserUser object with all the non-nullable fields within the table
	 * 
	 * <p>You have a primary key field of <code>synapticloop.h2zero.model.field.BigintField@68752231</code>
	 * Note, that if the primary key on this table is an <code>auto_increment</code> field
	 * then, passing in <code>null</code> will automatically generate this field value
	 * and will set the value.</p>
	 * 
	 * @param idUserUser - maps to the <code>id_user_user</code>
	 * @param idUserType - maps to the <code>id_user_type</code>
	 * @param numAge - maps to the <code>num_age</code>
	 * @param nmUsername - maps to the <code>nm_username</code>
	 * @param txtAddressEmail - maps to the <code>txt_address_email</code>
	 * @param txtPassword - maps to the <code>txt_password</code>
	 */
	public UserUser(Long idUserUser, Long idUserType, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword) {
		this.idUserUser = idUserUser;
		this.idUserType = idUserType;
		this.flIsAlive = null;
		this.numAge = numAge;
		this.nmUsername = nmUsername;
		this.txtAddressEmail = txtAddressEmail;
		this.txtPassword = txtPassword;
		this.tsSignup = null;
	}

	/**
	 * <p>Get a new UserUser model, or set the fields on an existing
	 * UserUser model.</p>
	 * 
	 * <p>If the passed in userUser is null, then a new UserUser
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param userUser the model to check
	 * @param idUserType - maps to the <code>id_user_type</code> field.
	 * @param flIsAlive - maps to the <code>fl_is_alive</code> field.
	 * @param numAge - maps to the <code>num_age</code> field.
	 * @param nmUsername - maps to the <code>nm_username</code> field.
	 * @param txtAddressEmail - maps to the <code>txt_address_email</code> field.
	 * @param txtPassword - maps to the <code>txt_password</code> field.
	 * @param tsSignup - maps to the <code>ts_signup</code> field.
	 * 
	 * @return Either the existing userUser with updated field values,
	 *   or a new UserUser with the field values set.
	 */
	public static UserUser getOrSet(UserUser userUser,Long idUserType, Boolean flIsAlive, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword, Timestamp tsSignup) {
		if(null == userUser) {
			return (new UserUser(null, idUserType, flIsAlive, numAge, nmUsername, txtAddressEmail, txtPassword, tsSignup));
		} else {
			userUser.setIdUserType(idUserType);
			userUser.setFlIsAlive(flIsAlive);
			userUser.setNumAge(numAge);
			userUser.setNmUsername(nmUsername);
			userUser.setTxtAddressEmail(txtAddressEmail);
			userUser.setTxtPassword(txtPassword);
			userUser.setTsSignup(tsSignup);

			return(userUser);
		}
	}

	/**
	 * Get a new UserUser model, or set the fields on an existing
	 * UserUser model.
	 * <p>
	 * If the passed in userUser is null, then a new UserUser
	 * will be created.  If not null, the fields will be updated on the existing model.
	 * <p>
	 * <strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call.
	 * 
	 * @param userUser the model to check
	 * @param idUserType - maps to the <code>id_user_type</code> field.
	 * @param numAge - maps to the <code>num_age</code> field.
	 * @param nmUsername - maps to the <code>nm_username</code> field.
	 * @param txtAddressEmail - maps to the <code>txt_address_email</code> field.
	 * @param txtPassword - maps to the <code>txt_password</code> field.
	 * 
	 * @return Either the existing userUser with updated field values,
	 *   or a new UserUser with the field values set.
	 */
	public static UserUser getOrSet(UserUser userUser, Long idUserType, Integer numAge, String nmUsername, String txtAddressEmail, String txtPassword) {
		if(null == userUser) {
			return (new UserUser(null , idUserType, numAge, nmUsername, txtAddressEmail, txtPassword));
		} else {
			userUser.setIdUserType(idUserType);
			userUser.setNumAge(numAge);
			userUser.setNmUsername(nmUsername);
			userUser.setTxtAddressEmail(txtAddressEmail);
			userUser.setTxtPassword(txtPassword);

			return(userUser);
		}
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idUserUser);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert user_user model when primary key is not null.");
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
			ConnectionManager.setTimestamp(preparedStatement, 7, tsSignup);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idUserUser = resultSet.getLong(1);
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
			ConnectionManager.setTimestamp(preparedStatement, 7, tsSignup);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idUserUser = resultSet.getLong(1);
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
				ConnectionManager.setTimestamp(preparedStatement, 7, tsSignup);
				// now set the primary key
				preparedStatement.setLong(8, idUserUser);
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
			preparedStatement.setLong(1, idUserUser);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'UserUser' when primary key is null.");
		}

		UserUser userUser = UserUserFinder.findByPrimaryKeySilent(connection, this.idUserUser);
		if(null == userUser) {
			throw new H2ZeroFinderException("Could not find the model 'UserUser' with primaryKey of " + getPrimaryKey());
		}
		this.idUserUser = userUser.getIdUserUser();
		this.idUserType = userUser.getIdUserType();
		this.flIsAlive = userUser.getFlIsAlive();
		this.numAge = userUser.getNumAge();
		this.nmUsername = userUser.getNmUsername();
		this.txtAddressEmail = userUser.getTxtAddressEmail();
		this.txtPassword = userUser.getTxtPassword();
		this.tsSignup = userUser.getTsSignup();
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
			preparedStatement.setLong(1, idUserUser);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) {
				throw new H2ZeroPrimaryKeyException("Could not find result with primary key of: " + getPrimaryKey());
			}
			this.numAge = ConnectionManager.getNullableResultInt(resultSet, 1);
			this.tsSignup = ConnectionManager.getNullableResultTimestamp(resultSet, 2);
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
	 * <p>{@link #HIT_ID_USER_USER Use <code>UserUser.HIT_ID_USER_USER</code> to retrieve the hit count for the <code>id_user_user</code> field}</p>
	 * <p>{@link #HIT_ID_USER_TYPE Use <code>UserUser.HIT_ID_USER_TYPE</code> to retrieve the hit count for the <code>id_user_type</code> field}</p>
	 * <p>{@link #HIT_FL_IS_ALIVE Use <code>UserUser.HIT_FL_IS_ALIVE</code> to retrieve the hit count for the <code>fl_is_alive</code> field}</p>
	 * <p>{@link #HIT_NUM_AGE Use <code>UserUser.HIT_NUM_AGE</code> to retrieve the hit count for the <code>num_age</code> field}</p>
	 * <p>{@link #HIT_NM_USERNAME Use <code>UserUser.HIT_NM_USERNAME</code> to retrieve the hit count for the <code>nm_username</code> field}</p>
	 * <p>{@link #HIT_TXT_ADDRESS_EMAIL Use <code>UserUser.HIT_TXT_ADDRESS_EMAIL</code> to retrieve the hit count for the <code>txt_address_email</code> field}</p>
	 * <p>{@link #HIT_TXT_PASSWORD Use <code>UserUser.HIT_TXT_PASSWORD</code> to retrieve the hit count for the <code>txt_password</code> field}</p>
	 * <p>{@link #HIT_TS_SIGNUP Use <code>UserUser.HIT_TS_SIGNUP</code> to retrieve the hit count for the <code>ts_signup</code> field}</p>

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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idUserUser); }
	public void setPrimaryKey(Long idUserUser) { if(isDifferent(this.idUserUser, idUserUser)) { this.idUserUser = idUserUser;this.isDirty = true; }}
	public Long getIdUserUser() { updateHitCount(1); return(this.idUserUser); }
	public void setIdUserUser(Long idUserUser) { if(isDifferent(this.idUserUser, idUserUser)) { this.idUserUser = idUserUser;this.isDirty = true; }}
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
	public Timestamp getTsSignup() { updateHitCount(8); if(!isHydrated) { hydrateSilent(); this.isHydrated = true; } return(this.tsSignup); }
	public void setTsSignup(Timestamp tsSignup) { if(isDifferent(this.tsSignup, tsSignup)) { this.tsSignup = tsSignup;this.isDirty = true; }}

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
		validationBean.addValidationFieldBean(new TimestampValidator("ts_signup", tsSignup.toString(), 0, 0, true).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("{\"UserUser\": {\n")
			.append("\"idUserUser\":\"").append(this.idUserUser).append("\"")
			.append("\"idUserType\":\"").append(this.idUserType).append("\"")
			.append("\"flIsAlive\":\"").append(this.flIsAlive).append("\"")
			.append("\"numAge\":\"").append(this.numAge).append("\"")
			.append("\"nmUsername\":\"").append(this.nmUsername).append("\"")
			.append("\"txtAddressEmail\":\"").append(this.txtAddressEmail).append("\"")
			.append("\"txtPassword\": \"<**secure**>\"\n")
			.append("\"tsSignup\":\"").append(this.tsSignup).append("\"")
			.append("}");
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserUser");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserUser", this.getIdUserUser());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserType", this.getIdUserType());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "flIsAlive", this.getFlIsAlive());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numAge", this.getNumAge());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUsername", this.getNmUsername());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "txtAddressEmail", this.getTxtAddressEmail());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "tsSignup", this.getTsSignup());

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
	 * <p>Return an XML representation of the <code>UserUser</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user_user /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user_user>" + 
			String.format("<id_user_user null=\"%b\">%s</id_user_user>", (this.getIdUserUser() == null), (this.getIdUserUser() != null ? this.getIdUserUser() : "")) + 
			String.format("<id_user_type null=\"%b\">%s</id_user_type>", (this.getIdUserType() == null), (this.getIdUserType() != null ? this.getIdUserType() : "")) + 
			String.format("<fl_is_alive null=\"%b\">%s</fl_is_alive>", (this.getFlIsAlive() == null), (this.getFlIsAlive() != null ? this.getFlIsAlive() : "")) + 
			String.format("<num_age null=\"%b\">%s</num_age>", (this.getNumAge() == null), (this.getNumAge() != null ? this.getNumAge() : "")) + 
			String.format("<nm_username null=\"%b\">%s</nm_username>", (this.getNmUsername() == null), (this.getNmUsername() != null ? XmlHelper.escapeXml(this.getNmUsername()) : "")) + 
			String.format("<txt_address_email null=\"%b\">%s</txt_address_email>", (this.getTxtAddressEmail() == null), (this.getTxtAddressEmail() != null ? XmlHelper.escapeXml(this.getTxtAddressEmail()) : "")) + 
			String.format("<ts_signup null=\"%b\">%s</ts_signup>", (this.getTsSignup() == null), (this.getTsSignup() != null ? this.getTsSignup() : "")) + 
			"</user_user>");
	}


	/**
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "UserUser");
		jsonObject.put("total", HIT_COUNTS[0]);
		JSONObject fieldObject = new JSONObject();
		fieldObject.put("idUserUser", HIT_COUNTS[1]);
		fieldObject.put("idUserType", HIT_COUNTS[2]);
		fieldObject.put("flIsAlive", HIT_COUNTS[3]);
		fieldObject.put("numAge", HIT_COUNTS[4]);
		fieldObject.put("nmUsername", HIT_COUNTS[5]);
		fieldObject.put("txtAddressEmail", HIT_COUNTS[6]);
		fieldObject.put("txtPassword", HIT_COUNTS[7]);
		fieldObject.put("tsSignup", HIT_COUNTS[8]);
		jsonObject.put("fields", fieldObject);
		return(jsonObject.toString());
	}

}