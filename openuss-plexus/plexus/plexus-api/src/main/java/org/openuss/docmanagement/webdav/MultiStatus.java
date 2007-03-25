package org.openuss.docmanagement.webdav;

import java.util.List;

import org.dom4j.Document;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface MultiStatus {
	/**
	 * Adds a reponse to the multi-status.
	 * @param response The response to add.
	 */
	public void addResponse(MultiStatusResponse response);
	
	/**
	 * Creates a {@link PropertyResponse} associated with this multi-status.
	 * @param href The href of the resource the response is associated with.
	 * @param description The description of the response.
	 * @return The PropertyResponse.
	 */
	public PropertyResponse createPropertyResponse(String href, String description);
	
	/**
	 * Creates a {@link StatusResponse} associated with this multi-status.
	 * @param href The href of the resource the response is associated with.
	 * @param statusCode The status code of the response.
	 * @param description The description of the response.
	 * @return The StatusResponse.
	 */
	public StatusResponse createStatusResponse(String href, int statusCode, String description);
	
	/**
	 * Getter for the description of the multi-status.
	 * @return The description.
	 */
	public String getDescription();
	
	/**
	 * Returns a list of the associated responses.
	 * @return The list of responses.
	 */
	public List<MultiStatusResponse> getResponses();
	
	/**
	 * Setter for the description of the multi-status.
	 * @param description The description to set.
	 */
	public void setDescription(String description);

	/**
	 * Transforms the multi-status to XML and adds it to the given {@link Document}.
	 * @param document The document to add the multi-status to.
	 */
	public void toXml(Document document);
}
