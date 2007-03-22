package org.openuss.docmanagement.webdav;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.openuss.docmanagement.MimeType;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class ImportContextImpl extends IOContextBase implements ImportContext {
	private final Logger logger = Logger.getLogger(ImportContextImpl.class);

	private final HttpServletRequest request;
	private String systemId;
	private File inputFile;
	private boolean completed = false;
	
	/**
	 * Constructor.
	 * @param context The underlying input context.
	 * @param systemId The ID of the resource.
	 */
	public ImportContextImpl(HttpServletRequest request, InputStream stream, String systemId) throws IOException {
		this.request = request;
		this.systemId = systemId;
		
		// spool data from context to temporary file, if stream is present
		if (stream != null) {
			inputFile = File.createTempFile("icontext", "tmp");
			FileOutputStream outputStream = new FileOutputStream(inputFile);
			transferData(stream, outputStream);
			outputStream.close();
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#hasStream()
	 */
	public boolean hasStream() {
		return (inputFile != null);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#informCompleted(boolean)
	 */
	public void informCompleted(boolean success) {
		checkCompleted();

		// mark context as completed
		completed = true;
		
		// delete temporary file
		if (inputFile != null) {
			inputFile.delete();
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#isCompleted()
	 */
	public boolean isCompleted() {
		return completed;
	}
	
	/**
	 * Checks, if context has been completed.
	 */
	private void checkCompleted() {
		// check, if context has been completed
		if (completed) {
			logger.error("Context has already been completed.");
			throw new IllegalStateException("Context has already been completed.");
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getContentLanguage()
	 */
	public String getContentLanguage() {
		return request.getHeader(DavConstants.HEADER_CONTENT_LANGUAGE);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getContentLength()
	 */
	public long getContentLength() {
		String lengthString = request.getHeader(DavConstants.HEADER_CONTENT_LENGTH);
		if (lengthString != null) {
			try {
				return Integer.parseInt(lengthString);
			} catch (NumberFormatException ex) {
				// TODO handle exception
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getEncoding()
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
	 * @see org.openuss.docmanagement.webdav.ImportContext#getInputStream()
	 */
	public InputStream getInputStream() {
		checkCompleted();
		InputStream inputStream = null;
		
		// check, if input file is present
		if (inputFile != null) {
			try {
				// create new input stream on file
				inputStream = new FileInputStream(inputFile);
			} catch (IOException ex) {
				logger.error("IO exception occurred.");
				logger.error("Exception: " + ex.getMessage());
			}
		}
		
		return inputStream;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getMimeType()
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
		
		// return mime type for system id
		return MimeType.getMimeType(getSystemId());
	}
	
	
	/**
	 * Returns the content type or null.
	 * @return The content type or null.
	 */
	private String getContentType() {
		return request.getHeader(DavConstants.HEADER_CONTENT_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getModificationTime()
	 */
	public long getModificationTime() {
		return System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getProperty(java.lang.String)
	 */
	public String getProperty(String propertyName) {
		return request.getHeader(propertyName);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#getSystemId()
	 */
	public String getSystemId() {
		return systemId;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ImportContext#setSystemId(java.lang.String)
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;		
	}
}
