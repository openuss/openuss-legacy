/**
 * 
 */
package org.openuss.docmanagement;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class MimeType {
	public static final String DEFAULT_MIME_TYPE = "application/octet-stream";

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
	 * @param resourceName
	 * @return
	 */
	public static String getMimeType(String name) {
		// retrieve resource type from name
		String[] nameFields = name.split(".");
		
		// convert last part of name to lower case and search for mime type
		String mimeType = mimeTypes.getProperty(nameFields[nameFields.length - 1].toLowerCase());
		if (mimeType == null) {
			return DEFAULT_MIME_TYPE;
		}
		return mimeType;
	}
}
