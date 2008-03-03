package org.openuss.webdav;

/**
 * A WebDAV path specification, optionally including yet unresolved path elements.
 * Implementationns should be immutable. 
 */
public interface WebDAVPath {
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
	 * Add a further path element.
	 * 
	 * @param subResStr The name of a sub resource.
	 * @return A WebDAVPathElement representing the designated sub resource.
	 */
	public WebDAVPath concat(String subResStr);
	
	/**
	 * @return The local file name.
	 * 			For example, a fully resolved WebDAVPath("/a/b/cd.e") would return "cd.e".
	 * 			null is returned for the root path.
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
	 * Strict equals function.
	 * 
	 * @param p The path to compare with.
	 * @return true iff all requesting methods on both objects will return the same.
	 */
	public boolean equals(WebDAVPath p);
	
	/**
	 * @return The already resolved path of this object, not encoded.
	 */
	public String getPrefix();
	
	/**
	 * @return A string that can be extradited to the WebDAV client and represents the already resolved path.
	 */
	public String toClientString();

	/**
	 * @return The path yet to resolve or null, if this path is already resolved.
	 */
	public String getToResolve();
	
	/**
	 * @return The complete path representation, unencoded
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
	 * Returns the common path of two yet unresolved WebDAV paths.
	 * 
	 * @param other The other path to compare this with.
	 * 				For useful results, its prefix should equal this object's.
	 * @return A struct consisting of the two resolved paths with updated prefixes.
	 */
	public CommonPathRes common(WebDAVPath other);
	public final class CommonPathRes {
		public final WebDAVPath newThis;
		public final WebDAVPath newOther;
		
		public CommonPathRes(WebDAVPath newThis,
				WebDAVPath newOther) {
			super();
			this.newThis = newThis;
			this.newOther = newOther;
		}
	}
}
