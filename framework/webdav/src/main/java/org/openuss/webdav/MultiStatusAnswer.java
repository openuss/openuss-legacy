package org.openuss.webdav;

import java.util.List;

/**
* Represents a whole WebDAV multi-status answer which consists of multiple MultiStatusResponse s.
* Each MultiStatusResponse normally details various information about a certain URI.
*/
public interface MultiStatusAnswer extends WebDAVAnswer {
	/**
	 * Adds a reponse to the multi-status.
	 * @param response The response to add.
	 */
	public void addResponse(MultiStatusResponse response);
	
	/**
	 * Returns a list of the associated responses.
	 * @return The list of responses. Writing to it is undefined.
	 */
	public List<MultiStatusResponse> getResponses();
	
	/**
	 * Getter for the description of the multi-status.
	 * @return The description.
	 */
	public String getDescription();

	/**
	 * Setter for the description of the multi-status.
	 * @param description The description to set.
	 */
	public void setDescription(String description);
	
	/**
	 * @return The number of responses
	 */
	public int getResponseCount();
}
