package synapticloop.h2zero.base.form.taglib.field;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class BaseFieldTag extends BodyTagSupport {
	private static final long serialVersionUID = 9107782570688060028L;
	protected String fieldName = null;

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
