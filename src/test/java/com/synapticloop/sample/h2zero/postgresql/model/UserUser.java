package com.synapticloop.sample.h2zero.postgresql.model;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                  (/java/model/java-create-model.templar)

import com.synapticloop.h2zero.base.manager.postgresql.ConnectionManager;
import com.synapticloop.h2zero.base.validator.bean.ValidationBean;
import com.synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import com.synapticloop.sample.h2zero.postgresql.question.UserTypeQuestion;
import com.synapticloop.h2zero.base.validator.*;
import com.synapticloop.h2zero.base.model.postgresql.ModelBase;
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
import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;

import com.synapticloop.sample.h2zero.postgresql.finder.UserUserFinder;


/**
 * <p>This is the model for the <code>UserUser</code> which maps to the <code>user_user</code> database table.</p>
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
 *       <td><code>id_user_user</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td><code>primary</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>id_user_type</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td> <code>foreign -> user_type.id_user_type</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>fl_is_alive</code></td>
 *       <td>boolean</td>
 *       <td> -- </td>
 *       <td>true</td>
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
 *       <td><code>nm_username</code></td>
 *       <td>varchar</td>
 *       <td>(0:64)</td>
 *       <td>false</td>
 *       <td> <primary>unique</primary></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>txt_address_email</code></td>
 *       <td>varchar</td>
 *       <td>(6:256)</td>
 *       <td>false</td>
 *       <td> <primary>unique</primary></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>txt_password</code></td>
 *       <td>varchar</td>
 *       <td>(8:32)</td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>ts_signup</code></td>
 *       <td>timestamp</td>
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
	// automatically computed, external classes can use these static 
	// fields to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;  // The total number of hits on all fields of this model
	public static final int HIT_ID_USER_USER = 1; // The number of hits on the id_user_user property
	public static final int HIT_ID_USER_TYPE = 2; // The number of hits on the id_user_type property
	public static final int HIT_FL_IS_ALIVE = 3; // The number of hits on the fl_is_alive property
	public static final int HIT_NUM_AGE = 4; // The number of hits on the num_age property
	public static final int HIT_NM_USERNAME = 5; // The number of hits on the nm_username property
	public static final int HIT_TXT_ADDRESS_EMAIL = 6; // The number of hits on the txt_address_email property
	public static final int HIT_TXT_PASSWORD = 7; // The number of hits on the txt_password property
	public static final int HIT_TS_SIGNUP = 8; // The number of hits on the ts_signup property


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user_user", "id_user_type", "fl_is_alive", "num_age", "nm_username", "txt_address_email", "txt_password", "ts_signup" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private boolean isHydrated = false; // whether this model is populated


	private Long idUserUser = null; // maps to the id_user_user field
	private Long idUserType = null; // maps to the id_user_type field
	private Boolean flIsAlive = null; // maps to the fl_is_alive field
	private Integer numAge = null; // maps to the num_age field
	private String nmUsername = null; // maps to the nm_username field
	private String txtAddressEmail = null; // maps to the txt_address_email field
	private String txtPassword = null; // maps to the txt_password field
	private Timestamp tsSignup = null; // maps to the ts_signup field

	/**
	 * <p>Create a new <code>UserUser</code> object with all fields
	 * some of which can be null.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object.</p>
	 * 
	 * <p>Creating a new UserUser:</p>
	 * 
	 * <pre>new UserUser(
	 *     Long idUserUser,  // id_user_user 
	 *     Long idUserType,  // id_user_type 
	 *     Boolean flIsAlive,  // fl_is_alive  [NULLABLE]
	 *     Integer numAge,  // num_age 
	 *     String nmUsername,  // nm_username 
	 *     String txtAddressEmail,  // txt_address_email 
	 *     String txtPassword,  // txt_password 
	 *     Timestamp tsSignup // ts_signup  [NULLABLE]
	 * );</pre>
	 * 
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
	 * <p>Create a new <code>UserUser</code> object with only the
	 * fields that are non-nullable.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object</p>
	 * 
	 * <p>Creating a new UserUser:</p>
	 * 
	 * <pre>new UserUser(
	 *     Long idUserUser,  // id_user_user
	 *     Long idUserType,  // id_user_type
	 *     Integer numAge,  // num_age
	 *     String nmUsername,  // nm_username
	 *     String txtAddressEmail,  // txt_address_email
	 *     String txtPassword // txt_password
	 * );</pre>
	 * 
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
	 * @param idUserType
	 * @param numAge
	 * @param nmUsername
	 * @param txtAddressEmail
	 * @param txtPassword
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

		UserUser userUser = UserUserFinder.findByPrimaryKey(this.idUserUser)
				.withConnection(connection)
				.executeSilent();

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

	/**
	 * <p>The <code>id_user_type</code> is a foreign key, consequently this is a convenience
	 * method to get the UserType Model object, rather than returning the
	 * primary key field.</p>
	 * 
	 * <p>To find the referenced primary key field - use the <code>getIdUserType()</code>
	 * method.</p>
	 * 
	 * <p>This reference is a to a <code>CONSTANT</code> table, so the <code>ALL_LOOKUP</code>
	 * reference is used, rather than a call to the database.</p>
	 * 
	 * @return The model for the foreign key reference. 
	 */
	public UserType getUserType() {
		return(UserType.ALL_LOOKUP.get(this.idUserType));
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
	 * <p>Convenience method for returning the primary key field (which is the id_user_user field).</p>
	 * 
	 * <p><strong>NOTE:</strong> This will update the hit count.</p>
	 * 
	 * @return The value of the primary key
	 */
	public Long getPrimaryKey() {updateHitCount(1); return(this.idUserUser); }

	/**
	 * <p>Set the primary key field (which is the id_user_user field).</p>
	 * 
	 * <p>If the primary key value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 * 
	 * <p>If the primary key value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 * 
	 * @param idUser The primary key field to update
	 */
	public void setPrimaryKey(Long idUserUser) {if(isDifferent(this.idUserUser, idUserUser)) { this.idUserUser = idUserUser; this.isDirty = true;}}

	/**
	 * <p>Return the value of the id_user_user, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user_user which may NOT be null.
	 */
	public Long getIdUserUser() {updateHitCount(1); return(this.idUserUser); }

	/**
	 * <p>Set the id_user_user value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param idUserUser the id_user_user value to be set
	 */
	public void setIdUserUser(Long idUserUser) {if(isDifferent(this.idUserUser, idUserUser)) { this.idUserUser = idUserUser; this.isDirty = true;}}

	/**
	 * <p>Return the value of the id_user_type, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user_type which may NOT be null.
	 */
	public Long getIdUserType() {updateHitCount(2); return(this.idUserType); }

	/**
	 * <p>Set the id_user_type value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param idUserType the id_user_type value to be set
	 */
	public void setIdUserType(Long idUserType) {if(isDifferent(this.idUserType, idUserType)) { this.idUserType = idUserType; this.isDirty = true;}}

	/**
	 * <p>Return the value of the fl_is_alive, updating the hit count for this field.</p>
	 * 
	 * @return the value of the fl_is_alive which may be null.
	 */
	public Boolean getFlIsAlive() {updateHitCount(3); return(this.flIsAlive); }

	/**
	 * <p>Set the fl_is_alive value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param flIsAlive the fl_is_alive value to be set
	 */
	public void setFlIsAlive(Boolean flIsAlive) {if(isDifferent(this.flIsAlive, flIsAlive)) { this.flIsAlive = flIsAlive; this.isDirty = true;}}

	/**
	 * <p>Return the value of the num_age, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_age which may NOT be null.
	 */
	public Integer getNumAge() {updateHitCount(4); if(!isHydrated) { hydrateSilent(); this.isHydrated = true; } return(this.numAge); }

	/**
	 * <p>Set the num_age value.</p>
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
	 * <p>Return the value of the nm_username, updating the hit count for this field.</p>
	 * 
	 * @return the value of the nm_username which may NOT be null.
	 */
	public String getNmUsername() {updateHitCount(5); return(this.nmUsername); }

	/**
	 * <p>Set the nm_username value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param nmUsername the nm_username value to be set
	 */
	public void setNmUsername(String nmUsername) {if(isDifferent(this.nmUsername, nmUsername)) { this.nmUsername = nmUsername; this.isDirty = true;}}

	/**
	 * <p>Return the value of the txt_address_email, updating the hit count for this field.</p>
	 * 
	 * @return the value of the txt_address_email which may NOT be null.
	 */
	public String getTxtAddressEmail() {updateHitCount(6); return(this.txtAddressEmail); }

	/**
	 * <p>Set the txt_address_email value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param txtAddressEmail the txt_address_email value to be set
	 */
	public void setTxtAddressEmail(String txtAddressEmail) {if(isDifferent(this.txtAddressEmail, txtAddressEmail)) { this.txtAddressEmail = txtAddressEmail; this.isDirty = true;}}

	/**
	 * <p>Return the value of the txt_password, updating the hit count for this field.</p>
	 * 
	 * @return the value of the txt_password which may NOT be null.
	 */
	public String getTxtPassword() {updateHitCount(7); return(this.txtPassword); }

	/**
	 * <p>Set the txt_password value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param txtPassword the txt_password value to be set
	 */
	public void setTxtPassword(String txtPassword) {if(isDifferent(this.txtPassword, txtPassword)) { this.txtPassword = txtPassword; this.isDirty = true;}}

	/**
	 * <p>Return the value of the ts_signup, updating the hit count for this field.</p>
	 * 
	 * @return the value of the ts_signup which may be null.
	 */
	public Timestamp getTsSignup() {updateHitCount(8); if(!isHydrated) { hydrateSilent(); this.isHydrated = true; } return(this.tsSignup); }

	/**
	 * <p>Set the ts_signup value.</p>
	 * 
	 * <p>If the field value has the same value as the passed in parameter, then 
	 * nothing will be done.</p>
	 *
	 * <p>If the field value differs in value to the passed in parameter, then 
	 * the field will be set and this model will be marked as 'dirty'.</p>
	 *
	 * @param tsSignup the ts_signup value to be set
	 */
	public void setTsSignup(Timestamp tsSignup) {if(isDifferent(this.tsSignup, tsSignup)) { this.tsSignup = tsSignup; this.isDirty = true;}}

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
		return(
			"{\"UserUser\": {" +
			"\"idUserUser\":\"" + this.idUserUser + "\"" +
			"\"idUserType\":\"" + this.idUserType + "\"" +
			"\"flIsAlive\":\"" + this.flIsAlive + "\"" +
			"\"numAge\":\"" + this.numAge + "\"" +
			"\"nmUsername\":\"" + this.nmUsername + "\"" +
			"\"txtAddressEmail\":\"" + this.txtAddressEmail + "\"" +
			"\"txtPassword\": \"<**secure**>\"" +
			"\"tsSignup\":\"" + this.tsSignup + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserUser");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserUser", this.getIdUserUser());
		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserType", this.getIdUserType());
		ModelBaseHelper.addToJSONObject(fieldsObject, "flIsAlive", this.getFlIsAlive());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numAge", this.getNumAge());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmUsername", this.getNmUsername());
		ModelBaseHelper.addToJSONObject(fieldsObject, "txtAddressEmail", this.getTxtAddressEmail());
		ModelBaseHelper.addToJSONObject(fieldsObject, "tsSignup", this.getTsSignup());

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
	 * <p>Get the hit count statistics as a JSON encoded object as a <code>String</code></p>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "UserUser");
		jsonObject.put("total", HIT_COUNTS[0]);
		jsonObject.put("idUserUser", HIT_COUNTS[1]);
		jsonObject.put("idUserType", HIT_COUNTS[2]);
		jsonObject.put("flIsAlive", HIT_COUNTS[3]);
		jsonObject.put("numAge", HIT_COUNTS[4]);
		jsonObject.put("nmUsername", HIT_COUNTS[5]);
		jsonObject.put("txtAddressEmail", HIT_COUNTS[6]);
		jsonObject.put("txtPassword", HIT_COUNTS[7]);
		jsonObject.put("tsSignup", HIT_COUNTS[8]);
		return(jsonObject.toString());
	}

}