package synapticloop.h2zero.base.form.taglib;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;
import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.manager.FieldManager;


public class FieldIsSelectedTag extends BaseFormTag {
	private static final long serialVersionUID = 5091145196058321341L;
	private String fieldName = null;
	private String fieldValue = null;

	public int doStartTag() throws JspException {
		FormBean formBean = (FormBean)pageContext.getAttribute(getFormBeanName());
		if(null != formBean) {
			BaseFormField baseField = formBean.getField(fieldName);
			if(null != baseField) {
				String value = baseField.getValue();
				if(null != value && value.equals(fieldValue)) {
					return(EVAL_BODY_INCLUDE);
				}
			}
		} else {
			// we haven't submitted the form, so use the default if available
			BaseFormField defaultValueField = FieldManager.getField(fieldName);
			if(null != defaultValueField && 
					null != defaultValueField.getDefaultValue() && 
					defaultValueField.getDefaultValue().equals(fieldValue)) {
				return(EVAL_BODY_INCLUDE);
			}
		}
		return(SKIP_BODY);
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldValue() {
		return fieldValue;
	}

}
