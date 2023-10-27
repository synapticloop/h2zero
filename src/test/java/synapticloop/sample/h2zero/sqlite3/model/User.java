package synapticloop.sample.h2zero.sqlite3.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import synapticloop.h2zero.base.validator.bean.ValidationBean;
import synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import synapticloop.sample.h2zero.sqlite3.question.UserTypeQuestion;
import synapticloop.h2zero.base.validator.*;
import synapticloop.h2zero.base.model.sqlite3.ModelBase;
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

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.sqlite3.model.util.Constants;

import synapticloop.sample.h2zero.sqlite3.finder.UserFinder;


/**
 * This is the model for the User which maps to the user database table
 * and contains the default CRUD methods.
 */
 public class User extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_user";  // the primary key - a convenience field

	private static final String SQL_INSERT = "insert into user (id_user_type, fl_is_alive, num_age, nm_username, txt_address_email, txt_password, dtm_signup) values (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update user set id_user_type = ?, fl_is_alive = ?, num_age = ?, nm_username = ?, txt_address_email = ?, txt_password = ?, dtm_signup = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from user where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from user where id_user_type = ? and fl_is_alive = ? and num_age = ? and nm_username = ? and txt_address_email = ? and txt_password = ? and dtm_signup = ?";

	private static final String SQL_SELECT_HYDRATE = "select num_age, dtm_signup from user where " + PRIMARY_KEY_FIELD + " = ?";

// Static lookups for fields in the hit counter.
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
	private static int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
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

		User user = UserFinder.findByPrimaryKeySilent(connection, this.idUser);
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Model: 'User'\n")
			.append("  Field: 'idUser:").append(this.idUser).append("'\n")
			.append("  Field: 'idUserType:").append(this.idUserType).append("'\n")
			.append("  Field: 'flIsAlive:").append(this.flIsAlive).append("'\n")
			.append("  Field: 'numAge:").append(this.numAge).append("'\n")
			.append("  Field: 'nmUsername:").append(this.nmUsername).append("'\n")
			.append("  Field: 'txtAddressEmail:").append(this.txtAddressEmail).append("'\n")
			.append("  Field: 'txtPassword:<**secure**>'\n")
			.append("  Field: 'dtmSignup:").append(this.dtmSignup).append("'\n")
			;
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "User");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUser", this.getIdUser());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserType", this.getIdUserType());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "flIsAlive", this.getFlIsAlive());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "numAge", this.getNumAge());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUsername", this.getNmUsername());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "txtAddressEmail", this.getTxtAddressEmail());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "dtmSignup", this.getDtmSignup());

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