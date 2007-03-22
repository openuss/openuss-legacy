package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavException extends Exception {
	static final long serialVersionUID = -23;
	
	private final int errorCode;
	private final String statusPhrase;
	
	public DavException(int errorCode) {
		this(errorCode, null, null);
	}

	public DavException(int errorCode, String statusPhrase) {
		this(errorCode, statusPhrase, null);
	}
	
	public DavException(int errorCode, String statusPhrase, Throwable cause) {
		super(statusPhrase, cause);
		this.errorCode = errorCode;
		this.statusPhrase = statusPhrase;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public String getStatusPhrase() {
		return statusPhrase;
	}
}
