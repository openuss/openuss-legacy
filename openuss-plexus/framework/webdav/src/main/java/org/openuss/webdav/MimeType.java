/**
 * 
 */
package org.openuss.webdav;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 * 
 * @see WebDAVConstants for static mime type definitions
 */
public class MimeType {
	private static final Logger logger = Logger.getLogger(MimeType.class);
	
	private static final Properties mimeTypes = new Properties();
	
	static {
		try {
			mimeTypes.load(MimeType.class.getResourceAsStream("../../../mimeTypes.properties"));
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
	public static String getMimeTypeByName(String name) {
		String ext = WebDAVPathImpl.getExtension(name);
		
		return getMimeTypeByExt(ext);
	}
	
	/**
	 * @param ext The extension
	 * @return The MIME type associated with the specified extension
	 */
	public static String getMimeTypeByExt(String ext) {
		String mimeType = null;
		
		if (ext != null) {
			mimeType = mimeTypes.getProperty(ext.toLowerCase());
		}

		if (mimeType == null) {
			return WebDAVConstants.MIMETYPE_DEFAULT;
		}
		
		return mimeType;
	}
}