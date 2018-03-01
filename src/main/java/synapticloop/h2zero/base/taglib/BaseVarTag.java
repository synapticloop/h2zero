package synapticloop.h2zero.base.taglib;

/*
 * Copyright (c) 2012-2018 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class BaseVarTag extends BodyTagSupport {
	private static final long serialVersionUID = 4908196758573941815L;

	protected String var = null;
	protected Integer offset = null;
	protected Integer limit = null;

	protected boolean removeVar = false;

	@Override
	public int doEndTag() throws JspException {
		if(removeVar) {
			pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
		}
		return(EVAL_PAGE);
	}

	public String getVar() { return var; }
	public void setVar(String var) { this.var = var; }
	public void setRemoveVar(boolean removeVar) { this.removeVar = removeVar; }
	public boolean getRemoveVar() { return removeVar; }

	public Integer getOffset() { return(this.offset); }
	public void setOffset(String offset) {
		try {
			this.offset = Integer.valueOf(offset);
		} catch(NumberFormatException ex) {
			// do nothing
		}
	}

	public Integer getLimit() { return(this.limit); }

	public void setLimit(String limit) {
		try {
			this.limit = Integer.valueOf(limit);
		} catch(NumberFormatException ex) {
			// do nothing
		}
	}

}
