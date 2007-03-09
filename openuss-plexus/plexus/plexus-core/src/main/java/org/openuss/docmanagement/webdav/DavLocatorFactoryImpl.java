package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.util.Text;
import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavLocatorFactoryImpl implements DavLocatorFactory {
	private final String resourcePathPrefix;
	
	/**
	 * Constructor
	 * @param resourcePathPrefix
	 */
	public DavLocatorFactoryImpl(String resourcePathPrefix) {
		this.resourcePathPrefix = resourcePathPrefix;
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String href) {
        String locatorPrefix = "";
        
        // remove prefix from href, if present
        if ((prefix != null) && (prefix.length() > 0)) {
            locatorPrefix += prefix;
            if (href.startsWith(prefix)) {
                href = href.substring(prefix.length());
            }
        }

        // remove resource path prefix from href, if present and not already removed
        if ((resourcePathPrefix != null) && (resourcePathPrefix.length() > 0) && !prefix.endsWith(resourcePathPrefix)) {
            locatorPrefix += resourcePathPrefix;
            if (href.startsWith(resourcePathPrefix)) {
                href = href.substring(resourcePathPrefix.length());
            }
        }

        // set slash as path for root, if nothing is left in href
        if (href == null || "".equals(href)) {
            href = "/";
        }
        
        return new DavResourceLocatorImpl(locatorPrefix, Text.unescape(href), this);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String, java.lang.String)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String resourcePath) {
		// delegate to other method
		return createResourceLocator(prefix, workspacePath, resourcePath, true);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavLocatorFactory#createResourceLocator(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public DavResourceLocator createResourceLocator(String prefix, String workspacePath, String path, boolean isResourcePath) {
		// TODO add support for workspaces
		return new DavResourceLocatorImpl(prefix, path, this);
	}
}
