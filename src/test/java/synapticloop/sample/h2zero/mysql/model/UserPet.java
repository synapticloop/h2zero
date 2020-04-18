package synapticloop.sample.h2zero.mysql.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.base.validator.bean.ValidationBean;
import synapticloop.h2zero.base.validator.bean.ValidationFieldBean;
import synapticloop.sample.h2zero.mysql.question.UserQuestion;
import synapticloop.sample.h2zero.mysql.question.PetQuestion;
import synapticloop.h2zero.base.validator.*;
import synapticloop.h2zero.base.model.mysql.ModelBase;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import org.json.JSONObject;

import synapticloop.sample.h2zero.mysql.model.util.Constants;

import synapticloop.sample.h2zero.mysql.finder.UserPetFinder;
import synapticloop.sample.h2zero.mysql.finder.UserFinder;
import synapticloop.sample.h2zero.mysql.finder.PetFinder;


public class UserPet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_PET_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_user_pet";

	private static final String SQL_INSERT = "insert into user_pet values (?, ?, ?)";
	private static final String SQL_UPDATE = "update user_pet set id_user = ?, id_pet = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from user_pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from user_pet where id_user = ? and id_pet = ?";


// Static lookups for fields in the hit counter.
	public static final int HIT_TOTAL = 0;
	public static final int HIT_PRIMARY_KEY = 0;
	public static final int HIT_ID_USER_PET = 1;
	public static final int HIT_ID_USER = 2;
	public static final int HIT_ID_PET = 3;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_user_pet", "id_user", "id_pet" };
	// the number of read-hits for a particular field
	private static int[] HIT_COUNTS = { 0, 0, 0, 0 };

	private User User = null;
	private Pet Pet = null;

	private Long idUserPet = null;
	private Long idUser = null;
	private Long idPet = null;

	public UserPet(Long idUserPet, Long idUser, Long idPet) {
		this.idUserPet = idUserPet;
		this.idUser = idUser;
		this.idPet = idPet;
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
		// create this bean 
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		ConnectionManager.setBigint(preparedStatement, 1, idUserPet);
		ConnectionManager.setBigint(preparedStatement, 2, idUser);
		ConnectionManager.setBigint(preparedStatement, 3, idPet);
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		if(resultSet.next()) {
			this.idUserPet = resultSet.getLong(1);
		} else {
			throw new H2ZeroPrimaryKeyException("Could not get return value for primary key!");
		}
		ConnectionManager.closeAll(resultSet, preparedStatement);
	}

	@Override
	public void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_ENSURE);
		ConnectionManager.setBigint(preparedStatement, 1, idUser);
		ConnectionManager.setBigint(preparedStatement, 2, idPet);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			this.idUserPet = resultSet.getLong(1);
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
			ConnectionManager.setBigint(preparedStatement, 1, idUser);
			ConnectionManager.setBigint(preparedStatement, 2, idPet);
			// now set the primary key
			preparedStatement.setLong(3, idUserPet);
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
		preparedStatement.setLong(1, idUserPet);
		preparedStatement.executeUpdate();
		ConnectionManager.closeAll(preparedStatement);
	}

	@Override
	public void refresh(Connection connection) throws H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh bean when primary key is null.");
		}
		UserPet userPet = UserPetFinder.findByPrimaryKeySilent(connection, this.idUserPet);
		this.idUserPet = userPet.getIdUserPet();
		this.idUser = userPet.getIdUser();
		this.idPet = userPet.getIdPet();
		this.User = null;
		this.Pet = null;
	}

	public static String[] getHitFields() { return(HIT_FIELDS); }
	public static int[] getHitCounts() { return(HIT_COUNTS); }

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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model[UserPet]\n");
		stringBuilder.append("  Field[idUserPet:" + this.idUserPet + "]\n");
		stringBuilder.append("  Field[idUser:" + this.idUser + "]\n");
		stringBuilder.append("  Field[idPet:" + this.idPet + "]\n");
		return(stringBuilder.toString());
	}

	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "UserPet");
		if(null == this.idUserPet) {
			jsonObject.putOpt("idUserPet", null);
		} else {
			jsonObject.put("idUserPet", this.idUserPet.toString());
		}
		if(null == this.idUser) {
			jsonObject.putOpt("idUser", null);
		} else {
			jsonObject.put("idUser", this.idUser.toString());
		}
		if(null == this.idPet) {
			jsonObject.putOpt("idPet", null);
		} else {
			jsonObject.put("idPet", this.idPet.toString());
		}
		return(jsonObject.toString());
	}

	public String getJsonString() {
		return(toJsonString());
	}

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