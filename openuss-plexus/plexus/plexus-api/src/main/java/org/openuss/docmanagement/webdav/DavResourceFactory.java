package org.openuss.docmanagement.webdav;

import javax.jcr.Session;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public interface DavResourceFactory {
	/**
	 * Creates an realization of {@link DavResource} identified by given locator.
	 * @param session The repository session which will be used for repository operations.
	 * @param locator The locator identifying the resource to create.
	 * @param isCollection True, if a new collection should be created. Ignored, if resource already present.
	 * @return The DavResource.
	 */
	public DavResource createResource(Session session, DavResourceLocator locator, boolean isCollection);

	/**
	 * Getter for the {@link DavService}.
	 * @return The DavService.
	 */
	public DavService getService();
	
	/**
	 * Setter for the {@link DavService}.
	 * @param service
	 */
	public void setDavService(DavService service);
	
	/**
	 * Getter for the {@link DavConfiguration}.
	 * @return The DavResourceConfiguration.
	 */
	public DavConfiguration getConfiguration();
	
	/**
	 * Setter for the {@link DavConfiguration}.
	 * @param configuration
	 */
	public void setConfiguration(DavConfiguration configuration);
}
