package synapticloop.sample.h2zero.cockroach.bean;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-select-clause-bean.templar)

import java.sql.Date;
import java.sql.Timestamp;
import synapticloop.sample.h2zero.cockroach.model.util.Constants;
import synapticloop.h2zero.util.XmlHelper;

/**
 * This is the generated bean for the selectClause finder from
 * <p>
 * table name: user_user
 * <p>
 * finder name: findNmUserDtmSignup
 * <p>
 * and is returned either as a single object, or a list of objects
 */
public class UserUserFindNmUserDtmSignupBean {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_USER_findNmUserDtmSignup_BINDER;

	private String nmUser = null; // maps to nm_user
	private Timestamp dtmSignup = null; // maps to dtm_signup

	public UserUserFindNmUserDtmSignupBean(String nmUser, Timestamp dtmSignup) {
		this.nmUser = nmUser;
		this.dtmSignup = dtmSignup;
	}

	public String getNmUser() { return(this.nmUser); }
	public void setNmUser(String nmUser) { this.nmUser = nmUser; }
	public Timestamp getDtmSignup() { return(this.dtmSignup); }
	public void setDtmSignup(Timestamp dtmSignup) { this.dtmSignup = dtmSignup; }

	/**
	 * Return an XML representation of the select clause bean for the FindNmUserDtmSignup
	 * finder.  The root node is name of the select clause finder - i.e. <FindNmUserDtmSignup>
	 * and the child nodes the name of the fields.
	 * 
	 * @return An XML representation of the model.  
	 */
	public String toXMLString() {
		return("<user_user>" + 
			String.format("<nm_user null=\"%b\">%s</nm_user>", (this.getNmUser() == null), (this.getNmUser() != null ? XmlHelper.escapeXml(this.getNmUser()) : "")) + 
			String.format("<dtm_signup null=\"%b\">%s</dtm_signup>", (this.getDtmSignup() == null), (this.getDtmSignup() != null ? this.getDtmSignup() : "")) + 
			"</user_user>");
	}

}