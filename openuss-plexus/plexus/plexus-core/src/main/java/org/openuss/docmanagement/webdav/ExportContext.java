package org.openuss.docmanagement.webdav;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class ExportContext implements IOContext {
	private final Logger logger = Logger.getLogger(ExportContext.class);
	
	private final OutputContext context;
	private File outputFile;
	private OutputStream outputStream;
	private Dictionary<String, String> properties = new Hashtable<String, String>();
	private boolean completed = false;
	
	/**
	 * Constructor.
	 * @param context The underlying output context.
	 */
	public ExportContext(OutputContext context) throws IOException {
		this.context = context;
		if (hasStream()) {
			outputFile = File.createTempFile("econtext", "tmp");
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#hasStream()
	 */
	public boolean hasStream() {
		if (context == null) {
			return false;
		}
		return context.hasStream();
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#informCompleted(boolean)
	 */
	public void informCompleted(boolean success) {
		checkCompleted();

		// mark context as completed
		completed = true;
		
		// close output stream
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException ex) {
				logger.debug("IO exception occurred.");
				logger.debug("Exception: " + ex.getMessage());
				// ignoring exception
			}
		}
		
		// transfer properties and data to underlying context, if successful
		if (success && (context != null)) {
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
					context.setProperty(name, value);
					// check for content-length
					hasContentLength = name.equals(DavConstants.HEADER_CONTENT_LENGTH);
				}
			}

			// transfer data from temporary file, if present
			if (context.hasStream() && outputFile != null) {
				OutputStream contextOutputStream = context.getOutputStream();
				try {
					// set content-length, if not set correctly
					if (!hasContentLength) {
						context.setContentLength(outputFile.length());
					}
					FileInputStream fileInputStream = new FileInputStream(outputFile);

					// copy data between streams
					transferData(fileInputStream, contextOutputStream);
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
	
	/**
	 * Copies data from {@link InputStream} to {@link OutputStream}.
	 * @param inputStream The data source.
	 * @param outputStream The data target.
	 * @throws IOException
	 */
	private void transferData(InputStream inputStream, OutputStream outputStream) throws IOException {
		try {
			// read in 8k-blocks until end of input stream is reached
			byte[] buffer = new byte[8192];
			int readBytes;
			while ((readBytes = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, readBytes);
			}
		} finally {
			inputStream.close();
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#isCompleted()
	 */
	public boolean isCompleted() {
		return completed;
	}
	
	/**
	 * Gets the output stream, if present.
	 * @return The output stream.
	 */
	public OutputStream getOutputStream() {
		checkCompleted();
		
		try {
			// close output stream, if preceeding operations did not completed 
			if (outputStream != null) {
				outputStream.close();
			}
			
			// create new output stream
			outputStream = new FileOutputStream(outputFile);
			return outputStream;
		} catch (IOException ex) {
			logger.debug("IO exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// ignoring exception will return null
		}
		
		return null;
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
	
	/**
	 * Setter for the content language.
	 * @param contentLanguage The content language to set.
	 */
	public void setContentLanguage(String contentLanguage) {
		properties.put(DavConstants.HEADER_CONTENT_LANGUAGE, contentLanguage);
	}
	
	/**
	 * Setter for the content length.
	 * @param contentLength The content length to set.
	 */
	public void setContentLength(long contentLength) {
		properties.put(DavConstants.HEADER_CONTENT_LENGTH, contentLength + "");
	}
	
	/**
	 * Setter for the content type.
	 * @param mimeType The mime type to set.
	 * @param encoding The encoding to set.
	 */
	public void setContentType(String mimeType, String encoding) {
		String contentType = mimeType;
		// append encoding as parameter, if media type is present
		if ((contentType != null) && (encoding != null)) {
			contentType += "; charset=" + encoding;
		}
		properties.put(DavConstants.HEADER_CONTENT_TYPE, contentType);
	}
	
	/**
	 * Setter for the entity tag.
	 * @param etag The entity tag to set.
	 */
	public void setETag(String etag) {
		properties.put(DavConstants.HEADER_ETAG, etag);
	}
	
	/**
	 * Setter for the time of last modification.
	 * @param modificationTime The time to set.
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
	
	/**
	 * Setter for any other property name-value-combination.
	 * @param propertyName The name of the property to set.
	 * @param propertyValue The value of the property to set.
	 */
	public void setProperty(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
	}
}
