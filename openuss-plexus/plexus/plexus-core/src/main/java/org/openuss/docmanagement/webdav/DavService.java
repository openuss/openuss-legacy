package org.openuss.docmanagement.webdav;

import java.io.IOException;

import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.DavSession;
import org.apache.jackrabbit.webdav.WebdavRequest;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.ResourceConfiguration;
import org.openuss.docmanagement.ResourceDao;
import org.openuss.docmanagement.ResourceFactory;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavService {
	private final Logger logger = Logger.getLogger(DavService.class);
	
	private final ResourceConfiguration configuration;
	private Session session;
	private ResourceFactory resourceFactory;
	
	/**
	 * Constructor
	 * @param configuration
	 */
	public DavService(ResourceConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @param context
	 * @param locator
	 * @param withContent
	 * @throws IOException
	 */
	public void spoolResource(OutputContext context, DavResourceLocator locator, boolean withContent) throws IOException {
		// TODO
	}
	
	/**
	 * @param context
	 * @param locator
	 */
	public void addMember(InputContext context, DavResourceLocator locator) throws IOException {
		// TODO importContext anlegen
	}
	
	/**
	 * @return
	 */
	public ResourceFactory getResourceFactory() {
		if (resourceFactory == null) {
			resourceFactory = new ResourceFactory();
		}
		return resourceFactory;
	}
}
