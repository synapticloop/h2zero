package synapticloop.h2zero.base.form.taglib;

import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class BaseFormTag extends BodyTagSupport {
	private static final long serialVersionUID = -476580790716009354L;
	private static final String DEFAULT_FORM_BEAN_NAME = "formBean";
	protected String formBeanName = DEFAULT_FORM_BEAN_NAME;

	public void setFormBeanName(String formBeanName) {
		this.formBeanName = formBeanName;
	}

	public String getFormBeanName() {
		return formBeanName;
	}

	public void release() {
		super.release();
		this.formBeanName = DEFAULT_FORM_BEAN_NAME;
	}

}
