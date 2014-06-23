package synapticloop.h2zero.base.form.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;
import synapticloop.h2zero.base.form.field.BaseFormField;


public class FieldValueTag extends BaseFormTag {
	private static final String DOT_CONFIRM = ".confirm";
	private static final long serialVersionUID = -6835190466287959934L;
	private String fieldName = null;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(formBeanName);
		if(null != formBean) {
			// now get the fields
			String fieldValue = null;
			if(fieldName.endsWith(DOT_CONFIRM)) {
				BaseFormField baseField = formBean.getField(fieldName.substring(0, fieldName.length() - DOT_CONFIRM.length()));
				if(null != baseField) {
					fieldValue = baseField.getConfirmValue();
				}
			} else {
				BaseFormField baseField = formBean.getField(fieldName);
				if(null != baseField) {
					fieldValue = baseField.getValue();
				}
			}

			if(null != fieldValue) {
				try {
					pageContext.getOut().write(fieldValue);
				} catch (IOException jiioex) {
					// do nothing
				}
			}
		}
		return(EVAL_PAGE);
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
