package synapticloop.sample.h2zero.sqlite3.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import java.util.HashMap;

import java.util.Map;

import synapticloop.sample.h2zero.sqlite3.model.util.Constants;


public class UserType {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TYPE_BINDER;

	public static final UserType NORMAL = new UserType(new Long(1), "normal");
	public static final UserType SPECIAL = new UserType(new Long(2), "special");
	public static final UserType ADMIN = new UserType(new Long(3), "admin");
	public static final UserType SUPER_ADMIN = new UserType(new Long(4), "super admin");

	public static final UserType[] ALL =  {
		UserType.NORMAL, UserType.SPECIAL, UserType.ADMIN, UserType.SUPER_ADMIN
	};

	public static final Map<Long, UserType> ALL_LOOKUP = new HashMap<Long, UserType>();
	static{
		ALL_LOOKUP.put(new Long(1), UserType.NORMAL);
		ALL_LOOKUP.put(new Long(2), UserType.SPECIAL);
		ALL_LOOKUP.put(new Long(3), UserType.ADMIN);
		ALL_LOOKUP.put(new Long(4), UserType.SUPER_ADMIN);

	};

	public static final String PRIMARY_KEY_FIELD = "id_user_type";


	private Long idUserType = null;
	private String nmUserType = null;

	public UserType(Long idUserType, String nmUserType) {
		this.idUserType = idUserType;
		this.nmUserType = nmUserType;
	}
	/*
	 * Boring ol' getters and setters 
	 */

	public Long getPrimaryKey() { return(this.idUserType); }
	public Long getIdUserType() { return(this.idUserType); }
	public String getNmUserType() { return(this.nmUserType); }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model[UserType]\n");
		stringBuilder.append("  Field[idUserType:" + this.idUserType + "]\n");
		stringBuilder.append("  Field[nmUserType:" + this.nmUserType + "]\n");
		return(stringBuilder.toString());
	}

	public String toJsonString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");
		stringBuilder.append("  \"model\": {\n");
		stringBuilder.append("    \"name\": \"UserType\",\n");
		stringBuilder.append("    \"fields\": {\n");
		stringBuilder.append("     \"idUserType\": " + this.idUserType + " , \n");
		stringBuilder.append("     \"nmUserType\": \"" + this.nmUserType + "\" \n");
		stringBuilder.append("    }\n");
		stringBuilder.append("  }\n");
		stringBuilder.append("}\n");
		return(stringBuilder.toString());
	}

	public String getJsonString() {
		return(toJsonString());
	}
}