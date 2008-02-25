package org.openuss.webdav;

import java.io.IOException;
import java.io.InputStream;

/**
 * A structure of the information needed to read a file from a HTTP client.
 * 
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface ImportContext {
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
	 * @throws IOException On read errrors
	 */
	public InputStream getInputStream() throws IOException;

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
}
