package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface DavResourceLocator {
	/**
	 * Returns a reference to the factory class used to create this locator.
	 * @return The factory class.
	 */
	public DavLocatorFactory getFactory();
	
	/**
	 * Returns a href representation of the resource path.
	 * @param isCollection True, if resource is a collection.
	 * @return The href representation of the resource path.
	 */
	public String getHref(boolean isCollection);
	
	/**
	 * Returns the name of the resource.
	 * @return The name of the resource.
	 */
	public String getName();
	
	/**
	 * Returns the prefix of the resource path.
	 * @return The prefix of the resource path.
	 */
	public String getPrefix();
	
	/**
	 * Returns the path of the resource in the repository.
	 * @return The path of the resource in the repository.
	 */
	public String getRepositoryPath();
	
	/**
	 * Returns the path of the resource.
	 * @return The path of the resource.
	 */
	public String getResourcePath();
	
	/**
	 * Returns the name of the workspace.
	 * @return The name of the workspace.
	 */
	public String getWorkspaceName();
	
	/**
	 * Returns the path component equivalent to the workspace.
	 * @return The path component equivalent to the workspace.
	 */
	public String getWorkspacePath();
	
	/**
	 * Returns true, if resource is root in a workspace.
	 * @return True, if resource is root in a workspace.
	 */
	public boolean isRootLocation();
	
	/**
	 * Compares the workspace of this locator with the one from the given locator.
	 * @param locator The locator to compare with.
	 * @return True, if both locators identifies resources within the same workspace.
	 */
	public boolean isSameWorkspace(DavResourceLocator locator);
	
	/**
	 * Compares the workspace name of this locator with the given one.
	 * @param workspaceName The workspace name to compare with.
	 * @return True, if the given workspace name equals the workspace name of this locator.
	 */
	public boolean isSameWorkspace(String workspaceName);
}
