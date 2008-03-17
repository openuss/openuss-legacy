package org.openuss.webdav;

import java.util.Map;

/**
 * The outputtable answer of a query.
 * This is different from a {@link MultiStatusResponse} insofar as that the latter is a part of a complete multi-status answers.  
 */
public interface WebDAVAnswer {
	/**
	 * @return The numeric status code.
	 * See {@link WebDAVStatusCodes} for possible values.
	 */
	public int getStatusCode();
	
	/**
	 * @return The message of this answer. Depending on getContentType, this may be human-/machine-readable.
	 */
	public String getMessage();
	
	/**
	 * @return The MIME type of the message without charset specification.
	 */
	public String getContentType();
	
	/**
	 * @return The map of special header values to set or null if there are none
	 */
	public Map<String,String> getXHeaders();
}
