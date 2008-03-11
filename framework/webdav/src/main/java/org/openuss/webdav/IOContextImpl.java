package org.openuss.webdav;

import java.io.InputStream;
import java.sql.Timestamp;

/**
 * Implementation of {@link IOContext}.
 * 
 * (Uncommented because this is merely a struct).
 * @see WebDAVUtils#writeToClient(javax.servlet.http.HttpServletResponse, IOContext)
 * @see WebDAVUtils#readFromClient()
 */
public class IOContextImpl implements IOContext {
	private InputStream inputStream;
	private String contentLanguage;
	private long contentLength;
	private String contentType;
	private String eTag;
	private Timestamp modificationTime;
	
	public IOContextImpl() {
		this(null);
	}
	
	public IOContextImpl(InputStream inputStream) {
		this(inputStream, null, -1, null, null, null);
	}
	
	public IOContextImpl(InputStream inputStream, String contentLanguage,
			long contentLength, String contentType, String tag,
			Timestamp modificationTime) {
		super();
		this.inputStream = inputStream;
		this.contentLanguage = contentLanguage;
		this.contentLength = contentLength;
		this.contentType = contentType;
		eTag = tag;
		this.modificationTime = modificationTime;
	}
	public String getContentLanguage() {
		return contentLanguage;
	}
	public void setContentLanguage(String contentLanguage) {
		this.contentLanguage = contentLanguage;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getETag() {
		return eTag;
	}
	public void setETag(String tag) {
		eTag = tag;
	}
	public Timestamp getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Timestamp modificationTime) {
		this.modificationTime = modificationTime;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
