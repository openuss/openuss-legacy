package org.openuss.docmanagement.webdav;

import java.io.OutputStream;

import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ExportContext implements IOContext {
	private final OutputContext context;
	
	public ExportContext(OutputContext context) {
		this.context = context;
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
	public OutputStream getOutputStream() {
		return context.getOutputStream();
	}
	
	/**
	 * @param contentLanguage
	 */
	public void setContentLanguage(String contentLanguage) {
		context.setContentLanguage(contentLanguage);
	}
	
	/**
	 * @param contentLength
	 */
	public void setContentLength(long contentLength) {
		context.setContentLength(contentLength);
	}
	
	/**
	 * @param mimeType
	 * @param encoding
	 */
	public void setContentType(String mimeType, String encoding) {
//		context.setContentType(contentType);
	}
	
	/**
	 * @param creationTime
	 */
	public void setCreationTime(long creationTime) {
		// TODO
	}
	
	/**
	 * @param etag
	 */
	public void setETag(String etag) {
		context.setETag(etag);
	}
	
	/**
	 * @param modificationTime
	 */
	public void setModificationTime(long modificationTime) {
		context.setModificationTime(modificationTime);
	}
	
	/**
	 * @param propertyName
	 * @param propertyValue
	 */
	public void setProperty(Object propertyName, Object propertyValue) {
		context.setProperty(propertyName.toString(), propertyValue.toString());
	}
}
