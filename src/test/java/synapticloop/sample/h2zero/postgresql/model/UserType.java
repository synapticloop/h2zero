package synapticloop.sample.h2zero.postgresql.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;
import synapticloop.h2zero.util.XmlHelper;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.postgresql.model.util.Constants;


/**
 * This is the model for the UserType which maps to the user_type database table.
 * This is a constant table which cannot be changed
 */
 public class UserType  {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TYPE_BINDER;

	public static final UserType NORMAL = new UserType(Long.valueOf(1), "normal");
	public static final UserType SPECIAL = new UserType(Long.valueOf(2), "special");
	public static final UserType ADMIN = new UserType(Long.valueOf(3), "admin");
	public static final UserType SUPER_ADMIN = new UserType(Long.valueOf(4), "super admin");
 	public static final Long NORMAL_PRIMARY_KEY_VALUE = Long.valueOf(1);
 	public static final Long SPECIAL_PRIMARY_KEY_VALUE = Long.valueOf(2);
 	public static final Long ADMIN_PRIMARY_KEY_VALUE = Long.valueOf(3);
 	public static final Long SUPER_ADMIN_PRIMARY_KEY_VALUE = Long.valueOf(4);

	public static final UserType[] ALL =  {
		UserType.NORMAL, UserType.SPECIAL, UserType.ADMIN, UserType.SUPER_ADMIN
	};

	public static final Map<Long, UserType> ALL_LOOKUP = new HashMap<>();
	static{
		ALL_LOOKUP.put(Long.valueOf(1), UserType.NORMAL);
		ALL_LOOKUP.put(Long.valueOf(2), UserType.SPECIAL);
		ALL_LOOKUP.put(Long.valueOf(3), UserType.ADMIN);
		ALL_LOOKUP.put(Long.valueOf(4), UserType.SUPER_ADMIN);

	};

	public static final String PRIMARY_KEY_FIELD = "id_user_type";  // the primary key - a convenience field



	private Long idUserType = null; // maps to the id_user_type field
	private String nmUserType = null; // maps to the nm_user_type field

	public UserType(Long idUserType, String nmUserType) {
		this.idUserType = idUserType;
		this.nmUserType = nmUserType;
	}

	/**
	 * Get a new UserType model, or set the fields on an existing
	 * UserType model.
	 * <p>
	 * If the passed in userType is null, then a new UserType
	 * will be created.  If not null, the fields will be updated on the passed in model.
	 * <p>
	 * <strong>NOTE:</strong> You will still need to persist this to the database
	 * with an <code>upsert()</code> call.
	 * 
	 * @param userType the model to check
	 * @param idUserType
	 * @param nmUserType
	 * 
	 * @return Either the existing userType with updated field values,
	 *   or a new UserType with the field values set.
	 */
	public static UserType getOrSet(UserType userType,Long idUserType, String nmUserType) {
		if(null == userType) {
			return (new UserType(idUserType, nmUserType));
		} else {
			userType.setIdUserType(idUserType);
			userType.setNmUserType(nmUserType);

			return(userType);
		}
	}
	/*
	 * Boring ol' getters and setters 
	 * 
	 * On setting any of these fields - the 'isDirty' flag will be set
	 * 
	 */

	public Long getPrimaryKey() { return(this.idUserType); }
	public Long getIdUserType() { return(this.idUserType); }
	public String getNmUserType() { return(this.nmUserType); }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Model: 'UserType'\n")
			.append("  Field: 'idUserType:").append(this.idUserType).append("'\n")
			.append("  Field: 'nmUserType:").append(this.nmUserType).append("'\n")
			;
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserType");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUserType", this.getIdUserType());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUserType", this.getNmUserType());

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
	 * Return an XML representation of the 'UserType' model, with the root node being the
	 * name of the table - i.e. <user_type> and the child nodes the name of the 
	 * fields.
	 * <p>
	 * <strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document
	 * 
	 * @return An XML representation of the model.  
	 */
	public String toXMLString() {
		return("<user_type>" + 
			String.format("<id_user_type null=\"%b\">%s</id_user_type>", (this.getIdUserType() == null), (this.getIdUserType() != null ? this.getIdUserType() : "")) + 
			String.format("<nm_user_type null=\"%b\">%s</nm_user_type>", (this.getNmUserType() == null), (this.getNmUserType() != null ? XmlHelper.escapeXml(this.getNmUserType()) : "")) + 
			"</user_type>");
	}

}