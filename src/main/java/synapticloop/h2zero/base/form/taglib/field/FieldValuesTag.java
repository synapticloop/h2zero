package synapticloop.h2zero.base.form.taglib.field;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;
import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.manager.FieldManager;


public class FieldValuesTag extends BaseFieldTag {
	private static final long serialVersionUID = 4441426180776716725L;
	private String formBeanName = null;
	private String fieldName = null;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(getFormBeanName());
		if(null != formBean) {
			// we are in luck, we have a form bean with actual values
			BaseFormField baseField = formBean.getField(fieldName);
			pageContext.setAttribute("maxLength", baseField.getMaxLength());
			pageContext.setAttribute("value", baseField.getValue());
		} else {
			BaseFormField baseField = FieldManager.getField(fieldName);
			if(null != baseField) {
				pageContext.setAttribute("maxLength", baseField.getMaxLength());
				pageContext.setAttribute("value", "");
			}
		}
		return(EVAL_BODY_INCLUDE);
	}

	public int doEndTag() throws JspException {
		return(EVAL_PAGE);
	}

	public void setFormBeanName(String formBeanName) {
		this.formBeanName = formBeanName;
	}

	public String getFormBeanName() {
		return formBeanName;
	}
}
