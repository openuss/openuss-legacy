package org.openuss.web.dav;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.openuss.webdav.ImportContext;
import org.openuss.webdav.WebDAVConstants;

/**
 * The various information sources available on a file upload.  
 */
public class HTTPImportContext implements ImportContext {
//	private static final Logger logger = Logger.getLogger(HTTPImportContext.class);
	
	protected HttpServletRequest request;
	
	/**
	 * Constructor.
	 * @param request A representation of the request.
	 */
	public HTTPImportContext(HttpServletRequest request) throws IOException {
		// check parameter
		if (request == null) {
			throw new IllegalArgumentException("The parameter request must not be null.");
		}
		
		this.request = request;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ImportContext#getContentLanguage()
	 */
	public String getContentLanguage() {
		return request.getHeader(WebDAVConstants.HEADER_CONTENT_LANGUAGE);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ImportContext#getContentLength()
	 */
	public long getContentLength() {
		String lengthString = request.getHeader(WebDAVConstants.HEADER_CONTENT_LENGTH);
		if (lengthString != null) {
			try {
				return Integer.parseInt(lengthString);
			} catch (NumberFormatException ex) {
				// content length header value is not a number -> ignore
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.ImportContext#getEncoding()
	 */
	public String getEncoding() {
		String contentType = getContentType();
		
		if (contentType != null) {
			// check, if content type contains parameter charset
			int position = contentType.indexOf("charset=");
			if (position == -1) {
				return null;
			}
			
			// get substring right behind equal sign
			String encoding = contentType.substring(position + 8);
			
			// check, if there are other parameters and eventually cut them off
			position = encoding.indexOf(";");
			if (position != -1) {
				encoding = encoding.substring(0, position);
			}
			
			return encoding;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.ImportContext#getMimeType()
	 */
	public String getMimeType() {
		String contentType = getContentType();
		
		if (contentType != null) {
			// cut off any parameters, if present
			int position = contentType.indexOf(";");
			if (position != -1) {
				return contentType.substring(0, position);
			}
			
			return contentType;
		}
		
		return MimeType.DEFAULT_MIME_TYPE;
	}
	
	
	/**
	 * Returns the content type or null.
	 * @return The content type or null.
	 */
	private String getContentType() {
		return request.getHeader(WebDAVConstants.HEADER_CONTENT_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.ImportContext#getModificationTime()
	 */
	public long getModificationTime() {
		return System.currentTimeMillis();
	}

	public String getProperty(String propertyName) {
		return request.getHeader(propertyName);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.ImportContext#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return request.getInputStream();
	}
}
