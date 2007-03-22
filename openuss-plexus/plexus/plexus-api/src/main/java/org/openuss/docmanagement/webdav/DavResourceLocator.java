package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.8
 */
public interface DavResourceLocator {
	/**
	 * @return
	 */
	public DavLocatorFactory getFactory();
	
	/**
	 * @param isCollection
	 * @return
	 */
	public String getHref(boolean isCollection);
	
	/**
	 * @return
	 */
	public String getPrefix();
	
	/**
	 * @return
	 */
	public String getRepositoryPath();
	
	/**
	 * @return
	 */
	public String getResourcePath();
	
	/**
	 * @return
	 */
	public String getWorkspaceName();
	
	/**
	 * @return
	 */
	public String getWorkspacePath();
	
	/**
	 * @return
	 */
	public String getName();
	
	/**
	 * @return
	 */
	public boolean isRootLocation();
	
	/**
	 * @param locator
	 * @return
	 */
	public boolean isSameWorkspace(DavResourceLocator locator);
	
	/**
	 * @param workspaceName
	 * @return
	 */
	public boolean isSameWorkspace(String workspaceName);
}
