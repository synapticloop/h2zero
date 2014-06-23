package synapticloop.h2zero.base.form.taglib;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;


public class FieldsInErrorTag extends BaseFormTag {
	private static final long serialVersionUID = 93527808358487692L;
	private String var = null;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(getFormBeanName());
		if(null != formBean) {
			pageContext.setAttribute(var, formBean.getFieldsInError());
			return(EVAL_BODY_INCLUDE);
		} else {
			return(SKIP_BODY);
		}
	}

	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		return super.doEndTag();
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVar() {
		return var;
	}

}
