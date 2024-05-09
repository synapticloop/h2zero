package com.synapticloop.sample.h2zero.mariadb.model;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//                  (/java/model/java-create-model.templar)

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;
import com.synapticloop.h2zero.util.XmlHelper;

import com.synapticloop.h2zero.base.model.ModelBaseHelper;
import com.synapticloop.sample.h2zero.mariadb.model.util.Constants;


/**
 * <p>This is the model for the <code>UserTitle</code> which maps to the <code>user_title</code> database table.</p>
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
 *       <td><code>id_user_title</code></td>
 *       <td>bigint</td>
 *       <td> -- </td>
 *       <td>false</td>
 *       <td><code>primary</code></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>nm_user_title</code></td>
 *       <td>varchar</td>
 *       <td>(0:32)</td>
 *       <td>false</td>
 *       <td></td>
 *       <td> -- </td>
 *     </tr>
 *     <tr>
 *       <td><code>num_order_by</code></td>
 *       <td>int</td>
 *       <td> -- </td>
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
 *       <th>id_user_title</th> *       <th>nm_user_title</th> *       <th>num_order_by</th> *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td><code>UserTitle.MR</code></td>
 *       <td><code>1</code></td>
 *       <td><code>'Mr.'</code></td>
 *       <td><code>1</code></td>
 *     <tr>
 *     <tr>
 *       <td><code>UserTitle.MRS</code></td>
 *       <td><code>2</code></td>
 *       <td><code>'Mrs.'</code></td>
 *       <td><code>2</code></td>
 *     <tr>
 *     <tr>
 *       <td><code>UserTitle.MISS</code></td>
 *       <td><code>3</code></td>
 *       <td><code>'Miss'</code></td>
 *       <td><code>3</code></td>
 *     <tr>
 *     <tr>
 *       <td><code>UserTitle.DR</code></td>
 *       <td><code>4</code></td>
 *       <td><code>'Dr.'</code></td>
 *       <td><code>4</code></td>
 *     <tr>
 *   </tbody>
 * </table>
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

	/**
	 * <p>Create a new <code>UserTitle</code> object with all fields
	 * some of which can be null.</p>
	 * 
	 * <p><strong>NOTE:</strong> this does not insert the object into the database
	 * the <code>.insert()</code> method must be called to insert this object</p>
	 * 
	 * <p>Creating a new UserTitle:</p>
	 * 
	 * 
	 * <pre>new UserTitle(
	 *     Long idUserTitle,  // id_user_title 
	 *     String nmUserTitle,  // nm_user_title 
	 *     Integer numOrderBy // num_order_by 
	 * );</pre>
	 * 
	 */
	public UserTitle(Long idUserTitle, String nmUserTitle, Integer numOrderBy) {
		this.idUserTitle = idUserTitle;
		this.nmUserTitle = nmUserTitle;
		this.numOrderBy = numOrderBy;
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
	 * <p>Convenience method for returning the primary key field (which is the id_user_title field).</p>
	 * 
	 * <p><strong>NOTE:</strong> This will update the hit count.</p>
	 * 
	 * @return The value of the primary key
	 */
	public Long getPrimaryKey() {return(this.idUserTitle); }


	/**
	 * <p>Return the value of the id_user_title, updating the hit count for this field.</p>
	 * 
	 * @return the value of the id_user_title which may be null.
	 */
	public Long getIdUserTitle() {return(this.idUserTitle); }

	/**
	 * <p>Return the value of the nm_user_title, updating the hit count for this field.</p>
	 * 
	 * @return the value of the nm_user_title which may be null.
	 */
	public String getNmUserTitle() {return(this.nmUserTitle); }

	/**
	 * <p>Return the value of the num_order_by, updating the hit count for this field.</p>
	 * 
	 * @return the value of the num_order_by which may be null.
	 */
	public Integer getNumOrderBy() {return(this.numOrderBy); }

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