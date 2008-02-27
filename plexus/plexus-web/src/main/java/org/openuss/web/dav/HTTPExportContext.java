package org.openuss.web.dav;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.openuss.webdav.ExportContext;
import org.openuss.webdav.WebDAVConstants;

/**
 * A "real" export context encapsulating a HttpServletResponse.
 */
public class HTTPExportContext implements ExportContext {
	protected HttpServletResponse response;
	
	/**
	 * Constructor.
	 * @param response The HTTP response object.
	 */
	public HTTPExportContext(HttpServletResponse response) throws IOException {
		// check parameter
		if (response == null) {
			throw new IllegalArgumentException("The parameter response must not be null.");
		}
		
		this.response = response;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#setContentLanguage(java.lang.String)
	 */
	public void setContentLanguage(String contentLanguage) {
		if (contentLanguage != null) {
			response.setHeader(WebDAVConstants.HEADER_CONTENT_LANGUAGE, contentLanguage);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#setContentLength(long)
	 */
	public void setContentLength(long contentLength) {
		response.setHeader(WebDAVConstants.HEADER_CONTENT_LENGTH, String.valueOf(contentLength));
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#setContentType(java.lang.String, java.lang.String)
	 */
	public void setContentType(String mimeType, String encoding) {
		String contentType = mimeType;
		// append encoding as parameter, if media type is present
		if ((contentType != null) && (encoding != null)) {
			contentType += "; charset=" + encoding;
			response.setHeader(WebDAVConstants.HEADER_CONTENT_TYPE, contentType);
		} else {
			throw new IllegalArgumentException("Invalid contentType (" + contentType + " and encoding (" + encoding + ")specified");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#setETag(java.lang.String)
	 */
	public void setETag(String etag) {
		if (etag != null) {
			response.setHeader(WebDAVConstants.HEADER_ETAG, etag);
		} else {
			throw new IllegalArgumentException("Invalid eTag");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#setModificationTime(long)
	 */
	public void setModificationTime(long modificationTime) {
		// set modification time to now, if not defined or in the future
		if ((modificationTime < 0) || (modificationTime > System.currentTimeMillis())) {
			modificationTime = System.currentTimeMillis();
		}
		
		// format modification time as HTTP date
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String modificationHttpDate = dateFormat.format(new Date(modificationTime));
		
		response.setHeader(WebDAVConstants.HEADER_LAST_MODIFIED, modificationHttpDate);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#setProperty(java.lang.String, java.lang.String)
	 */
	public void setProperty(String propertyName, String propertyValue) {
		if (propertyValue != null) {
			response.setHeader(propertyName, propertyValue);
		} else {
			throw new IllegalArgumentException("Invalid " + propertyName + " value!");			
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.ExportContext#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		return response.getOutputStream();
	}
}
