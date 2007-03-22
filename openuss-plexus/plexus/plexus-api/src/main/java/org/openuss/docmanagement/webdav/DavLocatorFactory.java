package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.7
 */
public interface DavLocatorFactory {
	/**
	 * @param prefix
	 * @param href
	 * @return
	 */
	public DavResourceLocator createResourceLocator(String prefix, String href);
	
	/**
	 * @param prefix
	 * @param workspacePath
	 * @param resourcePath
	 * @return
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String resourcePath);

	/**
	 * @param prefix
	 * @param workspacePath
	 * @param path
	 * @param isResourcePath
	 * @return
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String path, boolean isResourcePath);
}
