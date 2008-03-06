package org.openuss.webdav;

import java.io.IOException;
import java.util.Set;

import org.w3c.dom.Document;

/**
 * This is the base frame of all WebDAV backends.
 * 
 * A backend realizes the connection between the virtual WebDAV namespace and the existing OpenUSS code.
 * It is represented by a *WebDAVResource class which is responsible for fine-grained authorization.
 *  
 * If the backend imposes further restrictions, the corresponding methods (like delete()) MUST check the validity of actions themselves.
 * Its state is defined by the current path.
 * 
 * All methods may throw RuntimeExceptions on internal errors.
 */
public interface WebDAVResource {
	/**
	 * The original path of this resource, including the name of this particular resource.
	 */
	public WebDAVPath getPath();

	/**
	 * Resolves a resource in this directory. 
	 * 
	 * @param path A representation of the complete path to resolve.
	 * @return A new WebDAVResource. Its path consists of this one's and the path argument.
	 * 			This object may not point to an existing file.Use exists() to find out.
	 * @throws WebDAVResourceException An error that can be handed directly to the client. 
	 */
	public WebDAVResource resolvePath(WebDAVPath path) throws WebDAVHrefException;
	
	/**
	 * Obtain information about this object.
	 * 
	 * @param req The request document sent by the client.
	 * @return A response to the client.
	 * @throws WebDAVResourceException An error that can be handed directly to the client. 
	 */
	public MultiStatusResponse getProperties(Document req) throws WebDAVResourceException;
	
	/**
	 * Change meta-data. See RFC 2518, 8.2.2 for details.
	 * 
	 * @param req The request document as sent by the client.
	 * 		It should contain a propertyupdate element.
	 * @return A response to the client.
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 */
	public MultiStatusResponse updateProperties(Document req) throws WebDAVResourceException;
	
	/**
	 * Spool a document to the client or read for a copy/move task.
	 * If this resource points to a collection, a human-readable HTML document should be written.
	 * 
	 * @return A meta information capsule. 
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 * @throws IOException On internal writing errors.
	 */
	public IOContext readContent() throws WebDAVResourceException,IOException;

	/**
	 * Read a document from the client or write for a copy/move task.
	 * If this resource points to a collection, the returned
	 * 
	 * @param ec The ExportContext that allows writing to the client. This may be a virtual one.
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 * @throws IOException On internal reading errors.
	 */
	public void writeContent(IOContext ioc) throws WebDAVResourceException,IOException;
	
	/**
	 * Creates a collection with the name of this resource.
	 * 
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 */
	public void createCollection() throws WebDAVResourceException;
	
	/**
	 * Deletes this resource.
	 *  
	 * @throws WebDAVResourceException On any errors (not existant, no permission etc.)
	 */
	public void delete() throws WebDAVResourceException;
	
	/**
	 * @return All children of this collection, if this element is a collection, otherwise null.
	 */
	public Set<WebDAVResource> getChildren();
	
	/**
	 * @return true iff getChildren does not return null.
	 */
	public boolean isCollection();
	
	/**
	 * @return The MIME content type of this resource.
	 */
	public String getContentType();
	
	/**
	 * @return true, iff this resource object points to an existant file.
	 */
	public boolean exists();
}
