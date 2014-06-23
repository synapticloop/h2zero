package synapticloop.h2zero.base.form.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import synapticloop.h2zero.base.form.FormBean;
import synapticloop.h2zero.base.form.factory.FormBeanFactory;


public class FormTag extends BaseFormTag {
	private static final long serialVersionUID = 8474505246181602346L;
	private String formName = null;
	private String respondTo = null;
	private String successPage = null;

	public int doEndTag() throws JspException {
		HttpServletRequest httpServletRequest =(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse httpServletResponse =(HttpServletResponse)pageContext.getResponse();

		if(httpServletRequest.getParameter(respondTo) != null) {
			FormBean formBean = FormBeanFactory.getFormBeanInstance(formName);
			formBean.process(httpServletRequest);
			if(formBean.isValid()) {
				try {
					httpServletResponse.sendRedirect(successPage);
				} catch (IOException jiioex) {
				}
				return(SKIP_PAGE);
			} else {
				pageContext.setAttribute(formBeanName, formBean);
			}
		}
		return(EVAL_PAGE);
	}

	public void setRespondTo(String respondTo) {
		this.respondTo = respondTo;
	}

	public String getRespondTo() {
		return respondTo;
	}

	public void setSuccessPage(String successPage) {
		this.successPage = successPage;
	}

	public String getSuccessPage() {
		return successPage;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormName() {
		return formName;
	}
}
