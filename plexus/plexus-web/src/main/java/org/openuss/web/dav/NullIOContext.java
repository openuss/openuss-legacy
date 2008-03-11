package org.openuss.web.dav;

import java.io.InputStream;
import java.sql.Timestamp;

import org.apache.commons.io.input.NullInputStream;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVConstants;

/**
 * A structure of file information with an empty InputStream and a context length of 0. 
 * Usable e.g. for HEAD-method
 *
 */
public class NullIOContext implements IOContext{
	
	/**
	 * The IOContext object, which is used to get the context
	 * null if even this should be emulated.
	 */
	protected IOContext context;
	
	/**
	 * Constructor for a completely virtual IOContext.
	 */
	public NullIOContext() {
		this(null);
	}
	
	/**
	 * @param context the IOContext object, which will be stored to get the context
	 * 				Provide null for a generic 0-byte information.
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
		return (context == null) ? null : context.getContentLanguage();
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
		return (context == null) ? WebDAVConstants.MIMETYPE_DEFAULT : context.getContentLanguage();
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
		return (context == null) ? WebDAVUtils.nowTimestamp() : context.getModificationTime();
	}
}
