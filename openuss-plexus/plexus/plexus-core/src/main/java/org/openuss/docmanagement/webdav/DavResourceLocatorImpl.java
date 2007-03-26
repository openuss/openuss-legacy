package org.openuss.docmanagement.webdav;

import org.apache.commons.lang.NotImplementedException;
import org.apache.jackrabbit.util.Text;
import org.openuss.docmanagement.DocConstants;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class DavResourceLocatorImpl implements DavResourceLocator {
	private final String prefix;
	private final String resourcePath;
	private final String repositoryPath;
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
		this.repositoryPath = mapPath(resourcePath);
		this.locatorFactory = locatorFactory;
		
        // FIXME analyze escape method with Umlaute
		this.href = prefix + Text.escapePath(resourcePath);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getFactory()
	 */
	public DavLocatorFactory getFactory() {
		return locatorFactory;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getHref(boolean)
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
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getPrefix()
	 */
	public String getPrefix() {
		return prefix;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getRepositoryPath()
	 */
	public String getRepositoryPath() {
		return repositoryPath;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getResourcePath()
	 */
	public String getResourcePath() {
		return resourcePath;
	}
	
	/**
	 * Maps virtual pathes to physical pathes.
	 * @param path The path to map.
	 * @return The mapped path.
	 */
	private String mapPath(String path) {
		// no mapping for: /distribution/*, /examarea/*, /workingplace/*
		if (resourcePath.startsWith("/" + DocConstants.DISTRIBUTION) || resourcePath.startsWith("/" + DocConstants.EXAMAREA) || resourcePath.startsWith("/" + DocConstants.WORKINGPLACE)) {
			return path; 
		}
		
		// mapping for: */distribution/*, */examarea/*, */workingplace/*
		// mapping to: /distribution/*, /examarea/*, /workingplace/*
		String[] pathFields = path.split("/");
		if (pathFields.length > 2) {
			String name = pathFields[2];
			if (name.equalsIgnoreCase(DocConstants.DISTRIBUTION) || name.equalsIgnoreCase(DocConstants.EXAMAREA) || name.equalsIgnoreCase(DocConstants.WORKINGPLACE)) {
				StringBuilder builder = new StringBuilder();
				builder.append("/" + pathFields[2]);
				builder.append("/" + pathFields[1]);
				
				for (int i = 3, j = pathFields.length; i < j; i++) {
					builder.append("/" + pathFields[i]);
				}
				
				return builder.toString();
			}
		}
		
		// no mapping for any other path
		return path;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getWorkspaceName()
	 */
	public String getWorkspaceName() {
		// workspaces are not supported
		throw new NotImplementedException("DavResourceLocatorImpl.getWorkspaceName() not supported.");
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getWorkspacePath()
	 */
	public String getWorkspacePath() {
		// workspaces are not supported
		throw new NotImplementedException("DavResourceLocatorImpl.getWorkspacePath() not supported.");
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#getName()
	 */
	public String getName() {
		return Text.getName(getRepositoryPath());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#isRootLocation()
	 */
	public boolean isRootLocation() {
		// test, if resource path is /
		return "/".equals(getResourcePath());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#isSameWorkspace(org.openuss.docmanagement.webdav.DavResourceLocator)
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
	 * @see org.openuss.docmanagement.webdav.DavResourceLocator#isSameWorkspace(java.lang.String)
	 */
	public boolean isSameWorkspace(String workspaceName) {
		return getWorkspaceName().equals(workspaceName);
	}
}
