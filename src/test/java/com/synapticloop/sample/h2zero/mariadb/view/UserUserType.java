package com.synapticloop.sample.h2zero.mariadb.view;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//              (/java/model/java-create-view-model.templar)

import com.synapticloop.h2zero.base.view.ViewBase;
import com.synapticloop.h2zero.generator.util.XmlHelper;

import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;
import org.json.JSONObject;
import com.synapticloop.h2zero.base.model.ModelBaseHelper;


import com.synapticloop.sample.h2zero.mariadb.finder.UserFinder;
import com.synapticloop.sample.h2zero.mariadb.model.User;
/**
 * <p>This is the model for the <code>UserUserType</code> which maps to the <code>user_user_type</code> database view.</p>
 * 
 * <p>This model maps all of the fields from the database as defined in the
 * <code>.h2zero</code> file.  The parsed definition of the table and fields are:</p>
 * 
 * <p><strong>NOTE:</strong> This is a view which cannot be changed and no CRUD methods are available.</p>
 * 
 * <p>This view was created with the following SQL select statement:</p>
 * <pre>
 * select 
 *   u.nm_username, 
 *   u.id_user, 
 *   ut.nm_user_type
 * from 
 *   user u, 
 *   user_type ut
 * where 
 *   u.id_user_type = ut.id_user_type
</pre>
 * 
 * With the following fields defined
 * 
 * 
 * <table>
 *   <thead>
 *     <tr>
 *       <th>Field name</th>
 *       <th>SQL type</th>
 *       <th>Field length<br />(min:max)</th>
 *       <th>Nullable?</th>
 *       <th>Keys</th>
 *       <th>Comments</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td><code>id_user</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td> <code>foreign -> user.id_user</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>nm_username</code></td>
 *       <td>varchar</td>
 *       <td>(0:64)</td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>nm_user_type</code></td>
 *       <td>varchar</td>
 *       <td>(0:64)</td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *   </tbody>
 * </table>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserUserType extends ViewBase {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_TYPE_BINDER;

	private User User = null;

	private Long idUser = null;
	private String nmUsername = null;
	private String nmUserType = null;

	public UserUserType(Long idUser, String nmUsername, String nmUserType) {
		this.idUser = idUser;
		this.nmUsername = nmUsername;
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

	/**
	 * <p>Return the value of the id_user, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user which may NOT be null.
	 */
	public Long getIdUser() { return(this.idUser); }

	/**
	 * <p>Return the value of the nm_username, updating the hit count for this field.</p>
	 * 
	 * @return the value of the nm_username which may NOT be null.
	 */
	public String getNmUsername() { return(this.nmUsername); }

	/**
	 * <p>Return the value of the nm_user_type, updating the hit count for this field.</p>
	 * 
	 * @return the value of the nm_user_type which may NOT be null.
	 */
	public String getNmUserType() { return(this.nmUserType); }


	@Override
	public String toString() {
		return(new StringBuilder()
			.append("Model: 'UserUserType'\n")
			.append("  Field: 'idUser:").append(this.idUser).append("'\n")
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

		ModelBaseHelper.addToJSONObject(fieldsObject, "idUser", this.getIdUser());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmUsername", this.getNmUsername());
		ModelBaseHelper.addToJSONObject(fieldsObject, "nmUserType", this.getNmUserType());

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
			String.format("<nm_username null=\"%b\">%s</nm_username>", (this.getNmUsername() == null), (this.getNmUsername() != null ? XmlHelper.escapeXml(this.getNmUsername()) : "")) + 
			String.format("<nm_user_type null=\"%b\">%s</nm_user_type>", (this.getNmUserType() == null), (this.getNmUserType() != null ? XmlHelper.escapeXml(this.getNmUserType()) : "")) + 
			"</user_user_type>");
	}

}