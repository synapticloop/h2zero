package synapticloop.h2zero.base.form.taglib.field;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.field.BaseFormField;
import synapticloop.h2zero.base.form.manager.FieldManager;


public class MaxLengthTag extends BaseFieldTag {
	private static final long serialVersionUID = -6041991437320559585L;

	public int doEndTag() throws JspException {
		BaseFormField baseField = FieldManager.getField(fieldName);
		if(null != baseField) {
			try {
				pageContext.getOut().write("" + baseField.getMaxLength());
			} catch (IOException jiioex) {
				// not much we can do
			}
		}
		return super.doEndTag();
	}
}
