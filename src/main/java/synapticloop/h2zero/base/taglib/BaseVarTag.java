package synapticloop.h2zero.base.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class BaseVarTag extends BodyTagSupport {
	private static final long serialVersionUID = 4908196758573941815L;

	protected String var = null;
	protected boolean removeVar = false;

	@Override
	public int doEndTag() throws JspException {
		if(removeVar) {
			pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
		}
		return(EVAL_PAGE);
	}

	public String getVar() { return var; }
	public void setVar(String var) { this.var = var; }
	public void setRemoveVar(boolean removeVar) { this.removeVar = removeVar; }
	public boolean getRemoveVar() { return removeVar; }
}
