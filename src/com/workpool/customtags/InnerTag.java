package com.workpool.customtags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class InnerTag extends SimpleTagSupport {

	private String url;
	private int id;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void doTag() throws JspException {
		LinkTag outerTag = (LinkTag) getParent();

		if (outerTag == null) {// IF THERE'S NO OUTER TAG
			throw new JspTagException("No Outer Tag");
		} else {
			try {

				url = " <a href='" + outerTag.getUrl() + "id=" + id + "' style=\"color: blue\" >" + outerTag.getName() + "</a>";

				JspWriter out = getJspContext().getOut();
				out.print(url);
			} catch (Exception ioe) {
				ioe.printStackTrace();
				throw new JspException("IOException while writing data to page" + ioe.getMessage());
			}
			// continue with jsp page

		}

	}

}
