package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.jackrabbit.util.Text;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavService {
//	private final Logger logger = Logger.getLogger(DavService.class);
	
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
	 * @throws IOException
	 */
	public void spoolResource(OutputContext context, DavResourceLocator locator) throws DavException {
		// check parameters
		if ((context == null) || (locator == null)) {
			throw new DavException(HttpStatus.SC_BAD_REQUEST);
		}

		// create resource and export content
		DavResource resource = getResourceFactory().createResource(session, locator, false);
		resource.exportContent(new ExportContext(context));
	}
	
	/**
	 * @param context
	 * @param locator
	 */
	public void addMember(InputContext context, DavResourceLocator locator) throws DavException {
		// check parameters
		if ((context == null) || (locator == null)) {
			throw new DavException(HttpStatus.SC_BAD_REQUEST);
		}
		
		// create resource and import content
		DavResource resource = getResourceFactory().createResource(session, locator, false);
		resource.getCollection().addMember(resource, context);
	}
	
	public MultiStatus getProperties(Document requestDocument, DavResourceLocator locator, int depth) throws DavException {
		// TODO kommentieren
		DavResource resource = getResourceFactory().createResource(getSession(), locator, false);
		if (!resource.exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		MultiStatus multistatus = new MultiStatus();
		
		addResponse(multistatus, resource, getRequestedProperties(requestDocument), isNamesOnlyRequest(requestDocument), depth);
		
		return multistatus;
	}
	
	private List<String> getRequestedProperties(Document requestDocument) throws DavException {
		// TODO kommentieren, Fehlerprüfungen
		List<String> propertyNames = new ArrayList<String>();
		
		if (requestDocument != null) {
			Element propfindElement = requestDocument.getRootElement();
			if (!propfindElement.getName().equals(DavConstants.XML_PROPFIND)) {
				throw new DavException(HttpStatus.SC_BAD_REQUEST);
			}

			// TODO kann ein Document kein root-Element haben?
			Element propElement = propfindElement.element(DavConstants.XML_PROP);
			
			if ((propElement != null) && (propElement.hasContent())) {
				Iterator elementIterator = propElement.elementIterator();
				Element element;
				while (elementIterator.hasNext()) {
					element = (Element)elementIterator.next();
					propertyNames.add(element.getName());
				}
			}
		}
		return propertyNames;
	}
	
	private boolean isNamesOnlyRequest(Document requestDocument) throws DavException {
		// TODO kommentieren, Fehlerprüfungen 
		if (requestDocument != null) {
			Element propfindElement = requestDocument.getRootElement();
			if (!propfindElement.getName().equals(DavConstants.XML_PROPFIND)) {
				throw new DavException(HttpStatus.SC_BAD_REQUEST);
			}
			
			Element propnameElement = propfindElement.element(DavConstants.XML_PROPNAME);
			return (propnameElement != null);
		}
		return false;
	}
	
	private void addResponse(MultiStatus multistatus, DavResource resource, List<String> properties, boolean namesOnly, int depth) throws DavException {
		multistatus.addResponse(resource.getProperties(properties, namesOnly));
		
		if (depth > 0) {
			DavResource[] members = resource.getMembers();
			for (int i = 0, j = members.length; i < j; i++) {
				if (depth == DavConstants.DEPTH_INFINITY) {
					addResponse(multistatus, members[i], properties, namesOnly, depth);
				} else {
					addResponse(multistatus, members[i], properties, namesOnly, 0);
				}
			}
		}
	}
	
	// TODO setProperties
	
	public void createCollection(DavResourceLocator locator) throws DavException {
		// check parameters
		if (locator == null) {
			throw new DavException(HttpStatus.SC_BAD_REQUEST);
		}
		
		// create resource and import content
		DavResource resource = getResourceFactory().createResource(session, locator, true);
		resource.getCollection().addMember(resource, null);
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
			resourceFactory = new DavResourceFactory(configuration);
		}
		return resourceFactory;
	}
}
