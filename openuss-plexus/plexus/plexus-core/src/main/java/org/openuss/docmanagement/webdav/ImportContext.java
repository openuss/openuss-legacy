package org.openuss.docmanagement.webdav;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.MimeType;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
 */
public class ImportContext extends IOContext {
	private final Logger logger = Logger.getLogger(ImportContext.class);
	
	private final InputContext context;
	private final String systemId;
	private File inputFile;
	private boolean completed = false;
	
	/**
	 * Constructor.
	 * @param context The underlying input context.
	 * @param systemId The ID of the resource.
	 */
	public ImportContext(InputContext context, String systemId) throws IOException {
		this.context = context;
		this.systemId = systemId;

		// spool data from context to temporary file, if stream is present
		if ((context != null) && (context.getInputStream() != null)) {
			inputFile = File.createTempFile("icontext", "tmp");
			FileOutputStream outputStream = new FileOutputStream(inputFile);
			transferData(context.getInputStream(), outputStream);
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

	/**
	 * Returns the content language or null.
	 * @return The content language or null.
	 */
	public String getContentLanguage() {
		if (context != null) {
			return context.getContentLanguage();
		}
		return null;
	}
	
	/**
	 * Returns the content length or -1.
	 * @return The content length or -1 for undefined length.
	 */
	public long getContentLength() {
		if (context != null) {
			return context.getContentLength();
		}
		return -1;
	}

	/**
	 * Returns the encoding or null.
	 * @return The encoding or null.
	 */
	public String getEncoding() {
		String contentType = context.getContentType();
		
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

	/**
	 * Returns an input stream, if temporary file is present.
	 * @return The input stream or null.
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

	/**
	 * Returns the mime type.
	 * @return The mime type.
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
		if (context != null) {
			return context.getContentType();
		}
		return null;
	}

	/**
	 * Returns the time of last modification.
	 * @return The time of last modification.
	 */
	public long getModificationTime() {
		if (context != null) {
			return context.getModificationTime();
		}
		return System.currentTimeMillis();
	}

	/**
	 * Returns the value of a property or null.
	 * @param propertyName The name of the property.
	 * @return The value of the property or null.
	 */
	public String getProperty(String propertyName) {
		if (context != null) {
			return context.getProperty(propertyName);
		}
		return null;
	}

	/**
	 * Getter for the system ID.
	 * @return The system ID.
	 */
	public String getSystemId() {
		return systemId;
	}
}
