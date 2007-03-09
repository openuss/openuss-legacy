package org.openuss.docmanagement.webdav;

import java.io.OutputStream;

import javax.jcr.Item;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ExportContext implements IOContext {
	private final Item exportRoot;
	private final OutputContext context;
	
	public ExportContext(Item exportRoot, OutputContext context) {
		this.exportRoot = exportRoot;
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
	public Item getExportRoot() {
		return exportRoot;
	}
	
	/**
	 * @return
	 */
	public OutputStream getOutputStream() {
		// TODO
		return null;
	}
	
	/**
	 * @param contentLanguage
	 */
	public void setContentLanguage(String contentLanguage) {
		// TODO
	}
	
	/**
	 * @param contentLength
	 */
	public void setContentLength(long contentLength) {
		// TODO
	}
	
	/**
	 * @param mimeType
	 * @param encoding
	 */
	public void setContentType(String mimeType, String encoding) {
		// TODO
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
		// TODO
	}
	
	/**
	 * @param modificationTime
	 */
	public void setModificationTime(long modificationTime) {
		// TODO
	}
	
	/**
	 * @param propertyName
	 * @param properyValue
	 */
	public void setProperty(Object propertyName, Object properyValue) {
		// TODO
	}
}
