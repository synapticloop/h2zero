package synapticloop.sample.h2zero.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.ConnectionManager;
import synapticloop.h2zero.base.model.ModelBase;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import synapticloop.sample.h2zero.model.util.Constants;

import synapticloop.sample.h2zero.finder.PetFinder;


public class Pet extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_pet";

	private static final String SQL_INSERT = "insert into pet values (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update pet set nm_pet = ?, num_age = ?, flt_weight = ?, dt_birthday = ? where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_DELETE = "delete from pet where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from pet where nm_pet = ? and num_age = ? and flt_weight = ? and dt_birthday = ?";

	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_pet", "nm_pet", "num_age", "flt_weight", "dt_birthday" };
	// the number of read-hits for a particular field
	private static int[] HIT_COUNTS = { 0, 0, 0, 0, 0, 0 };

	private Long idPet = null;
	private Boolean nmPet = null;
	private Integer numAge = null;
	private Float fltWeight = null;
	private Date dtBirthday = null;

	public Pet(Long idPet, Boolean nmPet, Integer numAge, Float fltWeight, Date dtBirthday) {
		this.idPet = idPet;
		this.nmPet = nmPet;
		this.numAge = numAge;
		this.fltWeight = fltWeight;
		this.dtBirthday = dtBirthday;
	}

	public Pet(Long idPet, Integer numAge) {
		this.idPet = idPet;
		this.nmPet = null;
		this.numAge = numAge;
		this.fltWeight = null;
		this.dtBirthday = null;
	}

	public boolean primaryKeySet() {
		return(null != idPet);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert bean when primary key is not null.");
		}
		// create this bean 
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		ConnectionManager.setBigint(preparedStatement, 1, idPet);
		ConnectionManager.setBoolean(preparedStatement, 2, nmPet);
		ConnectionManager.setInt(preparedStatement, 3, numAge);
		ConnectionManager.setFloat(preparedStatement, 4, fltWeight);
		ConnectionManager.setDate(preparedStatement, 5, dtBirthday);
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		if(resultSet.next()) {
			this.idPet = resultSet.getLong(1);
		} else {
			throw new H2ZeroPrimaryKeyException("Could not get return value for primary key!");
		}
		ConnectionManager.closeAll(resultSet, preparedStatement);
	}

	@Override
	public void ensure(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_ENSURE);
		ConnectionManager.setBoolean(preparedStatement, 1, nmPet);
		ConnectionManager.setInt(preparedStatement, 2, numAge);
		ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
		ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			this.idPet = resultSet.getLong(1);
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
			ConnectionManager.setBoolean(preparedStatement, 1, nmPet);
			ConnectionManager.setInt(preparedStatement, 2, numAge);
			ConnectionManager.setFloat(preparedStatement, 3, fltWeight);
			ConnectionManager.setDate(preparedStatement, 4, dtBirthday);
			// now set the primary key
			preparedStatement.setLong(5, idPet);
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
		preparedStatement.setLong(1, idPet);
		preparedStatement.executeUpdate();
		ConnectionManager.closeAll(preparedStatement);
	}

	@Override
	public void refresh(Connection connection) throws H2ZeroPrimaryKeyException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh bean when primary key is null.");
		}
		Pet pet = PetFinder.findByPrimaryKeySilent(connection, this.idPet);
		this.idPet = pet.getIdPet();
		this.nmPet = pet.getNmPet();
		this.numAge = pet.getNumAge();
		this.fltWeight = pet.getFltWeight();
		this.dtBirthday = pet.getDtBirthday();
	}

	public static String[] getHitFields() { return(HIT_FIELDS); }
	public static int[] getHitCounts() { return(HIT_COUNTS); }

	/*
	 * Boring ol' getters and setters 
	 */

	public Long getPrimaryKey() { updateHitCount(0 + 1);return(this.idPet); }
	public void setPrimaryKey(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; }}
	public static void updateHitCount(int offset) {
		HIT_COUNTS[0]++;
		HIT_COUNTS[offset]++;
	}

	public Long getIdPet() { updateHitCount(0 + 1); return(this.idPet); }
	public void setIdPet(Long idPet) { if(isDifferent(this.idPet, idPet)) { this.idPet = idPet;this.isDirty = true; }}
	public Boolean getNmPet() { updateHitCount(1 + 1); return(this.nmPet); }
	public void setNmPet(Boolean nmPet) { if(isDifferent(this.nmPet, nmPet)) { this.nmPet = nmPet;this.isDirty = true; }}
	public Integer getNumAge() { updateHitCount(2 + 1); return(this.numAge); }
	public void setNumAge(Integer numAge) { if(isDifferent(this.numAge, numAge)) { this.numAge = numAge;this.isDirty = true; }}
	public Float getFltWeight() { updateHitCount(3 + 1); return(this.fltWeight); }
	public void setFltWeight(Float fltWeight) { if(isDifferent(this.fltWeight, fltWeight)) { this.fltWeight = fltWeight;this.isDirty = true; }}
	public Date getDtBirthday() { updateHitCount(4 + 1); return(this.dtBirthday); }
	public void setDtBirthday(Date dtBirthday) { if(isDifferent(this.dtBirthday, dtBirthday)) { this.dtBirthday = dtBirthday;this.isDirty = true; }}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model[Pet]\n");
		stringBuilder.append("  Field[idPet:" + this.idPet + "]\n");
		stringBuilder.append("  Field[nmPet:" + this.nmPet + "]\n");
		stringBuilder.append("  Field[numAge:" + this.numAge + "]\n");
		stringBuilder.append("  Field[fltWeight:" + this.fltWeight + "]\n");
		stringBuilder.append("  Field[dtBirthday:" + this.dtBirthday + "]\n");
		return(stringBuilder.toString());
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");
		stringBuilder.append("  \"model\": {\n");
		stringBuilder.append("    \"name\": \"Pet\",\n");
		stringBuilder.append("    \"fields\": [\n");
		stringBuilder.append("     { \"name\": \"idPet\", \"value\": " + this.idPet + " }, \n");
		stringBuilder.append("     { \"name\": \"nmPet\", \"value\": " + this.nmPet + " }, \n");
		stringBuilder.append("     { \"name\": \"numAge\", \"value\": " + this.numAge + " }, \n");
		stringBuilder.append("     { \"name\": \"fltWeight\", \"value\": " + this.fltWeight + " }, \n");
		stringBuilder.append("     { \"name\": \"dtBirthday\", \"value\": \"" + this.dtBirthday + "\" }\n");
		stringBuilder.append("    ]\n");
		stringBuilder.append("  }\n");
		stringBuilder.append("}\n");
		return(stringBuilder.toString());
	}

	public String getJsonString() {
		return(toJsonString());
	}
}