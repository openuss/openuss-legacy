/**
 * 
 */
package org.openuss.web.dav;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class MimeType {
	/**
	 * The default mime type for resources.
	 */
	public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
	/**
	 * Mimetype for directories.
	 */
	public static final String DIRECTORY_MIMETYPE = "httpd/unix-directory";
	
	private static final Logger logger = Logger.getLogger(MimeType.class);
	
	private static final Properties mimeTypes = new Properties();

	static {
		try {
			mimeTypes.load(MimeType.class.getResourceAsStream("mimeTypes.properties"));
		} catch (IOException ex) {
			logger.error("IO exception occurred.");
			logger.error("Exception: " + ex.getMessage());
		}
	}
	
	/**
	 * Returns mime type associated with the suffix of the given name.
	 * @param name The name whose suffix is used to lookup mime type.
	 * @return The associated mime type or the default mime type;
	 */
	public static String getMimeType(String name) {
		String mimeType = null;

		if (name != null) {
			// retrieve resource type from name
			int position = name.lastIndexOf(".");

			if (position != -1) {
				// convert suffix of name to lower case and search for mime type
				mimeType = mimeTypes.getProperty(name.substring(position).toLowerCase());
			}
		}

		if (mimeType == null) {
			return DEFAULT_MIME_TYPE;
		}
		
		return mimeType;
	}
}
