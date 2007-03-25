package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public class DavException extends Exception {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 5132720312122649706L;

	
	private final int errorCode;
	private final String statusPhrase;
	
	/**
	 * Constructor.
	 * @param errorCode The error code of the exception.
	 */
	public DavException(int errorCode) {
		this(errorCode, null, null);
	}

	/**
	 * Constructor.
	 * @param errorCode The error code of the exception.
	 * @param statusPhrase The status phrase of the exception.
	 */
	public DavException(int errorCode, String statusPhrase) {
		this(errorCode, statusPhrase, null);
	}
	
	/**
	 * Constructor.
	 * @param errorCode The error code of the exception.
	 * @param statusPhrase The status phrase of the exception.
	 * @param cause The cause of the exception.
	 */
	public DavException(int errorCode, String statusPhrase, Throwable cause) {
		super(statusPhrase, cause);
		this.errorCode = errorCode;
		this.statusPhrase = statusPhrase;
	}
	
	/**
	 * Getter for the error code.
	 * @return The error code of the exception.
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Getter for the status phrase.
	 * @return The status phrase of the exception.
	 */
	public String getStatusPhrase() {
		return statusPhrase;
	}
}
