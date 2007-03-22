package org.openuss.docmanagement.webdav;

import java.io.InputStream;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public interface ImportContext extends IOContext {
	/**
	 * Returns the content language or null.
	 * @return The content language or null.
	 */
	public String getContentLanguage();
	
	/**
	 * Returns the content length or -1.
	 * @return The content length or -1 for undefined length.
	 */
	public long getContentLength();

	/**
	 * Returns the encoding or null.
	 * @return The encoding or null.
	 */
	public String getEncoding();

	/**
	 * Returns an input stream, if temporary file is present.
	 * @return The input stream or null.
	 */
	public InputStream getInputStream();

	/**
	 * Returns the mime type.
	 * @return The mime type.
	 */
	public String getMimeType();
	
	/**
	 * Returns the time of last modification.
	 * @return The time of last modification.
	 */
	public long getModificationTime();

	/**
	 * Returns the value of a property or null.
	 * @param propertyName The name of the property.
	 * @return The value of the property or null.
	 */
	public String getProperty(String propertyName);

	/**
	 * Getter for the system ID.
	 * @return The system ID.
	 */
	public String getSystemId();

	/**
	 * Setter for the system ID.
	 * @return The system ID to set.
	 */
	public void setSystemId(String systemId);
}
