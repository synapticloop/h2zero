package com.synapticloop.sample.h2zero.mysql.view;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language

//              (java-create-view-model.templar)

import com.synapticloop.h2zero.base.view.ViewBase;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.sample.h2zero.mysql.model.util.Constants;
import org.json.JSONObject;
import com.synapticloop.h2zero.base.model.ModelBaseHelper;


import com.synapticloop.sample.h2zero.mysql.finder.UserFinder;
import com.synapticloop.sample.h2zero.mysql.model.User;
public class UserUserType extends ViewBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_TYPE_BINDER;

	private User User = null;

	private Long idUser = null;
	private String nmUser = null;
	private String nmUserType = null;

	public UserUserType(Long idUser, String nmUser, String nmUserType) {
		this.idUser = idUser;
		this.nmUser = nmUser;
		this.nmUserType = nmUserType;
	}

	public User getUser() {
		if(null == this.User) {
			this.User = UserFinder.findByPrimaryKey(this.idUser).executeSilent();
		}
		return(this.User);
	}

	/*
	 * Boring ol' getters
	 */

	public Long getIdUser() { return(this.idUser); }

	public String getNmUser() { return(this.nmUser); }

	public String getNmUserType() { return(this.nmUserType); }


	@Override
	public String toString() {
		return(new StringBuilder()
			.append("Model: 'UserUserType'\n")
			.append("  Field: 'idUser:").append(this.idUser).append("'\n")
			.append("  Field: 'nmUser:").append(this.nmUser).append("'\n")
			.append("  Field: 'nmUserType:").append(this.nmUserType).append("'\n")
			.toString());
	}

	public String toJsonString() {
		return(toJSON().toString());
	}

	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "view");
		jsonObject.put("name", "UserUserType");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idUser", this.getIdUser());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUser", this.getNmUser());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUserType", this.getNmUserType());

		jsonObject.put("fields", fieldsObject);

		return(jsonObject);
	}

	public String getJsonString() {
		return(toJsonString());
	}

	/**
	 * <p>Return an XML representation of the <code>UserUserType</code> model as a <code>String</code>, 
	 * with the root node being the name of the table - i.e. <code>&lt;user_user_type /&gt;</code> 
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the model as a <code>String</code>.
	 */
	public String toXMLString() {
		return("<user_user_type>" + 
			String.format("<id_user null=\"%b\">%s</id_user>", (this.getIdUser() == null), (this.getIdUser() != null ? this.getIdUser() : "")) + 
			String.format("<nm_user null=\"%b\">%s</nm_user>", (this.getNmUser() == null), (this.getNmUser() != null ? XmlHelper.escapeXml(this.getNmUser()) : "")) + 
			String.format("<nm_user_type null=\"%b\">%s</nm_user_type>", (this.getNmUserType() == null), (this.getNmUserType() != null ? XmlHelper.escapeXml(this.getNmUserType()) : "")) + 
			"</user_user_type>");
	}

}