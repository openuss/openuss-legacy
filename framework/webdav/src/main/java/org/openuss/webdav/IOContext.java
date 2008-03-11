package org.openuss.webdav;

import java.io.InputStream;
import java.sql.Timestamp;

/**
 * A structure of file information.
 */
public interface IOContext {
	/**
	 * @return The content language or null.
	 */
	public String getContentLanguage();
	
	/**
	 * @return The content length or -1 for undefined length.
	 */
	public long getContentLength();

	/**
	 * @return The input stream that supplies the data.
	 */
	public InputStream getInputStream();

	/**
	 * @return The mime type of the resource, including the charset.
	 */
	public String getContentType();
	
	/**
	 * @return The time of last modification or null for no specification.
	 */
	public Timestamp getModificationTime();
	
	/**
	 * @return The identification tag of this resource's version or null for none.
	 */
	public String getETag();
}
