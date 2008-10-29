package org.openuss.framework.utilities;

/**
 * @author Ingo Dueppe
 */
public class ImageException extends RuntimeException {

	private static final long serialVersionUID = 638800911302851801L;

	public ImageException() {
		super();
	}

	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageException(String message) {
		super(message);
	}

	public ImageException(Throwable cause) {
		super(cause);
	}
}
