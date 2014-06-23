package synapticloop.h2zero.base.form.taglib.field;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.manager.FieldManager;



public class AllowableValuesTag extends BaseFieldTag {
	private static final long serialVersionUID = 1L;
	private String var = null;

	public int doStartTag() throws JspException {
		BaseFormField baseField = FieldManager.getField(fieldName);
		if(null != baseField) {
			pageContext.setAttribute(var, baseField.getAllowableValues().toArray());
		}
		return(EVAL_BODY_INCLUDE);
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
