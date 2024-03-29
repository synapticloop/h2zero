package synapticloop.sample.h2zero.mysql.bean;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-select-clause-bean.templar)

import java.sql.Date;
import java.sql.Timestamp;
import synapticloop.sample.h2zero.mysql.model.util.Constants;
import synapticloop.h2zero.util.XmlHelper;

/**
 * <p>This is the generated bean for the selectClause finder from</p>
 * 
 * <p>table name: <code>user</code></p>
 * 
 * <p>finder name: <code>findGroupNumAge</code></p>
 * 
 * <p>and is returned either as a single object, or a list of objects</p>
 * 
 * @author synapticloop h2zero
 * 
 * <p>@see <a href="https://github.com/synapticloop/h2zero">Synapticloop h2zero GitHub repository</a></p>
 */
public class UserFindGroupNumAgeBean {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_findGroupNumAge_BINDER;

	private Integer numCount = null; // maps to num_count
	private Integer numAge = null; // maps to num_age

	public UserFindGroupNumAgeBean(Integer numCount, Integer numAge) {
		this.numCount = numCount;
		this.numAge = numAge;
	}

	public Integer getNumCount() { return(this.numCount); }
	public void setNumCount(Integer numCount) { this.numCount = numCount; }
	public Integer getNumAge() { return(this.numAge); }
	public void setNumAge(Integer numAge) { this.numAge = numAge; }

	/**
	 * <p>Return an XML representation of the select clause bean for the call to the 
	 * <code>UserFinder.FindGroupNumAge()</code> finder as a <code>String</code>.</p>
	 * 
	 * <p>The root node is name of the select clause finder - i.e. <code>&lt;FindGroupNumAge /&gt;</code>
	 * and the child nodes the name of the fields.</p>
	 * 
	 * <p><strong>NOTE:</strong> Any field marked as secure will not be included as
	 * part of the XML document</p>
	 * 
	 * @return An XML representation of the bean as a <code>String</code>.  
	 */
	public String toXMLString() {
		return("<user>" + 
			String.format("<num_count null=\"%b\">%s</num_count>", (this.getNumCount() == null), (this.getNumCount() != null ? this.getNumCount() : "")) + 
			String.format("<num_age null=\"%b\">%s</num_age>", (this.getNumAge() == null), (this.getNumAge() != null ? this.getNumAge() : "")) + 
			"</user>");
	}

}