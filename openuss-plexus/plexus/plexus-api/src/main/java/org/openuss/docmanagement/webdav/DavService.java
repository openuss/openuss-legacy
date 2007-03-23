package org.openuss.docmanagement.webdav;

import java.util.List;

import javax.jcr.Session;

import org.dom4j.Document;
import org.openuss.lecture.Enrollment;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public interface DavService {
	/**
	 * Getter for the {@link Session}.
	 * @return
	 */
	public Session getSession();
	
	/**
	 * Setter for the {@link Session}.
	 * @param session The repository session to set.
	 */
	public void setSession(Session session);

	/**
	 * Getter for the list of subscribed {@link Enrollment}s.
	 * @return The subscribed Enrollments.
	 */
	public List<Enrollment> getSubscribedEnrollments();

	/**
	 * Setter for the list of subscribed {@link Enrollment}s.
	 * @param subscribedEnrollments
	 */
	public void setSubscribedEnrollments(List<Enrollment> subscribedEnrollments);

	/**
	 * Spools the {@link DavResource} identified by the given {@link DavResourceLocator} to the given {@link OutputContext}.
	 * @param context The target context to which the resource should be spooled.
	 * @param locator The locator identifying the resource to spool.
	 * @throws DavException
	 */
	public void spoolResource(ExportContext context, DavResourceLocator locator) throws DavException;
	
	/**
	 * Adds an member identified by the given {@link DavResourceLocator} and imports required data from given {@link InputContext}.
	 * @param context The source context from which the data for the resource should be imported.
	 * @param locator The locator identifying the resource to add.
	 * @throws DavException
	 */
	public void addMember(ImportContext context, DavResourceLocator locator) throws DavException;
	
	/**
	 * Retrieves properties from a resource and returns an instance of {@link MultiStatus} as demanded by RFC2518.
	 * @param requestDocument The XML document containing data for the PROPFIND method.
	 * @param locator The locator identifying the root of the request.
	 * @param depth The recursion depth.
	 * @return The response Multistatus containing requested property informations. 
	 * @throws DavException
	 */
	public MultiStatus getProperties(Document requestDocument, DavResourceLocator locator, int depth) throws DavException;
	
	/**
	 * Updates properties from a resource and returns an instance of {@link MultiStatus}.
	 * @param requestDocument The XML document containing data for the PROPPATCH method.
	 * @param locator The locator identifying the root of the request.
	 * @return The response Multistatus containing status information.
	 * @throws DavException
	 */
	public MultiStatus updateProperties(Document requestDocument, DavResourceLocator locator) throws DavException;
	
	/**
	 * Creates a collection identified by the given instance of {@link DavResourceLocator}.
	 * @param locator The locator identifying the collection to create.
	 * @throws DavException
	 */
	public void createCollection(DavResourceLocator locator) throws DavException;
	
	/**
	 * Creates a copy of a repository object.
	 * @param source The locator identifying the source repository object.
	 * @param target The locator identifying the target for the copy operation.
	 * @param overwriteAllowed
	 * @param recursive
	 * @return The multi-status containing error information or null.
	 * @throws DavException 
	 */
	public MultiStatus copyResource(DavResourceLocator source, DavResourceLocator destination, boolean overwriteAllowed, boolean recursive) throws DavException;
	
	/**
	 * Moves a repository object to a different location.
	 * @param source The locator identifying the source repository object.
	 * @param target The locator identifying the target for the copy operation.
	 * @param overwriteAllowed True, if destination resource may be non-null.
	 * @return The multi-status containing error informations or null.
	 * @throws DavException
	 */
	public MultiStatus moveResource(DavResourceLocator source, DavResourceLocator destination, boolean overwriteAllowed) throws DavException;
	
	/**
	 * Deletes a resource identified by the {@link DavResourceLocator}.
	 * @param locator The locator identifying the repository object to delete.
	 * @return The multi-status containing error informations or null.
	 * @throws DavException
	 */
	public MultiStatus deleteResource(DavResourceLocator locator) throws DavException;
	
	/**
	 * Getter for instance of {@link DavResourceFactory}.
	 * @return The resource factory.
	 */
	public DavResourceFactory getResourceFactory();
}
