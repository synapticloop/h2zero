package com.synapticloop.sample.h2zero.mysql.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.mysql.model.util.Constants;


/**
 * <p>This is the model for the <code>UserType</code> which maps to the <code>user_type</code> database table.</p>
 * <p><strong>NOTE:</strong> This is a constant table which cannot be changed and no CRUD methods are available.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
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

	public static final Map<String, UserType> LOOKUP_NM_USER_TYPE = new HashMap<>();
	public static final Map<Long, UserType> LOOKUP_ID_USER_TYPE = new HashMap<>();
	static {
		LOOKUP_NM_USER_TYPE.put(UserType.NORMAL.getNmUserType(), UserType.NORMAL);
		LOOKUP_NM_USER_TYPE.put(UserType.SPECIAL.getNmUserType(), UserType.SPECIAL);
		LOOKUP_NM_USER_TYPE.put(UserType.ADMIN.getNmUserType(), UserType.ADMIN);
		LOOKUP_NM_USER_TYPE.put(UserType.SUPER_ADMIN.getNmUserType(), UserType.SUPER_ADMIN);

		LOOKUP_ID_USER_TYPE.put(UserType.NORMAL.getIdUserType(), UserType.NORMAL);
		LOOKUP_ID_USER_TYPE.put(UserType.SPECIAL.getIdUserType(), UserType.SPECIAL);
		LOOKUP_ID_USER_TYPE.put(UserType.ADMIN.getIdUserType(), UserType.ADMIN);
		LOOKUP_ID_USER_TYPE.put(UserType.SUPER_ADMIN.getIdUserType(), UserType.SUPER_ADMIN);

	};

	public static final String PRIMARY_KEY_FIELD = "id_user_type";  // the primary key - a convenience field



	private Long idUserType = null; // maps to the id_user_type field
	private String nmUserType = null; // maps to the nm_user_type field

	public UserType(Long idUserType, String nmUserType) {
		this.idUserType = idUserType;
		this.nmUserType = nmUserType;
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
		return(
			"{\"UserType\": {" +
			"\"idUserType\":\"" + this.idUserType + "\"" +
			"\"nmUserType\":\"" + this.nmUserType + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserType");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserType", this.getIdUserType());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmUserType", this.getNmUserType());

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
	 * <p>Return an XML representation of the <code>UserType</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user_type /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user_type>" + 
			String.format("<id_user_type null=\"%b\">%s</id_user_type>", (this.getIdUserType() == null), (this.getIdUserType() != null ? this.getIdUserType() : "")) + 
			String.format("<nm_user_type null=\"%b\">%s</nm_user_type>", (this.getNmUserType() == null), (this.getNmUserType() != null ? XmlHelper.escapeXml(this.getNmUserType()) : "")) + 
			"</user_type>");
	}

}