package org.openuss.webdav;

import java.io.IOException;
import java.util.List;

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
	 * @return The original path of this resource, including the name of this particular resource.
	 */
	public WebDAVPath getPath();

	/**
	 * @return The file name of this resource
	 */
	public String getName();
	
	/**
	 * Resolves a resource in this directory. 
	 * 
	 * @param path A representation of the complete path to resolve.
	 * @return A WebDAVResource. Its path is the resolved path.
	 * @throws WebDAVResourceException typically with a "404 Not Found" if the path can't be fully resolved. 
	 */
	public WebDAVResource resolvePath(WebDAVPath path) throws WebDAVHrefException;
	
	/**
	 * Resolve a single path element
	 * 
	 * @param pathElem The name of the child resource to resolve.
	 * @return A WebDAVResource like resolvePath(getPath().concat(pathElem)
	 * @throws WebDAVHrefException
	 * @see {@link #resolvePath(WebDAVPath)}
	 */
	public WebDAVResource resolvePathElem(String pathElem)  throws WebDAVHrefException;
	
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
	 * @param ioc Wrapper of the data to write.
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 * @throws IOException On internal reading errors.
	 */
	public void writeContent(IOContext ioc) throws WebDAVResourceException,IOException;
	
	/**
	 * Creates a collection with the the given name as a child of this resource.
	 * @param name The name of the collection to create.
	 * @return The new collection as a WebDAVResource object
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 * 			For example, this might be thrown if the resource is already mapped.
	 * 			If !isCollection() or the resource already exists, a Conflict exception is thrown.
	 */
	public WebDAVResource createCollection(String name) throws WebDAVResourceException;
	
	/**
	 * Creates a file with the the given name as a child of this resource.
	 * 
	 * @param name The name of the new file
	 * @param ioc The data to write.
	 * @return the new collection as a WebDAVResource object
	 * @throws WebDAVResourceException An error that can be handed directly to the client.
	 * 			If !isCollection() or the resource already exists, a Conflict exception is thrown.
	 */
	public WebDAVResource createFile(String name, IOContext ioc) throws WebDAVResourceException;
	
	/**
	 * @param name The name of the child.
	 * @return true iff resolvePath(name) would return a WebDAVResource.
	 * @throws WebDAVResourceException If the current user is not allowed to check that.
	 */
	public boolean hasChild(String name) throws WebDAVResourceException;
	
	/**
	 * Deletes this resource.
	 *  
	 * @throws WebDAVResourceException On any errors (not existant, no permission etc.)
	 */
	public void delete() throws WebDAVResourceException;
	
	/**
	 * @return All children of this collection, if this element is a collection, otherwise null.
	 * @throws WebDAVResourceException If the current user may not list the contents of the collection.
	 */
	public List<WebDAVResource> getChildren() throws WebDAVResourceException;
	
	/**
	 * @return true iff getChildren does not return null.
	 */
	public boolean isCollection();
	
	/**
	 * @return The MIME content type of this resource.
	 */
	public String getContentType();
	
	/**
	 * @return true if the current user is allowed to read the resource, otherwise false.
	 * 				If isCollection(), this means the user can list this collection's contents.
	 */
	public abstract boolean isReadable();
	
	/**
	 * @return true iff the current user is allowed to write to the resource.
	 * 		For collections, that means the creation of new objects is allowed.
	 */
	public abstract boolean isWritable();
	
	/**
	 * @return true if the current user may delete this resource.
	 */
	public boolean isDeletable();
	
	/**
	 * This MUST be overwritten to ensure prevention of copying trees in itself.
	 * 
	 * @param o The object to compare with.
	 * @return See the general contract of {@link Object#equals(Object)}.
	 */
	public boolean equals(Object o);
	
	/**
	 * This must not return Object.hashCode()!
	 * 
	 * @return See the general contract of {@link Object#hashCode()}.
	 */
	public int hashCode();
}
