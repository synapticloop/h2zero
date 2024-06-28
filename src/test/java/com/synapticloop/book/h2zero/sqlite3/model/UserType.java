package com.synapticloop.book.h2zero.sqlite3.model;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                  (/java/model/java-create-model.templar)

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;
import com.synapticloop.h2zero.generator.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.book.h2zero.sqlite3.model.util.Constants;


/**
 * <p>This is the model for the <code>UserType</code> which maps to the <code>user_type</code> database table.</p>
 * 
 * <p>This model maps all of the fields from the database as defined in the
 * <code>.h2zero</code> file.  The parsed definition of the table and fields are:</p>
 * 
  * <p><strong>NOTE:</strong> This is a constant table which cannot be changed and no CRUD methods are available.</p>
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
 *       <td><code>id_user_type</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td><code>primary</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>nm_user_type</code></td>
 *       <td>varchar</td>
 *       <td>(0:32)</td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *   </tbody>
 * </table>
 * 
 * <p><strong>NOTE:</strong>This table is a <code>CONSTANT</code> table with the following values:</p>


 * <table>
 *   <thead>
 *     <tr>
 *       <th>Constant<br />accessor</th>
 *       <th>id_user_type</th> *       <th>nm_user_type</th> *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td><code>UserType.NORMAL</code></td>
 *       <td><code>1</code></td>
 *       <td><code>'normal'</code></td>
 *     <tr>
 *     <tr>
 *       <td><code>UserType.SPECIAL</code></td>
 *       <td><code>2</code></td>
 *       <td><code>'special'</code></td>
 *     <tr>
 *     <tr>
 *       <td><code>UserType.ADMIN</code></td>
 *       <td><code>3</code></td>
 *       <td><code>'admin'</code></td>
 *     <tr>
 *     <tr>
 *       <td><code>UserType.SUPER_ADMIN</code></td>
 *       <td><code>4</code></td>
 *       <td><code>'super admin'</code></td>
 *     <tr>
 *   </tbody>
 * </table>
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


	public static final String PRIMARY_KEY_FIELD = "id_user_type";  // the primary key - a convenience field



	private Long idUserType = null; // maps to the id_user_type field
	private String nmUserType = null; // maps to the nm_user_type field

	/**
	 * <p>Create a new <code>UserType</code> object with all fields
	 * some of which can be null.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object.</p>
	 * 
	 * <p>Creating a new UserType:</p>
	 * 
	 * <pre>new UserType(
	 *     Long idUserType,  // id_user_type 
	 *     String nmUserType // nm_user_type 
	 * );</pre>
	 * 
	 */
	public UserType(Long idUserType, String nmUserType) {
		this.idUserType = idUserType;
		this.nmUserType = nmUserType;
	}


	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Boring ol' getters and setters 
	 * 
	 * Getters will update the hit count upon access.
	 * 
	 * Setters, if the passed in parameter's value differs will set the
	 * 'isDirty' flag
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * <p>Convenience method for returning the primary key field (which is the id_user_type field).</p>
	 * 
	 * <p><strong>NOTE:</strong> This will update the hit count.</p>
	 * 
	 * @return The value of the primary key
	 */
	public Long getPrimaryKey() {return(this.idUserType); }


	/**
	 * <p>Return the value of the id_user_type, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user_type which may NOT be null.
	 */
	public Long getIdUserType() {return(this.idUserType); }

	/**
	 * <p>Return the value of the nm_user_type, updating the hit count for this field.</p>
	 * 
	 * @return the value of the nm_user_type which may NOT be null.
	 */
	public String getNmUserType() {return(this.nmUserType); }

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