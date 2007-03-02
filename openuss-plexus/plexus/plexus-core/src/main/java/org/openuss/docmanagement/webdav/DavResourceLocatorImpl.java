package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceLocatorImpl implements DavResourceLocator {
	private final String prefix;
	private final String resourcePath;
	private final String workspaceName;
	private final String workspacePath;
	private final DavLocatorFactory factory;
	private final String href;

	/**
	 * Constructor
	 * @param prefix
	 * @param resourcePath
	 * @param factory
	 */
	public DavResourceLocatorImpl(String prefix, String resourcePath, DavLocatorFactory factory) {
		// TODO
		this.prefix = prefix;
		this.workspaceName = "";
		this.workspacePath = "";
		// remove trailing slash for resource pathes, but NOT for root
		if (resourcePath.endsWith("/") && (resourcePath.length() > 1)) {
			this.resourcePath = resourcePath.substring(0, resourcePath.length() - 1);
		} else {
			this.resourcePath = resourcePath;
		}
		this.factory = factory;
		// TODO escape resourcePath!!!
		href = prefix + this.resourcePath;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getFactory()
	 */
	public DavLocatorFactory getFactory() {
		return factory;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getHref(boolean)
	 */
	public String getHref(boolean isCollection) {
		// append trailing slash for collections, but NOT for root
		String suffix = "";
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
		// TODO Auto-generated method stub
		return null;
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
		return workspaceName;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#getWorkspacePath()
	 */
	public String getWorkspacePath() {
		return workspacePath;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#isRootLocation()
	 */
	public boolean isRootLocation() {
		return getResourcePath().equals("/");
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#isSameWorkspace(org.apache.jackrabbit.webdav.DavResourceLocator)
	 */
	public boolean isSameWorkspace(DavResourceLocator locator) {
		// test parameters
		if (locator == null) {
			return false;
		}

		// redirect to other method
		return isSameWorkspace(locator.getWorkspaceName());
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavResourceLocator#isSameWorkspace(java.lang.String)
	 */
	public boolean isSameWorkspace(String workspaceName) {
		return getWorkspaceName().equals(workspaceName);
	}
}
