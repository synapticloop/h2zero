package com.synapticloop.sample.h2zero.postgresql.view;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language

//              (java-create-view-model.templar)

import com.synapticloop.h2zero.base.view.ViewBase;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;
import org.json.JSONObject;
import com.synapticloop.h2zero.base.model.ModelBaseHelper;


public class UserUserType extends ViewBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_TYPE_BINDER;


	private String nmUsername = null;
	private String nmUserType = null;

	public UserUserType(String nmUsername, String nmUserType) {
		this.nmUsername = nmUsername;
		this.nmUserType = nmUserType;
	}

	/*
	 * Boring ol' getters
	 */

	public String getNmUsername() { return(this.nmUsername); }

	public String getNmUserType() { return(this.nmUserType); }


	@Override
	public String toString() {
		return(new StringBuilder()
			.append("Model: 'UserUserType'\n")
			.append("  Field: 'nmUsername:").append(this.nmUsername).append("'\n")
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

		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUsername", this.getNmUsername());
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
			String.format("<nm_username null=\"%b\">%s</nm_username>", (this.getNmUsername() == null), (this.getNmUsername() != null ? XmlHelper.escapeXml(this.getNmUsername()) : "")) + 
			String.format("<nm_user_type null=\"%b\">%s</nm_user_type>", (this.getNmUserType() == null), (this.getNmUserType() != null ? XmlHelper.escapeXml(this.getNmUserType()) : "")) + 
			"</user_user_type>");
	}

}