package org.openuss.web.dav;

import org.springframework.web.context.WebApplicationContext;

/**
 * A class for encapsulating an user object. For use in WebDAVResources.
 */
public class WebDAVContext {
	protected WebApplicationContext wac;
	
	public WebDAVContext(WebApplicationContext wac) {
		super();
		this.wac = wac;
	}

	public WebApplicationContext getWac() {
		return wac;
	}

	protected void setWac(WebApplicationContext wac) {
		this.wac = wac;
	}
}
