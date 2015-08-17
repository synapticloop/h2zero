package synapticloop.sample.h2zero.taglib.user;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-taglib-question.templar)

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import synapticloop.sample.h2zero.model.util.Constants;
import synapticloop.sample.h2zero.model.User;
import synapticloop.sample.h2zero.finder.UserFinder;
import synapticloop.h2zero.base.taglib.BaseVarTag;


import synapticloop.sample.h2zero.question.UserQuestion;

@SuppressWarnings("serial")
public class DoWeHaveMoreThanTwentyUsersTag extends BaseVarTag {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;
	private static final Logger LOGGER = Logger.getLogger(DoWeHaveMoreThanTwentyUsersTag.class);


	public int doStartTag() throws JspException {
		pageContext.setAttribute(var, UserQuestion.doWeHaveMoreThanTwentyUsersSilent());
		return(EVAL_BODY_INCLUDE);
	}
}