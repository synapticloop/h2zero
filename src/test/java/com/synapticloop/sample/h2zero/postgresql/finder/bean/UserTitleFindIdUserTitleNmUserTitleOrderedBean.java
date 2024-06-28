package com.synapticloop.sample.h2zero.postgresql.finder.bean;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//            (/java/finder/java-create-select-clause-bean.templar)

import com.synapticloop.sample.h2zero.postgresql.model.util.Constants;
import com.synapticloop.h2zero.generator.util.XmlHelper;

/**
 * <p>This is the generated bean for the selectClause finder from</p>
 * 
 * <p>table name: <code>user_title</code></p>
 * 
 * <p>finder name: <code>findIdUserTitleNmUserTitleOrdered</code></p>
 * 
 * <p>and is returned either as a single object, or a list of objects</p>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserTitleFindIdUserTitleNmUserTitleOrderedBean {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_TITLE_findIdUserTitleNmUserTitleOrdered_BINDER;

	private Long idUserTitle = null; // maps to id_user_title
	private String nmUserTitle = null; // maps to nm_user_title

	public UserTitleFindIdUserTitleNmUserTitleOrderedBean(Long idUserTitle, String nmUserTitle) {
		this.idUserTitle = idUserTitle;
		this.nmUserTitle = nmUserTitle;
	}

	public Long getIdUserTitle() { return(this.idUserTitle); }
	public void setIdUserTitle(Long idUserTitle) { this.idUserTitle = idUserTitle; }
	public String getNmUserTitle() { return(this.nmUserTitle); }
	public void setNmUserTitle(String nmUserTitle) { this.nmUserTitle = nmUserTitle; }

	/**
	 * <p>Return an XML representation of the select clause bean for the call to the 
	 * <code>UserTitleFinder.FindIdUserTitleNmUserTitleOrdered()</code> finder as a <code>String</code>.</p>
	 * 
	 * <p>The root node is name of the select clause finder - i.e. <code>&lt;FindIdUserTitleNmUserTitleOrdered /&gt;</code>
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the bean as a <code>String</code>.  
	 */
	public String toXMLString() {
		return("<user_title>" + 
			String.format("<id_user_title null=\"%b\">%s</id_user_title>", (this.getIdUserTitle() == null), (this.getIdUserTitle() != null ? this.getIdUserTitle() : "")) + 
			String.format("<nm_user_title null=\"%b\">%s</nm_user_title>", (this.getNmUserTitle() == null), (this.getNmUserTitle() != null ? XmlHelper.escapeXml(this.getNmUserTitle()) : "")) + 
			"</user_title>");
	}

}