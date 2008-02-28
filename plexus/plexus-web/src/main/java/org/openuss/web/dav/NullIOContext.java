package org.openuss.web.dav;

import java.io.InputStream;
import java.sql.Timestamp;

import org.apache.commons.io.input.NullInputStream;
import org.openuss.webdav.IOContext;

/**
 * A structure of file information with an empty InputStream and a context length of 0. 
 * Usable e.g. for HEAD-method
 *
 */
public class NullIOContext implements IOContext{
	
	/**
	 * the IOContext object, which is used to get the context
	 */
	protected IOContext context;
	
	/**
	 * @param context the IOContext object, which will be stored to get the context
	 */
	public NullIOContext(IOContext context){
		this.context = context;
	}

	/**
	 * @return the IOContext object
	 */
	public IOContext getContext() {
		return context;
	}

	/**
	 * @param context the IOContext object to store
	 */
	public void setContext(IOContext context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.IOContext#getContentLanguage()
	 */
	public String getContentLanguage() {
		return context.getContentLanguage();
	}

	/** 
	 * @return returns an content length of 0
	 */
	public long getContentLength() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.IOContext#getContentType()
	 */
	public String getContentType() {
		return context.getContentType();
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.IOContext#getETag()
	 */
	public String getETag() {
		return context.getETag();
	}

	/**
	 * @return returns an empty InputStream
	 */
	public InputStream getInputStream() {
		return new NullInputStream(0);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.IOContext#getModificationTime()
	 */
	public Timestamp getModificationTime() {
		return context.getModificationTime();
	}
}
