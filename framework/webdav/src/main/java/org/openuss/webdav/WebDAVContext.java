package org.openuss.webdav;

import org.springframework.web.context.WebApplicationContext;

/**
 * A class for encapsulating WebDAV context information.
 * For use in WebDAVResources.
 */
public class WebDAVContext {
	/**
	 * Special value of maxFileSize that allows every file size.
	 */
	public static final long NO_MAX_FILESIZE = -1;
	protected WebApplicationContext wac;
	/**
	 * The maximum allowed file size for uploads.
	 */
	protected long maxFileSize;
	
	public WebDAVContext(WebApplicationContext wac, long maxFileSize) {
		this.wac = wac;
		this.maxFileSize = maxFileSize;
	}

	public WebApplicationContext getWAC() {
		return wac;
	}

	/**
	 * @return The maximal allowed file size of uploads.
	 */
	public long getMaxFileSize() {
		return maxFileSize;
	}

	/**
	 * Checks whether an upload of a specified size is allowed.
	 * 
	 * @param size The file size to check. -1 for undefined (=> result true).
	 * @return true iff uploading a file of size size is allowed.
	 */
	public boolean checkMaxFileSize(long size) {
		if (maxFileSize == NO_MAX_FILESIZE) {
			return true;
		}
		if (size == -1) {
			return true;
		}
		
		return size <= maxFileSize;
	}
}
