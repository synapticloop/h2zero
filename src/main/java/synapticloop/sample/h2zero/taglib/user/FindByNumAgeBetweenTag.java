package synapticloop.sample.h2zero.taglib.user;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//           (java-create-taglib-finder.templar)

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

@SuppressWarnings("serial")
public class FindByNumAgeBetweenTag extends BodyTagSupport {
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.USER_binder;

	private static final Logger LOGGER = Logger.getLogger(FindByNumAgeBetweenTag.class);

	private String var = null;
	private boolean removeVar = false;

	private Integer numAgeMin = null;
	private Integer numAgeMax = null;

	public int doStartTag() throws JspException {
		pageContext.setAttribute(var, UserFinder.findByNumAgeBetweenSilent(numAgeMin, numAgeMax));
		return(EVAL_BODY_INCLUDE);
	}

	public int doEndTag() throws JspException {
		if(removeVar) {
			pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
		}
		return(EVAL_PAGE);
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setRemoveVar(boolean removeVar) {
		this.removeVar = removeVar;
	}

	public boolean getRemoveVar() {
		return removeVar;
	}

	public void setNumAgeMin(Integer numAgeMin) {
		this.numAgeMin = numAgeMin;
	}

	public void setNumAgeMax(Integer numAgeMax) {
		this.numAgeMax = numAgeMax;
	}

}