package synapticloop.sample.h2zero.mysql.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import synapticloop.h2zero.base.manager.mysql.ConnectionManager;
import synapticloop.h2zero.base.validator.bean.ValidationBean;
import synapticloop.h2zero.base.validator.*;
import synapticloop.h2zero.base.model.mysql.ModelBase;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import synapticloop.h2zero.util.XmlHelper;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.mysql.model.util.Constants;

import synapticloop.sample.h2zero.mysql.finder.PetTypeFinder;


/**
 * <p>This is the model for the <code>PetType</code> which maps to the <code>pet_type</code> database table.</p>
 * <p>This class contains all CRUD (Create, Read, Update, and Delete) methods.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class PetType extends ModelBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.PET_TYPE_BINDER;

	public static final String PRIMARY_KEY_FIELD = "id_pet_type";  // the primary key - a convenience field

	private static final String SQL_INSERT = 
		"""
			insert into
			pet_type (
				nm_pet_type,
				txt_desc_pet_type
			) values (
				?,
				?
			)
		""";
	private static final String SQL_UPDATE = 
		"""
			update
				pet_type
			set
				nm_pet_type = ?,
				txt_desc_pet_type = ?
			where
		"""
			+ PRIMARY_KEY_FIELD + 
		"""
			= ?
		""";
	private static final String SQL_DELETE = "delete from pet_type where " + PRIMARY_KEY_FIELD + " = ?";
	private static final String SQL_ENSURE = "select " + PRIMARY_KEY_FIELD + " from pet_type where nm_pet_type = ? and txt_desc_pet_type = ?";


	// Static lookups for fields in the hit counter.
	// Whilst these aren't used internally (the offset to the array is 
	// automatically computer, external classes can use these static fields 
	// to look up the hit counts in the array 
	public static final int HIT_TOTAL = 0;
	public static final int HIT_ID_PET_TYPE = 1;
	public static final int HIT_NM_PET_TYPE = 2;
	public static final int HIT_TXT_DESC_PET_TYPE = 3;


	// the list of fields for the hit - starting with 'TOTAL'
	private static final String[] HIT_FIELDS = { "TOTAL", "id_pet_type", "nm_pet_type", "txt_desc_pet_type" };
	// the number of read-hits for a particular field
	private static final int[] HIT_COUNTS = { 0, 0, 0, 0 };


	private Long idPetType = null; // maps to the id_pet_type field
	private String nmPetType = null; // maps to the nm_pet_type field
	private String txtDescPetType = null; // maps to the txt_desc_pet_type field

	/**
	 * Instantiate the PetType object with all the fields within the table.
	 * 
	 * <p>You have a primary key field of <code>synapticloop.h2zero.model.field.BigintField@3670be97</code>
	 * Note, that if the primary key on this table is an <code>auto_increment</code> field
	 * then, passing in <code>null</code> will automatically generate this field value
	 * and will set the value.</p>
	 * 
	 * @param idPetType - maps to the <code>id_pet_type</code>
	 * @param nmPetType - maps to the <code>nm_pet_type</code>
	 * @param txtDescPetType - maps to the <code>txt_desc_pet_type</code>
	 */
	public PetType(Long idPetType, String nmPetType, String txtDescPetType) {
		this.idPetType = idPetType;
		this.nmPetType = nmPetType;
		this.txtDescPetType = txtDescPetType;
	}

	/**
	 * <p>Get a new PetType model, or set the fields on an existing
	 * PetType model.</p>
	 * 
	 * <p>If the passed in petType is null, then a new PetType
	 * will be created.  If not null, the fields will be updated on the passed in model.</p>
	 * 
	 * <p><strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call - this will insert the model if it .
	 * doesn't exist, or update the existing model.</p>
	 * 
	 * @param petType the model to check
	 * @param nmPetType - maps to the <code>nm_pet_type</code> field.
	 * @param txtDescPetType - maps to the <code>txt_desc_pet_type</code> field.
	 * 
	 * @return Either the existing petType with updated field values,
	 *   or a new PetType with the field values set.
	 */
	public static PetType getOrSet(PetType petType,String nmPetType, String txtDescPetType) {
		if(null == petType) {
			return (new PetType(null, nmPetType, txtDescPetType));
		} else {
			petType.setNmPetType(nmPetType);
			petType.setTxtDescPetType(txtDescPetType);

			return(petType);
		}
	}

	@Override
	public boolean primaryKeySet() {
		return(null != idPetType);
	}


	@Override
	public void insert(Connection connection) throws SQLException, H2ZeroPrimaryKeyException {
		if(primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot insert pet_type model when primary key is not null.");
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// create this bean 
			preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ConnectionManager.setVarchar(preparedStatement, 1, nmPetType);
			ConnectionManager.setVarchar(preparedStatement, 2, txtDescPetType);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				this.idPetType = resultSet.getLong(1);
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
			ConnectionManager.setVarchar(preparedStatement, 1, nmPetType);
			ConnectionManager.setVarchar(preparedStatement, 2, txtDescPetType);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				this.idPetType = resultSet.getLong(1);
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
				ConnectionManager.setVarchar(preparedStatement, 1, nmPetType);
				ConnectionManager.setVarchar(preparedStatement, 2, txtDescPetType);
				// now set the primary key
				preparedStatement.setLong(3, idPetType);
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
			preparedStatement.setLong(1, idPetType);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void refresh(Connection connection) throws SQLException, H2ZeroPrimaryKeyException, H2ZeroFinderException {
		if(!primaryKeySet()) {
			throw new H2ZeroPrimaryKeyException("Cannot refresh model 'PetType' when primary key is null.");
		}

		PetType petType = PetTypeFinder.findByPrimaryKeySilent(connection, this.idPetType);
		if(null == petType) {
			throw new H2ZeroFinderException("Could not find the model 'PetType' with primaryKey of " + getPrimaryKey());
		}
		this.idPetType = petType.getIdPetType();
		this.nmPetType = petType.getNmPetType();
		this.txtDescPetType = petType.getTxtDescPetType();
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
	 * <p>{@link #HIT_ID_PET_TYPE Use <code>PetType.HIT_ID_PET_TYPE</code> to retrieve the hit count for the <code>id_pet_type</code> field}</p>
	 * <p>{@link #HIT_NM_PET_TYPE Use <code>PetType.HIT_NM_PET_TYPE</code> to retrieve the hit count for the <code>nm_pet_type</code> field}</p>
	 * <p>{@link #HIT_TXT_DESC_PET_TYPE Use <code>PetType.HIT_TXT_DESC_PET_TYPE</code> to retrieve the hit count for the <code>txt_desc_pet_type</code> field}</p>

	 */
	public static int getHitCountForField(int hitCountField) { return(HIT_COUNTS[hitCountField]); }

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

	public Long getPrimaryKey() { updateHitCount(1); return(this.idPetType); }
	public void setPrimaryKey(Long idPetType) { if(isDifferent(this.idPetType, idPetType)) { this.idPetType = idPetType;this.isDirty = true; }}
	public Long getIdPetType() { updateHitCount(1); return(this.idPetType); }
	public void setIdPetType(Long idPetType) { if(isDifferent(this.idPetType, idPetType)) { this.idPetType = idPetType;this.isDirty = true; }}
	public String getNmPetType() { updateHitCount(2); return(this.nmPetType); }
	public void setNmPetType(String nmPetType) { if(isDifferent(this.nmPetType, nmPetType)) { this.nmPetType = nmPetType;this.isDirty = true; }}
	public String getTxtDescPetType() { updateHitCount(3); return(this.txtDescPetType); }
	public void setTxtDescPetType(String txtDescPetType) { if(isDifferent(this.txtDescPetType, txtDescPetType)) { this.txtDescPetType = txtDescPetType;this.isDirty = true; }}

	@Override
	public ValidationBean validate() {
		ValidationBean validationBean = new ValidationBean();

		validationBean.addValidationFieldBean(new VarcharValidator("nm_pet_type", nmPetType.toString(), 0, 64, false).validate());
		validationBean.addValidationFieldBean(new VarcharValidator("txt_desc_pet_type", txtDescPetType.toString(), 0, 64, false).validate());
		return(validationBean);
	}


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("{\"PetType\": {\n")
			.append("\"idPetType\":\"").append(this.idPetType).append("\"")
			.append("\"nmPetType\":\"").append(this.nmPetType).append("\"")
			.append("\"txtDescPetType\":\"").append(this.txtDescPetType).append("\"")
			.append("}");
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "PetType");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idPetType", this.getIdPetType());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmPetType", this.getNmPetType());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "txtDescPetType", this.getTxtDescPetType());

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
	 * <p>Return an XML representation of the <code>PetType</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;pet_type /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<pet_type>" + 
			String.format("<id_pet_type null=\"%b\">%s</id_pet_type>", (this.getIdPetType() == null), (this.getIdPetType() != null ? this.getIdPetType() : "")) + 
			String.format("<nm_pet_type null=\"%b\">%s</nm_pet_type>", (this.getNmPetType() == null), (this.getNmPetType() != null ? XmlHelper.escapeXml(this.getNmPetType()) : "")) + 
			String.format("<txt_desc_pet_type null=\"%b\">%s</txt_desc_pet_type>", (this.getTxtDescPetType() == null), (this.getTxtDescPetType() != null ? XmlHelper.escapeXml(this.getTxtDescPetType()) : "")) + 
			"</pet_type>");
	}


	/**
	 * Get the hit count statistics as a JSON encoded object as a <code>String</code>.
	 *
	 * @return the JSON Object as a <code>String</code>.
	 */
	public static String getHitCountJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "PetType");
		jsonObject.put("total", HIT_COUNTS[0]);
		JSONObject fieldObject = new JSONObject();
		fieldObject.put("idPetType", HIT_COUNTS[1]);
		fieldObject.put("nmPetType", HIT_COUNTS[2]);
		fieldObject.put("txtDescPetType", HIT_COUNTS[3]);
		jsonObject.put("fields", fieldObject);
		return(jsonObject.toString());
	}

}