package synapticloop.sample.h2zero.mysql.taglib.user;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-taglib-question.templar)

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

<<<<<<< HEAD:src/test/java/synapticloop/sample/h2zero/mysql/taglib/user/DoWeHaveMoreThanTwentyUsersTag.java
=======

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

>>>>>>> master:src/test/java/synapticloop/sample/h2zero/taglib/user/DoWeHaveMoreThanTwentyUsersTag.java

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import synapticloop.sample.h2zero.mysql.model.util.Constants;
import synapticloop.sample.h2zero.mysql.model.User;
import synapticloop.sample.h2zero.mysql.finder.UserFinder;
import synapticloop.h2zero.base.taglib.BaseVarTag;


import synapticloop.sample.h2zero.mysql.question.UserQuestion;

@SuppressWarnings("serial")
public class DoWeHaveMoreThanTwentyUsersTag extends BaseVarTag {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_BINDER;
	private static final Logger LOGGER = LoggerFactory.getLogger(DoWeHaveMoreThanTwentyUsersTag.class);


	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute(var, UserQuestion.doWeHaveMoreThanTwentyUsersSilent());
		return(EVAL_BODY_INCLUDE);
	}
}