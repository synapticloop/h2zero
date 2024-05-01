package com.synapticloop.sample.h2zero.postgresql.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;


/**
 * <p>This is the model for the <code>UserTitle</code> which maps to the <code>user_title</code> database table.</p>
 * <p><strong>NOTE:</strong> This is a constant table which cannot be changed and no CRUD methods are available.</p>
  * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserTitle  {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TITLE_BINDER;

	public static final UserTitle MR = new UserTitle(Long.valueOf(1), "Mr.", Integer.valueOf(1));
	public static final UserTitle MRS = new UserTitle(Long.valueOf(2), "Mrs.", Integer.valueOf(2));
	public static final UserTitle MISS = new UserTitle(Long.valueOf(3), "Miss", Integer.valueOf(3));
	public static final UserTitle DR = new UserTitle(Long.valueOf(4), "Dr.", Integer.valueOf(4));
 	public static final Long MR_PRIMARY_KEY_VALUE = Long.valueOf(1);
 	public static final Long MRS_PRIMARY_KEY_VALUE = Long.valueOf(2);
 	public static final Long MISS_PRIMARY_KEY_VALUE = Long.valueOf(3);
 	public static final Long DR_PRIMARY_KEY_VALUE = Long.valueOf(4);

	public static final UserTitle[] ALL =  {
		UserTitle.MR, UserTitle.MRS, UserTitle.MISS, UserTitle.DR
	};

	public static final Map<Long, UserTitle> ALL_LOOKUP = new HashMap<>();
	static{
		ALL_LOOKUP.put(Long.valueOf(1), UserTitle.MR);
		ALL_LOOKUP.put(Long.valueOf(2), UserTitle.MRS);
		ALL_LOOKUP.put(Long.valueOf(3), UserTitle.MISS);
		ALL_LOOKUP.put(Long.valueOf(4), UserTitle.DR);

	};


	public static final String PRIMARY_KEY_FIELD = "id_user_title";  // the primary key - a convenience field



	private Long idUserTitle = null; // maps to the id_user_title field
	private String nmUserTitle = null; // maps to the nm_user_title field
	private Integer numOrderBy = null; // maps to the num_order_by field

	public UserTitle(Long idUserTitle, String nmUserTitle, Integer numOrderBy) {
		this.idUserTitle = idUserTitle;
		this.nmUserTitle = nmUserTitle;
		this.numOrderBy = numOrderBy;
	}

	/*
	 * Boring ol' getters and setters 
	 * 
	 * On setting any of these fields - the 'isDirty' flag will be set
	 * 
	 */

	public Long getPrimaryKey() { return(this.idUserTitle); }
	public Long getIdUserTitle() { return(this.idUserTitle); }
	public String getNmUserTitle() { return(this.nmUserTitle); }
	public Integer getNumOrderBy() { return(this.numOrderBy); }

	@Override
	public String toString() {
		return(
			"{\"UserTitle\": {" +
			"\"idUserTitle\":\"" + this.idUserTitle + "\"" +
			"\"nmUserTitle\":\"" + this.nmUserTitle + "\"" +
			"\"numOrderBy\":\"" + this.numOrderBy + "\"" +
			"}");
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "UserTitle");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addToJSONObject(fieldsObject, "idUserTitle", this.getIdUserTitle());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmUserTitle", this.getNmUserTitle());
		ModelBaseHelper.addToJSONObject(fieldsObject, "numOrderBy", this.getNumOrderBy());

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
	 * <p>Return an XML representation of the <code>UserTitle</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user_title /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user_title>" + 
			String.format("<id_user_title null=\"%b\">%s</id_user_title>", (this.getIdUserTitle() == null), (this.getIdUserTitle() != null ? this.getIdUserTitle() : "")) + 
			String.format("<nm_user_title null=\"%b\">%s</nm_user_title>", (this.getNmUserTitle() == null), (this.getNmUserTitle() != null ? XmlHelper.escapeXml(this.getNmUserTitle()) : "")) + 
			String.format("<num_order_by null=\"%b\">%s</num_order_by>", (this.getNumOrderBy() == null), (this.getNumOrderBy() != null ? this.getNumOrderBy() : "")) + 
			"</user_title>");
	}

}