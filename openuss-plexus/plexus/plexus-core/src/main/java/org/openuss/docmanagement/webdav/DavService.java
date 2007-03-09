package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
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
		logger.debug("spool of resource '" + locator.getResourcePath() + "' requested.");
		// TODO
		
		
		// HACK
		// Item aus Repository holen
		try {
			Item item = session.getItem(locator.getResourcePath());

			context.setContentType("text/html");
			context.setModificationTime(new Date().getTime());
			context.setETag("");

			if (context.hasStream()) {
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(context.getOutputStream(), "utf8"));
				writer.print("<html><head><title>OpenUSS WebDav</title></head><body>");
				writer.print("<h1>Spool of resource: " + locator.getResourcePath() + "</h1>");
				writer.print("<h2>Properties:</h2><ul>");
				if ((item != null) && item.isNode()) {
					PropertyIterator propertyIterator = ((Node)item).getProperties();
					Property property;
					while (propertyIterator.hasNext()) {
						property = propertyIterator.nextProperty();
						writer.print("<li>" + property.getName() + "</li>");
					}

					writer.print("</ul><h2>Subnodes:</h2><ul>");

					NodeIterator nodeIterator = ((Node)item).getNodes();
					Node node;
					while (nodeIterator.hasNext()) {
						node = nodeIterator.nextNode();
						writer.print("<li>" + node.getName() + "</li>");
					}
				}
				writer.print("</ul></body></html>");
				writer.close();
			}
		} catch (RepositoryException ex) {
			logger.debug("Repository exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
		}
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
