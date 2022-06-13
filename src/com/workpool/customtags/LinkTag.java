package com.workpool.customtags;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class LinkTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private String url;
	private String link_name;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}


	public String getUrl() {
		return url;
	}

	public String getName() {
		return link_name;
	}

	public int doStartTag() throws JspException {
		//InnerTag innerTag = (InnerTag) findAncestorWithClass(this, InnerTag.class);

		try {
			
			//System.out.println("inside Outer  tag --URL " + url);
			//System.out.println("inside Outer  tag --URL " + link_name);

		} catch (Exception ioe) {
			ioe.printStackTrace();
			throw new JspException("IOException while writing data to page" + ioe.getMessage());
		}
		return EVAL_BODY_INCLUDE;

	}

}
