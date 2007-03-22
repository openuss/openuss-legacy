package org.openuss.docmanagement.webdav;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class ExportContextImpl extends IOContextBase implements ExportContext {
	private final Logger logger = Logger.getLogger(ExportContextImpl.class);
	
	private final HttpServletResponse response;
	private final OutputStream responseOutputStream;
	private File outputFile;
	private OutputStream fileOutputStream;
	private Dictionary<String, String> properties = new Hashtable<String, String>();
	private boolean completed = false;
	
	/**
	 * Constructor.
	 * @param context The underlying output context.
	 */
	public ExportContextImpl(HttpServletResponse response, OutputStream stream) throws IOException {
		// TODO prüfen
		this.response = response;
		responseOutputStream = stream;
		if (hasStream()) {
			outputFile = File.createTempFile("econtext", "tmp");
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#hasStream()
	 */
	@Override
	public boolean hasStream() {
		return (responseOutputStream != null);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#informCompleted(boolean)
	 */
	@Override
	public void informCompleted(boolean success) {
		checkCompleted();

		// mark context as completed
		completed = true;
		
		// close output stream
		if (fileOutputStream != null) {
			try {
				fileOutputStream.close();
			} catch (IOException ex) {
				logger.debug("IO exception occurred.");
				logger.debug("Exception: " + ex.getMessage());
				// ignoring exception
			}
		}
		
		// transfer properties and data to underlying context, if successful
		if (success) {
			// content-length is an MUST have property for the context
			boolean hasContentLength = false;
			
			// enumerate properties and copy them to context
			Enumeration<String> propertyNames = properties.keys();
			String name;
			String value;
			while (propertyNames.hasMoreElements()) {
				name = propertyNames.nextElement();
				value = properties.get(name);
				if (name != null && value != null) {
					response.setHeader(name, value);
					// check for content-length
					hasContentLength = name.equals(DavConstants.HEADER_CONTENT_LENGTH);
				}
			}

			// transfer data from temporary file, if present
			if (hasStream() && outputFile != null) {
				try {
					// set content-length, if not set correctly
					if (!hasContentLength) {
						response.setHeader(DavConstants.HEADER_CONTENT_LENGTH, outputFile.length() + "");
					}
					FileInputStream fileInputStream = new FileInputStream(outputFile);

					// copy data between streams
					transferData(fileInputStream, responseOutputStream);
				} catch (IOException ex) {
					logger.error("IO exception occurred.");
					logger.error("Exception: " + ex.getMessage());
				}
			}
		}
		
		// delete temporary file, if present
		if (outputFile != null) {
			outputFile.delete();
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#isCompleted()
	 */
	@Override
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
	 * @see org.openuss.docmanagement.webdav.ExportContext#getOutputStream()
	 */
	public OutputStream getOutputStream() {
		checkCompleted();
		
		try {
			// close output stream, if preceeding operations did not completed 
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
			
			// create new output stream
			fileOutputStream = new FileOutputStream(outputFile);
			return fileOutputStream;
		} catch (IOException ex) {
			logger.debug("IO exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// ignoring exception will return null
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ExportContext#setContentLanguage(java.lang.String)
	 */
	public void setContentLanguage(String contentLanguage) {
		if (contentLanguage != null) {
			properties.put(DavConstants.HEADER_CONTENT_LANGUAGE, contentLanguage);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ExportContext#setContentLength(long)
	 */
	public void setContentLength(long contentLength) {
		properties.put(DavConstants.HEADER_CONTENT_LENGTH, contentLength + "");
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ExportContext#setContentType(java.lang.String, java.lang.String)
	 */
	public void setContentType(String mimeType, String encoding) {
		String contentType = mimeType;
		// append encoding as parameter, if media type is present
		if ((contentType != null) && (encoding != null)) {
			contentType += "; charset=" + encoding;
			properties.put(DavConstants.HEADER_CONTENT_TYPE, contentType);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ExportContext#setETag(java.lang.String)
	 */
	public void setETag(String etag) {
		if (etag != null) {
			properties.put(DavConstants.HEADER_ETAG, etag);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ExportContext#setModificationTime(long)
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
		
		properties.put(DavConstants.HEADER_LAST_MODIFIED, modificationHttpDate);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ExportContext#setProperty(java.lang.String, java.lang.String)
	 */
	public void setProperty(String propertyName, String propertyValue) {
		if (propertyValue != null) {
			properties.put(propertyName, propertyValue);
		}
	}
}
