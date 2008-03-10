package org.openuss.web.dav;

import org.openuss.security.User;
import org.springframework.web.context.WebApplicationContext;

/**
 * A class for encapsulating WebApplicationContext and User. For use in WebDAVResources.
 */
public class WebDAVContext {
	protected WebApplicationContext wac;
	protected User user;
	
	public WebDAVContext(WebApplicationContext wac, User user) {
		super();
		this.wac = wac;
		this.user = user;
	}

	public WebApplicationContext getWac() {
		return wac;
	}

	public void setWac(WebApplicationContext wac) {
		this.wac = wac;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
