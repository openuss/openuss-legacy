package org.openuss.docmanagement.webdav;

import java.io.IOException;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavService {
	private final Logger logger = Logger.getLogger(DavService.class);
	
	private final DavResourceConfiguration configuration;
	private Session session;
	private DavResourceFactory resourceFactory;
	
	/**
	 * Constructor
	 * @param configuration
	 */
	public DavService(DavResourceConfiguration configuration) {
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
		// check parameters
		if ((context == null) || (locator == null)) {
			throw new IOException();
		}
		
		if (locator.isRootLocation()) {
			// TODO abonnierte enrollments anfordern und Startsicht ausgeben
		} else {
			// TODO ressource ausgeben
		}
	}
	
	/**
	 * @param context
	 * @param locator
	 */
	public void addMember(InputContext context, DavResourceLocator locator) throws IOException {
		// check parameters
		if ((context == null) || (locator == null)) {
			throw new IOException();
		}
		
		// TODO
	}
	
	public MultiStatus getProperties(Document requestDocument, DavResourceLocator locator, int depth) throws DavException {
		// TODO korrekt implementieren
		DavResource resource = getResourceFactory().createResource(getSession(), locator);
		if (!resource.exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		MultiStatus multistatus = new MultiStatus();

		// TODO entscheiden, ob es in der Verantwortung der DavResource liegt ...
		MultiStatusResponse response = new MultiStatusResponse(locator.getHref(resource.isCollection()), null);
		response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_DISPLAYNAME, resource.getDisplayName());
//		response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_CREATIONDATE, resource.getCreationDate());
		if (resource.isCollection()) {
			QName collectionName = DocumentHelper.createQName(DavConstants.XML_COLLECTION, new Namespace("D", "DAV:"));
			Element collectionElement = DocumentHelper.createElement(collectionName);
			response.addProperty(HttpStatus.SC_OK, null, DavConstants.PROPERTY_RESOURCETYPE, collectionElement);
		} else {
			response.addProperty(HttpStatus.SC_OK, null, DavConstants.PROPERTY_RESOURCETYPE, "");
		}
		multistatus.addResponse(response);
		
		return multistatus;
	}
	
	// TODO setProperties
	
	public void createCollection(DavResourceLocator locator) throws IOException {
		// TODO
		logger.debug("Collection should be created: " + locator.getResourcePath());
		
		// HACK
		throw new IOException("Creation of collection not allowed.");
	}
	
	/**
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public void copyResource(DavResourceLocator source, DavResourceLocator target) throws IOException {
		// TODO
		// HACK
		throw new IOException("Copy not implemented.");
	}
	
	/**
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public void moveResource(DavResourceLocator source, DavResourceLocator target) throws IOException {
		// TODO
		// HACK
		throw new IOException("Copy not implemented.");
	}
	
	/**
	 * @param locator
	 * @throws IOException
	 */
	public void deleteResource(DavResourceLocator locator) throws IOException {
		// TODO
		// HACK
		throw new IOException("Copy not implemented.");
	}
	
	/**
	 * @return
	 */
	public DavResourceFactory getResourceFactory() {
		if (resourceFactory == null) {
			resourceFactory = new DavResourceFactory();
		}
		return resourceFactory;
	}
}
