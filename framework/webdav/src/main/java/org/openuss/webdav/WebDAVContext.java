package org.openuss.webdav;

import org.openuss.documents.DocumentService;
import org.springframework.web.context.WebApplicationContext;

/**
 * A class for encapsulating WebDAV context information.
 * For use in WebDAVResources.
 */
public class WebDAVContext {
	protected WebApplicationContext wac;
	
	protected DocumentService documentService = null;
	
	public WebDAVContext(WebApplicationContext wac) {
		super();
		this.wac = wac;
	}

	public WebApplicationContext getWAC() {
		return wac;
	}

	protected void setWAC(WebApplicationContext wac) {
		this.wac = wac;
	}
}
