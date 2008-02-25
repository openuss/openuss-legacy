package org.openuss.webdav;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A structure of the information needed to write a file to a HTTP client. 
 * 
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface ExportContext {
	/**
	 * Gets the output stream, if present.
	 * @return The output stream.
	 * @throws IOException If no valid output stream is present for whatever reason.
	 */
	public OutputStream getOutputStream() throws IOException;
	
	/**
	 * Setter for the content language.
	 * @param contentLanguage The content language to set.
	 */
	public void setContentLanguage(String contentLanguage);
	
	/**
	 * Setter for the content length.
	 * @param contentLength The content length to set.
	 */
	public void setContentLength(long contentLength);
	
	/**
	 * Setter for the content type.
	 * @param mimeType The mime type to set.
	 * @param encoding The encoding to set.
	 */
	public void setContentType(String mimeType, String encoding);
	
	/**
	 * Setter for the entity tag.
	 * @param etag The entity tag to set.
	 */
	public void setETag(String etag);
	
	/**
	 * Setter for the time of last modification.
	 * @param modificationTime The time to set.
	 */
	public void setModificationTime(long modificationTime);
	
	/**
	 * Setter for any other property name-value-combination.
	 * @param propertyName The name of the property to set.
	 * @param propertyValue The value of the property to set.
	 */
	public void setProperty(String propertyName, String propertyValue);
}
