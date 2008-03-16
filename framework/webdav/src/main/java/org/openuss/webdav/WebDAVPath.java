package org.openuss.webdav;

import java.io.Serializable;
import java.util.List;

/**
 * A WebDAV path specification, optionally including yet unresolved path elements.
 * Implementationns should be immutable. 
 */
public interface WebDAVPath extends Serializable {
	/**
	 * HTTP path separator.
	 */
	public final String PATH_SEP = "/";
	/**
	 * The file extension marker.
	 * @see #getFileExt()
	 */
	public final String FILEEXT_SEP = ".";
	
	/**
	 * Add a further path element to the list of elements to resolve.
	 * 
	 * @param subResStr The name of a sub resource.
	 * @return A WebDAVPathElement representing the designated sub resource.
	 */
	public WebDAVPath concat(String subResStr);
	
	/**
	 * @return The local file name.
	 * 			For example, a fully resolved WebDAVPath("/a/b/cd.e") would return "cd.e".
	 * 			null is returned for the root path or if this element is not resolved yet.
	 */
	public String getFileName();
	
	/**
	 * @return The extension of the file represented by this path.
	 * 			For example, a WebDAVPath("/a/b/cd.e") would return "e".
	 * 			null if there is no recognizable extension. For example, "/a/b/cde" would return null.
	 */
	public String getFileExt();

	/**
	 * Strict equals function.
	 * 
	 * @param o The object to compare with.
	 * @return true iff the object to compare with implements this itnerface and all requesting methods on both objects will return the same.
	 */
	public boolean equals(Object o);

	/**
	 * @return The already resolved path of this object, not encoded.
	 * 			This includes any prefixes
	 */
	public String getResolved();
	
	/**
	 * @return A string that can be extradited to the WebDAV client and represents the already resolved path.
	 */
	public String toClientString();

	/**
	 * @return The path yet to resolve or null iff this path is already resolved.
	 */
	public String getToResolve();
	
	/**
	 * @return The list elements yet to resolve or null iff this path is already resolved.
	 */
	public List<String> getToResolveList();
	
	/**
	 * @return The complete path representation including elements yet to resolve, unencoded
	 */
	public String getCompleteString();
	
	/**
	 * @return true iff this path is already resolved.
	 */
	public boolean isResolved();
	
	/**
	 * @return The name of the next element to resolve or null if there are no more elements to resolve.
	 */
	public String getNextName();
	
	/**
	 * Resolve one element.
	 * 
	 * @return An object that represents the path after resolving a further path component.
	 * 			null if no more elements are to be resolved. 
	 */
	public WebDAVPath next();

	/**
	 * @return The fully resolved path. This should be equal to sequentially calling next() until it returns null. 
	 */
	public WebDAVPath asResolved();
	
	/**
	 * @return The current state of this WebDAVPath as an resolved WebDAVPath without resolving further elements.
	 */
	public WebDAVPath asFinalPath();
	
	/**
	 * @return The path of the resource to resolve, if this resource is not fully resolved.
	 * 			Otherwise the current path.
	 */
	public WebDAVPath getParent();
	
	/**
	 * @return The number of elements yet to resolve.
	 */
	public int getNumberOfElemsToResolve();

	/**
	 * Call next() until getNumberOfElementsToResolve() <= but 
	 * 
	 * @param but When to stop resolving further path elements
	 * @return The resolved path
	 */
	public WebDAVPath resolveAllBut(int but);
}
