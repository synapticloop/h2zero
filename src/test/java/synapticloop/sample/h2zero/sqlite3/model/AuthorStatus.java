package synapticloop.sample.h2zero.sqlite3.model;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                  (java-create-model.templar)

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;

import synapticloop.h2zero.base.model.ModelBaseHelper;
import synapticloop.sample.h2zero.sqlite3.model.util.Constants;


public class AuthorStatus  {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.AUTHOR_STATUS_BINDER;

	public static final AuthorStatus WAITING = new AuthorStatus(Long.valueOf(1), "WAITING", "Waiting for the number of followers for the author to be hit");
	public static final AuthorStatus TO_BE_EVALUATED = new AuthorStatus(Long.valueOf(2), "TO_BE_EVALUATED", "Author is waiting to be evaluated.");
	public static final AuthorStatus IGNORED = new AuthorStatus(Long.valueOf(3), "IGNORED", "Author is being ignored.");
	public static final AuthorStatus FOLLOWED = new AuthorStatus(Long.valueOf(4), "FOLLOWED", "Author is followed.");

	public static final AuthorStatus[] ALL =  {
		AuthorStatus.WAITING, AuthorStatus.TO_BE_EVALUATED, AuthorStatus.IGNORED, AuthorStatus.FOLLOWED
	};

	public static final Map<Long, AuthorStatus> ALL_LOOKUP = new HashMap<>();
	static{
		ALL_LOOKUP.put(Long.valueOf(1), AuthorStatus.WAITING);
		ALL_LOOKUP.put(Long.valueOf(2), AuthorStatus.TO_BE_EVALUATED);
		ALL_LOOKUP.put(Long.valueOf(3), AuthorStatus.IGNORED);
		ALL_LOOKUP.put(Long.valueOf(4), AuthorStatus.FOLLOWED);

	};

	public static final String PRIMARY_KEY_FIELD = "id_author_status";  // the primary key - a convenience field



	private Long idAuthorStatus = null; // maps to the id_author_status field
	private String txtAuthorStatus = null; // maps to the txt_author_status field
	private String txtDescAuthorStatus = null; // maps to the txt_desc_author_status field

	public AuthorStatus(Long idAuthorStatus, String txtAuthorStatus, String txtDescAuthorStatus) {
		this.idAuthorStatus = idAuthorStatus;
		this.txtAuthorStatus = txtAuthorStatus;
		this.txtDescAuthorStatus = txtDescAuthorStatus;
	}
	/*
	 * Boring ol' getters and setters 
	 * 
	 * On setting any of these fields - the 'isDirty' flag will be set
	 * 
	 */

	public Long getPrimaryKey() { return(this.idAuthorStatus); }
	public Long getIdAuthorStatus() { return(this.idAuthorStatus); }
	public String getTxtAuthorStatus() { return(this.txtAuthorStatus); }
	public String getTxtDescAuthorStatus() { return(this.txtDescAuthorStatus); }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Model: 'AuthorStatus'\n")
			.append("  Field: 'idAuthorStatus:").append(this.idAuthorStatus).append("'\n")
			.append("  Field: 'txtAuthorStatus:").append(this.txtAuthorStatus).append("'\n")
			.append("  Field: 'txtDescAuthorStatus:").append(this.txtDescAuthorStatus).append("'\n")
			;
		return(stringBuilder.toString());
	}
	public JSONObject getToJSON() {
		return(toJSON());
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "table");
		jsonObject.put("name", "AuthorStatus");
		JSONObject fieldsObject = new JSONObject();

		ModelBaseHelper.addtoJSONObject(fieldsObject, "idAuthorStatus", this.getIdAuthorStatus());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "txtAuthorStatus", this.getTxtAuthorStatus());
		ModelBaseHelper.addtoJSONObject(fieldsObject, "txtDescAuthorStatus", this.getTxtDescAuthorStatus());

		jsonObject.put("fields", fieldsObject);

		return(jsonObject);
	}


	public String toJsonString() {
		return(toJSON().toString());
	}

	public String getJsonString() {
		return(toJsonString());
	}
}