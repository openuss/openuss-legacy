package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavLocatorFactoryImpl implements DavLocatorFactory {

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String path, boolean isResourcePath) {
		// TODO workspacePath beachten
		return new DavResourceLocatorImpl(prefix, path, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String, java.lang.String)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String resourcePath) {
		// redirect to other method
		return createResourceLocator(prefix, workspacePath, resourcePath, true);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String href) {
		// TODO prefix aus href entfernen, um resourcePath zu ermitteln
		return null;
	}
}
