package synapticloop.h2zero.base.form.taglib;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;


public class FormInErrorTag extends BaseFormTag {
	private static final long serialVersionUID = 9078451398555999176L;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(formBeanName);
		if(null != formBean && !formBean.isValid()) {
			return(EVAL_BODY_INCLUDE);
		} else {
			return(SKIP_BODY);
		}
	}
}
