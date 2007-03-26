package org.openuss.docmanagement.webdav;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface DavResource {
	/**
	 * Adds a member to this resource.
	 * @param resource
	 * @param context
	 * @throws DavException
	 */
	public void addMember(DavResource resource, ImportContext context) throws DavException;
	
	/**
	 * Copies data and properties from source.
	 * @param source The source of the data and the properties.
	 * @param recursive True, if members of source should be copied.
	 * @return The multi-status containing error informations.
	 * @throws DavException
	 */
	public MultiStatus copyFrom(DavResource source, boolean recursive) throws DavException;

	/**
	 * Returns, whether this resource is a representation of an existing object.
	 * @return True, if this resource is a representation of an existing object.
	 */
	public boolean exists();
	
	/**
	 * Exports the content of the resource to the given {@link ExportContext}.
	 * @param context The export context.
	 * @throws DavException
	 */
	public void exportContent(ExportContext context) throws DavException;
	
	/**
	 * Returns the parent collection containing this resource or null.
	 * @return The parent collection or null.
	 */
	public DavResource getCollection();
	
	/**
	 * Returns the date and time of creation.
	 * @return The creation date and time.
	 * @throws DavException
	 */
	public String getCreationDate() throws DavException;
	
	/**
	 * Returns the name to display to user.
	 * @return The display name.
	 * @throws DavException
	 */
	public String getDisplayName() throws DavException;
	
	/**
	 * Getter for the {@link DavResourceFactory} used to create this resource.
	 * @return The resource factory.
	 */
	public DavResourceFactory getFactory();
	
	/**
	 * Returns the time of last modification.
	 * @return The time of last modification.
	 */
	public long getLastModified() throws DavException;
	
	/**
	 * Getter for the {@link DavResourceLocator} identifying this resource.
	 * @return The resource locator.
	 */
	public DavResourceLocator getLocator();
	
	/**
	 * Returns a filtered list of {@link DavResource} containing the descendents.
	 * @return The list of descendent resources.
	 * @throws DavException
	 */
	public List<DavResource> getMembers() throws DavException;
	
	/**
	 * Returns response containing the requested properties.
	 * @param properties The names of the requested properties or null.
	 * @param namesOnly True, if only the names are requested.
	 * @return The response containing the properties.
	 * @throws DavException
	 */
	public MultiStatusResponse getProperties(List<String> properties, boolean namesOnly) throws DavException;
	
	/**
	 * Returns the visibility of the resource.
	 * @return The visitibility as defined in DocRights.
	 * @throws DavException
	 */
	public int getVisibility() throws DavException;
	
	/**
	 * Imports the content from the given {@link ImportContext}.
	 * @param context The import context.
	 * @return 
	 * @throws DavException
	 */
	public boolean importContent(ImportContext context) throws DavException;
	
	/**
	 * Returns resource type.
	 * @return True, if the resource is a collection.
	 */
	public boolean isCollection();
	
	/**
	 * Removes the resource and returns multi-status, if any error occurred.
	 * @return The multi-status containing error informations.
	 * @throws DavException
	 */
	public MultiStatus remove() throws DavException;
	
	/**
	 * Sets the given property values and removes given properties.
	 * @param propertiesToSet The property names and values to set.
	 * @param propertiesToRemove The properties to remove.
	 * @return The response containing the status information.
	 * @throws DavException
	 */
	public MultiStatusResponse updateProperties(Map<String, String> propertiesToSet, List<String> propertiesToRemove) throws DavException;
}
