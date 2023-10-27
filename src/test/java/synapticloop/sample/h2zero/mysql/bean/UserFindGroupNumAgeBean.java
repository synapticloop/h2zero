package synapticloop.sample.h2zero.mysql.bean;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-select-clause-bean.templar)

import java.sql.Date;
import java.sql.Timestamp;
import synapticloop.sample.h2zero.mysql.model.util.Constants;

/**
 * This is the generated bean for the selectClause finder from
 * <p>
 * table name: user
 * <p>
 * finder name: findGroupNumAge
 * <p>
 * and is returned either as a single object, or a list of objects
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
}