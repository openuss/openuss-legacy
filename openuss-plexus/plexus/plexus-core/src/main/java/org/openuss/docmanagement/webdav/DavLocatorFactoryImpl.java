package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavResourceLocator;

public class DavLocatorFactoryImpl implements DavLocatorFactory {
	public DavLocatorFactoryImpl(String resourcePathPrefix) {
		// TODO
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String href) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String, java.lang.String)
	 */
	public DavResourceLocator createResourceLocator(String prefix,
			String workspacePath, String resourcePath) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public DavResourceLocator createResourceLocator(String prefix,
			String workspacePath, String path, boolean isResourcePath) {
		// TODO Auto-generated method stub
		return null;
	}

}
