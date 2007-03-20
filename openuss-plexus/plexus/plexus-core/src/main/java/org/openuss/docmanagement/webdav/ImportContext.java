package org.openuss.docmanagement.webdav;

import java.io.InputStream;

import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
 */
public class ImportContext implements IOContext {
	private final Logger logger = Logger.getLogger(ImportContext.class);
	
	private final InputContext context;
	private final String systemId;
	
	public ImportContext(InputContext context, String systemId) {
		this.context = context;
		this.systemId = systemId;
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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#isCompleted()
	 */
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return
	 */
	public String getContentLanguage() {
		return context.getContentLanguage();
	}
	
	/**
	 * @return
	 */
	public long getContentLength() {
		return context.getContentLength();
	}

	/**
	 * @return
	 */
	public String getEncoding() {
		// TODO
		return null;
	}

	/**
	 * @return
	 */
	public InputStream getInputStream() {
		return context.getInputStream();
	}

	/**
	 * @return
	 */
	public String getMimeType() {
		String contentType = context.getContentType();
		if (contentType != null) {
			String mimeType = contentType.split(";")[0];
			logger.debug(mimeType);
			return mimeType;
		} else {
			logger.debug("application/octet-stream");
			return "application/octet-stream";
		}
	}

	/**
	 * @return
	 */
	public long getModificationTime() {
		return context.getModificationTime();
	}

	/**
	 * @param propertyName
	 * @return
	 */
	public Object getProperty(Object propertyName) {
		return context.getProperty(propertyName.toString());
	}

	/**
	 * @return
	 */
	public String getSystemId() {
		return systemId;
	}
}
