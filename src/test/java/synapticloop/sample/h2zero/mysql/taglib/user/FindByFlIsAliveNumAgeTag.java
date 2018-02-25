package synapticloop.sample.h2zero.mysql.taglib.user;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-taglib-finder.templar)

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;
import synapticloop.sample.h2zero.mysql.model.User;
import synapticloop.sample.h2zero.mysql.finder.UserFinder;
import synapticloop.h2zero.base.taglib.BaseVarTag;


@SuppressWarnings("serial")
public class FindByFlIsAliveNumAgeTag extends BaseVarTag {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;
	private static final Logger LOGGER = LoggerFactory.getLogger(FindByFlIsAliveNumAgeTag.class);

	private Boolean flIsAlive = null;
	private Integer numAge = null;

	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute(var, UserFinder.findByFlIsAliveNumAgeSilent(flIsAlive, numAge));
		return(EVAL_BODY_INCLUDE);
	}
	public void setFlIsAlive(Boolean flIsAlive) {
		this.flIsAlive = flIsAlive;
	}

	public void setNumAge(Integer numAge) {
		this.numAge = numAge;
	}

}