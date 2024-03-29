package synapticloop.sample.h2zero.sqlite3.view;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language

//              (java-create-view-model.templar)

import synapticloop.h2zero.base.view.ViewBase;
import synapticloop.h2zero.util.XmlHelper;

import synapticloop.sample.h2zero.sqlite3.model.util.Constants;
import org.json.JSONObject;
import synapticloop.h2zero.base.model.ModelBaseHelper;


public class UserUserType extends ViewBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_TYPE_BINDER;


	private String nmUser = null;
	private String nmUserType = null;

	public UserUserType(String nmUser, String nmUserType) {
		this.nmUser = nmUser;
		this.nmUserType = nmUserType;
	}

	/*
	 * Boring ol' getters
	 */

	public String getNmUser() { return(this.nmUser); }

	public String getNmUserType() { return(this.nmUserType); }


	@Override
	public String toString() {
		return(new StringBuilder()
			.append("Model: 'UserUserType'\n")
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

		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUser", this.getNmUser());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "nmUserType", this.getNmUserType());

		jsonObject.put("fields", fieldsObject);

		return(jsonObject);
	}

	public String getJsonString() {
		return(toJsonString());
	}

	/**
	 * Return an XML representation of the 'UserUserType' model, with the root node being the
	 * name of the table - i.e. <user_user_type> and the child nodes the name of the 
	 * fields.
	 * <p>
	 * <strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document
	 * 
	 * @return An XML representation of the model.  
	 */
	public String toXMLString() {
		return("<user_user_type>" + 
			String.format("<nm_user null=\"%b\">%s</nm_user>", (this.getNmUser() == null), (this.getNmUser() != null ? XmlHelper.escapeXml(this.getNmUser()) : "")) + 
			String.format("<nm_user_type null=\"%b\">%s</nm_user_type>", (this.getNmUserType() == null), (this.getNmUserType() != null ? XmlHelper.escapeXml(this.getNmUserType()) : "")) + 
			"</user_user_type>");
	}

}