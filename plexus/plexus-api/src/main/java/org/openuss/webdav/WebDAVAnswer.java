package org.openuss.webdav;

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
}
