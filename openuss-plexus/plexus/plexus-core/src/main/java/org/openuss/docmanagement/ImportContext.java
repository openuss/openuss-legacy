package org.openuss.docmanagement.webdav;

import java.io.InputStream;

import javax.jcr.Item;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ImportContext implements IOContext {

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOContext#hasStream()
	 */
	public boolean hasStream() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO
		return null;
	}
	
	/**
	 * @return
	 */
	public long getContentLength() {
		// TODO
		return 0;
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
		// TODO
		return null;
	}

	/**
	 * @return
	 */
	public Item getImportRoot() {
		// TODO
		return null;
	}

	/**
	 * @return
	 */
	public String getMimeType() {
		// TODO
		return null;
	}

	/**
	 * @return
	 */
	public long getModificationTime() {
		// TODO
		return 0;
	}

	/**
	 * @param propertyName
	 * @return
	 */
	public Object getProperty(Object propertyName) {
		// TODO
		return null;
	}

	/**
	 * @return
	 */
	public String getSystemId() {
		// TODO
		return null;
	}
}
