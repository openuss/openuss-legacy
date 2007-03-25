package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface DavLocatorFactory {
	/**
	 * Creates a {@link DavResourceLocator} with a given prefix and given href representation of the resource path.
	 * @param prefix The prefix to subtract from the path.
	 * @param href The path of the resource.
	 * @return The DavResourceLocator.
	 */
	public DavResourceLocator createResourceLocator(String prefix, String href);
	
	/**
	 * Creates a {@link DavResourceLocator} with a given prefix, a given workspace path and given resource path.
	 * @param prefix The prefix to subtract from the path.
	 * @param workspacePath The workspace path.
	 * @param resourcePath The resource path.
	 * @return The DavResourceLocator.
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String resourcePath);

	/**
	 * Creates a {@link DavResourceLocator} with a given prefix, a given workspace path and a given path.
	 * @param prefix The prefix to subtract from the path.
	 * @param workspacePath The workspace path.
	 * @param path The path.
	 * @param isResourcePath True, if path is a resource path.
	 * @return The DavResourceLocator.
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String path, boolean isResourcePath);
}
