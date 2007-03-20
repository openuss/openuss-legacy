package org.openuss.docmanagement.webdav;

import org.apache.commons.lang.NotImplementedException;
import org.apache.jackrabbit.util.Text;
import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class DavResourceLocatorImpl implements DavResourceLocator {
	private final String prefix;
	private final String resourcePath;
	private final String href;
	private final DavLocatorFactory locatorFactory;
	
	/**
	 * Constructor.
	 * @param prefix The prefix which can be used to build absolute pathes.
	 * @param resourcePath The relative path of the resource.
	 * @param locatorFactory Reference to the locator factory implementation.
	 */
	DavResourceLocatorImpl(String prefix, String resourcePath, DavLocatorFactory locatorFactory) {
		this.prefix = prefix;
		// remove trailing slash except for root location
		if (resourcePath.endsWith("/") && (resourcePath.length() > 1)) {
			resourcePath = resourcePath.substring(0, resourcePath.length() - 1);
		}
		this.resourcePath = resourcePath;
		this.locatorFactory = locatorFactory;
		
		this.href = prefix + Text.escapePath(resourcePath);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getFactory()
	 */
	public DavLocatorFactory getFactory() {
		return locatorFactory;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getHref(boolean)
	 */
	public String getHref(boolean isCollection) {
		String suffix = "";
		// append trailing slash for collections except for root
		if (isCollection && !isRootLocation()) {
			suffix = "/";
		}
		return href + suffix;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getPrefix()
	 */
	public String getPrefix() {
		return prefix;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getRepositoryPath()
	 */
	public String getRepositoryPath() {
		// no mapping between resource path and storage in repository
		return resourcePath;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getResourcePath()
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getWorkspaceName()
	 */
	public String getWorkspaceName() {
		// TODO workspaces are not supported
		throw new NotImplementedException("DavResourceLocatorImpl.getWorkspaceName() not supported.");
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getWorkspacePath()
	 */
	public String getWorkspacePath() {
		// TODO workspaces not supported
		throw new NotImplementedException("DavResourceLocatorImpl.getWorkspacePath() not supported.");
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#isRootLocation()
	 */
	public boolean isRootLocation() {
		// test, if resource path is /
		return "/".equals(getResourcePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#isSameWorkspace(org.apache.jackrabbit.webdav.DavResourceLocator)
	 */
	public boolean isSameWorkspace(DavResourceLocator locator) {
		// workspaces cannot be equal if parameter is null
		if (locator == null) {
			return false;
		}
		// delegate to other method
		return isSameWorkspace(locator.getWorkspaceName());
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#isSameWorkspace(java.lang.String)
	 */
	public boolean isSameWorkspace(String workspaceName) {
		return getWorkspaceName().equals(workspaceName);
	}
}
