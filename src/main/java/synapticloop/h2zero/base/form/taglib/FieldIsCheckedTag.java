package synapticloop.h2zero.base.form.taglib;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;


public class FieldIsCheckedTag extends BaseFormTag {
	private static final long serialVersionUID = 250184792149159334L;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(formBeanName);
		if(null != formBean) {
			return(EVAL_BODY_INCLUDE);
		}
		return(EVAL_PAGE);
	}

}
