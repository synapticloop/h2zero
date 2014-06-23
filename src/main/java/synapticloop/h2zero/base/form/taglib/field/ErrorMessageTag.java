package synapticloop.h2zero.base.form.taglib.field;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.manager.FieldManager;


public class ErrorMessageTag extends BaseFieldTag {
	private static final long serialVersionUID = -435625896081870986L;

	public int doEndTag() throws JspException {
		BaseFormField baseField = FieldManager.getField(fieldName);
		if(null != baseField) {
			try {
				pageContext.getOut().write(baseField.getErrorMessage());
			} catch (IOException jiioex) {
				// not much we can do
			}
		}
		return super.doEndTag();
	}
}
