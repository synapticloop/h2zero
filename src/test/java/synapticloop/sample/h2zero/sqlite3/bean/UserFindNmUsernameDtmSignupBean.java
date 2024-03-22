package synapticloop.sample.h2zero.sqlite3.bean;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-select-clause-bean.templar)

import java.sql.Date;
import java.sql.Timestamp;
import synapticloop.sample.h2zero.sqlite3.model.util.Constants;
import synapticloop.h2zero.util.XmlHelper;

/**
 * <p>This is the generated bean for the selectClause finder from</p>
 * 
 * <p>table name: <code>user</code></p>
 * 
 * <p>finder name: <code>findNmUsernameDtmSignup</code></p>
 * 
 * <p>and is returned either as a single object, or a list of objects</p>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserFindNmUsernameDtmSignupBean {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_findNmUsernameDtmSignup_BINDER;

	private String nmUsername = null; // maps to nm_username
	private Timestamp dtmSignup = null; // maps to dtm_signup

	public UserFindNmUsernameDtmSignupBean(String nmUsername, Timestamp dtmSignup) {
		this.nmUsername = nmUsername;
		this.dtmSignup = dtmSignup;
	}

	public String getNmUsername() { return(this.nmUsername); }
	public void setNmUsername(String nmUsername) { this.nmUsername = nmUsername; }
	public Timestamp getDtmSignup() { return(this.dtmSignup); }
	public void setDtmSignup(Timestamp dtmSignup) { this.dtmSignup = dtmSignup; }

	/**
	 * <p>Return an XML representation of the select clause bean for the call to the 
	 * <code>UserFinder.FindNmUsernameDtmSignup()</code> finder as a <code>String</code>.</p>
	 * 
	 * <p>The root node is name of the select clause finder - i.e. <code>&lt;FindNmUsernameDtmSignup /&gt;</code>
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the bean as a <code>String</code>.  
	 */
	public String toXMLString() {
		return("<user>" + 
			String.format("<nm_username null=\"%b\">%s</nm_username>", (this.getNmUsername() == null), (this.getNmUsername() != null ? XmlHelper.escapeXml(this.getNmUsername()) : "")) + 
			String.format("<dtm_signup null=\"%b\">%s</dtm_signup>", (this.getDtmSignup() == null), (this.getDtmSignup() != null ? this.getDtmSignup() : "")) + 
			"</user>");
	}

}