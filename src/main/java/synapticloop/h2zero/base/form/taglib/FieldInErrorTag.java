package synapticloop.h2zero.base.form.taglib;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;


public class FieldInErrorTag extends BaseFormTag {
	private static final long serialVersionUID = 1843311605006144801L;
	private String fieldName = null;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(formBeanName);
		if(null != formBean) {
			if(formBean.getIsFieldInError(fieldName)) {
				return(EVAL_BODY_INCLUDE);
			} else {
				return(SKIP_BODY);
			}
		} else {
			return(SKIP_BODY);
		}
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
